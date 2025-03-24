package com.gridnine.behaviour.interpreter;

import java.util.ArrayList;
import java.util.List;
/* I    | II  | III | IV  | V   | VI  | VII | VIII | IX
 * 1    | 2   |  3  | 4   | 5   | 6   | 7   | 8    | 9
 * _____|_____|_____|_____|_____|_____|_____|______|___
 * X    | XX  | XXX | XL  | L   | LX  | LXX | LXXX | XC
 * 10   | 20  | 30  | 40  | 50  | 60  | 70  | 80   | 90
 * _____|_____|_____|_____|_____|_____|_____|______|___
 * C    | CC  | CCC | CD  | D   | DC  | DCC | DCCC | MC
 * 100  | 200 | 300 | 400 | 500 | 600 | 700 | 800  |900
 * _____|_____|_____|_____|_____|_____|_____|______|___
 * M    |
 * 1000 | */

// MainApp class
class Client {
    public static void main(String[] args) {
        String roman = "MMMCMXLV";
        int arab = Interpreter.interpret(roman);
        System.out.printf("%s = %d\n", roman, arab);
    }
}

class Interpreter {
    static Context context = new Context();

    static int interpret(String input) {
        context.setInput(input);

        List<Expression> tree = new ArrayList<>();
        tree.add(new ThousandExpression());
        tree.add(new HundredExpression());
        tree.add(new TenExpression());
        tree.add(new OneExpression());

        // Интерпретатор
        for (Expression exp : tree) exp.interpret(context);
        return context.getOutput();
    }
}

// Context class
class Context {
    private String input;
    private int output;

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public int getOutput() {
        return output;
    }

    public void setOutput(int output) {
        this.output = output;
    }
}


// AbstractExpression class
abstract class Expression {
    public void interpret(Context context) {
        if (context.getInput() == null || context.getInput().isEmpty()) return;

        if (context.getInput().startsWith(nine())) {
            context.setOutput(context.getOutput() + 9 * multiplier());
            context.setInput(context.getInput().substring(2));

        } else if (context.getInput().startsWith(five())) {
            context.setOutput(context.getOutput() + 5 * multiplier());
            context.setInput(context.getInput().substring(1));

        } else if (context.getInput().startsWith(four())) {
            context.setOutput(context.getOutput() + 4 * multiplier());
            context.setInput(context.getInput().substring(2));
        }

        for (;context.getInput().startsWith(one()); context.setInput(context.getInput().substring(1)))
            context.setOutput(context.getOutput() + multiplier());
    }

    public abstract String one();

    public abstract String four();

    public abstract String five();

    public abstract String nine();

    public abstract int multiplier();
}

// ThousandExpression class
class ThousandExpression extends Expression {
    @Override
    public String one() {
        return "M";
    }

    @Override
    public String four() {
        return " ";
    }

    @Override
    public String five() {
        return " ";
    }

    @Override
    public String nine() {
        return " ";
    }

    @Override
    public int multiplier() {
        return 1000;
    }
}

// HundredExpression class
class HundredExpression extends Expression {
    @Override
    public String one() {
        return "C";
    }

    @Override
    public String four() {
        return "CD";
    }

    @Override
    public String five() {
        return "D";
    }

    @Override
    public String nine() {
        return "CM";
    }

    @Override
    public int multiplier() {
        return 100;
    }
}

// TenExpression class
class TenExpression extends Expression {
    @Override
    public String one() {
        return "X";
    }

    @Override
    public String four() {
        return "XL";
    }

    @Override
    public String five() {
        return "L";
    }

    @Override
    public String nine() {
        return "XC";
    }

    @Override
    public int multiplier() {
        return 10;
    }
}

// OneExpression class
class OneExpression extends Expression {
    @Override
    public String one() {
        return "I";
    }

    @Override
    public String four() {
        return "IV";
    }

    @Override
    public String five() {
        return "V";
    }

    @Override
    public String nine() {
        return "IX";
    }

    @Override
    public int multiplier() {
        return 1;
    }
}
