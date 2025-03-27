package com.gridnine.custom_classes.table.constructor;

import java.util.*;
import java.util.stream.IntStream;

import static com.gridnine.custom_classes.table.constructor.TableConstructorAlignment.*;
import static com.gridnine.custom_classes.table.constructor.TableConstructorConstants.*;

/**
 * Класс для создания таблиц.
 * Рекомендуется создавать экземпляры класса через
 * {@link TableConstructorBuilder}
 */
public class TableConstructor {
    // конфиги
    private char[] verticalSeparator = DEFAULT_VERTICAL_SEPARATOR;
    private char[] horizontalSeparator = DEFAULT_HORIZONTAL_SEPARATOR;
    private int maxWidthColumn = DEFAULT_WIDTH_COLUMN;
    private int numberOfHorizontalSeparators = DEFAULT_NUMBER_OF_HORIZONTAL_SEPARATORS;
    private int numberOfVerticalSeparators = DEFAULT_NUMBER_OF_VERTICAL_SEPARATORS;
    private boolean wordsWrapping = DEFAULT_WORDS_WRAPPING;
    private boolean cellValueWrapping = DEFAULT_CELL_VALUE_WRAPPING;
    private boolean orientationVerticalSeparator = DEFAULT_ORIENTATION_VERTICAL_SEPARATOR;
    private boolean orientationHorizontalSeparator = DEFAULT_ORIENTATION_HORIZONTAL_SEPARATOR;
    private boolean celleCompression = DEFAULT_CELLE_COMPRESSION;
    private List<TableConstructorAlignment> alignment = DEFAULT_ALIGNMENT;

    // элементы таблицы
    private List<Integer> columnWidths;
    private List<Integer> rowHeights;
    private Map<Cell, List<String>> cells;

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
        initTableParam(numberOfColumns, numberOfRows);

        for (int row = 0; row < numberOfRows; row++) {
            List<String> formattedRow = formatRow(lines[row], numberOfColumns);
            for (int col = 0; col < numberOfColumns; col++)
                initCell(row, col, getCellValue(formattedRow.get(col)));
        }
        cells.keySet().forEach(this::optimizeCellValue);
    }

    private String buildTable() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < numberOfHorizontalSeparators; i++)
            appendHeaderSeparator(builder);

        int rowNum = 0;
        for (int row = 0; row < rowHeights.size(); row++) {
            for (int indexRow = 0; indexRow < rowHeights.get(row); indexRow++) {
                for (int col = 0; col < columnWidths.size(); col++) {
                    String segmentCellValue = cells.get(new Cell(row, col)).get(indexRow);
                    buildSegmentCell(builder, getVerticalSeparator(rowNum, col), segmentCellValue, col, row);
                }
                builder.append(getVerticalSeparator(rowNum++, columnWidths.size())).append("\n");
            }
            for (int i = 0; i < numberOfHorizontalSeparators; i++)
                appendRowSeparator(builder, rowNum++, row + 1);
        }
        return builder.toString();
    }

    private void initTableParam(int numberOfColumns, int numberOfRows) {
        columnWidths = new ArrayList<>(numberOfColumns);
        rowHeights = new ArrayList<>(numberOfRows);
        cells = new HashMap<>(numberOfColumns * numberOfRows);
        if (!celleCompression)
            IntStream.range(0, numberOfColumns).forEach(i -> columnWidths.add(maxWidthColumn));
        else IntStream.range(0, numberOfColumns).forEach(i -> columnWidths.add(0));

        IntStream.range(0, numberOfRows).forEach(o -> rowHeights.add(0));
    }

    private List<String> formatRow(List<?> row, int numberOfColumns) {
        List<String> formattedRow = new ArrayList<>(numberOfColumns);
        row.forEach(item -> formattedRow.add(item.toString()));
        return formattedRow;
    }

    private void initCell(int row, int col, List<String> cellValue) {
        Cell cell = new Cell(row, col);
        columnWidths.set(col, Integer.max(columnWidths.get(col), getMaxStringLength(cellValue)));
        rowHeights.set(row, Integer.max(rowHeights.get(row), cellValue.size()));
        cells.put(cell, cellValue);
    }

    private void buildSegmentCell(StringBuilder builder, String verSeparator, String segmentCellValue, int colWidth, int rowNum) {
        if ((alignment.contains(HEADER_CENTER_ALIGNMENT) && rowNum == 0)
                || (alignment.contains(BODY_CENTER_ALIGNMENT) && rowNum != 0))
            buildSegmentCellCenterAlignment(builder, verSeparator, segmentCellValue, colWidth);

        else if ((alignment.contains(HEADER_RIGHT_ALIGNMENT) && rowNum == 0)
                || (alignment.contains(BODY_RIGHT_ALIGNMENT) && rowNum != 0)) {
            buildSegmentCellRightAlignment(builder, verSeparator, segmentCellValue, colWidth);

        } else {
            buildSegmentCellLeftAlignment(builder, verSeparator, segmentCellValue, colWidth);
        }
    }

    private void buildSegmentCellCenterAlignment(StringBuilder builder, String verSeparator, String str, int colWidth) {
        int shift = Integer.max(0, (columnWidths.get(colWidth) - str.length() + 1) / 2);
        builder.append(verSeparator)
                .append(" ".repeat(shift))
                .append(str)
                .append(" ".repeat(Integer.max(0, (columnWidths.get(colWidth) - str.length() + 1) - shift)));
    }

    private void buildSegmentCellRightAlignment(StringBuilder builder, String verSeparator, String str, int colWidth) {
        builder.append(verSeparator)
                .append(" ".repeat(Integer.max(0, columnWidths.get(colWidth) - str.length())))
                .append(str)
                .append(" ");
    }

    private void buildSegmentCellLeftAlignment(StringBuilder builder, String verSeparator, String str, int colWidth) {
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
        if (horizontalSeparator.length == 0) return "";
        int indexStr = index % horizontalSeparator.length;
        return String.valueOf(horizontalSeparator[indexStr]);
    }

    private String getVerticalSeparator(int rowNum, int col) {
        if (!orientationVerticalSeparator)
            return getVerticalChar(col).repeat(numberOfVerticalSeparators);
        else return getVerticalChar(rowNum).repeat(numberOfVerticalSeparators);
    }

    private String getVerticalChar(int rowNum) {
        if (verticalSeparator.length == 0) return "";
        int indexStr = rowNum % verticalSeparator.length;
        return String.valueOf(verticalSeparator[indexStr]);
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
        List<String> cellValue = cells.get(cell);
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
        this.verticalSeparator = new char[verticalSeparator.length()];
        for (int i = 0; i < verticalSeparator.length(); i++)
            this.verticalSeparator[i] = verticalSeparator.charAt(i);
    }

    /**
     * Установить горизонтальный разделитель
     *
     * @param horizontalSeparator горизонтальный разделитель
     */
    public void setHorizontalSeparator(String horizontalSeparator) {
        if (horizontalSeparator == null) return;
        this.horizontalSeparator = new char[horizontalSeparator.length()];
        for (int i = 0; i < horizontalSeparator.length(); i++)
            this.horizontalSeparator[i] = horizontalSeparator.charAt(i);
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
     * Установить возможность переноса слов на новую строку при достижении максимальной ширины столбца
     *
     * @param wordsWrapping возможность переноса слов
     */
    public void setWordsWrapping(boolean wordsWrapping) {
        this.wordsWrapping = wordsWrapping;
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
     * Установить сжатие ширины ячеек по размеру максимального слова в столбце.
     * true - сжатие включено,
     * false - сжатие выключено
     *
     * @param celleCompression сжатие ячеек
     */
    public void setCelleCompression(boolean celleCompression) {
        this.celleCompression = celleCompression;
    }

    /**
     * Установить выравнивание ячеек
     *
     * @param alignment список {@link TableConstructorAlignment} значений для выравнивания ячеек
     */
    public void setAlignment(List<TableConstructorAlignment> alignment) {
        if (alignment.isEmpty()) return;
        this.alignment = alignment;
    }

    /**
     * Установить выравнивание ячеек
     *
     * @param alignment массив {@link TableConstructorAlignment} значений для выравнивания ячеек
     */
    public void setAlignment(TableConstructorAlignment... alignment) {
        List<TableConstructorAlignment> alignmentList = List.of(alignment);
        setAlignment(alignmentList);
    }

    private static class Cell {
        int lineNum;
        int columnNum;

        public Cell(int lineNum, int columnNum) {
            this.lineNum = lineNum;
            this.columnNum = columnNum;
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