package com.gridnine.custom_classes.printer_table;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс - генератор таблиц.
 * Для корректного отображения таблицы
 * необходимо переопределить метод toString
 */
public class PrinterTable {

    private static List<Integer> columns;
    private static List<Cell> cells;

    /**
     * Возвращает таблицу в виде строки.
     * Количество столбцов определяется количеством заголовков.
     * Т.е. наличие в какой-либо строке большего количества
     * значений, чем имеется в наличии заголовков -
     * они напечатаны не будут.
     * Аналогично в обратную сторону, если в какой-либо строке
     * не имеется достаточного количества значений -
     * строка будет дополнена пустыми ячейками.
     *
     * @param header список заголовков
     * @param lines  массив строк таблицы
     * @return таблица в виде строки
     */
    public static String getTable(List<String> header, List<?>... lines) {
        createColumns(header);
        cells = new ArrayList<>();

        //для каждой строки
        for (int i = 0; i < lines.length; i++) {
            //создаем строку
            List<String> line = new ArrayList<>(columns.size());
            for (Object o : lines[i]) line.add(o.toString());
            for (int j = line.size(); j < columns.size(); j++) line.add("");

            //для каждого столбца
            for (int j = 0; j < columns.size(); j++) {
                //создаем ячейки
                String value = line.get(j);
                cells.add(new Cell(i, j, value));
                //обновляем ширину столбцов, если надо
                columns.set(j, Integer.max(columns.get(j), value.length()));
            }
        }

        return getTable(header);
    }

    private static String getTable(List<String> header) {
        StringBuilder builder = new StringBuilder();
        String boldLineChar = "=";
        String thinLineChar = "_";

        //первая линия
        builder.append("=");
        for (Integer column : columns)
            builder.append("=".repeat(column + 3));
        builder.append("\n");

        //шапка таблицы
        for (int i = 0; i < columns.size(); i++) {
            String head = header.get(i);
            builder.append("| ").append(head);
            builder.append(" ".repeat(Integer.max(0, columns.get(i) - head.length()) + 1));
        }
        builder.append("|\n");
        line(builder, boldLineChar);

        //для каждой ячейки
        int lineNum = cells.getFirst().lineNum;
        for (Cell cell : cells) {
            if (cell.lineNum != lineNum) {
                lineNum = cell.lineNum;
                builder.append("|\n");
                line(builder, thinLineChar);
            }
            builder.append("| ").append(cell.value).append(" ".repeat(Integer.max(0, columns.get(cell.columnNum) - cell.value.length()) + 1));
        }
        builder.append("|\n");
        line(builder, thinLineChar);
        return builder.toString();
    }

    private static void line(StringBuilder builder, String lineChar) {
        builder.append("|");
        for (Integer column : columns)
            builder.append(lineChar.repeat(column)).append(lineChar.repeat(2)).append("|");
        builder.append("\n");
    }

    private static void createColumns(List<String> header) {
        columns = new ArrayList<>(header.size());
        for (String head : header) columns.add(head.length());
    }

    /**
     * Ячейка таблицы
     */
    protected static class Cell {
        int lineNum;
        int columnNum;
        String value;

        public Cell(int lineNum, int columnNum, String value) {
            this.lineNum = lineNum;
            this.columnNum = columnNum;
            this.value = value;
        }

        @Override
        public String toString() {
            return value != null ? value : null;
        }
    }
}
