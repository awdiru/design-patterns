package com.gridnine.custom_classes.table.constructor;

import java.util.List;

import static com.gridnine.custom_classes.table.constructor.TableConstructorAlignment.ALL_LEFT_ALIGNMENT;

public class TableConstructorConstants {
    /**
     * Вертикальный разделитель по умолчанию
     */
    public static final char[] DEFAULT_VERTICAL_SEPARATOR = {'|'};
    /**
     * Горизонтальный разделитель по умолчанию
     */
    public static final char[] DEFAULT_HORIZONTAL_SEPARATOR = {'_'};
    /**
     * Максимальная ширина столбца по умолчанию
     */
    public static final int DEFAULT_WIDTH_COLUMN = 20;
    /**
     * Количество горизонтальных разделителей между строками по умолчанию
     */
    public static final int DEFAULT_NUMBER_OF_HORIZONTAL_SEPARATORS = 1;
    /**
     * Количество вертикальных разделителей между столбцами по умолчанию
     */
    public static final int DEFAULT_NUMBER_OF_VERTICAL_SEPARATORS = 1;
    /**
     * Перенос слов на новую строку при достижении максимальной ширины столбца по умолчанию
     */
    public static final boolean DEFAULT_WORDS_WRAPPING = false;
    /**
     * Перенос значения ячейки на новую строку при достижении максимального размера столбца по умолчанию
     */
    public static final boolean DEFAULT_CELL_VALUE_WRAPPING = true;
    /**
     * Ориентация вертикального разделителя по умолчанию
     */
    public static final boolean DEFAULT_ORIENTATION_VERTICAL_SEPARATOR = true;
    /**
     * Ориентация горизонтального разделителя по умолчанию
     */
    public static final boolean DEFAULT_ORIENTATION_HORIZONTAL_SEPARATOR = true;
    /**
     * Выравнивание по умолчанию
     */
    public static final List<TableConstructorAlignment> DEFAULT_ALIGNMENT = List.of(ALL_LEFT_ALIGNMENT);
}
