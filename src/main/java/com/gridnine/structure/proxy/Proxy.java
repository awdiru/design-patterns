package com.gridnine.structure.proxy;

// Client class
class Client {
    public static void main(String[] args) {
        Subject subject = new Proxy();
        subject.request();
    }
}

// Subject abstract class
abstract class Subject {
    public abstract void request();
}

// RealSubject class
class RealSubject extends Subject {
    @Override
    public void request() {
        System.out.println("RealSubject: Handling request.");
    }
}

// Proxy class
class Proxy extends Subject {
    private RealSubject realSubject;

    @Override
    public void request() {
        if (realSubject == null) {
            realSubject = new RealSubject(); // Ленивая инициализация
        }
        realSubject.request();
    }
}
