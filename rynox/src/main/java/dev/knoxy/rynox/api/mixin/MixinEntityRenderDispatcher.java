package dev.knoxy.rynox.api.mixin;

import dev.knoxy.rynox.client.Prestige;
import dev.knoxy.rynox.client.event.impl.RenderHitboxEvent;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={EntityRenderDispatcher.class})
public class MixinEntityRenderDispatcher {
    @Inject(method={"renderHitbox"}, at={@At(value="RETURN")})
    private static void renderHitboxReturn(MatrixStack matrixStack, VertexConsumer vertexConsumer, Entity entity, float f, CallbackInfo callbackInfo) {
        if (Prestige.Companion.getSelfDestructed()) {
            return;
        }
        new RenderHitboxEvent(null).invoke();
    }

    @Inject(method={"renderHitbox"}, at={@At(value="HEAD")})
    private static void renderHitbox(MatrixStack matrixStack, VertexConsumer vertexConsumer, Entity entity, float f, CallbackInfo callbackInfo) {
        if (Prestige.Companion.getSelfDestructed()) {
            return;
        }
        new RenderHitboxEvent(entity).invoke();
    }
}
