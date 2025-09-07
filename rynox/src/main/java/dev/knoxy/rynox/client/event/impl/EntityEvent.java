package dev.knoxy.rynox.client.event.impl;

import dev.knoxy.rynox.client.event.Event;
import dev.knoxy.rynox.client.event.Phase;
import net.minecraft.entity.Entity;

public class EntityEvent extends Event {
    public Entity entity;

    public EntityEvent(Entity entity, Phase phase) {
        super(phase);
        this.entity = entity;
    }

    public Entity getEntity() {
        return this.entity;
    }
}
