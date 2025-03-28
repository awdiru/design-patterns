package com.gridnine.custom_classes.table;

import com.gridnine.custom_classes.table.constructor.TableConstructor;
import com.gridnine.custom_classes.table.constructor.TableConstructorBuilder;
import com.gridnine.custom_classes.table.constructor.model.Cell;

import java.util.List;

import static com.gridnine.custom_classes.table.constructor.alignment.Alignment.*;
import static com.gridnine.custom_classes.table.constructor.constants.Color.*;

class Client {
    public static void main(String[] args) {
        List<Student> studentList = List.of(new Student("Саша", 18, "name1@email.com"),
                new Student("Гоша", 19, "name2@email.com"),
                new Student("Катя", 20, "name3@email.com"),
                new Student("Лена", 18, "name4@email.com"),
                new Student("Олег", 18, "name5@email.com"));

        List<String> studentHeader = List.of("Имя студента", "Название предмета", "Стадия завершения диплома");
        List<Object> list1 = List.of(studentList.getFirst(), "Математика", "Почти - почти");
        List<Object> list2 = List.of(studentList.get(1), "Физика", "Только начал");
        List<Object> list3 = List.of(studentList.get(2), "История", "Уже наполовину");
        List<Object> list4 = List.of(studentList.get(3), "Биология", "Нормально так сделал");
        List<Object> list5 = List.of(studentList.get(4), "Информатика", "Даже не приходил в учебное заведение");

        List<Cell> head = List.of(Cell.of(0, 0), Cell.of(0, 1), Cell.of(0, 2));
        TableConstructor constructor = new TableConstructorBuilder()
                .horizontalSeparator("_")
                .verticalSeparator("|")
                .maxWidthColumn(20)
                .numberOfHorizontalSeparators(1)
                .numberOfVerticalSeparators(1)
                .wordsWrapping(false)
                .contentWrapping(true)
                .orientationVerticalSeparator(true)
                .orientationHorizontalSeparator(true)
                .addAlignments(CENTER, head)
                .addAlignments(RIGHT, Cell.of(2,1))
                .addColors(BLACK, head)
                .addBgColors(PURPLE, head)
                .addColors(BLACK, Cell.of(1,1))
                .addBgColors(CYAN, Cell.of(1,1))
                .addColors(RED, Cell.of(1,0))
                .addBgColors(YELLOW, Cell.of(1,2))
                .build();

        String table = constructor.getTable(studentHeader, list1, list2, list3, list4, list5);
        System.out.println(table);
    }
}
