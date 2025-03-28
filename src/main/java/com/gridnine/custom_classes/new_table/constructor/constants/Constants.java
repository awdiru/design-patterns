package com.gridnine.custom_classes.new_table.constructor.constants;

import com.gridnine.custom_classes.table.constructor.alignment.Alignment;
import com.gridnine.custom_classes.table.constructor.constants.Color;
import com.gridnine.custom_classes.table.constructor.model.Cell;

import java.util.HashMap;
import java.util.Map;

public class Constants {
    /**
     * Вертикальный разделитель по умолчанию
     */
    public static final String DEFAULT_VERTICAL_SEPARATOR = "|";
    /**
     * Горизонтальный разделитель по умолчанию
     */
    public static final String DEFAULT_HORIZONTAL_SEPARATOR = "_";
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
    public static final boolean DEFAULT_CONTENT_WRAPPING = true;
    /**
     * Ориентация вертикального разделителя по умолчанию
     */
    public static final boolean DEFAULT_ORIENTATION_VERTICAL_SEPARATOR = true;
    /**
     * Ориентация горизонтального разделителя по умолчанию
     */
    public static final boolean DEFAULT_ORIENTATION_HORIZONTAL_SEPARATOR = true;
    /**
     * Сжатие ширины ячеек по размеру максимального слова в столбце.
     */
    public static final boolean DEFAULT_CELLE_COMPRESSION = true;
    /**
     * Выравнивание по умолчанию
     */
    public static final Map<Cell, Alignment> DEFAULT_ALIGNMENT = new HashMap<>();
    /**
     * Цвет текста по умолчанию
     */
    public static final Map<Cell, com.gridnine.custom_classes.table.constructor.constants.Color> DEFAULT_TEXT_COLOR = new HashMap<>();
    /**
     * Цвет фона ячеек по умолчанию
     */
    public static final Map<Cell, Color> DEFAULT_BG_COLOR = new HashMap<>();
}
