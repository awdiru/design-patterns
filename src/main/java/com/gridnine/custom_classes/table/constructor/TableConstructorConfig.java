package com.gridnine.custom_classes.table.constructor;

import com.gridnine.custom_classes.table.constructor.alignment.Alignment;
import com.gridnine.custom_classes.table.constructor.constants.Color;
import com.gridnine.custom_classes.table.constructor.model.Cell;

import java.util.Map;

import static com.gridnine.custom_classes.table.constructor.constants.Constants.*;

public class TableConstructorConfig {
    private String verticalSeparator = DEFAULT_VERTICAL_SEPARATOR;
    private String horizontalSeparator = DEFAULT_HORIZONTAL_SEPARATOR;
    private int maxWidthColumn = DEFAULT_WIDTH_COLUMN;
    private int numberOfHorizontalSeparators = DEFAULT_NUMBER_OF_HORIZONTAL_SEPARATORS;
    private int numberOfVerticalSeparators = DEFAULT_NUMBER_OF_VERTICAL_SEPARATORS;
    private boolean celleCompression = DEFAULT_CELLE_COMPRESSION;
    private boolean wordsWrapping = DEFAULT_WORDS_WRAPPING;
    private boolean contentWrapping = DEFAULT_CONTENT_WRAPPING;
    private boolean orientationVerticalSeparator = DEFAULT_ORIENTATION_VERTICAL_SEPARATOR;
    private boolean orientationHorizontalSeparator = DEFAULT_ORIENTATION_HORIZONTAL_SEPARATOR;
    private Map<Cell, Alignment> alignments = DEFAULT_ALIGNMENT;
    private Map<Cell, Color> textColors = DEFAULT_TEXT_COLOR;
    private Map<Cell, Color> bgColors = DEFAULT_BG_COLOR;

    public String getHorizontalSeparator() {
        return horizontalSeparator;
    }

    public void setHorizontalSeparator(String separator) {
        if (horizontalSeparator == null) horizontalSeparator = "";
        else this.horizontalSeparator = separator;
    }

    public String getVerticalSeparator() {
        return verticalSeparator;
    }

    public void setVerticalSeparator(String separator) {
        if (verticalSeparator == null) verticalSeparator = "";
        else this.verticalSeparator = separator;
    }

    public boolean isWordsWrapping() {
        return wordsWrapping;
    }

    public int getMaxWidthColumn() {
        return maxWidthColumn;
    }

    public void setMaxWidthColumn(int maxWidthColumn) {
        this.maxWidthColumn = maxWidthColumn;
    }

    public int getNumberOfHorizontalSeparators() {
        return numberOfHorizontalSeparators;
    }

    public void setNumberOfHorizontalSeparators(int numberOfHorizontalSeparators) {
        this.numberOfHorizontalSeparators = numberOfHorizontalSeparators;
    }

    public int getNumberOfVerticalSeparators() {
        return numberOfVerticalSeparators;
    }

    public void setNumberOfVerticalSeparators(int numberOfVerticalSeparators) {
        this.numberOfVerticalSeparators = numberOfVerticalSeparators;
    }

    public boolean isCelleCompression() {
        return celleCompression;
    }

    public void setCelleCompression(boolean celleCompression) {
        this.celleCompression = celleCompression;
    }

    public void setWordsWrapping(boolean wordsWrapping) {
        this.wordsWrapping = wordsWrapping;
    }

    public boolean isContentWrapping() {
        return contentWrapping;
    }

    public void setContentWrapping(boolean contentWrapping) {
        this.contentWrapping = contentWrapping;
    }

    public boolean isOrientationVerticalSeparator() {
        return orientationVerticalSeparator;
    }

    public void setOrientationVerticalSeparator(boolean orientationVerticalSeparator) {
        this.orientationVerticalSeparator = orientationVerticalSeparator;
    }

    public boolean isOrientationHorizontalSeparator() {
        return orientationHorizontalSeparator;
    }

    public void setOrientationHorizontalSeparator(boolean orientationHorizontalSeparator) {
        this.orientationHorizontalSeparator = orientationHorizontalSeparator;
    }

    public Map<Cell, Alignment> getAlignments() {
        return alignments;
    }

    public void setAlignments(Map<Cell, Alignment> alignments) {
        this.alignments = alignments;
    }

    public Map<Cell, Color> getTextColors() {
        return textColors;
    }

    public void setTextColors(Map<Cell, Color> textColors) {
        this.textColors = textColors;
    }

    public Map<Cell, Color> getBgColors() {
        return bgColors;
    }

    public void setBgColors(Map<Cell, Color> bgColors) {
        this.bgColors = bgColors;
    }
}
