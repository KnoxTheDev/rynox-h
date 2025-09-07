package dev.knoxy.rynox.client.event.impl;

import dev.knoxy.rynox.client.event.Event;
import net.minecraft.client.util.math.MatrixStack;

public class Render2DEvent extends Event {
    public MatrixStack matrixStack;
    public int scaledWidth;
    public int scaledHeight;

    public Render2DEvent(MatrixStack matrixStack, int scaledWidth, int scaledHeight) {
        this.matrixStack = matrixStack;
        this.scaledWidth = scaledWidth;
        this.scaledHeight = scaledHeight;
    }

    public MatrixStack getMatrixStack() {
        return this.matrixStack;
    }

    public int getScaledWidth() {
        return this.scaledWidth;
    }

    public int getScaledHeight() {
        return this.scaledHeight;
    }
}
