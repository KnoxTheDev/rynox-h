package dev.knoxy.rynox.api.mixin;

import dev.knoxy.rynox.client.Rynox;
import dev.knoxy.rynox.client.event.impl.ParticleEvent;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={ParticleManager.class})
public class MixinParticleManager {
    @Inject(method={"addParticle(Lnet/minecraft/client/particle/Particle;)V"}, at={@At(value="HEAD")}, cancellable=true)
    void addParticle(Particle particle, CallbackInfo callbackInfo) {
        if (Rynox.Companion.getSelfDestructed()) {
            return;
        }
        if (new ParticleEvent().invoke()) {
            callbackInfo.cancel();
        }
    }
}
