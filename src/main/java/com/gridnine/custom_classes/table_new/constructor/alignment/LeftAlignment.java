package com.gridnine.custom_classes.table_new.constructor.alignment;


public class LeftAlignment extends AlignmentStrategy {
    @Override
    public String align(String text, int width) {
        if (width - text.length() < 0) return text;
        return text + " ".repeat( width - text.length());
    }
}
