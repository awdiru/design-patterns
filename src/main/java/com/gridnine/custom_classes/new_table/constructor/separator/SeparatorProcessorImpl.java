package com.gridnine.custom_classes.new_table.constructor.separator;


public class SeparatorProcessorImpl implements SeparatorProcessor {
    private char[] horizontalSeparator = {'_'};
    private char[] verticalSeparator = {'|'};
    private int numberOfVerticalSeparators = 1;
    private boolean orientationVerticalSeparator = true;
    private boolean orientationHorizontalSeparator = true;

    @Override
    public String getHorizontalSeparator(int col, int row) {
        if (orientationHorizontalSeparator)
            return getHorizontalChar(col);
        return getHorizontalChar(row);
    }

    @Override
    public String getVerticalSeparator(int col, int row) {
        if (orientationVerticalSeparator)
            return getVerticalChar(col).repeat(numberOfVerticalSeparators);
        return getVerticalChar(row).repeat(numberOfVerticalSeparators);
    }

    private String getHorizontalChar(int index) {
        int indexChar = Math.abs(index % horizontalSeparator.length);
        return String.valueOf(horizontalSeparator[indexChar]);
    }

    private String getVerticalChar(int index) {
        int indexChar = Math.abs(index % verticalSeparator.length);
        return String.valueOf(verticalSeparator[indexChar]);
    }
}
