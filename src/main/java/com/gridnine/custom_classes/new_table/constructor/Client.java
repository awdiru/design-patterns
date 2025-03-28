package com.gridnine.custom_classes.new_table.constructor;

import com.gridnine.custom_classes.table.Student;

import java.util.List;

class Client {
    public static void main(String[] args) {
        TableConstructor constructor = new TableConstructor();
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

        String table = constructor.getTable(studentHeader, list1, list2, list3, list4, list5);
        System.out.println(table);
    }
}
