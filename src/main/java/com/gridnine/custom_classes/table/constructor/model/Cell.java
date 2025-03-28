package com.gridnine.custom_classes.table.constructor.model;

public record Cell(int rowNum, int columnNum) {
    public static Cell of(int rowNum, int columnNum) {
        return new Cell(rowNum, columnNum);
    }
}