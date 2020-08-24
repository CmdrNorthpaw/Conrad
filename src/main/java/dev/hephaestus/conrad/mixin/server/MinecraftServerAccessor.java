package dev.hephaestus.conrad.mixin.server;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.LevelStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Environment(EnvType.CLIENT)
@Mixin(MinecraftServer.class)
public interface MinecraftServerAccessor {
	@Accessor("session")
	LevelStorage.Session getSession();
}
