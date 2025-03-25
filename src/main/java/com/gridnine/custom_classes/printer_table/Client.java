package com.gridnine.custom_classes.printer_table;

import java.util.List;

public class Client {

    public static void main(String[] args) {
        List<String> header = List.of("столбец 1", "столбец 2", "3", "столбец 4 и еще 4", "новый столбец");
        List<String> line1 = List.of("Значение 1", "Большое значение 2", " ", "нормальноеttttttttt значение");
        List<Object> line2 = List.of("Длинная строка", "зн", "", "видимое значение", "видимое значение очень большое");

        TableConstructorBuilder builder = new TableConstructorBuilder();
        TableConstructor tableConstructor = builder
                .setHorizontalSeparator("<<>>")
                .setVerticalSeparator("||||")
                .setMaxWidthColumn(10)
                .setNumberOfHorizontalSeparators(1)
                .setNumberOfVerticalSeparators(1)
                .setWordsWrapping(true)
                .setStringWrapping(true)
                .setOrientationVerticalSeparator(true)
                .build();

        String table = tableConstructor.getTable(List.of(header, line1, line2));
        System.out.println(table);
    }
}
