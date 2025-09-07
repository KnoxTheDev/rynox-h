package dev.knoxy.rynox.client.module.impl.combat;

import dev.knoxy.rynox.client.Rynox;
import dev.knoxy.rynox.client.event.EventListener;
import dev.knoxy.rynox.client.event.impl.TickEvent;
import dev.knoxy.rynox.client.module.Category;
import dev.knoxy.rynox.client.module.Module;
import dev.knoxy.rynox.client.setting.impl.IntSetting;
import dev.knoxy.rynox.client.setting.impl.ModeSetting;
import dev.knoxy.rynox.client.util.impl.TimerUtil;

public class AutoClicker extends Module {

    public ModeSetting mode;
    public IntSetting delay;
    public TimerUtil timer;

    public AutoClicker() {
        super("Auto Clicker", Category.Combat, "Automatically clicks your mouse");
        mode = this.setting("Mode", "Left", new String[]{"Left", "Right"});
        delay = this.setting("Interval", 100, 0, 10000);
        timer = new TimerUtil();
    }

    @EventListener
    public void event(TickEvent event) {
        if (getMc().currentScreen != null || Rynox.Companion.getClickManager().click() || !timer.delay(this.delay.getObject())) {
            return;
        }
        Rynox.Companion.getClickManager().setClick(mode.getObject().equals("Left") ? 0 : 1, 0);
    }
}
