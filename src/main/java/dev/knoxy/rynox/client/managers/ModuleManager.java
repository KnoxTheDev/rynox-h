package dev.knoxy.rynox.client.managers;

import dev.knoxy.rynox.client.module.Module;
import dev.knoxy.rynox.client.module.impl.combat.*;

import java.util.ArrayList;
import java.util.Arrays;

public class ModuleManager {

    public ArrayList<Module> modules = new ArrayList();

    public ArrayList<Module> getModules() {
        return this.modules;
    }

    public ModuleManager() {
        modules.addAll(Arrays.asList(new AimAssist(), new AnchorExploder(), new AnchorPlacer(), new AntiBot(), new AutoCrystal(), new AutoHitCrystal(), new AutoShieldBreaker(), new AutoTotem(), new GhostObby(), new Hitboxes(), new NoMissDelay(), new PredictDoubleHand(), new SilentAim(), new TotemHit(), new Triggerbot(), new AutoClicker()));
    }
}
