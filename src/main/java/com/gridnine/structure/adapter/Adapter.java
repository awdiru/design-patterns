package com.gridnine.structure.adapter;

// MainApp class
class MainApp {
    public static void main(String[] args) {
        // Create adapter and place a request
        Target target = new Adapter();
        target.request();

        // Wait for user (optional in Java)
        // In Java, console applications typically exit after main() completes.
    }
}

// The 'Target' class
class Target {
    public void request() {
        System.out.println("Called Target request()");
    }
}

// The 'Adapter' class
class Adapter extends Target {
    private Adaptee adaptee = new Adaptee();

    @Override
    public void request() {
        // Possibly do some other work
        // and then call specificRequest
        adaptee.specificRequest();
    }
}

// The 'Adaptee' class
class Adaptee {
    public void specificRequest() {
        System.out.println("Called specificRequest()");
    }
}
