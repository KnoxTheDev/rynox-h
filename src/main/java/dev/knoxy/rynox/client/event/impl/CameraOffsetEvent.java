package dev.knoxy.rynox.client.event.impl;

import dev.knoxy.rynox.client.event.Event;

public class CameraOffsetEvent extends Event {
    public double x;
    public double y;
    public double z;

    public CameraOffsetEvent(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return this.z;
    }

    public void setZ(double z) {
        this.z = z;
    }
}
