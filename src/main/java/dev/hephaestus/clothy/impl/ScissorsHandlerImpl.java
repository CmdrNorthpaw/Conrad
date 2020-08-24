package dev.hephaestus.clothy.impl;

import com.google.common.collect.Lists;
import dev.hephaestus.clothy.ClothConfigInitializer;
import dev.hephaestus.clothy.api.ScissorsHandler;
import dev.hephaestus.math.impl.Rectangle;
import dev.hephaestus.math.api.Executor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import org.lwjgl.opengl.GL11;

import java.util.Collections;
import java.util.List;

@Environment(EnvType.CLIENT)
public final class ScissorsHandlerImpl implements ScissorsHandler {
    
    public static final ScissorsHandler INSTANCE = new ScissorsHandlerImpl();
    
    static {
        Executor.runIf(() -> FabricLoader.getInstance().isModLoaded("notenoughcrashes"), () -> () -> {
            try {
                Class.forName("fudge.notenoughcrashes.api.NotEnoughCrashesApi").getDeclaredMethod("onEveryCrash", Runnable.class).invoke(null, (Runnable) () -> {
                    try {
                        ScissorsHandler.INSTANCE.clearScissors();
                    } catch (Throwable t) {
                        ClothConfigInitializer.LOGGER.error("[ClothConfig] Failed clear scissors on game crash!", t);
                    }
                });
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }
    
    private final List<Rectangle> scissorsAreas;
    
    public ScissorsHandlerImpl() {
        this.scissorsAreas = Lists.newArrayList();
    }
    
    @Override
    public void clearScissors() {
        scissorsAreas.clear();
        applyScissors();
    }
    
    @Override
    public List<Rectangle> getScissorsAreas() {
        return Collections.unmodifiableList(scissorsAreas);
    }
    
    @Override
    public void scissor(Rectangle rectangle) {
        scissorsAreas.add(rectangle);
        applyScissors();
    }
    
    @Override
    public void removeLastScissor() {
        if (!scissorsAreas.isEmpty())
            scissorsAreas.remove(scissorsAreas.size() - 1);
        applyScissors();
    }
    
    @Override
    public void applyScissors() {
        if (!scissorsAreas.isEmpty()) {
            Rectangle r = scissorsAreas.get(0).clone();
            for (int i = 1; i < scissorsAreas.size(); i++) {
                r.setBounds(r.intersection(scissorsAreas.get(i)));
            }
            r.setBounds(Math.min(r.x, r.x + r.width), Math.min(r.y, r.y + r.height), Math.abs(r.width), Math.abs(r.height));
            Window window = MinecraftClient.getInstance().getWindow();
            double scaleFactor = window.getScaleFactor();
            GL11.glEnable(GL11.GL_SCISSOR_TEST);
            GL11.glScissor((int) (r.x * scaleFactor), (int) ((window.getScaledHeight() - r.height - r.y) * scaleFactor), (int) (r.width * scaleFactor), (int) (r.height * scaleFactor));
        } else
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }
}
