package com.gridnine.custom_classes.table_new.constructor.model;

import java.util.Objects;

public class Cell {
    int rowNum;
    int columnNum;

    private Cell(int rowNum, int columnNum) {
        this.rowNum = rowNum;
        this.columnNum = columnNum;
    }

    public static Cell of (int rowNum, int columnNum) {
        return new Cell(rowNum, columnNum);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return rowNum == cell.rowNum && columnNum == cell.columnNum;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rowNum, columnNum);
    }

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public int getColumnNum() {
        return columnNum;
    }

    public void setColumnNum(int columnNum) {
        this.columnNum = columnNum;
    }
}
