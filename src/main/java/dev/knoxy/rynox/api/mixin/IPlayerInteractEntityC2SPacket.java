package dev.knoxy.rynox.api.mixin;

import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={PlayerInteractEntityC2SPacket.class})
public interface IPlayerInteractEntityC2SPacket {
    @Accessor(value="entityId")
    int getEntityId();
}
