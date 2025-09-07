package dev.knoxy.rynox.client.event.impl;

import dev.knoxy.rynox.client.event.Event;

public class FloatingItemEvent extends Event {
    public int speed;

    public FloatingItemEvent(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return this.speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
