package dev.knoxy.rynox.client.module.impl.combat;
import net.minecraft.registry.tag.ItemTags;

import dev.knoxy.rynox.client.event.EventListener;
import dev.knoxy.rynox.client.event.impl.PacketSendEvent;
import dev.knoxy.rynox.client.module.Category;
import dev.knoxy.rynox.client.module.Module;
import dev.knoxy.rynox.client.util.impl.InventoryUtil;
import dev.knoxy.rynox.client.util.impl.PacketUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.registry.tag.ItemTags;

public class TotemHit extends Module {

    public TotemHit() {
        super("Totem Hit", Category.Combat, "More knockback when hitting players with totems");
    }

    @EventListener
    public void event(PacketSendEvent event) {
        if (event.getPacket() instanceof PlayerInteractEntityC2SPacket packet) {
            if (packet.getType() == PlayerInteractEntityC2SPacket.InteractionType.ATTACK) {
                Entity entity = getMc().world.getEntityById(packet.getEntityId());
                if (entity == null) {
                    return;
                }
                if (entity instanceof PlayerEntity && getMc().player.getMainHandStack().getItem() == Items.TOTEM_OF_UNDYING) {
                    for (int i = 0; i < 9; ++i) {
                        ItemStack itemStack = getMc().player.getInventory().getStack(i);
                        if (itemStack.isIn(ItemTags.SWORDS)) {
                            int slot = getMc().player.getInventory().selectedSlot;
                            InventoryUtil.INSTANCE.setCurrentSlot(i);
                            PacketUtil.INSTANCE.sendPacket(event.getPacket());
                            InventoryUtil.INSTANCE.setCurrentSlot(slot);
                            return;
                        }
                    }
                }
            }
        }
    }
}
