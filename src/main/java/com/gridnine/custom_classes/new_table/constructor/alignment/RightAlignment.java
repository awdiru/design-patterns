package com.gridnine.custom_classes.new_table.constructor.alignment;

public class RightAlignment extends AlignmentStrategy {
    @Override
    public String align(String text, int width) {
        if (width - text.length() < 0) return text;
        return " ".repeat(width - text.length()) + text;
    }
}
