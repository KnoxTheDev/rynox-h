package dev.knoxy.rynox.client.event.impl;

import dev.knoxy.rynox.client.event.Event;

public class KeyEvent extends Event {
    public int key;
    public int action;

    public KeyEvent(int n, int n2) {
        this.key = n;
        this.action = n2;
    }

    public int getKey() {
        return this.key;
    }

    public int getAction() {
        return this.action;
    }
}
