package com.gridnine.custom_classes.printer_table;

import java.util.List;

public class Client {

    public static void main(String[] args) {
        List<String> header = List.of("столбец 1", "столбец 2", "3", "столбец 4", "новый столбец");
        List<String> line1 = List.of("Значение 1", "Большое значение 2", " ", "нормально значение");
        List<Object> line2 = List.of("Длинная строка", "зн", "", "видимое значение", "видимое значение очень большое");

        String table = PrinterTable.getTable(header, line1, line2);
        System.out.println(table);
    }
}
