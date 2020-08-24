package dev.hephaestus.conrad.mixin.client;

import dev.hephaestus.conrad.api.Config;
import dev.hephaestus.conrad.api.networking.NetworkSerializerRegistry;
import dev.hephaestus.conrad.impl.common.config.ValueContainer;
import dev.hephaestus.conrad.impl.common.keys.KeyRing;
import dev.hephaestus.conrad.impl.common.keys.ValueKey;
import dev.hephaestus.conrad.impl.common.networking.packets.all.ConfigValuePacket;
import dev.hephaestus.conrad.impl.common.util.ReflectionUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Environment(EnvType.CLIENT)
@Mixin(ClientPlayNetworkHandler.class)
public class SendConfigValues {
	@Inject(method = "onGameJoin", at = @At("TAIL"))
	private void sendConfigValues(GameJoinS2CPacket packet, CallbackInfo ci) {
		for (Map.Entry<ValueKey, Object> value : ValueContainer.ROOT) {
			if (NetworkSerializerRegistry.getWriter(value.getValue().getClass()) != null && ReflectionUtil.getRoot(KeyRing.get(value.getKey().getConfig())).getAnnotation(Config.SaveType.class).value() == Config.SaveType.Type.USER) {
				new ConfigValuePacket(ConfigValuePacket.INFO, value.getKey(), value.getValue()).send();
			}
		}
	}
}
