/*
 * Copyright 2021 Haven King
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.inkwell.conrad.api.gui.widgets.value.slider;

import dev.inkwell.conrad.api.gui.screen.ConfigScreen;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class LongSliderWidget extends SliderWidget<Long> {
    public LongSliderWidget(ConfigScreen parent, int x, int y, int width, int height, Supplier<@NotNull Long> defaultValueSupplier, Consumer<Long> changedListener, Consumer<Long> saveConsumer, @NotNull Long value, @NotNull Long min, @NotNull Long max) {
        super(parent, x, y, width, height, defaultValueSupplier, changedListener, saveConsumer, value, min, max);
    }

    @Override
    protected Long subtract(Long left, Long right) {
        return left - right;
    }

    @Override
    protected Long add(Long left, Long right) {
        return left + right;
    }

    @Override
    protected Long multiply(Long aLong, double d) {
        return (long) (aLong * d);
    }

    @Override
    public boolean isWithinBounds(Long value) {
        return value >= this.min && value <= this.max;
    }

    @Override
    public Text getDefaultValueAsText() {
        return new LiteralText(this.getDefaultValue().toString());
    }

    @Override
    protected float getPercentage() {
        return ((this.getValue() - getMin()) / (float) (getMax() - getMin()));
    }

    @Override
    protected String stringValue() {
        return this.getValue().toString();
    }
}
