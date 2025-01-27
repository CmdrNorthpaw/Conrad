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

package dev.inkwell.conrad.api.gui;

import dev.inkwell.conrad.api.gui.util.Group;
import dev.inkwell.conrad.api.gui.widgets.WidgetComponent;
import net.minecraft.text.MutableText;

public class Category extends Group<Group<WidgetComponent>> {
    private Runnable saveCallback = () -> {
    };

    public Category(MutableText name, Runnable saveCallback) {
        this(name);
        this.saveCallback = saveCallback;
    }

    public Category(MutableText name) {
        super(name);
    }

    public void save() {
        this.saveCallback.run();
    }
}
