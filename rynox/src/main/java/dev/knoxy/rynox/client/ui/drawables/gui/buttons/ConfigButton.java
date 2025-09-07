package dev.knoxy.rynox.client.ui.drawables.gui.buttons;

import dev.knoxy.rynox.client.Rynox;
import dev.knoxy.rynox.client.ui.drawables.Drawable;
import dev.knoxy.rynox.client.ui.drawables.gui.screens.impl.ConfigScreen;
import dev.knoxy.rynox.client.ui.font.FontRenderer;
import dev.knoxy.rynox.client.util.impl.RenderHelper;
import java.awt.Color;

import dev.knoxy.rynox.client.util.impl.RenderUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL11;

public class ConfigButton extends Drawable {
    public String name;
    public String description;
    public String owner;
    public Identifier downloadTexture;

    public ConfigButton(String string, String string2, String string3) {
        super(0, 0, 180, 100, "");
        this.name = string;
        this.description = string2;
        this.owner = string3;
        this.downloadTexture = new Identifier("rynox", "icons/download.png");
    }

    @Override
    public void render(int n, int n2, float f, float f2) {
        RenderUtil.setScissorRegion(getX(), ConfigScreen.getMinY() + 5, getX() + getWidth() - 2.5f, ConfigScreen.getMaxY() - 5);
        RenderUtil.renderRoundedRect(getX() - 0.5f, getY() - 0.5f, getX() + getWidth() + 0.5f, getY() + getHeight() + 0.5f, RenderUtil.getColor(0, 0.2f), 10);
        RenderUtil.renderRoundedRect(getX(), getY(), getX() + getWidth(), getY() + getHeight(), RenderUtil.getColor(3, 1), 10);
        RenderUtil.renderCircularGradient(getX(), getY(), getX() + getWidth(), getY() + 20, RenderUtil.getColor(5, 1), 10);
        RenderUtil.renderColoredQuad(getX(), getY() + 20, getX() + getWidth(), getY() + 25, true, true, false, false, 0.4f);
        FontRenderer font = Rynox.Companion.getFontManager().getFontRenderer();
        font.drawString(name, getX() + 5, getY() + 10 - font.getStringHeight() / 2, Color.WHITE);
        MatrixStack matrixStack = RenderHelper.getMatrixStack();
        matrixStack.push();
        matrixStack.scale(0.8f, 0.8f, 0.8f);
        String[] stringArray = description.split(" ");
        float n8 = 0;
        float n9 = 0;
        int bruh = 0;
        for (String string : stringArray) {
            float f6 = font.getStringWidth(string) * 0.8f;
            if (n8 + f6 > 170) {
                n8 = 0;
                bruh = 0;
                n9++;
            } else if(bruh != 0) {
                n8 += 1.6f;
            }
            font.drawString(string, (getX() + 5 + n8) / 0.8f, (getY() + 25.1f + n9 * 10) / 0.8f, RenderUtil.getColor(0.7f, 1));
            n8 += f6;
            bruh++;
        }
        matrixStack.pop();
        Color themeColor = Color.WHITE;
        matrixStack.push();
        matrixStack.scale(0.6f, 0.6f, 0.6f);;
        font.drawString(owner, (getX() + 2.5f + (5 + font.getStringWidth(name))) / 0.6f, (getY() + 14 - font.getStringHeight() / 2 * 0.6f) / 0.6f, themeColor);
        matrixStack.pop();
        RenderUtil.renderColoredRoundedRect(getX() + getWidth() - 25, getY() + getHeight() - 25, getX() + getWidth() - 5, getY() + getHeight() - 5, 4, RenderUtil.getColor(themeColor, f2), RenderUtil.getColor(themeColor.darker(), f2), RenderUtil.getColor(themeColor.darker(), f2), RenderUtil.getColor(themeColor.darker().darker(), f2));
        RenderUtil.renderTexturedRect(getX() + getWidth() - 22.5f, getY() + getHeight() - 22.5f, 15, 15, downloadTexture, RenderUtil.getColor(241, 0.7f));
        if (isInsideCreate(n, n2)) {
            RenderUtil.renderRoundedRect(getX() + getWidth() - 25, getY() + getHeight() - 25, getX() + getWidth() - 5, getY() + getHeight() - 5, RenderUtil.getColor(0, 0.3f), 4);
        }
        GL11.glDisable(3089);
    }

    @Override
    public void mouseClicked(double d, double d2, int n) {
        if (n == 0) {
            if (isInsideCreate((int)d, (int)d2)) {
                // Config loading is not supported in this version
            }
        }
    }

    boolean isInsideCreate(int n, int n2) {
        return n >= getX() + getWidth() - 25 && n <= getX() + getWidth() - 5 && n2 >= getY() + getHeight() - 25 && n2 <= getY() + getHeight() - 5;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getOwner() {
        return this.owner;
    }
}
