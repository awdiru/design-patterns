package com.gridnine.custom_classes.new_table.constructor.model;

import com.gridnine.custom_classes.new_table.constructor.alignment.Alignment;
import com.gridnine.custom_classes.new_table.constructor.constants.Color;

import java.util.List;
import java.util.Objects;

public class Cell {
    private final int col;
    private final int row;
    private List<String> content;
    private Alignment alignment;
    private Color textColor;
    private Color bgColor;

    public Cell (int row, int col) {
        this.col = col;
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

    public Alignment getAlignment() {
        return alignment;
    }

    public void setAlignment(Alignment alignment) {
        this.alignment = alignment;
    }

    public Color getTextColor() {
        return textColor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    public Color getBgColor() {
        return bgColor;
    }

    public void setBgColor(Color bgColor) {
        this.bgColor = bgColor;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return col == cell.col && row == cell.row;
    }

    @Override
    public int hashCode() {
        return Objects.hash(col, row);
    }
}
