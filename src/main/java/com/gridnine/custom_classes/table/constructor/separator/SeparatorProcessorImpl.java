package com.gridnine.custom_classes.table.constructor.separator;

import com.gridnine.custom_classes.table.constructor.TableConstructorConfig;


public class SeparatorProcessorImpl implements SeparatorProcessor {
    private final char[] horizontalSeparator;
    private final char[] verticalSeparator;
    private final int numberOfVerticalSeparators;
    private final boolean orientationVerticalSeparator;
    private final boolean orientationHorizontalSeparator;

    public SeparatorProcessorImpl(TableConstructorConfig config) {
        this.orientationVerticalSeparator = config.isOrientationVerticalSeparator();
        this.orientationHorizontalSeparator = config.isOrientationHorizontalSeparator();
        this.numberOfVerticalSeparators = config.getNumberOfVerticalSeparators();

        String separator = config.getHorizontalSeparator();
        this.horizontalSeparator = new char[separator.length()];
        for (int i = 0; i < separator.length(); i++)
            this.horizontalSeparator[i] = separator.charAt(i);

        separator = config.getVerticalSeparator();
        this.verticalSeparator = new char[separator.length()];
        for (int i = 0; i < separator.length(); i++)
            this.verticalSeparator[i] = separator.charAt(i);
    }

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
