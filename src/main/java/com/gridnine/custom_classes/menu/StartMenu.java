package com.gridnine.custom_classes.menu;

import com.gridnine.custom_classes.custom_list.CustomLinkedList;

import java.util.List;

public class StartMenu extends Menu {
    protected final List<Menu> children = new CustomLinkedList<>();

    public StartMenu(String name) {
        super(name);
    }

    @Override
    protected StringBuilder display(String currentNumber, StringBuilder builder) {
        builder.append(name);
        for (int i = 0; i < children.size(); i++)
            children.get(i).display(String.valueOf(i + 1), builder);
        return builder;
    }

    @Override
    public void addMenu(String name) {
        children.add(new MenuItem(name));
    }

    @Override
    public void addItem(String name) {
        children.add(new Item(name));
    }

    @Override
    public Menu get(String num) {
        String[] level = num.split("\\.");
        Menu menu = this;
        for (String string : level) {
            Menu newMenu = menu.getChildren().get(Integer.parseInt(string) - 1);
            if (newMenu != null)
                menu = newMenu;
            else throw new RuntimeException("Пункт меню не найден");
        }
        return menu;
    }

    @Override
    protected List<Menu> getChildren() {
        return children;
    }

    @Override
    public boolean remove(String num) {
        return children.remove(get(num));
    }

    private static class MenuItem extends StartMenu {

        public MenuItem(String name) {
            super(name);
        }

        @Override
        protected StringBuilder display(String currentNumber, StringBuilder builder) {
            int level = currentNumber.split("\\.").length - 1;
            builder.append("\n")
                    .append("-".repeat(level))
                    .append("> ")
                    .append(currentNumber)
                    .append(" ")
                    .append(name);
            int childNum = 1;
            for (Menu child : children) {
                child.display(currentNumber + "." + childNum, builder);
                childNum++;
            }
            return builder;
        }
    }

    private static class Item extends Menu {
        public Item(String name) {
            super(name);
        }

        @Override
        protected StringBuilder display(String currentNumber, StringBuilder builder) {
            int level = currentNumber.split("\\.").length - 1;
            builder.append("\n")
                    .append("-".repeat(level))
                    .append("*> ")
                    .append(currentNumber)
                    .append(" ")
                    .append(name);

            return builder;
        }

        //Методы выдают только исключения
        @Override
        public void addMenu(String name) {
            getException();
        }

        @Override
        public void addItem(String name) {
            getException();
        }

        @Override
        public Menu get(String num) {
            getException();
            return null;
        }

        @Override
        protected List<Menu> getChildren() {
            getException();
            return null;
        }

        @Override
        public boolean remove(String num) {
            getException();
            return false;
        }

        private void getException() {
            throw new RuntimeException("Нельзя обратиться к подпунктам конечного пункта меню");
        }
    }
}
