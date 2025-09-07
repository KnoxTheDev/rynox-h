package dev.knoxy.rynox.client.handler.impl;

import dev.knoxy.rynox.client.event.impl.Render2DEvent;
import net.minecraft.client.MinecraftClient;
import dev.knoxy.rynox.client.event.EventListener;
import dev.knoxy.rynox.client.module.Module;
import net.minecraft.client.gui.screen.Screen;
import dev.knoxy.rynox.client.Rynox;
import dev.knoxy.rynox.client.event.impl.KeyEvent;
import dev.knoxy.rynox.client.handler.Handler;
import dev.knoxy.rynox.client.util.MC;
import org.lwjgl.glfw.GLFW;

public class KeybindHanlder implements MC, Handler {

    public static double scale;

    @Override
    public void register() {
        Rynox.Companion.getEventBus().registerListener(this);
    }

    @EventListener
    public void event(KeyEvent event) {
        if (this.getMc().currentScreen == null) {
            if (event.getKey() == ((Number)Rynox.Companion.getModuleManager().getMenu().getBind().getObject()).intValue()) {
                Rynox.Companion.getClickGUI().setInitPos(true);
                scale = this.getMc().getWindow().getScaleFactor();
                this.getMc().getWindow().setScaleFactor(2.0);
                this.getMc().setScreen(Rynox.Companion.getClickGUI());
            }
        }
        if (this.getMc().currentScreen == null) {
            for (Module module : Rynox.Companion.getModuleManager().getModules()) {
                if (module.getKeybind().isListening()) {
                    if (event.getAction() == 0) {
                        if (!module.isEnabled() || module.getKey() != event.getKey()) continue;
                        module.toggle();
                        continue;
                    }
                    if (event.getAction() != 1 || module.isEnabled() || module.getKey() != event.getKey()) continue;
                    module.toggle();
                    continue;
                }
                if (module.getKey() == -1 || event.getAction() != 1 || module.getKey() != event.getKey()) continue;
                module.toggle();
            }
        }
    }

    @EventListener
    public void event(Render2DEvent event) {
        if (getMc().currentScreen == null) {
            boolean b = GLFW.glfwGetMouseButton(getMc().getWindow().getHandle(), 0) == 1;
            boolean b2 = GLFW.glfwGetMouseButton(getMc().getWindow().getHandle(), 1) == 1;
            boolean b3 = GLFW.glfwGetMouseButton(getMc().getWindow().getHandle(), 2) == 1;
            for (Module module : Rynox.Companion.getModuleManager().getModules()) {
                toggle(module, b, b2, b3);
            }
        }
    }

    @Override
    public MinecraftClient getMc() {
        return MinecraftClient.getInstance();
    }

    void toggle(Module module, boolean b, boolean b2, boolean b3) {
        if (module.getKey() == 9999) {
            if (!module.isEnabled() && b) {
                module.toggle();
            } else if (!b) {
                module.toggle();
            }
        }
        if (module.getKey() == 9998) {
            if (!module.isEnabled() && b2) {
                module.toggle();
            } else if (!b2) {
                module.toggle();
            }
        }
        if (module.getKey() == 9997) {
            if (!module.isEnabled() && b3) {
                module.toggle();
            } else if (!b3) {
                module.toggle();
            }
        }
    }

    public static double getScale() {
        return scale;
    }

    public static void setScale(double s) {
        scale = s;
    }
}
