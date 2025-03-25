package com.gridnine.custom_classes.menu;

import java.util.List;

public abstract class Menu {
    protected String name;

    public Menu(String name) {
        this.name = name;
    }

    protected abstract StringBuilder display(String currentNumber, StringBuilder builder);


    public abstract void addMenu(String name);

    public abstract void addItem(String name);

    public abstract Menu get(String num);

    protected abstract List<Menu> getChildren();

    public abstract boolean remove(String num);

    public String display() {
        return display("1", new StringBuilder()).toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

