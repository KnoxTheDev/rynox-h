package dev.knoxy.rynox.client.setting.impl;

import dev.knoxy.rynox.client.setting.Setting;

import java.util.function.Predicate;

public class BooleanSetting extends Setting<Boolean> {

    public BooleanSetting(String name, Boolean value) {
        super(name, value);
    }

    @Override
    public BooleanSetting invokeVisibility(Predicate<Boolean> visible) {
        super.invokeVisibility(visible);
        return this;
    }

    @Override
    public BooleanSetting description(String description) {
        super.description(description);
        return this;
    }
}
