package dev.hephaestus.clothy.impl.builders;

import dev.hephaestus.clothy.gui.entries.FloatListEntry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.Text;
import dev.hephaestus.conrad.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class FloatFieldBuilder extends FieldBuilder<Float, FloatListEntry> {
    
    private Consumer<Float> saveConsumer = null;
    private Function<Float, Optional<List<Text>>> tooltipSupplier = f -> Optional.empty();
    private final float value;
    private Float min = null, max = null;
    
    public FloatFieldBuilder(Text resetButtonKey, Text fieldNameKey, float value) {
        super(resetButtonKey, fieldNameKey);
        this.value = value;
    }
    
    public FloatFieldBuilder setErrorSupplier(Function<Float, Optional<Text>> errorSupplier) {
        this.errorSupplier = errorSupplier;
        return this;
    }
    
    public FloatFieldBuilder requireRestart() {
        requireRestart(true);
        return this;
    }
    
    public FloatFieldBuilder setSaveConsumer(Consumer<Float> saveConsumer) {
        this.saveConsumer = saveConsumer;
        return this;
    }
    
    public FloatFieldBuilder setDefaultValue(Supplier<Float> defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }
    
    public FloatFieldBuilder setDefaultValue(float defaultValue) {
        this.defaultValue = () -> defaultValue;
        return this;
    }
    
    public FloatFieldBuilder setTooltipSupplier(Function<Float, Optional<List<Text>>> tooltipSupplier) {
        this.tooltipSupplier = tooltipSupplier;
        return this;
    }
    
    public FloatFieldBuilder setTooltipSupplier(Supplier<Optional<List<Text>>> tooltipSupplier) {
        this.tooltipSupplier = f -> tooltipSupplier.get();
        return this;
    }
    
    public FloatFieldBuilder setTooltip(Optional<List<Text>> tooltip) {
        this.tooltipSupplier = f -> tooltip;
        return this;
    }
    
    public FloatFieldBuilder setMin(float min) {
        this.min = min;
        return this;
    }
    
    public FloatFieldBuilder setMax(float max) {
        this.max = max;
        return this;
    }
    
    public FloatFieldBuilder removeMin() {
        this.min = null;
        return this;
    }
    
    public FloatFieldBuilder removeMax() {
        this.max = null;
        return this;
    }
    
    @NotNull
    @Override
    public FloatListEntry build() {
        FloatListEntry entry = new FloatListEntry(getFieldNameKey(), value, getResetButtonKey(), defaultValue, saveConsumer, null, isRequireRestart());
        if (min != null)
            entry.setMinimum(min);
        if (max != null)
            entry.setMaximum(max);
        entry.setTooltipSupplier(() -> tooltipSupplier.apply(entry.getValue()));
        if (errorSupplier != null)
            entry.setErrorSupplier(() -> errorSupplier.apply(entry.getValue()));
        return entry;
    }
    
}
