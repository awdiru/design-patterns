package com.gridnine.structure.bridge;

// Client class
class Client {
    public static void main(String[] args) {
        Abstraction abstraction;
        abstraction = new RefinedAbstraction(new ConcreteImplementorA());
        abstraction.operation();
        abstraction.setImplementor(new ConcreteImplementorB());
        abstraction.operation();
    }
}

// Abstraction class
abstract class Abstraction {
    protected Implementor implementor;

    public void setImplementor(Implementor implementor) {
        this.implementor = implementor;
    }

    public Abstraction(Implementor implementor) {
        this.implementor = implementor;
    }

    public void operation() {
        implementor.operationImp();
    }
}

// Implementor interface
abstract class Implementor {
    public abstract void operationImp();
}

// RefinedAbstraction class
class RefinedAbstraction extends Abstraction {
    public RefinedAbstraction(Implementor implementor) {
        super(implementor);
    }

    @Override
    public void operation() {
        // Дополнительная логика, если требуется
        super.operation();
    }
}

// ConcreteImplementorA class
class ConcreteImplementorA extends Implementor {
    @Override
    public void operationImp() {
        System.out.println("ConcreteImplementorA: operationImp()");
    }
}

// ConcreteImplementorB class
class ConcreteImplementorB extends Implementor {
    @Override
    public void operationImp() {
        System.out.println("ConcreteImplementorB: operationImp()");
    }
}
