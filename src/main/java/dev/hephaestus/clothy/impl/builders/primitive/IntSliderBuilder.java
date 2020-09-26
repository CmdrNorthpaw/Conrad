package dev.hephaestus.clothy.impl.builders.primitive;

import dev.hephaestus.clothy.impl.gui.entries.IntegerSliderEntry;
import dev.hephaestus.clothy.impl.builders.FieldBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class IntSliderBuilder extends FieldBuilder<Integer, IntegerSliderEntry> {
    
    private Consumer<Integer> saveConsumer = null;
    private Function<Integer, Optional<List<Text>>> tooltipSupplier = i -> Optional.empty();
    private final int value;
    private int max;
    private int min;
    private Function<Integer, Text> textGetter = null;
    
    public IntSliderBuilder(Text resetButtonKey, Text fieldNameKey, int value, int min, int max) {
        super(resetButtonKey, fieldNameKey);
        this.value = value;
        this.max = max;
        this.min = min;
    }
    
    public IntSliderBuilder setErrorSupplier(Function<Integer, Optional<Text>> errorSupplier) {
        this.errorSupplier = errorSupplier;
        return this;
    }
    
    public IntSliderBuilder requireRestart() {
        requireRestart(true);
        return this;
    }
    
    public IntSliderBuilder setTextGetter(Function<Integer, Text> textGetter) {
        this.textGetter = textGetter;
        return this;
    }
    
    public IntSliderBuilder setSaveConsumer(Consumer<Integer> saveConsumer) {
        this.saveConsumer = saveConsumer;
        return this;
    }
    
    public IntSliderBuilder setDefaultValue(Supplier<Integer> defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }
    
    public IntSliderBuilder setDefaultValue(int defaultValue) {
        this.defaultValue = () -> defaultValue;
        return this;
    }
    
    public IntSliderBuilder setTooltipSupplier(Function<Integer, Optional<List<Text>>> tooltipSupplier) {
        this.tooltipSupplier = tooltipSupplier;
        return this;
    }
    
    public IntSliderBuilder setTooltipSupplier(Supplier<Optional<List<Text>>> tooltipSupplier) {
        this.tooltipSupplier = i -> tooltipSupplier.get();
        return this;
    }
    
    public IntSliderBuilder setTooltip(Optional<List<Text>> tooltip) {
        this.tooltipSupplier = i -> tooltip;
        return this;
    }
    
    public IntSliderBuilder setMax(int max) {
        this.max = max;
        return this;
    }
    
    public IntSliderBuilder setMin(int min) {
        this.min = min;
        return this;
    }
    
    @NotNull
    @Override
    public IntegerSliderEntry build() {
        IntegerSliderEntry entry = new IntegerSliderEntry(getFieldNameKey(), min, max, value, getResetButtonKey(), defaultValue, saveConsumer, null, isRequireRestart());
        if (textGetter != null)
            entry.setTextGetter(textGetter);
        entry.setTooltipSupplier(() -> tooltipSupplier.apply(entry.getValue()));
        if (errorSupplier != null)
            entry.setErrorSupplier(() -> errorSupplier.apply(entry.getValue()));
        return entry;
    }
    
}
