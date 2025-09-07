package dev.knoxy.rynox.client.event;

import dev.knoxy.rynox.client.Rynox;

public class Event {
    public Phase phase;
    public boolean cancelled;

    public Event(Phase phase) {
        this.phase = phase;
    }

    public Event() {
        this(Phase.NONE);
    }

    public boolean invoke() {
        return Rynox.Companion.getEventBus().invoke(this);
    }

    public Phase getPhase() {
        return this.phase;
    }

    public void setCancelled() {
        this.cancelled = true;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }
}
