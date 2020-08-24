package dev.hephaestus.clothy.impl.builders;

import dev.hephaestus.clothy.gui.entries.ColorEntry;
import dev.hephaestus.math.impl.Color;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import dev.hephaestus.conrad.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class ColorFieldBuilder extends FieldBuilder<Color, ColorEntry> {
    
    private final Color value;
    private boolean alpha = false;
    
    public ColorFieldBuilder(Text resetButtonKey, Text fieldNameKey, Color value) {
        super(resetButtonKey, fieldNameKey);
        this.value = value;
    }

    public ColorFieldBuilder setAlphaMode(boolean withAlpha) {
        this.alpha = withAlpha;
        return this;
    }
    
    @NotNull
    @Override
    public ColorEntry build() {
        ColorEntry entry = new ColorEntry(getFieldNameKey(), value, getResetButtonKey(), defaultValue, saveConsumer, null, isRequireRestart());
        if (this.alpha) {
            entry.withAlpha();
        } else {
            entry.withoutAlpha();
        }
        entry.setTooltipSupplier(() -> tooltipSupplier.apply(entry.getValue()));
        if (errorSupplier != null)
            entry.setErrorSupplier(() -> errorSupplier.apply(entry.getValue()));
        return entry;
    }
    
}