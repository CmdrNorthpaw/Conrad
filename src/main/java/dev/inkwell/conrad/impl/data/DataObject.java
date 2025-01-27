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

package dev.inkwell.conrad.impl.data;

import dev.inkwell.conrad.api.value.data.DataType;
import dev.inkwell.conrad.api.value.data.Flag;
import dev.inkwell.conrad.api.value.util.ListView;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public interface DataObject {
    DataObject EMPTY = new DataObject() {
        @Override
        public <D> @NotNull ListView<D> getData(DataType<D> dataType) {
            return ListView.empty();
        }

        @Override
        public @NotNull ListView<DataType<?>> getDataTypes() {
            return ListView.empty();
        }

        @Override
        public @NotNull ListView<Flag> getFlags() {
            return ListView.empty();
        }
    };

    <D> @NotNull ListView<D> getData(DataType<D> dataType);

    @NotNull ListView<DataType<?>> getDataTypes();

    @NotNull ListView<Flag> getFlags();
}
