package com.gridnine.custom_classes.printer_table;

import java.util.ArrayList;
import java.util.List;

public class PrinterTableTwo {
    private static List<Integer> columnsSize;
    private static List<Integer> linesSize;
    private static List<Cell> cells;
    private static final int MAX_SIZE_COLUMN = 10;

    public static String getTable(List<String> headers, List<?>... lines) {
        createHeader(headers, lines.length);

        //для каждой строки
        for (int i = 0; i < lines.length; i++){
            //создаем строку
            List<String> line = new ArrayList<>(columnsSize.size());
            for (Object o : lines[i]) line.add(o.toString());
            for (int j = line.size(); j < columnsSize.size(); j++) line.add("");

            //для каждого столбца
            for (int j = 0; j < columnsSize.size(); j++) {
                if (line.get(j).length() > MAX_SIZE_COLUMN) {
                    String[] s = null;
                }
            }

        }
        return null;
    }


    private static void createHeader(List<String> headers, int numberOfRows) {
        columnsSize = new ArrayList<>(headers.size());
        linesSize = new ArrayList<>(numberOfRows);
        linesSize.add(1);
        for (int i = 0; i < headers.size(); i++) {
            Cell cell = new Cell(0, i, null);
            String head = headers.get(i);

            if (head.length() > MAX_SIZE_COLUMN) {
                String[] h = head.split(" ");
                StringBuilder builder = new StringBuilder();
                builder.append(h[0]);

                for (int j = 1; j < h.length; j++) {
                    if ((builder + " " + h[j]).length() < MAX_SIZE_COLUMN)
                        builder.append(" ").append(h[j]);
                    else {
                        cell.value.add(builder.toString());
                        builder = new StringBuilder();
                        builder.append(h[j]);
                    }
                }
                linesSize.set(0, Integer.max(linesSize.getFirst(), cell.value.size()));
                columnsSize.add(getMaxStringLength(builder.toString().split("\n")));
                cells.add(cell);

            } else {
                cell.value = List.of(head);
                columnsSize.add(head.length());
                cells.add(cell);
            }
        }
    }

    private static List<String> getCellValue(String value) {
        if (value.length() <= MAX_SIZE_COLUMN) return List.of(value);


        StringBuilder builder = new StringBuilder();
        String[]s = value.split(" ");
        builder.append(s[0]);
        return null;
    }


    private static int getMaxStringLength(String[] lines) {
        int maxLength = -1;
        for (String line : lines) if (line.length() > maxLength) maxLength = line.length();
        return maxLength;
    }

    private static class Cell {
        int lineNum;
        int columnNum;
        List<String> value;

        public Cell(int lineNum, int columnNum, List<String> value) {
            this.lineNum = lineNum;
            this.columnNum = columnNum;
            this.value = value;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            for (String s : value) builder.append(s).append("\n");
            return builder.toString();
        }
    }
}



