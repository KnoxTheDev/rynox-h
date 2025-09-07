package dev.knoxy.rynox.client.managers;

import dev.knoxy.rynox.client.Rynox;
import dev.knoxy.rynox.client.event.EventListener;
import dev.knoxy.rynox.client.event.Priority;
import dev.knoxy.rynox.client.event.impl.Render2DEvent;

public class RenderManager {
    public long ms;
    public long lastMs;

    public RenderManager() {
        Rynox.Companion.getEventBus().registerListener(this);
    }

    @EventListener(getPriority=Priority.HIGHEST)
    public void event(Render2DEvent event) {
        ms = System.currentTimeMillis() - lastMs;
        lastMs = System.currentTimeMillis();
        if (ms > 30) {
            ms = 0L;
        }
    }

    public float getMs() {
        return (float)this.ms * 0.005f;
    }
}
