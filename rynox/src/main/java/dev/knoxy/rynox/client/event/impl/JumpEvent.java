package dev.knoxy.rynox.client.event.impl;

import dev.knoxy.rynox.client.event.Event;

public class JumpEvent extends Event {
    public float yaw;

    public JumpEvent(float yaw) {
        this.yaw = yaw;
    }

    public float getYaw() {
        return this.yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }
}
