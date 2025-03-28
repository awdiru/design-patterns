package com.gridnine.custom_classes.table.constructor;

import com.gridnine.custom_classes.table.constructor.alignment.*;
import com.gridnine.custom_classes.table.constructor.constants.Color;
import com.gridnine.custom_classes.table.constructor.formatter.ContentFormatter;
import com.gridnine.custom_classes.table.constructor.formatter.ContentFormatterImpl;
import com.gridnine.custom_classes.table.constructor.model.Cell;
import com.gridnine.custom_classes.table.constructor.separator.SeparatorProcessor;
import com.gridnine.custom_classes.table.constructor.separator.SeparatorProcessorImpl;

import java.util.*;
import java.util.List;
import java.util.stream.IntStream;

import static com.gridnine.custom_classes.table.constructor.constants.Color.*;

public class TableConstructor {
    private List<Integer> columnsWidths;
    private List<Integer> rowsHeights;
    private Map<Cell, List<String>> content;

    private final TableConstructorConfig config;
    private final Map<Cell, Color> textColors;
    private final Map<Cell, Color> bgColors;
    private final Map<Cell, Alignment> alignments;
    private final SeparatorProcessor separatorProcessor;
    private final ContentFormatter contentFormatter;

    public TableConstructor(TableConstructorConfig config) {
        this.config = config;
        this.textColors = config.getTextColors();
        this.bgColors = config.getBgColors();
        this.alignments = config.getAlignments();
        this.separatorProcessor = new SeparatorProcessorImpl(config);
        this.contentFormatter = new ContentFormatterImpl(config);
    }


    public TableConstructor() {
        this.config = new TableConstructorConfig();
        this.textColors = config.getTextColors();
        this.bgColors = config.getBgColors();
        this.alignments = config.getAlignments();
        this.separatorProcessor = new SeparatorProcessorImpl(config);
        this.contentFormatter = new ContentFormatterImpl(config);
    }


    public String getTable(List<?>... lines) {
        initTable(lines);
        return buildTable();
    }

    private void initTable(List<?>... lines) {
        final int numberOfColumns = lines[0].size();
        final int numberOfRows = lines.length;
        initTableParam(numberOfColumns, numberOfRows);

        for (int row = 0; row < numberOfRows; row++)
            for (int col = 0; col < numberOfColumns; col++)
                initCell(Cell.of(row, col), lines[row].get(col).toString());

        content.keySet().forEach(this::optimizeContent);

        for (Cell cell : content.keySet())
            while (content.get(cell).size() < rowsHeights.get(cell.rowNum())) content.get(cell).add("");
    }

    private String buildTable() {
        StringBuilder builder = new StringBuilder();
        final int numberOfColumns = columnsWidths.size();
        final int numberOfRows = rowsHeights.size();
        buildHeaderSeparator(builder);
        for (int i = 0; i < config.getNumberOfHorizontalSeparators() - 1; i++)
            buildSeparator(builder, 0);

        int rowIndex = 0;
        for (int row = 0; row < numberOfRows; row++) {
            for (int rowNum = 0; rowNum < rowsHeights.get(row); rowNum++) {
                builder.append(WHITE.getBgColor()).append(BLACK.getTextColor());

                if (rowNum != 0) builder.append("  ");
                else builder.append(row).append(" ");

                builder.append(separatorProcessor.getVerticalSeparator(rowIndex, 0)).append(RESET.getTextColor());


                for (int col = 0; col < numberOfColumns; col++) {
                    buildCell(builder, Cell.of(row, col), rowNum);
                    builder.append(separatorProcessor.getVerticalSeparator(rowIndex, col + 1)).append(RESET.getTextColor());
                }
                builder.append("\n");
            }
            for (int i = 0; i < config.getNumberOfHorizontalSeparators(); i++)
                buildSeparator(builder, rowIndex++);
        }
        return builder.toString();
    }

    private void initTableParam(int numberOfColumns, int numberOfRows) {
        columnsWidths = new ArrayList<>(numberOfColumns);
        rowsHeights = new ArrayList<>(numberOfRows);
        content = new HashMap<>(numberOfColumns * numberOfRows);

        IntStream.range(0, numberOfRows).forEach(i -> rowsHeights.add(0));
        if (config.isCelleCompression())
            IntStream.range(0, numberOfColumns).forEach(i -> columnsWidths.add(0));
        else IntStream.range(0, numberOfColumns).forEach(i -> columnsWidths.add(config.getMaxWidthColumn()));
    }

    private void initCell(Cell cell, String cellContent) {
        List<String> formatCellContent = contentFormatter.format(cellContent);
        columnsWidths.set(cell.columnNum(), Integer.max(columnsWidths
                .get(cell.columnNum()), getMaxCellContentSegment(formatCellContent)));
        content.put(cell, formatCellContent);
    }

    private void buildCell(StringBuilder builder, Cell cell, int rowIndex) {
        AlignmentStrategy aligner = switch (alignments.get(cell)) {
            case CENTER -> new CenterAlignment();
            case RIGHT -> new RightAlignment();
            case null, default -> new LeftAlignment();
        };
        String color = textColors.getOrDefault(cell, DEFAULT).getTextColor();
        String bgColor = bgColors.getOrDefault(cell, DEFAULT).getBgColor();
        builder.append(color)
                .append(bgColor)
                .append(" ")
                .append(aligner.align(content.get(cell).get(rowIndex), columnsWidths.get(cell.columnNum())))
                .append(" ");
    }

    private void optimizeContent(Cell cell) {
        List<String> value = content.get(cell);
        if (!config.isWordsWrapping() && config.isContentWrapping()) {
            for (int row = 0; row < value.size() - 1; row++) {
                if ((value.get(row) + value.get(row + 1)).length() < columnsWidths.get(cell.columnNum())) {
                    value.set(row, value.get(row) + " " + value.get(row + 1));
                    value.remove(1 + row--);
                }
            }
        }
        rowsHeights.set(cell.rowNum(), Integer.max(rowsHeights.get(cell.rowNum()), value.size()));
    }

    private int getMaxCellContentSegment(List<String> cellContent) {
        return cellContent.stream()
                .mapToInt(String::length)
                .max()
                .orElse(0);
    }

    private void buildHeaderSeparator(StringBuilder builder) {
        int index = 0;
        builder.append(WHITE.getBgColor()).append(BLACK.getTextColor())
                .append(separatorProcessor.getHorizontalSeparator(index - 2, -2))
                .append(separatorProcessor.getHorizontalSeparator(index - 1, -1))
                .append(separatorProcessor.getVerticalSeparator(index++, 0));
        for (int i = 0; i < columnsWidths.size(); i++) {
            builder.append(WHITE.getBgColor());
            Integer colVal = columnsWidths.get(i);
            // + 2 потому что на этапе сборки таблицы добавляется по пробелу с каждой стороны ячейки
            int padding = (colVal + 2) / 2;
            for (int j = 0; j < padding; j++)
                builder.append(separatorProcessor.getHorizontalSeparator(index++, 0));
            builder.append(i);
            index++;
            for (int j = 0; j < colVal + 1 - padding; j++)
                builder.append(separatorProcessor.getHorizontalSeparator(index++, 0));
            builder.append(separatorProcessor.getVerticalSeparator(index++, 0));
        }
        builder.append(RESET.getBgColor()).append("\n");
    }

    private void buildSeparator(StringBuilder builder, int rowIndex) {
        int colIndex = 0;
        builder.append(WHITE.getBgColor()).append(BLACK.getTextColor())
                .append(separatorProcessor.getHorizontalSeparator(colIndex - 2, rowIndex))
                .append(separatorProcessor.getHorizontalSeparator(colIndex - 1, rowIndex))
                .append(separatorProcessor.getVerticalSeparator(colIndex++, rowIndex))
                .append(RESET.getTextColor());

        for (Integer colVal : columnsWidths) {
            // + 2 потому что на этапе сборки таблицы добавляется по пробелу с каждой стороны ячейки
            for (int i = 0; i < colVal + 2; i++)
                builder.append(separatorProcessor.getHorizontalSeparator(colIndex++, rowIndex));
            builder.append(separatorProcessor.getVerticalSeparator(colIndex++, rowIndex));
        }
        builder.append("\n");
    }


    public TableConstructorConfig getConfig() {
        return config;
    }
}
