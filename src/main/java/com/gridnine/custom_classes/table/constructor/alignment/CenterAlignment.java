package com.gridnine.custom_classes.table.constructor.alignment;


public class CenterAlignment extends AlignmentStrategy {
    @Override
    public String align(String text, int width) {
        if (width - text.length() < 0) return text;
        int padding = (width - text.length()) / 2;
        return " ".repeat(padding) + text + " ".repeat(width - text.length() - padding);
    }
}
