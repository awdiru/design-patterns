package com.gridnine.custom_classes.new_table.constructor.constants;

public enum Color {
    /**
     * Значение по умолчанию
     */
    DEFAULT("", ""),
    /**
     * Сброс цвета текста
     */
    RESET("\u001B[0m", "\u001B[0m"),
    /**
     * Черный цвет текста
     */
    BLACK("\u001B[30m", "\u001B[40m"),
    /**
     * Красный цвет текста
     */
    RED("\u001B[31m", "\u001B[41m"),
    /**
     * Зеленый цвет текста
     */
    GREEN("\u001B[32m", "\u001B[42m"),
    /**
     * Желтый цвет текста
     */
    YELLOW("\u001B[33m", "\u001B[43m"),
    /**
     * Синий цвет текста
     */
    BLUE("\u001B[34m", "\u001B[44m"),
    /**
     * Пурпурный цвет текста
     */
    PURPLE("\u001B[35m", "\u001B[45m"),
    /**
     * Голубой цвет текста
     */
    CYAN("\u001B[36m", "\u001B[46m"),
    /**
     * Белый цвет текста
     */
    WHITE("\u001B[37m", "\u001B[47m");

    private final String textColor;
    private final String bgColor;

    Color(String textColor, String bgColor) {
        this.textColor = textColor;
        this.bgColor = bgColor;
    }

    public String getTextColor() {
        return textColor;
    }

    public String getBgColor() {
        return bgColor;
    }
}
