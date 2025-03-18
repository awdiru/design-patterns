package com.gridnine.structure.flyweight;

import java.util.HashMap;
import java.util.Map;

// Flyweight
class CharacterFlyweight {
    private char symbol;
    private String font;
    private int size;

    public CharacterFlyweight(char symbol, String font, int size) {
        this.symbol = symbol;
        this.font = font;
        this.size = size;
    }

    public void draw(int x, int y) {
        System.out.printf("Symbol: %c, Font: %s, Size: %d, Position: (%d, %d)\n",
                symbol, font, size, x, y);
    }
}

// FlyweightFactory
class CharacterFactory {
    private Map<String, CharacterFlyweight> characters = new HashMap<>();

    public CharacterFlyweight getCharacter(char symbol, String font, int size) {
        String key = symbol + font + size;
        if (!characters.containsKey(key)) {
            characters.put(key, new CharacterFlyweight(symbol, font, size));
        }
        return characters.get(key);
    }
}

// Клиент
class TextEditor {
    public static void main(String[] args) {
        CharacterFactory factory = new CharacterFactory();

        // Создаем символы с общим шрифтом и размером
        CharacterFlyweight a1 = factory.getCharacter('A', "Arial", 12);
        a1.draw(10, 20); // Внешнее состояние: координаты

        CharacterFlyweight a2 = factory.getCharacter('A', "Arial", 12);
        a2.draw(30, 40);

        // a1 и a2 ссылаются на один и тот же объект в памяти
        System.out.println(a1 == a2); // true
    }
}