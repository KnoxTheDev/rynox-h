package dev.knoxy.rynox.client.managers;

import dev.knoxy.rynox.client.Rynox;
import dev.knoxy.rynox.client.module.Module;
import dev.knoxy.rynox.client.setting.Setting;
import dev.knoxy.rynox.client.setting.impl.*;

import java.awt.*;

public class ConfigManager {

    public void load(String string) {
        String[] split = string.split(";");
        for (String s : split) {
            if (!s.isEmpty()) {
                String[] split2 = s.split(",");
                for (Module module : Rynox.Companion.getModuleManager().getModules()) {
                    if (!module.getName().equals(split2[0])) continue;
                    for (Setting<?> setting : module.getModuleSettings()) {
                        if (!setting.getName().equals(split2[1])) continue;
                        if (split2[1].equals("Enabled")) {
                            if (split2[2].equals("true") && module.isEnabled()) {
                                module.toggle();
                                continue;
                            }
                            if (module.isEnabled()) {
                                module.toggle();
                                continue;
                            }
                        }
                        loadSetting(setting, split2[2]);
                    }
                    for (Setting<?> setting : module.getSettings()) {
                        if (setting.getName().equals(split2[1])) {
                            loadSetting(setting, split2[2]);
                        }
                    }
                }
            }
        }
    }

    private String getValue(Setting setting) {
        if (setting instanceof ColorSetting setting1) {
            Color color = setting1.getObject();
            return color.getRed() + "." + color.getGreen() + "." + color.getBlue() + "." + color.getAlpha();
        }
        if (setting instanceof BindSetting setting1) {
            return setting1.getObject() + "." + setting1.isListening();
        }
        if (setting instanceof MultipleSetting setting1) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String string : setting1.getOptions()) {
                stringBuilder.append(string).append(".").append(setting1.getValue(string)).append("&");
            }
            return stringBuilder.toString();
        }
        if (setting instanceof DragSetting setting1) {
            return setting1.getFirst() + "|" + setting1.getSecond();
        }
        return setting.getObject().toString();
    }

    public String getSettings() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Module module : Rynox.Companion.getModuleManager().getModules()) {
            for (Setting<?> setting : module.getModuleSettings()) {
                stringBuilder.append(module.getName()).append(",").append(setting.getName()).append(",").append(getValue(setting)).append(";");
            }
            for (Setting<?> setting : module.getSettings()) {
                stringBuilder.append(module.getName()).append(",").append(setting.getName()).append(",").append(getValue(setting)).append(";");
            }
        }
        return stringBuilder.toString();
    }

    public void loadSetting(Setting setting, Object value) {
        if (setting instanceof BooleanSetting setting1) {
            setting1.invokeValue(Boolean.parseBoolean(value.toString()));
        }
        if (setting instanceof BindSetting setting1) {
            String[] split = value.toString().split("\\.");
            setting1.invokeValue(Integer.parseInt(split[0]));
            setting1.setListening(Boolean.parseBoolean(split[1]));
        }
        if (setting instanceof ColorSetting setting1) {
            String[] split = value.toString().split("\\.");
            setting1.invokeValue(new Color(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]), Integer.parseInt(split[3])));
        }
        if (setting instanceof FloatSetting setting1) {
            setting1.invokeValue(Float.parseFloat(value.toString()));
        }
        if (setting instanceof IntSetting setting1) {
            setting1.invokeValue(Integer.parseInt(value.toString()));
        }
        if (setting instanceof ModeSetting setting1) {
            setting1.invokeValue(value.toString());
        }
        if (setting instanceof MultipleSetting setting1) {
            String[] split = value.toString().split("&");
            for (String s : split) {
                String[] split2 = s.split("\\.");
                setting1.invokeValue(split2[0], Boolean.parseBoolean(split2[1]));
            }
        }
        if (setting instanceof DragSetting setting1) {
            String[] split = value.toString().split("\\|");
            setting1.setFirst(Float.parseFloat(split[0]));
            setting1.setSecond(Float.parseFloat(split[1]));
        }
    }
}
