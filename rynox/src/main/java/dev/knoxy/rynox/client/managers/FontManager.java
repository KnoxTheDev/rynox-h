package dev.knoxy.rynox.client.managers;

import dev.knoxy.rynox.client.Rynox;
import dev.knoxy.rynox.client.ui.font.FontRenderer;
import net.minecraft.client.util.math.MatrixStack;

public class FontManager {
    public MatrixStack matrix;
    public FontRenderer fontRenderer = new FontRenderer(Rynox.class.getClassLoader().getResourceAsStream("assets/rynox/font/font.ttf"), 18);

    public MatrixStack getMatrixStack() {
        return this.matrix;
    }

    public FontRenderer getFontRenderer() {
        return this.fontRenderer;
    }

    public void setMatrixStack(MatrixStack matrixStack) {
        this.matrix = matrixStack;
    }

}
