package com.gridnine.custom_classes.table_new.constructor;

import com.gridnine.custom_classes.table_new.constructor.alignment.*;
import com.gridnine.custom_classes.table_new.constructor.formatter.ContentFormatter;
import com.gridnine.custom_classes.table_new.constructor.formatter.ContentFormatterImpl;
import com.gridnine.custom_classes.table_new.constructor.model.Cell;
import com.gridnine.custom_classes.table_new.constructor.separator.SeparatorProcessor;
import com.gridnine.custom_classes.table_new.constructor.separator.SeparatorProcessorImpl;

import java.util.*;
import java.util.List;
import java.util.stream.IntStream;

public class TableConstructor {
    private List<Integer> columnsWidths;
    private List<Integer> rowsHeights;
    private Map<Cell, List<String>> content;
    private final Map<Cell, String> colors;
    private final Map<Cell, Alignment> alignments;
    private final TableConstructorConfig config;
    private final SeparatorProcessor separator;
    private final ContentFormatter contentFormatter;

    public TableConstructor(TableConstructorConfig config) {
        this.config = config;
        this.alignments = config.getAlignments();
        this.colors = config.getColors();
        this.contentFormatter = new ContentFormatterImpl(config);
        this.separator = new SeparatorProcessorImpl(config);
    }


    public TableConstructor() {
        this.config = new TableConstructorConfig();
        this.alignments = config.getAlignments();
        this.colors = config.getColors();
        this.contentFormatter = new ContentFormatterImpl(config);
        this.separator = new SeparatorProcessorImpl(config);
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
            while (content.get(cell).size() < rowsHeights.get(cell.getRowNum())) content.get(cell).add("");
    }

    private String buildTable() {
        StringBuilder builder = new StringBuilder();
        final int numberOfColumns = columnsWidths.size();
        final int numberOfRows = rowsHeights.size();
        buildHeaderSeparator(builder);

        int rowIndex = 0;
        for (int row = 0; row < numberOfRows; row++) {
            for (int rowNum = 0; rowNum < rowsHeights.get(row); rowNum++) {
                for (int col = 0; col < numberOfColumns; col++)
                    buildCell(builder, Cell.of(row, col), rowNum);
                builder.append(separator.getSeparator(columnsWidths.size(), row, false)).append("\n");
            }
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

        content.put(cell, formatCellContent);
        columnsWidths.set(cell.getColumnNum(), Integer.max(columnsWidths.get(cell.getColumnNum()), getMaxCellContentSegment(formatCellContent)));
    }

    private void buildCell(StringBuilder builder, Cell cell, int rowIndex) {
        builder.append(separator.getSeparator(rowIndex, cell.getColumnNum(), false)).append(" ");
        AlignmentStrategy aligner = switch (alignments.get(cell)) {
            case CENTER -> new CenterAlignment();
            case RIGHT -> new RightAlignment();
            case null, default -> new LeftAlignment();
        };
        builder.append(aligner.align(content.get(cell).get(rowIndex), columnsWidths.get(cell.getColumnNum())))
                .append(" ");
    }

    private void optimizeContent(Cell cell) {
        List<String> value = content.get(cell);
        if (!config.isWordsWrapping() && config.isContentWrapping()) {
            for (int row = 0; row < value.size() - 1; row++) {
                if ((value.get(row) + value.get(row + 1)).length() < columnsWidths.get(cell.getColumnNum())) {
                    value.set(row, value.get(row) + " " + value.get(row + 1));
                    value.remove(1 + row--);
                }
            }
        }
        rowsHeights.set(cell.getRowNum(), Integer.max(rowsHeights.get(cell.getRowNum()), value.size()));
    }

    private int getMaxCellContentSegment(List<String> cellContent) {
        return cellContent.stream()
                .mapToInt(String::length)
                .max()
                .orElse(0);
    }

    private void buildHeaderSeparator(StringBuilder builder) {
        int index = 0;
        builder.append(separator.getSeparator(index++, 0, true));

        for (Integer colNum : columnsWidths) {
            for (int i = 0; i < colNum + 2; i++)
                builder.append(separator.getSeparator(index++, 0, true));
            builder.append(separator.getSeparator(index++, 0, true));
        }
        builder.append("\n");
    }

    private void buildSeparator(StringBuilder builder, int rowIndex) {
        int colIndex = 0;
        builder.append(separator.getSeparator(colIndex++, rowIndex, false));

        for (Integer colNum : columnsWidths) {
            for (int i = 0; i < colNum + 2; i++)
                builder.append(separator.getSeparator(colIndex++, rowIndex, true));
            builder.append(separator.getSeparator(colIndex++, rowIndex, false));
        }
        builder.append("\n");
    }


    public TableConstructorConfig getConfig() {
        return config;
    }
}
