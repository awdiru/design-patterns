package com.gridnine.custom_classes.menu;

class Client {
    public static void main(String[] args) {
        Menu menu = new StartMenu("Главное меню");
        menu.addMenu("Меню 1");
        menu.addMenu("Меню 2");
        menu.addMenu("Меню 3");
        menu.get("3").addMenu("Подменю 1");
        menu.get("3").addMenu("Подменю 2");
        menu.get("3").addMenu("Подменю 3");
        menu.get("3.3").addItem("Пункт 1");
        menu.get("3.3").addItem("Пункт 2");
        menu.get("3.3").addItem("Пункт 3");
        menu.get("2").addMenu("Подменю 2.1");
        menu.get("2").addMenu("Подменю 2.2");
        menu.get("2").addMenu("Подменю 2.3");
        menu.get("2.1").addItem("Пункт 2.1");
        menu.get("2.1").addItem("Пункт 2.2");
        System.out.println(menu.display());
    }
}
