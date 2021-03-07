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

package dev.inkwell.conrad.api;

import dev.inkwell.oliver.api.data.DataType;
import dev.inkwell.oliver.api.value.ValueKey;
import dev.inkwell.oliver.impl.data.KeyView;
import dev.inkwell.vivian.api.screen.ConfigScreen;
import dev.inkwell.vivian.api.widgets.WidgetComponent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Supplier;

@FunctionalInterface
public interface WidgetFactory<T, V extends KeyView<T>> {
    DataType<WidgetFactory<?, ?>> DATA_TYPE = new DataType<>("widget_builder");

    WidgetComponent build(V keyView, ConfigScreen parent, int x, int y, int width, int height, Supplier<@NotNull T> defaultValueSupplier, Consumer<T> changedListener, Consumer<T> saveConsumer, @NotNull T value);

    @FunctionalInterface
    interface Default<T> extends WidgetFactory<T, KeyView<T>> {
        WidgetComponent build(KeyView<T> keyView, ConfigScreen parent, int x, int y, int width, int height, Supplier<@NotNull T> defaultValueSupplier, Consumer<T> changedListener, Consumer<T> saveConsumer, @NotNull T value);
    }

    @FunctionalInterface
    interface ValueDependent<T> extends WidgetFactory<T, ValueKey<T>> {
        WidgetComponent build(ValueKey<T> valueKey, ConfigScreen parent, int x, int y, int width, int height, Supplier<@NotNull T> defaultValueSupplier, Consumer<T> changedListener, Consumer<T> saveConsumer, @NotNull T value);
    }
}
