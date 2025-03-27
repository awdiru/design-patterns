package com.gridnine.custom_classes.table_new.constructor.separator;

import com.gridnine.custom_classes.table_new.constructor.TableConstructorConfig;


public class SeparatorProcessorImpl implements SeparatorProcessor {
    private final char[] horizontalSeparator;
    private final char[] verticalSeparator;
    private final int numberOfHorizontalSeparators;
    private final int numberOfVerticalSeparators;
    private final boolean orientationVerticalSeparator;
    private final boolean orientationHorizontalSeparator;

    public SeparatorProcessorImpl(TableConstructorConfig config) {
        this.orientationVerticalSeparator = config.isOrientationVerticalSeparator();
        this.orientationHorizontalSeparator = config.isOrientationHorizontalSeparator();
        this.numberOfHorizontalSeparators = config.getNumberOfHorizontalSeparators();
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
    public String getSeparator(int index, int row, boolean isHorizontal) {
        if (isHorizontal) return getHorizontalSeparator(index, row).repeat(numberOfHorizontalSeparators);
        return getVerticalSeparator(index, row).repeat(numberOfVerticalSeparators);
    }

    private String getHorizontalSeparator(int index, int row) {
        if (orientationHorizontalSeparator) return getHorizontalChar(index);
        return getHorizontalChar(row);
    }

    private String getVerticalSeparator(int index, int row) {
        if (orientationVerticalSeparator) return getVerticalChar(index);
        return getVerticalChar(row);
    }

    private String getHorizontalChar(int index) {
        int indexChar = index % horizontalSeparator.length;
        return String.valueOf(horizontalSeparator[indexChar]);
    }

    private String getVerticalChar(int index) {
        int indexChar = index % verticalSeparator.length;
        return String.valueOf(verticalSeparator[indexChar]);
    }
}
