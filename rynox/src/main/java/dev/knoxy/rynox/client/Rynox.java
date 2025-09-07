package dev.knoxy.rynox.client;

import dev.knoxy.rynox.client.bypass.ScreenshareBypass;
import dev.knoxy.rynox.client.bypass.impl.LogBypass;
import dev.knoxy.rynox.client.event.EventBus;
import dev.knoxy.rynox.client.handler.Handler;
import dev.knoxy.rynox.client.handler.impl.KeybindHanlder;
import dev.knoxy.rynox.client.managers.*;
import dev.knoxy.rynox.client.shader.impl.GradientGlowShader;
import dev.knoxy.rynox.client.ui.Interface;
import dev.knoxy.rynox.client.ui.drawables.gui.screens.impl.ConfigScreen;
import dev.knoxy.rynox.client.util.impl.RenderUtil;
import net.fabricmc.api.ModInitializer;

import java.util.Arrays;
import java.util.List;

public class Rynox implements ModInitializer {

    public static Companion Companion = new Companion();
    private static final EventBus eventBus = new EventBus();
    private static final FontManager fontManager = new FontManager();
    private static final ModuleManager moduleManager = new ModuleManager();
    private static final ConfigManager configManager = new ConfigManager();
    private static final DamageManager damageManager = new DamageManager();
    private static final SocialsManager socialsManager = new SocialsManager();
    private static final RenderManager renderManager = new RenderManager();
    private static final AntiBotManager antiBotManager = new AntiBotManager();
    private static final ClickManager clickManager = new ClickManager();
    private static final ScreenshareBypass screenshareBypass = new ScreenshareBypass();
    private static final Interface clickGUI = new Interface();
    private static ConfigScreen configScreen;
    private static final TargetManager targetManager = new TargetManager();
    private static final RotationManager rotationManager = new RotationManager();
    public static boolean selfDestructed;

    @Override
    public void onInitialize() {
        List<Handler> handlers = Arrays.asList(new KeybindHanlder());
        handlers.forEach(Handler::register);
        RenderUtil.shader = new GradientGlowShader();
        screenshareBypass.setList(List.of(new LogBypass()));
    }

    public static class Companion {

        public static EventBus getEventBus() {
            return eventBus;
        }

        public static FontManager getFontManager() {
            return fontManager;
        }

        public static ModuleManager getModuleManager() {
            return moduleManager;
        }

        public static ConfigManager getConfigManager() {
            return configManager;
        }

        public static DamageManager getDamageManager() {
            return damageManager;
        }

        public static SocialsManager getSocialsManager() {
            return socialsManager;
        }

        public static RenderManager getRenderManager() {
            return renderManager;
        }

        public static AntiBotManager getAntiBotManager() {
            return antiBotManager;
        }

        public static ClickManager getClickManager() {
            return clickManager;
        }

        public static Interface getClickGUI() {
            return clickGUI;
        }

        public static ConfigScreen getConfigScreen() {
            return configScreen;
        }

        public static void setConfigScreen(ConfigScreen value) {
            configScreen = value;
        }

        public static TargetManager getTargetManager() {
            return targetManager;
        }

        public static RotationManager getRotationManager() {
            return rotationManager;
        }

        public static boolean getSelfDestructed() {
            return selfDestructed;
        }

        public static void setSelfDestructed(boolean value) {
            selfDestructed = value;
        }
    }
}
