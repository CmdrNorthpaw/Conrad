package dev.hephaestus.clothy.impl.gui.entries;

import org.jetbrains.annotations.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class FloatListEntry extends TextFieldListEntry<Float> {
    
    private static final Function<String, String> stripCharacters = s -> {
        StringBuilder stringBuilder_1 = new StringBuilder();
        char[] var2 = s.toCharArray();
        int var3 = var2.length;
        
        for (char c : var2)
            if (Character.isDigit(c) || c == '-' || c == '.')
                stringBuilder_1.append(c);
        
        return stringBuilder_1.toString();
    };
    private float minimum, maximum;

    public FloatListEntry(Text fieldName, Float value, Text resetButtonKey, Supplier<Float> defaultValue, Consumer<Float> saveConsumer, @Nullable Supplier<Optional<List<Text>>> tooltipSupplier, boolean requiresRestart) {
        super(fieldName, value, resetButtonKey, defaultValue, saveConsumer, tooltipSupplier, requiresRestart);
        this.minimum = -Float.MAX_VALUE;
        this.maximum = Float.MAX_VALUE;
    }
    
    @Override
    protected String stripAddText(String s) {
        return stripCharacters.apply(s);
    }
    
    @Override
    protected void textFieldPreRender(TextFieldWidget widget) {
        try {
            double i = Float.parseFloat(textFieldWidget.getText());
            if (i < minimum || i > maximum)
                widget.setEditableColor(16733525);
            else
                widget.setEditableColor(14737632);
        } catch (NumberFormatException ex) {
            widget.setEditableColor(16733525);
        }
    }
    
    @Override
    protected boolean isMatchDefault(String text) {
        return getDefaultValue().isPresent() && text.equals(defaultValue.get().toString());
    }
    
    public FloatListEntry setMinimum(float minimum) {
        this.minimum = minimum;
        return this;
    }
    
    public FloatListEntry setMaximum(float maximum) {
        this.maximum = maximum;
        return this;
    }
    
    @Override
    public void save() {
        if (saveConsumer != null)
            saveConsumer.accept(getValue());
    }
    
    @Override
    public Float getValue() {
        try {
            return Float.valueOf(textFieldWidget.getText());
        } catch (Exception e) {
            return 0f;
        }
    }
    
    @Override
    public Optional<Text> getError() {
        try {
            float i = Float.parseFloat(textFieldWidget.getText());
            if (i > maximum)
                return Optional.of(new TranslatableText("text.cloth-config.error.too_large", maximum));
            else if (i < minimum)
                return Optional.of(new TranslatableText("text.cloth-config.error.too_small", minimum));
        } catch (NumberFormatException ex) {
            return Optional.of(new TranslatableText("text.cloth-config.error.not_valid_number_float"));
        }
        return super.getError();
    }
}
