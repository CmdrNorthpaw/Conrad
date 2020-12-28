package dev.inkwell.conrad.api;

import dev.inkwell.conrad.impl.ConfigKey;
import dev.inkwell.conrad.impl.ConradException;
import dev.inkwell.conrad.impl.ValueContainer;
import dev.inkwell.conrad.impl.entrypoints.RegisterConfigs;
import dev.inkwell.vivid.builders.WidgetComponentBuilder;
import dev.inkwell.vivid.constraints.Bounded;
import dev.inkwell.vivid.util.Array;
import dev.inkwell.vivid.util.Table;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class ConfigValue<T> implements Supplier<T>, Comparable<ConfigValue<?>> {
	private final Supplier<T> defaultValue;

	private boolean sync;
	private WidgetComponentBuilder<T> builder;
	private T min;
	private T max;

	private ConfigKey key = null;

	ConfigValue(@NotNull Supplier<@NotNull T> defaultValue) {
		this.defaultValue = defaultValue;

		if (RegisterConfigs.isFinished()) {
			throw new ConradException("Cannot create ConfigValue after registration has completed!");
		}
	}

	// Yes this is gross, please ignore it.
	ConfigValue(@NotNull Supplier<@NotNull T> defaultValue, Void dummy) {
		this.defaultValue = defaultValue;
	}

	public T get() {
		return ValueContainer.ROOT.get(this.getKey());
	}

	public ConfigValue<T> widget(WidgetComponentBuilder<T> builder) {
		this.builder = builder;
		return this;
	}

	public ConfigValue<T> sync() {
		this.sync = true;
		return this;
	}

	public ConfigValue<T> setMin(@Nullable T min) {
		this.min = min;
		return this;
	}

	public ConfigValue<T> setMax(@Nullable T max) {
		this.max = max;
		return this;
	}

	public ConfigValue<T> setBounds(@Nullable T min, @Nullable T max) {
		this.setMin(min);
		return this.setMax(max);
	}

	@ApiStatus.Internal
	public T getDefaultValue() {
		return this.defaultValue.get();
	}

	@ApiStatus.Internal
	public @Nullable WidgetComponentBuilder<T> getBuilder() {
		return this.builder;
	}

	@ApiStatus.Internal
	public boolean isSynced() {
		return this.sync;
	}

	@ApiStatus.Internal
	public @Nullable ConfigKey getKey() {
		return this.key;
	}

	@ApiStatus.Internal
	public void setKey(ConfigKey key) {
		this.key = key;
	}

	@Override
	public int compareTo(@NotNull ConfigValue<?> o) {
		return this.key.compareTo(o.key);
	}

	@ApiStatus.Internal
	public @Nullable T getMin() {
		return this.min;
	}

	@ApiStatus.Internal
	public @Nullable T getMax() {
		return this.max;
	}
}
