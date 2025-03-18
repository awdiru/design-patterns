package com.gridnine.structure.decorator;

// Abstract Component class
abstract class Component {
    public abstract void operation();
}

// ConcreteComponent class
class ConcreteComponent extends Component {
    @Override
    public void operation() {
        System.out.println("ConcreteComponent: operation()");
    }
}

// Abstract Decorator class
abstract class Decorator extends Component {
    protected Component component;

    public void setComponent(Component component) {
        this.component = component;
    }

    @Override
    public void operation() {
        if (component != null) {
            component.operation();
        }
    }
}

// ConcreteDecoratorA class
class ConcreteDecoratorA extends Decorator {
    @Override
    public void operation() {
        super.operation();
        System.out.println("ConcreteDecoratorA: operation()");
    }
}

// ConcreteDecoratorB class
class ConcreteDecoratorB extends Decorator {
    @Override
    public void operation() {
        super.operation();
        System.out.println("ConcreteDecoratorB: operation()");
    }
}

// Client class
class Client {
    public static void main(String[] args) {
        // Create a ConcreteComponent
        Component component = new ConcreteComponent();

        // Wrap it with ConcreteDecoratorA
        ConcreteDecoratorA decoratorA = new ConcreteDecoratorA();
        decoratorA.setComponent(component);

        // Wrap it with ConcreteDecoratorB
        ConcreteDecoratorB decoratorB = new ConcreteDecoratorB();
        decoratorB.setComponent(decoratorA);

        // Execute the operation
        decoratorB.operation();
    }
}
