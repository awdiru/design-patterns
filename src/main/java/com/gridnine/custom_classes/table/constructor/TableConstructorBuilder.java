package com.gridnine.custom_classes.table.constructor;

import java.util.List;

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
        tableConstructor.setNumberOfHorizontalSeparators(numberOfHorizontalSeparators);
        return this;
    }

    public TableConstructorBuilder setNumberOfVerticalSeparators(int numberOfVerticalSeparators) {
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
     * Если значение false - максимальная ширина столбца {@link #setMaxWidthColumn(int)}
     * и перенос слов {@link #setWordsWrapping(boolean)} не влияют на
     * конечный вид таблицы
     *
     * @param cellValueWrapping перенос строк
     * @return {@link TableConstructorBuilder}
     */
    public TableConstructorBuilder setCellValueWrapping(boolean cellValueWrapping) {
        tableConstructor.setCellValueWrapping(cellValueWrapping);
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
     * Установить ориентацию горизонтального разделителя. По умолчанию - "true".
     * Если значение true - заданный горизонтальный разделитель
     * будет весь написан на одной строке.
     * Если значение false - заданный горизонтальный разделитель
     * будет посимвольно написан вдоль вертикальной линии.
     *
     * @param orientationHorizontalSeparator ориентация вертикального разделителя
     * @return {@link TableConstructorBuilder}
     */
    public TableConstructorBuilder setOrientationHorizontalSeparator(boolean orientationHorizontalSeparator) {
        tableConstructor.setOrientationHorizontalSeparator(orientationHorizontalSeparator);
        return this;
    }

    /**
     * Установить сжатие ширины ячеек по размеру максимального слова в столбце. По умолчанию - "true".
     * Если значение true - ячейки сжимаются по размеру максимального слова в столбце.
     * Если значение false - ячейки остаются размера maxWidthColumns или больше
     *
     * @param celleCompression сжатие ячеек столбца
     * @return {@link TableConstructorBuilder}
     */
    public TableConstructorBuilder setCelleCompression(boolean celleCompression) {
        tableConstructor.setCelleCompression(celleCompression);
        return this;
    }

    /**
     * Установить выравнивание ячеек
     *
     * @param alignment список {@link TableConstructorAlignment} значений для выравнивания ячеек
     * @return {@link TableConstructorBuilder}
     */
    public TableConstructorBuilder setAlignment(List<TableConstructorAlignment> alignment) {
        tableConstructor.setAlignment(alignment);
        return this;
    }

    /**
     * Установить выравнивание ячеек
     *
     * @param alignment массив {@link TableConstructorAlignment} значений для выравнивания ячеек
     * @return {@link TableConstructorBuilder}
     */
    public TableConstructorBuilder setAlignment(TableConstructorAlignment... alignment) {
        tableConstructor.setAlignment(alignment);
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
