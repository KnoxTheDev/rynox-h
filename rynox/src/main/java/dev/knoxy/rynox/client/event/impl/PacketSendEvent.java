package dev.knoxy.rynox.client.event.impl;

import dev.knoxy.rynox.client.event.Event;
import net.minecraft.network.packet.Packet;

public class PacketSendEvent extends Event {
    public Packet packet;

    public PacketSendEvent(Packet packet) {
        this.packet = packet;
    }

    public Packet getPacket() {
        return this.packet;
    }
}
