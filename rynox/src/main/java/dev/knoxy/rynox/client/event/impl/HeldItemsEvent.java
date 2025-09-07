package dev.knoxy.rynox.client.event.impl;

import dev.knoxy.rynox.client.event.Event;
import net.minecraft.item.ItemStack;

public class HeldItemsEvent extends Event {
    public ItemStack item;

    public HeldItemsEvent(ItemStack item) {
        this.item = item;
    }

    public ItemStack getItem() {
        return this.item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }
}
