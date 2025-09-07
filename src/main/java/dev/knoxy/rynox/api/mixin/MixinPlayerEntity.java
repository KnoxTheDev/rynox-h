package dev.knoxy.rynox.api.mixin;

import dev.knoxy.rynox.client.Rynox;
import dev.knoxy.rynox.client.event.impl.LastAttackedEvent;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={PlayerEntity.class})
public class MixinPlayerEntity {
    @Inject(method={"resetLastAttackedTicks"}, at={@At(value="HEAD")}, cancellable=true)
    void resetLastAttackedTicks(CallbackInfo callbackInfo) {
        if (Rynox.Companion.getSelfDestructed()) {
            return;
        }
        if (new LastAttackedEvent().invoke()) {
            callbackInfo.cancel();
        }
    }
}
