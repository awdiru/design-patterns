package com.gridnine.custom_classes.table.constructor;

import com.gridnine.custom_classes.table.constructor.alignment.Alignment;
import com.gridnine.custom_classes.table.constructor.constants.Color;
import com.gridnine.custom_classes.table.constructor.model.Cell;

import java.util.List;

public class TableConstructorBuilder {
    private final TableConstructorConfig config = new TableConstructorConfig();

    public TableConstructorBuilder verticalSeparator (String verticalSeparator) {
        config.setVerticalSeparator(verticalSeparator);
        return this;
    }

    public TableConstructorBuilder horizontalSeparator(String horizontalSeparator) {
        config.setHorizontalSeparator(horizontalSeparator);
        return this;
    }

    public TableConstructorBuilder maxWidthColumn (int maxWidthColumn) {
        config.setMaxWidthColumn(maxWidthColumn);
        return this;
    }

    public TableConstructorBuilder numberOfHorizontalSeparators (int numberOfHorizontalSeparators) {
        config.setNumberOfHorizontalSeparators(numberOfHorizontalSeparators);
        return this;
    }

    public TableConstructorBuilder numberOfVerticalSeparators (int numberOfVerticalSeparators) {
        config.setNumberOfVerticalSeparators(numberOfVerticalSeparators);
        return this;
    }

    public TableConstructorBuilder celleCompression (boolean celleCompression) {
        config.setCelleCompression(celleCompression);
        return this;
    }

    public TableConstructorBuilder wordsWrapping (boolean wordsWrapping) {
        config.setWordsWrapping(wordsWrapping);
        return this;
    }

    public TableConstructorBuilder contentWrapping (boolean contentWrapping) {
        config.setContentWrapping(contentWrapping);
        return this;
    }

    public TableConstructorBuilder orientationVerticalSeparator (boolean orientationVerticalSeparator) {
        config.setOrientationVerticalSeparator(orientationVerticalSeparator);
        return this;
    }

    public TableConstructorBuilder orientationHorizontalSeparator ( boolean orientationHorizontalSeparator) {
        config.setOrientationHorizontalSeparator(orientationHorizontalSeparator);
        return this;
    }

    public TableConstructorBuilder addAlignments(Alignment alignment, Cell... cells) {
        for (Cell cell : cells) config.getAlignments().put(cell, alignment);
        return this;
    }

    public TableConstructorBuilder addAlignments(Alignment alignment, List<Cell> cells) {
        for (Cell cell : cells) config.getAlignments().put(cell, alignment);
        return this;
    }

    public TableConstructorBuilder addColors (Color color, Cell... cells) {
        for (Cell cell : cells) config.getTextColors().put(cell, color);
        return this;
    }

    public TableConstructorBuilder addColors (Color color, List<Cell> cells) {
        for (Cell cell : cells) config.getTextColors().put(cell, color);
        return this;
    }

    public TableConstructorBuilder addBgColors(Color color, List<Cell> cells) {
        for (Cell cell : cells) config.getBgColors().put(cell, color);
        return this;
    }

    public TableConstructorBuilder addBgColors(Color color, Cell... cells) {
        for (Cell cell : cells) config.getBgColors().put(cell, color);
        return this;
    }

    public TableConstructor build() {
        return new TableConstructor(config);
    }
}
