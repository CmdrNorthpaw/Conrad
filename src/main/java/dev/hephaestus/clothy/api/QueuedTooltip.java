package dev.hephaestus.clothy.api;

import dev.hephaestus.math.impl.Point;
import net.minecraft.text.Text;

import java.util.Arrays;
import java.util.List;

public class QueuedTooltip implements Tooltip {
    private Point location;
    private List<Text> text;

    private QueuedTooltip(Point location, List<Text> text) {
        this.location = location;
        this.text = text;
    }

    public static QueuedTooltip create(Point location, List<Text> text) {
        return new QueuedTooltip(location, text);
    }
    
    public static QueuedTooltip create(Point location, Text... text) {
        return QueuedTooltip.create(location, Arrays.asList(text));
    }
    
    @Override
    public Point getPoint() {
        return location;
    }
    
    @Override
    public List<Text> getText() {
        return text;
    }
}