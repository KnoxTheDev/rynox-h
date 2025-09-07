package dev.knoxy.rynox.client.event.impl;

import dev.knoxy.rynox.client.event.Event;

public class ReachEvent extends Event {
    public float reach;

    public ReachEvent(float reach) {
        this.reach = reach;
    }

    public float getReach() {
        return this.reach;
    }

    public void setReach(float reach) {
        this.reach = reach;
    }
}
