package dev.knoxy.rynox.api.mixin;

import com.mojang.datafixers.util.Pair;
import dev.knoxy.rynox.client.Rynox;
import dev.knoxy.rynox.client.event.impl.FloatingItemEvent;
import dev.knoxy.rynox.client.event.impl.Render3DEvent;
import dev.knoxy.rynox.client.event.impl.ReachEvent;
import dev.knoxy.rynox.client.event.impl.TiltEvent;
import dev.knoxy.rynox.client.shader.GlProgram;
import dev.knoxy.rynox.client.util.impl.RenderHelper;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.resource.ResourceFactory;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.entity.projectile.ProjectileUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture.CAPTURE_FAILHARD;

import java.util.List;
import java.util.function.Consumer;

@Mixin(value = GameRenderer.class, priority = 999)
public class MixinGameRenderer {

    @Shadow
    public int floatingItemTimeLeft;

    @Shadow
    @Final
    public MinecraftClient client;

    public MinecraftClient mc;

    public MixinGameRenderer() {
        mc = MinecraftClient.getInstance();
    }

    /**
     * @author
     * @reason
     */
    @Overwrite
    public void updateTargetedEntity(float tickDelta) {
        Entity entity = client.getCameraEntity();
        if (entity == null || client.world == null) {
            return;
        }
        client.getProfiler().push("pick");
        client.targetedEntity = null;

        double reach = client.interactionManager.getReachDistance();
        boolean invoke = false;

        if (!Rynox.Companion.getSelfDestructed()) {
            ReachEvent event = new ReachEvent(0);
            if (event.invoke()) {
                reach += event.getReach();
            }
        }

        client.crosshairTarget = entity.raycast(reach, tickDelta, false);
        Vec3d cameraPosVec = entity.getCameraPosVec(tickDelta);
        boolean far = false;
        double entityRange = reach;
        double maxRange;

        if (!client.interactionManager.hasExtendedReach()) {
            if (entityRange > 3.0 && !invoke) {
                far = true;
            }
            maxRange = entityRange;
        } else {
            entityRange = (maxRange = 6.0);
        }

        double squaredDistanceTo = entityRange * entityRange;
        if (client.crosshairTarget != null) {
            squaredDistanceTo = client.crosshairTarget.getPos().squaredDistanceTo(cameraPosVec);
        }

        Vec3d vec = entity.getRotationVec(1.0F);
        EntityHitResult raycast = ProjectileUtil.raycast(
                entity,
                cameraPosVec,
                cameraPosVec.add(vec.x * maxRange, vec.y * maxRange, vec.z * maxRange),
                entity.getBoundingBox().stretch(vec.multiply(maxRange)).expand(1.0, 1.0, 1.0),
                e -> !e.isSpectator() && e.canHit(),
                squaredDistanceTo
        );

        if (raycast != null) {
            double d3 = cameraPosVec.squaredDistanceTo(raycast.getPos());
            if (far && d3 > 9.0) {
                client.crosshairTarget = BlockHitResult.createMissed(
                        raycast.getPos(),
                        Direction.getFacing(vec.x, vec.y, vec.z),
                        BlockPos.ofFloored(raycast.getPos())
                );
            } else if (d3 < squaredDistanceTo || client.crosshairTarget == null) {
                client.crosshairTarget = raycast;
                if (raycast.getEntity() instanceof LivingEntity || raycast.getEntity() instanceof ItemFrameEntity) {
                    client.targetedEntity = raycast.getEntity();
                }
            }
        }

        client.getProfiler().pop();
    }

    /**
     * Capture projection/modelview/position matrices used inside GameRenderer.renderWorld.
     * In 1.21.8 GameRenderer.renderWorld has the signature renderWorld(RenderTickCounter).
     *
     * We inject near the point where the renderer switches to drawing the hand (same conceptual spot
     * as before) and capture the local MatrixStack produced by GameRenderer.renderWorld so RenderHelper
     * can reuse the same matrices.
     */
    @Inject(
            method = "renderWorld",
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/GameRenderer;renderHand:Z", opcode = 180, ordinal = 0),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    void render3dHook(RenderTickCounter tickCounter, CallbackInfo ci, /* captured locals */ MatrixStack matrixStack) {
        if (!Rynox.Companion.getSelfDestructed()) {
            RenderHelper.getProjectionMatrix().set(RenderSystem.getProjectionMatrix());
            RenderHelper.getModelViewMatrix().set(RenderSystem.getModelViewMatrix());
            RenderHelper.getPositionMatrix().set(matrixStack.peek().getPositionMatrix());
        }
    }

    /**
     * Allow Render3DEvent to cancel world rendering.
     * Inject near the profiler swap used inside renderWorld.
     * renderWorld(RenderTickCounter) in 1.21.8: capture MatrixStack local to pass into the event.
     */
    @Inject(
            method = "renderWorld",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Profiler;swap(Ljava/lang/String;)V", ordinal = 1),
            cancellable = true,
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    void renderWorld(RenderTickCounter tickCounter, CallbackInfo callbackInfo, MatrixStack matrixStack) {
        if (!Rynox.Companion.getSelfDestructed()) {
            if (new Render3DEvent(matrixStack, tickCounter.getTickProgress(true)).invoke()) {
                callbackInfo.cancel();
            }
        }
    }

    /**
     * Adjust floatingItemTimeLeft during tick.
     */
    @Inject(
            method = "tick",
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/GameRenderer;floatingItemTimeLeft:I", ordinal = 1)
    )
    void adjustFloatingTimeLeft(CallbackInfo callbackInfo) {
        if (!Rynox.Companion.getSelfDestructed()) {
            FloatingItemEvent event = new FloatingItemEvent(0);
            event.invoke();
            floatingItemTimeLeft -= event.getSpeed();
        }
    }

    /**
     * In 1.21.8 the shader preload plumbing uses preloadPrograms(ResourceFactory).
     * The local list of shader program pairs (shader program, consumer) is captured and we add our GlProgram loaders.
     *
     * This replaces the old loadPrograms/stages approach and uses the ShaderProgram type directly.
     */
    @Inject(
            method = "preloadPrograms",
            at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 0),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    void loadAllTheShaders(ResourceFactory factory, CallbackInfo ci, List<Pair<ShaderProgram, Consumer<ShaderProgram>>> shadersToLoad) {
        // GlProgram.forEachProgram supplies loaders (left: factory -> ShaderProgram, right: consumer)
        GlProgram.forEachProgram(loader -> shadersToLoad.add(new Pair<>(loader.getLeft().apply(factory), loader.getRight())));
    }

    /**
     * Cancel tilt behavior when hurt if our event returns true.
     */
    @Inject(at = @At("HEAD"), method = "tiltViewWhenHurt", cancellable = true)
    void tiltViewWhenHurt(MatrixStack matrixStack, float tickProgress, CallbackInfo callbackInfo) {
        if (!Rynox.Companion.getSelfDestructed()) {
            if (new TiltEvent().invoke()) {
                callbackInfo.cancel();
            }
        }
    }
}
