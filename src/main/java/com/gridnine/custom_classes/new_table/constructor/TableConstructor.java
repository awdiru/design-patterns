package com.gridnine.custom_classes.new_table.constructor;

import com.gridnine.custom_classes.new_table.constructor.alignment.*;
import com.gridnine.custom_classes.new_table.constructor.constants.Color;
import com.gridnine.custom_classes.new_table.constructor.formatter.ContentFormatter;
import com.gridnine.custom_classes.new_table.constructor.formatter.ContentFormatterImpl;
import com.gridnine.custom_classes.new_table.constructor.model.Cell;
import com.gridnine.custom_classes.new_table.constructor.separator.SeparatorProcessor;
import com.gridnine.custom_classes.new_table.constructor.separator.SeparatorProcessorImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static com.gridnine.custom_classes.new_table.constructor.alignment.Alignment.*;
import static com.gridnine.custom_classes.new_table.constructor.constants.Color.*;

public class TableConstructor {
    private List<List<Cell>> cells;
    private List<Integer> columnsWidth;
    private List<Integer> rowsHeights;

    Map<Cell, Color> textColors = Map.of(
            new Cell(0, 0), BLACK,
            new Cell(0, 1), BLACK,
            new Cell(0, 2), BLACK,
            new Cell(1, 0), GREEN,
            new Cell(1, 1), BLACK,
            new Cell(1, 2), BLACK,
            new Cell(2, 0), CYAN,
            new Cell(2, 1), BLACK,
            new Cell(3, 1), BLACK
    );

    Map<Cell, Color> bgColors = Map.of(
            new Cell(0, 0), PURPLE,
            new Cell(0, 1), BLUE,
            new Cell(0, 2), CYAN,
            new Cell(1, 0), RED,
            new Cell(1, 1), YELLOW,
            new Cell(1, 2), PURPLE,
            new Cell(2, 1), RED,
            new Cell(3, 1), GREEN
    );

    Map<Cell, Alignment> alignments = Map.of(new Cell(0, 0), CENTER,
            new Cell(0, 1), CENTER,
            new Cell(0, 2), CENTER,
            new Cell(2, 1), RIGHT);

    ContentFormatter contentFormatter = new ContentFormatterImpl();
    SeparatorProcessor separatorProcessor = new SeparatorProcessorImpl();

    public final String getTable(List<?>... lines) {
        initTable(lines);
        return buildTable();
    }

    private void initTable(List<?>... lines) {
        int numberOfColumns = lines[0].size();
        int numberOfRows = lines.length;
        columnsWidth = new ArrayList<>(numberOfColumns);
        rowsHeights = new ArrayList<>(numberOfRows);
        cells = new ArrayList<>(numberOfColumns * numberOfRows);

        IntStream.range(0, numberOfColumns).forEach(i -> columnsWidth.add(0));
        IntStream.range(0, numberOfRows).forEach(i -> rowsHeights.add(0));

        for (int row = 0; row < numberOfRows; row++) {
            List<Cell> rowCells = new ArrayList<>(numberOfColumns);
            for (int col = 0; col < numberOfColumns; col++) {
                String content = (lines[row].size() > col) ? lines[row].get(col).toString() : "";
                rowCells.add(initCell(row, col, content));
            }
            cells.add(rowCells);
        }
        for (List<Cell> row : cells) {
            for (Cell cell : row) {
                optimizeCellContent(cell);
                rowsHeights.set(cell.getRow(), Integer.max(rowsHeights.get(cell.getRow()), cell.getContent().size()));
            }
            for (Cell cell : row)
                while (cell.getContent().size() < rowsHeights.get(cell.getRow()))
                    cell.getContent().add("");
        }
    }

    private String buildTable() {
        StringBuilder builder = new StringBuilder();
        buildHeaderSeparator(builder);
        for (int rowNum = 0; rowNum < rowsHeights.size(); rowNum++) {
            List<Cell> row = cells.get(rowNum);
            for (int rowIndex = 0; rowIndex < rowsHeights.get(rowNum) + 1; rowIndex++) {
                builder.append(WHITE.getBgColor()).append(BLACK.getTextColor());
                if (rowIndex == rowsHeights.get(rowNum))
                    builder.append(separatorProcessor.getHorizontalSeparator(-2, rowNum))
                            .append(separatorProcessor.getHorizontalSeparator(-1, rowNum));
                else if (rowIndex != 0) builder.append("  ");
                else builder.append(rowNum).append(" ");

                builder.append(separatorProcessor.getVerticalSeparator(0, rowIndex))
                        .append(RESET.getTextColor());
                for (Cell cell : row) buildCell(builder, rowIndex, cell);
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    private Cell initCell(int row, int col, String content) {
        Cell cell = new Cell(row, col);
        Color textColor = textColors.getOrDefault(cell, DEFAULT);
        Color bgColor = bgColors.getOrDefault(cell, DEFAULT);
        Alignment alignment = alignments.getOrDefault(cell, LEFT);

        cell.setContent(contentFormatter.format(content));
        cell.setAlignment(alignment);
        cell.setBgColor(bgColor);
        cell.setTextColor(textColor);

        columnsWidth.set(col, Integer.max(columnsWidth.get(col), getMaxLength(cell.getContent())));
        return cell;
    }

    private void buildCell(StringBuilder builder, int rowIndex, Cell cell) {
        AlignmentStrategy aligner = switch (cell.getAlignment()) {
            case RIGHT -> new RightAlignment();
            case CENTER -> new CenterAlignment();
            case null, default -> new LeftAlignment();
        };
        builder.append(cell.getTextColor().getTextColor())
                .append(cell.getBgColor().getBgColor());

        if (rowIndex < rowsHeights.get(cell.getRow()))
            builder.append(" ")
                    .append(aligner.align(cell.getContent().get(rowIndex), columnsWidth.get(cell.getCol())))
                    .append(" ");

        else for (int colIndex = 0; colIndex < columnsWidth.get(cell.getCol()) + 2; colIndex++)
            builder.append(separatorProcessor.getHorizontalSeparator(colIndex, cell.getRow()));

        builder.append(separatorProcessor.getVerticalSeparator(cell.getCol(), rowIndex))
                .append(RESET.getTextColor());
    }

    private int getMaxLength(List<String> content) {
        int maxLength = 0;
        for (String s : content)
            if (s.length() > maxLength)
                maxLength = s.length();
        return maxLength;
    }

    private void optimizeCellContent(Cell cell) {
        List<String> contend = cell.getContent();
        for (int i = 0; i < contend.size() - 1; i++) {
            if ((contend.get(i) + contend.get(i + 1)).length() < columnsWidth.get(cell.getCol())) {
                contend.set(i, contend.get(i) + " " + contend.get(i + 1));
                contend.remove(1 + i--);
            }
        }
    }

    private void buildHeaderSeparator(StringBuilder builder) {
        int indexCol = -2;
        builder.append(WHITE.getBgColor()).append(BLACK.getTextColor());
        for (int i = 0; i < 2; i++)
            builder.append(separatorProcessor.getHorizontalSeparator(indexCol++, -1));
        builder.append(separatorProcessor.getVerticalSeparator(0, -1));
        for (int col = 0; col < columnsWidth.size(); col++) {
            int padding = (columnsWidth.get(col) + 2) / 2;
            for (int j = 0; j < padding; j++)
                builder.append(separatorProcessor.getHorizontalSeparator(indexCol++, -1));
            builder.append(col);
            indexCol++;
            for (int j = 0; j < columnsWidth.get(col) + 1 - padding; j++)
                builder.append(separatorProcessor.getHorizontalSeparator(indexCol++, -1));
            builder.append(separatorProcessor.getVerticalSeparator(col, -1));
        }
        builder.append(RESET.getBgColor()).append("\n");
    }
}
