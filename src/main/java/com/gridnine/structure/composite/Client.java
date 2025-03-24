package com.gridnine.structure.composite;

import java.util.ArrayList;
import java.util.List;

class Client {
    public static void main(String[] args) {
        Component root = new Composite("Root");
        Component leaf = new Leaf("Leaf");
        Composite subtree = new Composite("Subtree");
        root.add(leaf);
        subtree.add(new Leaf("SubLeaf"));
        root.add(subtree);
        root.display(); // Вызываем метод без параметров
    }
}

abstract class Component {
    protected String name;

    public Component(String name) {
        this.name = name;
    }

    public void display() {
        display(0);
    }

    public abstract void display(int level);

    public abstract void add(Component c);

    public abstract void remove(Component c);
}

class Composite extends Component {
    private final List<Component> children = new ArrayList<>();

    public Composite(String name) {
        super(name);
    }

    @Override
    public void add(Component component) {
        children.add(component);
    }

    @Override
    public void remove(Component component) {
        children.remove(component);
    }

    @Override
    public void display(int level) {
        System.out.print("-".repeat(level) + ">");
        System.out.println(name);
        for (Component component : children) component.display(level + 1);
    }
}

class Leaf extends Component {
    public Leaf(String name) {
        super(name);
    }

    @Override
    public void display(int level) {
        System.out.print("-".repeat(level) + ">");
        System.out.println(name);
    }

    @Override
    public void add(Component component) {
        throw new UnsupportedOperationException("Cannot add to a leaf");
    }

    @Override
    public void remove(Component component) {
        throw new UnsupportedOperationException("Cannot remove from a leaf");
    }
}