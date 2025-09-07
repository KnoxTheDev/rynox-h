package dev.knoxy.rynox.client.ui.drawables.gui.screens.impl;

import dev.knoxy.rynox.client.module.impl.combat.AutoTotem;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.player.PlayerEntity;

public class AutoTotemScreen extends InventoryScreen {

    /*boolean handleHotbarKeyPressed(int n, int n2) {
        return false;
    }*/

    public AutoTotemScreen(AutoTotem autoTotem, PlayerEntity playerEntity) {
        super(playerEntity);
    }
}
