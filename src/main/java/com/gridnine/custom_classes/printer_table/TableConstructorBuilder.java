package com.gridnine.custom_classes.printer_table;

/**
 * Конструктор класса PrinterTable
 */
public class TableConstructorBuilder {
    private final TableConstructor tableConstructor;

    public TableConstructorBuilder() {
        this.tableConstructor = new TableConstructor();
    }

    /**
     * Установить вертикальный разделитель.
     * По умолчанию: "|"
     *
     * @param verticalSeparator вертикальный разделитель
     * @return {@link TableConstructorBuilder}
     */
    public TableConstructorBuilder setVerticalSeparator(String verticalSeparator) {
        if (verticalSeparator == null || verticalSeparator.isEmpty())
            verticalSeparator = TableConstructor.DEFAULT_VERTICAL_SEPARATOR;

        tableConstructor.setVerticalSeparator(verticalSeparator);
        return this;
    }

    /**
     * Установить горизонтальный разделитель.
     * По умолчанию: "_"
     *
     * @param horizontalSeparator горизонтальный разделитель
     * @return {@link TableConstructorBuilder}
     */
    public TableConstructorBuilder setHorizontalSeparator(String horizontalSeparator) {
        if (horizontalSeparator == null || horizontalSeparator.isEmpty())
            horizontalSeparator = TableConstructor.DEFAULT_HORIZONTAL_SEPARATOR;

        tableConstructor.setHorizontalSeparator(horizontalSeparator);
        return this;
    }

    /**
     * Установить количество горизонтальных разделителей между строками таблицы
     *
     * @param numberOfHorizontalSeparators количество горизонтальных разделителей
     * @return {@link TableConstructorBuilder}
     */
    public TableConstructorBuilder setNumberOfHorizontalSeparators(int numberOfHorizontalSeparators) {
        if (numberOfHorizontalSeparators < 1)
            numberOfHorizontalSeparators = TableConstructor.DEFAULT_NUMBER_OF_HORIZONTAL_SEPARATORS;
        tableConstructor.setNumberOfHorizontalSeparators(numberOfHorizontalSeparators);
        return this;
    }

    public TableConstructorBuilder setNumberOfVerticalSeparators(int numberOfVerticalSeparators) {
        if (numberOfVerticalSeparators < 1)
            numberOfVerticalSeparators = TableConstructor.DEFAULT_NUMBER_OF_VERTICAL_SEPARATORS;
        tableConstructor.setNumberOfVerticalSeparators(numberOfVerticalSeparators);
        return this;
    }
    /**
     * Установить максимальную ширину столбца.
     * По умолчанию: "20"
     *
     * @param maxWidthColumn максимальная ширина столбца
     * @return {@link TableConstructorBuilder}
     */
    public TableConstructorBuilder setMaxWidthColumn(int maxWidthColumn) {
        if (maxWidthColumn < 1)
            maxWidthColumn = TableConstructor.DEFAULT_WIDTH_COLUMN;

        tableConstructor.setMaxWidthColumn(maxWidthColumn);
        return this;
    }

    /**
     * Установить возможность переноса слов.
     * По умолчанию: "false"
     *
     * @param wordsWrapping переносить слова (да/нет)
     * @return {@link TableConstructorBuilder}
     */
    public TableConstructorBuilder setWordsWrapping(boolean wordsWrapping) {
        tableConstructor.setWordsWrapping(wordsWrapping);
        return this;
    }

    /**
     * Установить перенос слов на новую строку с учетом
     * максимальной ширины столбца. По умолчанию true.
     * Если значение false - максимальная ширина столбца ({@link #setMaxWidthColumn(int)})
     * и перенос слов ({@link #setWordsWrapping(boolean)}) не влияют на
     * конечный вид таблицы
     *
     * @param stringWrapping перенос строк
     * @return {@link TableConstructorBuilder}
     */
    public TableConstructorBuilder setStringWrapping(boolean stringWrapping) {
        tableConstructor.setStringWrapping(stringWrapping);
        return this;
    }

    /**
     * Установить ориентацию вертикального разделителя. По умолчанию - "true".
     * Если значение false - заданный вертикальный разделитель
     * будет весь написан на одной строке между ячейками таблицы.
     * Если значение true - заданный вертикальный разделитель
     * будет посимвольно написан вдоль вертикальной линии, разделяющей столбцы
     *
     * @param orientationVerticalSeparator ориентация вертикального разделителя
     * @return {@link TableConstructorBuilder}
     */
    public TableConstructorBuilder setOrientationVerticalSeparator(boolean orientationVerticalSeparator) {
        tableConstructor.setOrientationVerticalSeparator(orientationVerticalSeparator);
        return this;
    }

    /**
     * Собрать PrinterTable
     *
     * @return {@link TableConstructor}
     */
    public TableConstructor build() {
        return tableConstructor;
    }
}
