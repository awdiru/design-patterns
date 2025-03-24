package com.gridnine.behaviour.chain_of_responsibility;

// Main class
class Client {
    public static void main(String[] args) {
        Receiver receiver = new Receiver(false, true, true);

        PaymentHandler bankPaymentHandler = new BankPaymentHandler();
        PaymentHandler moneyPaymentHandler = new MoneyPaymentHandler();
        PaymentHandler paypalPaymentHandler = new PayPalPaymentHandler();

        // Настраиваем цепочку
        bankPaymentHandler.setSuccessor(paypalPaymentHandler);
        paypalPaymentHandler.setSuccessor(moneyPaymentHandler);

        // Запускаем обработку
        bankPaymentHandler.handle(receiver);
    }
}

// Receiver class
class Receiver {
    // Банковские переводы
    private boolean bankTransfer;
    // Денежные переводы (WesternUnion, Unistream)
    private boolean moneyTransfer;
    // Перевод через PayPal
    private boolean payPalTransfer;

    public Receiver(boolean bankTransfer, boolean moneyTransfer, boolean payPalTransfer) {
        this.bankTransfer = bankTransfer;
        this.moneyTransfer = moneyTransfer;
        this.payPalTransfer = payPalTransfer;
    }

    public boolean isBankTransfer() {
        return bankTransfer;
    }

    public boolean isMoneyTransfer() {
        return moneyTransfer;
    }

    public boolean isPayPalTransfer() {
        return payPalTransfer;
    }
}

// Abstract PaymentHandler class
abstract class PaymentHandler {
    protected PaymentHandler successor;

    public void setSuccessor(PaymentHandler successor) {
        this.successor = successor;
    }

    public abstract void handle(Receiver receiver);
}

// BankPaymentHandler class
class BankPaymentHandler extends PaymentHandler {
    @Override
    public void handle(Receiver receiver) {
        if (receiver.isBankTransfer()) {
            System.out.println("Выполняем банковский перевод");
        } else if (successor != null) {
            successor.handle(receiver);
        }
    }
}

// PayPalPaymentHandler class
class PayPalPaymentHandler extends PaymentHandler {
    @Override
    public void handle(Receiver receiver) {
        if (receiver.isPayPalTransfer()) {
            System.out.println("Выполняем перевод через PayPal");
        } else if (successor != null) {
            successor.handle(receiver);
        }
    }
}

// MoneyPaymentHandler class
class MoneyPaymentHandler extends PaymentHandler {
    @Override
    public void handle(Receiver receiver) {
        if (receiver.isMoneyTransfer()) {
            System.out.println("Выполняем перевод через системы денежных переводов");
        } else if (successor != null) {
            successor.handle(receiver);
        }
    }
}
