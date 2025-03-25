package com.gridnine.custom_classes.custom_list;

import java.util.List;

class Client {
    public static void main(String[] args) {
        List<String> l = new CustomLinkedList<>();
        System.out.println(l.size() + ", " + l.isEmpty() + ", " + l.contains("1"));
        l.add("one");
        System.out.println(l + ", " + l.contains("one"));
        l.remove("one");
        System.out.println(l);
    }
}
