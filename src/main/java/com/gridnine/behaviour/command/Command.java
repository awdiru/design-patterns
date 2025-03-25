package com.gridnine.behaviour.command;

// MainApp class
class Client {
    public static void main(String[] args) {
        Receiver receiver = new Receiver();
        Command command = new ConcreteCommand(receiver);
        Invoker invoker = new Invoker();

        invoker.setCommand(command);
        invoker.executeCommand("one state");
        invoker.executeCommand("two State");
        invoker.undoCommand();
    }
}

// Command interface with undo support
interface Command {
    void execute(String state);
    void undo();
}

// ConcreteCommand class
class ConcreteCommand implements Command {
    private Receiver receiver;
    private String previousState;

    // Constructor
    public ConcreteCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute(String state) {
        previousState = receiver.getState();
        receiver.action(state);
    }

    @Override
    public void undo() {
        receiver.setState(previousState);
        System.out.println("Undo: State restored to " + previousState);
    }
}

// Receiver class
class Receiver {
    private String state;

    public void action(String state) {
        this.state = state;
        System.out.println("Called Receiver.action(), State: " + this.state);
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}

// Invoker class
class Invoker {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void executeCommand(String state) {
        command.execute(state);
    }

    public void undoCommand() {
        command.undo();
    }
}