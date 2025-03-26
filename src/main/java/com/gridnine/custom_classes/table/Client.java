package com.gridnine.custom_classes.table;

import com.gridnine.custom_classes.table.constructor.TableConstructor;
import com.gridnine.custom_classes.table.constructor.TableConstructorBuilder;

import java.util.List;

import static com.gridnine.custom_classes.table.constructor.Alignment.*;

public class Client {

    public static void main(String[] args) {
        List<Student> studentList = List.of(new Student("name 1", 18, "name1@email.com"),
                new Student("name 2", 19, "name2@email.com"),
                new Student("name 3", 20, "name3@email.com"),
                new Student("name 4", 18, "name4@email.com"),
                new Student("name 5", 18, "name5@email.com"));

        List<String> studentHeader = List.of("Имя студента", "Название предмета", "Стадия завершения диплома");
        List<Object> list1 = List.of(studentList.getFirst(), "Математика", "Почти - почти");
        List<Object> list2 = List.of(studentList.get(1), "Физика", "Только начал");
        List<Object> list3 = List.of(studentList.get(2), "История", "Уже наполовину");
        List<Object> list4 = List.of(studentList.get(3), "Биология", "Нормально так сделал");
        List<Object> list5 = List.of(studentList.get(4), "Информатика", "Даже не приходил в учебное заведение");

        TableConstructorBuilder builder = new TableConstructorBuilder();
        TableConstructor tableConstructor = builder
                .setHorizontalSeparator("_")
                .setVerticalSeparator("")
                .setMaxWidthColumn(20)
                .setNumberOfHorizontalSeparators(1)
                .setNumberOfVerticalSeparators(1)
                .setWordsWrapping(false)
                .setCellValueWrapping(true)
                .setOrientationVerticalSeparator(true)
                .setOrientationHorizontalSeparator(true)
                .setAlignment(HEADER_CENTER_ALIGNMENT, BODY_RIGHT_ALIGNMENT)
                .build();

        String table = tableConstructor.getTable(studentHeader, list1, list2, list3, list4, list5);
        System.out.println(table);
        /*
        tableConstructor = new TableConstructor();
        List<List<?>> lines = List.of(studentHeader, list1, list2, list3, list4, list5);
        table = tableConstructor.getTable(lines);
        System.out.println(table);
        */
    }
}
