package dev.hephaestus.clothy.impl.gui.entries;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.client.util.math.MatrixStack;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @param <T>           the configuration object type
 * @param <SELF>        the "curiously recurring template pattern" type parameter for this class
 * @param <OUTER_SELF>> the "curiously recurring template pattern" type parameter for the outer class
 * @see AbstractTextFieldListListEntry
 */
public abstract class AbstractTextFieldListCell<T, SELF extends AbstractTextFieldListCell<T, SELF, OUTER_SELF>, OUTER_SELF extends AbstractTextFieldListListEntry<T, SELF, OUTER_SELF>> extends AbstractListCell<T, SELF, OUTER_SELF> {

	protected TextFieldWidget widget;
	private boolean isSelected;

	public AbstractTextFieldListCell(@Nullable T value, OUTER_SELF listListEntry) {
		super(value, listListEntry);

		final T finalValue = substituteDefault(value);

		widget = new TextFieldWidget(MinecraftClient.getInstance().textRenderer, 0, 0, 100, 18, NarratorManager.EMPTY) {
			@Override
			public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
				setFocused(isSelected);
				super.render(matrices, mouseX, mouseY, delta);
			}
		};
		widget.setTextPredicate(this::isValidText);
		widget.setMaxLength(Integer.MAX_VALUE);
		widget.setHasBorder(false);
		widget.setText(Objects.toString(finalValue));
		widget.setChangedListener(s -> {
			widget.setEditableColor(getPreferredTextColor());
		});
	}

	@Override
	public void updateSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	/**
	 * Allows subclasses to substitute default values.
	 *
	 * @param value the (possibly null) value to substitute
	 * @return a substitution
	 */
	@Nullable
	protected abstract T substituteDefault(@Nullable T value);

	/**
	 * Tests if the text entered is valid. If not, the text is not changed.
	 *
	 * @param text the text to test
	 * @return {@code true} if the text may be changed, {@code false} to prevent the change
	 */
	protected abstract boolean isValidText(@NotNull String text);

	@Override
	public int getCellHeight() {
		return 20;
	}

	@Override
	public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean isSelected, float delta) {
		widget.setWidth(entryWidth - 12);
		widget.x = x;
		widget.y = y + 1;
		widget.setEditable(listListEntry.isEditable());
		widget.render(matrices, mouseX, mouseY, delta);
		if (isSelected && listListEntry.isEditable())
			fill(matrices, x, y + 12, x + entryWidth - 12, y + 13, getConfigError().isPresent() ? 0xffff5555 : 0xffe0e0e0);
	}

	@Override
	public List<? extends Element> children() {
		return Collections.singletonList(widget);
	}
}
