package dev.hephaestus.clothy.impl;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public interface KeyBindingHooks {
    void cloth_setId(String id);
}
