package com.gridnine.structure.facade;

// MainApp class
class MainApp {
    public static void main(String[] args) {
        Facade facade = new Facade();

        facade.methodA();
        facade.methodB();

        // Wait for user (optional in Java)
        // In Java, console applications typically exit after main() completes.
    }
}

// SubSystemOne class
class SubSystemOne {
    public void methodOne() {
        System.out.println(" SubSystemOne Method");
    }
}

// SubSystemTwo class
class SubSystemTwo {
    public void methodTwo() {
        System.out.println(" SubSystemTwo Method");
    }
}

// SubSystemThree class
class SubSystemThree {
    public void methodThree() {
        System.out.println(" SubSystemThree Method");
    }
}

// SubSystemFour class
class SubSystemFour {
    public void methodFour() {
        System.out.println(" SubSystemFour Method");
    }
}

// Facade class
class Facade {
    private SubSystemOne one;
    private SubSystemTwo two;
    private SubSystemThree three;
    private SubSystemFour four;

    public Facade() {
        one = new SubSystemOne();
        two = new SubSystemTwo();
        three = new SubSystemThree();
        four = new SubSystemFour();
    }

    public void methodA() {
        System.out.println("\nMethodA() ---- ");
        one.methodOne();
        two.methodTwo();
        four.methodFour();
    }

    public void methodB() {
        System.out.println("\nMethodB() ---- ");
        two.methodTwo();
        three.methodThree();
    }
}