package dev.knoxy.rynox.client.ui.drawables.gui.screens.impl;

import dev.knoxy.rynox.client.Rynox;
import dev.knoxy.rynox.client.ui.drawables.gui.buttons.ConfigButton;
import dev.knoxy.rynox.client.ui.drawables.gui.field.CustomTextField;
import dev.knoxy.rynox.client.ui.drawables.gui.screens.DrawableScreen;
import dev.knoxy.rynox.client.ui.font.FontRenderer;
import dev.knoxy.rynox.client.util.impl.RenderHelper;
import java.awt.Color;
import java.util.ArrayList;

import dev.knoxy.rynox.client.util.impl.RenderUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

public class ConfigScreen extends DrawableScreen {

    public CustomTextField name;
    public CustomTextField description;
    public Identifier logo = new Identifier("rynox", "icons/logo.png");
    public static ArrayList<ConfigButton> configComponents = new ArrayList<>();
    public static float maxY;
    public static float minY;
    public float screenWidth = 600;
    public float screenHeight = 400;
    public static String response = "";
    public float scroll;
    public float idk;
    public static long time;

    public ConfigScreen() {
        name = new CustomTextField("Config Name (max 20.)");
        description = new CustomTextField("Config Description (max 50.)");
    }

    public void render(DrawContext drawContext, int n, int n2, float f) {
        RenderHelper.setMatrixStack(drawContext.getMatrices());
        Rynox.Companion.getFontManager().setMatrixStack(drawContext.getMatrices());
        FontRenderer font = Rynox.Companion.getFontManager().getFontRenderer();
        float f2 = width / 2 - screenWidth / 2;
        float f3 = height / 2 - screenHeight / 2;
        RenderUtil.renderRoundedRect(f2, f3, f2 + screenWidth, f3 + screenHeight, RenderUtil.getColor(0, 0.8f), 9);
        RenderUtil.renderRoundedRect(f2, f3, f2 + screenWidth, f3 + screenHeight, RenderUtil.getColor(0, 1), 10);
        RenderUtil.renderCircularGradient(f2, f3, f2 + screenWidth, f3, RenderUtil.getColor(3, 1), 10);
        RenderUtil.renderColoredQuad(f2, f3 + 25, f2 + screenWidth, f3 + 30, true, true, false, false, 0.4f);
        RenderUtil.renderTexturedRect(f2 + 5, f3 + 2.5f, 20, 20, logo, Color.WHITE);
        RenderUtil.renderCrossed(f2 + screenWidth - 17.5f, f3 + 8.75f, f2 + screenWidth - 10, f3 + 16.25f, Color.WHITE);
        RenderUtil.renderColoredQuad(f2, f3 + screenHeight - 30, f2 + screenWidth, f3 + screenHeight - 25, false, false, true, true, 0.4f);
        RenderUtil.renderColoredEllipseBorder(f2, f3 + screenHeight - 25, f2 + screenWidth, f3 + screenHeight, RenderUtil.getColor(3, 1), 10);
        if (!response.isEmpty()) {
            font.drawString(response, f2 + screenWidth / 2 - font.getStringWidth(response) / 2, f3 + screenHeight + 10, Color.WHITE);
            if (System.currentTimeMillis() - time > 10000L) {
                response = "";
            }
        }
        minY = f3 + 25;
        maxY = f3 + screenHeight - 25;
    }

    public boolean mouseClicked(double d, double d2, int n) {
        if (n == 0) {
            if (isInsideCross((int)d, (int)d2)) {
                MinecraftClient.getInstance().setScreen(Rynox.Companion.getClickGUI());
            }
        }
        return super.mouseClicked(d, d2, n);
    }

    public boolean shouldCloseOnEsc() {
        MinecraftClient.getInstance().setScreen(Rynox.Companion.getClickGUI());
        return false;
    }

    public boolean mouseScrolled(double d, double d2, double d3) {
        scroll += (float) (d3 * 20);
        return super.mouseScrolled(d, d2, d3);
    }

    public boolean charTyped(char c, int n) {
        name.charTyped(c);
        description.charTyped(c);
        return super.charTyped(c, n);
    }

    public boolean keyPressed(int n, int n2, int n3) {
        name.keyPressed(n);
        description.keyPressed(n);
        return super.keyPressed(n, n2, n3);
    }

    public boolean shouldPause() {
        return false;
    }

    boolean isInsideCross(int n, int n2) {
        return n >= width / 2 - screenWidth / 2 + screenWidth - 17.5f && n <= width / 2 - screenWidth / 2 + screenWidth - 10 && n2 >= height / 2 - screenHeight / 2 + 8.75f && n2 <= height / 2 - screenHeight / 2 + 16.25f;
    }

    public static float getMaxY() {
        return maxY;
    }

    public static float getMinY() {
        return minY;
    }

    public static void setResponse(String string) {
        response = string;
    }

    public static void setTime(long l) {
        time = l;
    }
}
