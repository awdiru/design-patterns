package com.gridnine.custom_classes.table.constructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import static com.gridnine.custom_classes.table.constructor.Alignment.*;
import static com.gridnine.custom_classes.table.constructor.Constants.*;

/**
 * Класс для создания таблиц.
 * Рекомендуется создавать экземпляры класса через
 * {@link TableConstructorBuilder}
 */
public class TableConstructor {
    // конфиги
    private byte[] verticalSeparator = DEFAULT_VERTICAL_SEPARATOR;
    private byte[] horizontalSeparator = DEFAULT_HORIZONTAL_SEPARATOR;
    private int maxWidthColumn = DEFAULT_WIDTH_COLUMN;
    private int numberOfHorizontalSeparators = DEFAULT_NUMBER_OF_HORIZONTAL_SEPARATORS;
    private int numberOfVerticalSeparators = DEFAULT_NUMBER_OF_VERTICAL_SEPARATORS;
    private boolean wordsWrapping = DEFAULT_WORDS_WRAPPING;
    private boolean cellValueWrapping = DEFAULT_CELL_VALUE_WRAPPING;
    private boolean orientationVerticalSeparator = DEFAULT_ORIENTATION_VERTICAL_SEPARATOR;
    private boolean orientationHorizontalSeparator = DEFAULT_ORIENTATION_HORIZONTAL_SEPARATOR;
    private List<Alignment> alignment = DEFAULT_ALIGNMENT;

    // элементы таблицы
    private List<Integer> columnWidths;
    private List<Integer> rowHeights;
    private List<Cell> cells;

    /**
     * Создает таблицу из строк.
     * Строка здесь - это список элементов {@link List}.
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
     * Строка здесь - это список элементов {@link List}.
     * Число столбцов определяется по количеству
     * элементов в первом списке. Он же и становится
     * шапкой таблицы
     *
     * @param lines строки таблицы
     * @return созданная таблица
     */
    public String getTable(List<?>... lines) {
        if (lines == null || lines.length == 0) return "";
        initTable(lines);
        return buildTable();
    }

    private void initTable(List<?>... lines) {
        final int numberOfColumns = lines[0].size();
        final int numberOfRows = lines.length;
        ;
        initTableParam(numberOfColumns, numberOfRows);

        for (int row = 0; row < numberOfRows; row++) {
            List<String> formattedRow = formatRow(lines[row], numberOfColumns);
            for (int col = 0; col < numberOfColumns; col++)
                buildCell(row, col, getCellValue(formattedRow.get(col)));
        }
        cells.forEach(this::optimizeCellValue);
    }

    private String buildTable() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < numberOfHorizontalSeparators; i++)
            appendHeaderSeparator(builder);

        int indexRow = 0;
        for (int row = 0; row < rowHeights.size(); row++) {
            for (int rowNum = 0; rowNum < rowHeights.get(row); rowNum++) {
                for (int col = 0; col < columnWidths.size(); col++) {
                    String str = cells.get(cells.indexOf(new Cell(row, col, null))).value.get(rowNum);
                    buildCell(builder, getVerticalSeparator(indexRow, col), str, col, alignment, row);
                }
                builder.append(getVerticalSeparator(indexRow, columnWidths.size())).append("\n");
                indexRow++;
            }
            for (int i = 0; i < numberOfHorizontalSeparators; i++)
                appendRowSeparator(builder, indexRow++, row + 1);
        }
        return builder.toString();
    }

    private void initTableParam(int numberOfColumns, int numberOfRows) {
        columnWidths = new ArrayList<>(numberOfColumns);
        rowHeights = new ArrayList<>(numberOfRows);
        cells = new ArrayList<>(numberOfColumns * numberOfRows);

        IntStream.range(0, numberOfColumns).forEach(i -> columnWidths.add(0));
        IntStream.range(0, numberOfRows).forEach(o -> rowHeights.add(0));
    }

    private List<String> formatRow(List<?> row, int targetColumns) {
        List<String> formattedRow = new ArrayList<>(targetColumns);
        row.forEach(item -> formattedRow.add(item.toString()));
        return formattedRow;
    }

    private void buildCell(int row, int col, List<String> cellValue) {
        Cell cell = new Cell(row, col, cellValue);
        columnWidths.set(col, Integer.max(columnWidths.get(col), getMaxStringLength(cell.value)));
        rowHeights.set(row, Integer.max(rowHeights.get(row), cell.value.size()));
        cells.add(cell);
    }

    private void buildCell(StringBuilder builder, String verSeparator, String str, int colWidth, List<Alignment> alignment, int rowNum) {
        if ((alignment.contains(HEADER_CENTER_ALIGNMENT) && rowNum == 0)
                || (alignment.contains(BODY_CENTER_ALIGNMENT) && rowNum != 0))
            buildCellCenterAlignment(builder, verSeparator, str, colWidth);

        else if ((alignment.contains(HEADER_RIGHT_ALIGNMENT) && rowNum == 0)
                || (alignment.contains(BODY_RIGHT_ALIGNMENT) && rowNum != 0)) {
            buildCellRightAlignment(builder, verSeparator, str, colWidth);

        } else {
            buildCellLeftAlignment(builder, verSeparator, str, colWidth);
        }
    }

    private void buildCellCenterAlignment(StringBuilder builder, String verSeparator, String str, int colWidth) {
        int shift = Integer.max(0, (columnWidths.get(colWidth) - str.length() + 1) / 2);
        builder.append(verSeparator)
                .append(" ".repeat(shift))
                .append(str)
                .append(" ".repeat(Integer.max(0, (columnWidths.get(colWidth) - str.length() + 1) - shift)));
    }

    private void buildCellRightAlignment(StringBuilder builder, String verSeparator, String str, int colWidth) {
        builder.append(verSeparator)
                .append(" ".repeat(Integer.max(0, columnWidths.get(colWidth) - str.length())))
                .append(str)
                .append(" ");
    }

    private void buildCellLeftAlignment(StringBuilder builder, String verSeparator, String str, int colWidth) {
        builder.append(verSeparator)
                .append(str)
                .append(" ".repeat(Integer.max(0, columnWidths.get(colWidth) - str.length() + 1)));
    }

    private void appendHeaderSeparator(StringBuilder builder) {
        int indexChar = 0;
        int verticalSeparatorLength = getVerticalSeparator(0, 0).length();
        for (int i = 0; i < verticalSeparatorLength; i++)
            builder.append(getHorizontalSeparator(indexChar++, 0));

        for (Integer col : columnWidths) {
            for (int i = 0; i < col + 1; i++)
                builder.append(getHorizontalSeparator(indexChar++, 0));
            for (int i = 0; i < verticalSeparatorLength; i++)
                builder.append(getHorizontalSeparator(indexChar++, 0));
        }
        builder.append("\n");
    }

    private void appendRowSeparator(StringBuilder builder, int indexRow, int row) {
        int indexChar = 0;
        int colNum = 0;
        builder.append(getVerticalSeparator(indexRow, colNum++));
        for (Integer col : columnWidths) {
            for (int i = 0; i < col + 1; i++)
                builder.append(getHorizontalSeparator(indexChar++, row));
            builder.append(getVerticalSeparator(indexRow, colNum++));
        }
        builder.append("\n");
    }

    private String getHorizontalSeparator(int index, int row) {
        if (orientationHorizontalSeparator)
            return getHorizontalChar(index);
        else return getHorizontalChar(row);
    }

    private String getHorizontalChar(int index) {
        int indexStr = index % horizontalSeparator.length;
        return String.valueOf((char) horizontalSeparator[indexStr]);
    }

    private String getVerticalSeparator(int index, int col) {
        if (!orientationVerticalSeparator)
            return getVerticalChar(col).repeat(numberOfVerticalSeparators);
        else return getVerticalChar(index).repeat(numberOfVerticalSeparators);
    }

    private String getVerticalChar(int index) {
        if (verticalSeparator.length == 0) return "";
        int indexStr = index % verticalSeparator.length;
        return String.valueOf((char) verticalSeparator[indexStr]);
    }

    private List<String> getCellValue(String value) {
        if (cellValueWrapping && wordsWrapping) return getCellValueWordsWrapping(value);
        else if (cellValueWrapping) return getCellValueNotWordsWrapping(value);
        else return new ArrayList<>(List.of(" " + value));
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

    private void optimizeCellValue(Cell cell) {
        List<String> cellValue = cell.value;
        if (!wordsWrapping && cellValueWrapping) {
            for (int rowNum = 0; rowNum < cellValue.size() - 1; rowNum++) {
                if ((cellValue.get(rowNum) + cellValue.get(rowNum + 1)).length() <= columnWidths.get(cell.columnNum)) {
                    cellValue.set(rowNum, cellValue.get(rowNum) + cellValue.get(rowNum + 1));
                    cellValue.remove(cellValue.get(1 + rowNum--));
                }
            }
        }
        while (cellValue.size() < rowHeights.get(cell.lineNum)) cellValue.add("");
    }

    private static int getMaxStringLength(List<String> line) {
        return line.stream()
                .mapToInt(String::length)
                .max()
                .orElse(0);
    }

    /**
     * Установить вертикальный разделитель
     *
     * @param verticalSeparator вертикальный разделитель
     */
    public void setVerticalSeparator(String verticalSeparator) {
        if (verticalSeparator == null) return;
        this.verticalSeparator = verticalSeparator.getBytes();
    }

    /**
     * Установить горизонтальный разделитель
     *
     * @param horizontalSeparator горизонтальный разделитель
     */
    public void setHorizontalSeparator(String horizontalSeparator) {
        if (horizontalSeparator == null) return;
        if (horizontalSeparator.isEmpty()) this.horizontalSeparator = new byte[]{32};
        else this.horizontalSeparator = horizontalSeparator.getBytes();
    }

    /**
     * Установить максимальную ширину столбца
     *
     * @param maxWidthColumn максимальная ширина столбца
     */
    public void setMaxWidthColumn(int maxWidthColumn) {
        if (maxWidthColumn < 1) return;
        this.maxWidthColumn = maxWidthColumn;
    }

    /**
     * Установить возможность переноса слов на новую строку при достижении максимальной ширины столбца
     *
     * @param wordsWrapping возможность переноса слов
     */
    public void setWordsWrapping(boolean wordsWrapping) {
        this.wordsWrapping = wordsWrapping;
    }

    /**
     * Установить количество горизонтальных разделителей между строками
     *
     * @param numberOfHorizontalSeparators количество горизонтальных разделителей
     */
    public void setNumberOfHorizontalSeparators(int numberOfHorizontalSeparators) {
        if (numberOfHorizontalSeparators < 1) return;
        this.numberOfHorizontalSeparators = numberOfHorizontalSeparators;
    }

    /**
     * Установить количество вертикальных разделителей между столбцами
     *
     * @param numberOfVerticalSeparators количество вертикальных разделителей
     */
    public void setNumberOfVerticalSeparators(int numberOfVerticalSeparators) {
        if (numberOfVerticalSeparators < 1) return;
        this.numberOfVerticalSeparators = numberOfVerticalSeparators;
    }

    /**
     * Установить возможность переноса значения ячейки на новую строку при достижении максимальной ширины столбца
     *
     * @param cellValueWrapping возможность переноса значения ячейки на новую строку
     */
    public void setCellValueWrapping(boolean cellValueWrapping) {
        this.cellValueWrapping = cellValueWrapping;
    }

    /**
     * Установить ориентацию вертикального разделителя.
     * true - вертикальная ориентация,
     * false - горизонтальная ориентация.
     *
     * @param orientationVerticalSeparator ориентация горизонтального разделителя
     */
    public void setOrientationVerticalSeparator(boolean orientationVerticalSeparator) {
        this.orientationVerticalSeparator = orientationVerticalSeparator;
    }

    /**
     * Установить ориентацию горизонтального разделителя.
     * true - горизонтальная ориентация,
     * false - вертикальная ориентация.
     *
     * @param orientationHorizontalSeparator ориентация горизонтального разделителя
     */
    public void setOrientationHorizontalSeparator(boolean orientationHorizontalSeparator) {
        this.orientationHorizontalSeparator = orientationHorizontalSeparator;
    }

    /**
     * Установить выравнивание ячеек
     *
     * @param alignment список {@link Alignment} значений для выравнивания ячеек
     */
    public void setAlignment(List<Alignment> alignment) {
        if (alignment.isEmpty()) return;
        this.alignment = alignment;
    }

    /**
     * Установить выравнивание ячеек
     *
     * @param alignment массив {@link Alignment} значений для выравнивания ячеек
     */
    public void setAlignment(Alignment... alignment) {
        List<Alignment> alignmentList = List.of(alignment);
        setAlignment(alignmentList);
    }

    private class Cell {
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