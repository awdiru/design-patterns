package com.gridnine.custom_classes.printer_table;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

/**
 * Класс для создания таблиц.
 * Рекомендуется создавать экземпляры класса через
 * {@link TableConstructorBuilder}
 */
public class TableConstructor {
    //константы
    static final String DEFAULT_VERTICAL_SEPARATOR = "|";
    static final String DEFAULT_HORIZONTAL_SEPARATOR = "_";
    static final int DEFAULT_WIDTH_COLUMN = 20;
    static final int DEFAULT_NUMBER_OF_HORIZONTAL_SEPARATORS = 1;
    static final int DEFAULT_NUMBER_OF_VERTICAL_SEPARATORS = 1;
    static final boolean DEFAULT_WORDS_WRAPPING = false;
    static final boolean DEFAULT_STRING_WRAPPING = true;
    static final boolean DEFAULT_ORIENTATION_VERTICAL_SEPARATOR = true;
    //конфиги
    private String verticalSeparator;
    private String horizontalSeparator;
    private int maxWidthColumn;
    private int numberOfHorizontalSeparators;
    private int numberOfVerticalSeparators;
    private boolean wordsWrapping;
    private boolean stringWrapping;
    private boolean orientationVerticalSeparator;
    //элементы сборки таблицы
    private List<Integer> columnWidths;
    private List<Integer> rowHeights;
    private List<Cell> cells;

    public TableConstructor() {
        verticalSeparator = DEFAULT_VERTICAL_SEPARATOR;
        horizontalSeparator = DEFAULT_HORIZONTAL_SEPARATOR;
        maxWidthColumn = DEFAULT_WIDTH_COLUMN;
        numberOfHorizontalSeparators = DEFAULT_NUMBER_OF_HORIZONTAL_SEPARATORS;
        numberOfVerticalSeparators = DEFAULT_NUMBER_OF_VERTICAL_SEPARATORS;
        wordsWrapping = DEFAULT_WORDS_WRAPPING;
        stringWrapping = DEFAULT_STRING_WRAPPING;
        orientationVerticalSeparator = DEFAULT_ORIENTATION_VERTICAL_SEPARATOR;
    }

    /**
     * Создает таблицу из строк.
     * Строка здесь - это список элементов List
     * Число столбцов определяется по количеству
     * элементов в первом списке. Он же и становится
     * шапкой таблицы
     *
     * @param lines строки таблицы
     * @return созданная таблица
     */
    public String getTable(List<List<?>> lines) {
        List<?>[] linesAr = lines.toArray(new List<?>[0]);
        return getTable(linesAr);
    }

    /**
     * Создает таблицу из строк.
     * Строка здесь - это список элементов List
     * Число столбцов определяется по количеству
     * элементов в первом списке. Он же и становится
     * шапкой таблицы
     *
     * @param lines строки таблицы
     * @return созданная таблица
     */
    public String getTable(List<?>... lines) {
        if (lines.length == 0) return "";

        final int numberOfColumns = lines[0].size();

        columnWidths = new ArrayList<>(numberOfColumns);
        IntStream.range(0, numberOfColumns).forEach(i -> columnWidths.add(0));
        rowHeights = new ArrayList<>(lines.length);
        cells = new ArrayList<>(numberOfColumns * lines.length);

        for (int row = 0; row < lines.length; row++) {
            rowHeights.add(1);
            List<String> formattedRow = formatRow(lines[row], numberOfColumns);

            for (int col = 0; col < numberOfColumns; col++) {
                Cell cell = new Cell(row, col, getCellValue(formattedRow.get(col)));
                columnWidths.set(col, Integer.max(columnWidths.get(col), getMaxStringLength(cell.value)));
                rowHeights.set(row, Integer.max(rowHeights.get(row), cell.value.size()));
                cells.add(cell);
            }
        }
        return buildTableString();
    }

    private String buildTableString() {
        cells.forEach(this::optimizeCellValue);

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < numberOfHorizontalSeparators; i++)
            appendHeaderSeparator(builder);

        int indexRow = 0;
        for (int row = 0; row < rowHeights.size(); row++) {
            for (int rowNum = 0; rowNum < rowHeights.get(row); rowNum++) {

                String verSeparator = getVerticalSeparator(indexRow);
                for (int col = 0; col < columnWidths.size(); col++) {
                    String str = cells.get(cells.indexOf(new Cell(row, col, null))).value.get(rowNum);
                    builder.append(verSeparator)
                            .append(str)
                            .append(" ".repeat(Integer.max(0, columnWidths.get(col) - str.length() + 1)));
                }
                builder.append(verSeparator).append("\n");
                indexRow++;
            }
            for (int i = 0; i < numberOfHorizontalSeparators; i++)
                appendRowSeparator(builder, indexRow++);
        }
        return builder.toString();
    }

    private void appendHeaderSeparator(StringBuilder builder) {
        int indexChar = 0;
        int verticalSeparatorLength = getVerticalSeparator(0).length();
        for (int i = 0; i < verticalSeparatorLength; i++)
            builder.append(getHorizontalChar(indexChar++));

        for (Integer col : columnWidths) {
            for (int i = 0; i < col + 1; i++)
                builder.append(getHorizontalChar(indexChar++));
            for (int i = 0; i < verticalSeparatorLength; i++)
                builder.append(getHorizontalChar(indexChar++));
        }
        builder.append("\n");
    }

    private void appendRowSeparator(StringBuilder builder, int indexRow) {
        int indexChar = 0;
        String vertSeparator = getVerticalSeparator(indexRow);
        builder.append(vertSeparator);
        for (Integer col : columnWidths) {
            for (int i = 0; i < col + 1; i++)
                builder.append(getHorizontalChar(indexChar++));
            builder.append(vertSeparator);
        }
        builder.append("\n");
    }

    private String getHorizontalChar(int index) {
        int indexStr = index % horizontalSeparator.length();
        return horizontalSeparator.substring(indexStr, indexStr + 1);
    }

    private String getVerticalSeparator(int index) {
        if (!orientationVerticalSeparator)
            return verticalSeparator.repeat(numberOfVerticalSeparators);
        else return getVerticalChar(index).repeat(numberOfVerticalSeparators);
    }

    private String getVerticalChar(int index) {
        int indexStr = index % verticalSeparator.length();
        return verticalSeparator.substring(indexStr, indexStr + 1);
    }


    private List<String> getCellValue(String value) {
        if (stringWrapping && wordsWrapping) return getCellValueWordsWrapping(value);
        else if (stringWrapping) return getCellValueNotWordsWrapping(value);
        else return getCellValueNotWrapping(value);
    }

    private List<String> getCellValueWordsWrapping(String value) {
        List<String> cellValue = new ArrayList<>();
        for (int i = 0; i < value.length(); i += maxWidthColumn) {
            int end = Integer.min(value.length(), i + maxWidthColumn);
            String val = value.substring(i, end);
            if (!val.startsWith(" ")) val = " " + val;
            cellValue.add(val);
        }
        return cellValue;
    }

    private List<String> getCellValueNotWordsWrapping(String value) {
        List<String> cellValue = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        String[] str = value.split(" ");

        for (String s : str) {
            if (s.isBlank()) continue;
            if ((builder + s).length() <= maxWidthColumn) {
                builder.append(" ").append(s);
            } else {
                if (!builder.isEmpty())
                    cellValue.add(builder.toString());
                builder = new StringBuilder();
                builder.append(" ").append(s);
            }
        }
        if (!builder.isEmpty()) cellValue.add(builder.toString());
        return cellValue;
    }

    private List<String> getCellValueNotWrapping(String value) {
        return new ArrayList<>(List.of(" " + value));
    }

    private void optimizeCellValue(Cell cell) {
        List<String> cellValue = cell.value;
        for (int rowNum = 0; rowNum < cellValue.size() - 1; rowNum++) {

            if ((cellValue.get(rowNum) + cellValue.get(rowNum + 1)).length() <= columnWidths.get(cell.columnNum)) {
                cellValue.set(rowNum, cellValue.get(rowNum) + cellValue.get(rowNum + 1));
                cellValue.remove(cellValue.get(1 + rowNum--));
            }
        }
        if (cellValue.size() < rowHeights.get(cell.lineNum)) {
            for (int i = cellValue.size(); i < rowHeights.get(cell.lineNum); i++)
                cellValue.add("");
        }
    }

    private static int getMaxStringLength(List<String> line) {
        return line.stream().mapToInt(String::length).max().orElse(0);
    }

    private List<String> formatRow(List<?> row, int targetColumns) {
        List<String> formatted = new ArrayList<>(targetColumns);
        row.forEach(item -> formatted.add(item.toString()));
        while (formatted.size() < targetColumns) formatted.add("");
        return formatted;
    }

    void setVerticalSeparator(String verticalSeparator) {
        this.verticalSeparator = verticalSeparator;
    }

    void setHorizontalSeparator(String horizontalSeparator) {
        this.horizontalSeparator = horizontalSeparator;
    }

    void setMaxWidthColumn(int maxWidthColumn) {
        this.maxWidthColumn = maxWidthColumn;
    }

    void setWordsWrapping(boolean wordsWrapping) {
        this.wordsWrapping = wordsWrapping;
    }

    public void setNumberOfHorizontalSeparators(int numberOfHorizontalSeparators) {
        this.numberOfHorizontalSeparators = numberOfHorizontalSeparators;
    }

    public void setNumberOfVerticalSeparators(int numberOfVerticalSeparators) {
        this.numberOfVerticalSeparators = numberOfVerticalSeparators;
    }

    public void setStringWrapping(boolean stringWrapping) {
        this.stringWrapping = stringWrapping;
    }

    public void setOrientationVerticalSeparator(boolean verticalOrientationVerticalSeparator) {
        this.orientationVerticalSeparator = verticalOrientationVerticalSeparator;
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

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Cell cell = (Cell) o;
            return lineNum == cell.lineNum && columnNum == cell.columnNum;
        }

        @Override
        public int hashCode() {
            return Objects.hash(lineNum, columnNum);
        }
    }
}
