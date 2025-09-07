package dev.knoxy.rynox.client.event.impl;

import dev.knoxy.rynox.client.event.Event;
import net.minecraft.client.util.Window;

public class ResolutionChangeEvent extends Event {
    public Window window;

    public ResolutionChangeEvent(Window window) {
        this.window = window;
    }

    public Window getWindow() {
        return this.window;
    }
}
