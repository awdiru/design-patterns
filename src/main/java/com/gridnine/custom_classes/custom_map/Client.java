package com.gridnine.custom_classes.custom_map;

class Client {
    public static void main(String[] args) {
        LinkedMap<String, String> map = new LinkedMap<>();
        map.put("1", "один");
        map.put("2", "два");
        map.put("1", "one");
        System.out.println(map);
        System.out.println(map.get("2"));
        map.remove("2");
        System.out.println(map);
        //map.put("2", "one");
        map.remove("1");
        System.out.println(map);
        map.put("new", "new");
        System.out.println(map);
    }
}
