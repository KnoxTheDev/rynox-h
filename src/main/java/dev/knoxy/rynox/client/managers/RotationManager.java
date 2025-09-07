package dev.knoxy.rynox.client.managers;

import dev.knoxy.rynox.api.interfaces.IRotatable;
import dev.knoxy.rynox.client.Rynox;
import dev.knoxy.rynox.client.event.EventListener;
import dev.knoxy.rynox.client.event.impl.JumpEvent;
import dev.knoxy.rynox.client.event.impl.MoveEvent;
import dev.knoxy.rynox.client.event.impl.TickEvent;
import dev.knoxy.rynox.client.util.impl.Rotation;

import java.util.ArrayList;

public class RotationManager {
    public Rotation rotation;
    public ArrayList<IRotatable> rotations = new ArrayList();

    public RotationManager() {
        Rynox.Companion.getEventBus().registerListener(this);
    }


    @EventListener
    public void event(TickEvent event) {
        this.rotation = null;
        for (IRotatable iRotatable : this.rotations) {
            Rotation rotation = iRotatable.getRotation();
            if (rotation != null) {
                this.rotation = rotation;
            }
        }
    }

    @EventListener
    public void event(MoveEvent event) {
        if (this.rotation != null) {
            event.setYaw(this.rotation.getYaw());
            event.setPitch(this.rotation.getPitch());
        }
        event.setCancelled();
    }

    @EventListener
    public void event(JumpEvent event) {
        if (this.rotation != null) {
            Rotation rotation = this.rotation;
            event.setYaw(rotation.getYaw());
        }
        event.setCancelled();
    }

    public void addRotation(IRotatable iRotatable) {
        this.rotations.add(iRotatable);
    }

    public void removeRotation(IRotatable iRotatable) {
        this.rotations.remove(iRotatable);
    }
}
