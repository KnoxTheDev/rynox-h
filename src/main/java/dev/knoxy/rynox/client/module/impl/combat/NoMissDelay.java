package dev.knoxy.rynox.client.module.impl.combat;

import dev.knoxy.rynox.client.event.EventListener;
import dev.knoxy.rynox.client.event.impl.LastAttackedEvent;
import dev.knoxy.rynox.client.event.impl.SwingHandEvent;
import dev.knoxy.rynox.client.module.Category;
import dev.knoxy.rynox.client.module.Module;
import net.minecraft.item.AxeItem;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.hit.HitResult;

public class NoMissDelay extends Module {

    public NoMissDelay() {
        super("No Miss Delay", Category.Combat, "Removes the sword delay after missing");
    }

    @EventListener
    public void event(SwingHandEvent event) {
        if (getMc().crosshairTarget != null) {
            if (getMc().player.getMainHandStack().isIn(ItemTags.SWORDS) || getMc().player.getMainHandStack().getItem() instanceof AxeItem) {
                if (getMc().crosshairTarget.getType() == HitResult.Type.MISS) {
                    event.setCancelled();
                }
            }
        }
    }

    @EventListener
    public void event(LastAttackedEvent event) {
        if (getMc().crosshairTarget != null) {
            if (getMc().player.getMainHandStack().isIn(ItemTags.SWORDS) || getMc().player.getMainHandStack().getItem() instanceof AxeItem) {
                if (getMc().crosshairTarget.getType() == HitResult.Type.MISS) {
                    event.setCancelled();
                }
            }
        }
    }
}
