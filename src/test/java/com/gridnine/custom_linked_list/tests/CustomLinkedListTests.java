package com.gridnine.custom_linked_list.tests;


import com.gridnine.custom_classes.custom_list.CustomLinkedList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class CustomLinkedListTests {
    List<String> list;

    @BeforeEach
    public void initList() {
        list = new CustomLinkedList<>();
    }

    @Test
    public void addAllTest() {
        list.addAll(List.of("String 1", "String 2", "String 3"));
        String listMust = "{size=3; String 1, String 2, String 3}";

        Assertions.assertEquals(3, list.size());
        Assertions.assertEquals(listMust, list.toString());
    }

    @Test
    public void addTest() {
        list.add("String 1");
        list.add("String 2");
        list.add("String 3");
        String listMust = "{size=3; String 1, String 2, String 3}";

        Assertions.assertEquals(3, list.size());
        Assertions.assertEquals(listMust, list.toString());
    }

    @Test
    public void removeTest() {
        getList();
        list.remove(1);
        String listMust = "{size=2; String 1, String 3}";

        Assertions.assertEquals(2, list.size());
        Assertions.assertEquals(listMust, list.toString());
    }

    @Test
    public void removeObjectTest() {
        getList();
        list.remove("String 2");
        String listMust = "{size=2; String 1, String 3}";

        Assertions.assertEquals(2, list.size());
        Assertions.assertEquals(listMust, list.toString());
    }

    @Test
    public void isEmptyTest() {
        getList();
        list.removeFirst();
        list.removeFirst();
        list.removeFirst();
        String listMust = "{size=0}";

        Assertions.assertEquals(0, list.size());
        Assertions.assertTrue(list.isEmpty());
        Assertions.assertEquals(listMust, list.toString());
    }

    @Test
    public void clearTest() {
        getList();
        list.clear();
        String listMust = "{size=0}";

        Assertions.assertEquals(0, list.size());
        Assertions.assertTrue(list.isEmpty());
        Assertions.assertEquals(listMust, list.toString());
    }

    @Test
    public void containsTest() {
        getList();
        Assertions.assertTrue(list.contains("String 1"));
        Assertions.assertTrue(list.contains("String 2"));
        Assertions.assertTrue(list.contains("String 3"));
    }

    @Test
    public void containsAllTest() {
        getList();
        List<String> conList = List.of("String 1", "String 3");
        Assertions.assertTrue(list.containsAll(conList));
        conList = List.of("String 1", "String 3", "String new");
        Assertions.assertFalse(list.containsAll(conList));
    }

    @Test
    public void iteratorTest() {
        getList();
        Iterator<String> iterator = list.iterator();

        Assertions.assertTrue(iterator.hasNext());
        Assertions.assertEquals("String 1", iterator.next());
        Assertions.assertTrue(iterator.hasNext());
        Assertions.assertEquals("String 2", iterator.next());
        Assertions.assertTrue(iterator.hasNext());
        Assertions.assertEquals("String 3", iterator.next());
        Assertions.assertFalse(iterator.hasNext());
    }

    @Test
    public void toArrayTest() {
        getList();
        Object[] a = list.toArray();

        Assertions.assertEquals("String 1", a[0]);
        Assertions.assertEquals("String 2", a[1]);
        Assertions.assertEquals("String 3", a[2]);
        Assertions.assertEquals(3, a.length);
    }

    @Test
    public void removeAllTest() {
        getList();
        List<String> remList = List.of("String 1", "String 3");
        list.removeAll(remList);
        String listMust = "{size=1; String 2}";

        Assertions.assertEquals(1, list.size());
        Assertions.assertEquals(listMust, list.toString());

        getList();
        remList = List.of("String 2");
        list.removeAll(remList);
        listMust = "{size=2; String 1, String 3}";

        Assertions.assertEquals(2, list.size());
        Assertions.assertEquals(listMust, list.toString());

        getList();
        remList = List.of("String 1", "String 3");
        list.removeAll(remList);
        listMust = "{size=1; String 2}";

        Assertions.assertEquals(1, list.size());
        Assertions.assertEquals(listMust, list.toString());
    }

    @Test
    public void retainAllTest() {
        getList();
        List<String> retList = List.of("String 2");
        list.retainAll(retList);
        String listMust = "{size=1; String 2}";

        Assertions.assertEquals(1, list.size());
        Assertions.assertEquals(listMust, list.toString());

        getList();
        retList = List.of("String 2", "String 1");
        list.retainAll(retList);
        listMust = "{size=3; String 2, String 1, String 2}";

        Assertions.assertEquals(3, list.size());
        Assertions.assertEquals(listMust, list.toString());
    }

    @Test
    public void getTest() {
        getList();

        Assertions.assertEquals("String 2", list.get(1));
    }

    @Test
    public void setTest() {
        getList();
        list.set(1, "String new");
        String listMust = "{size=3; String 1, String new, String 3}";

        Assertions.assertEquals("String new", list.get(1));
        Assertions.assertEquals(listMust, list.toString());
    }

    @Test
    public void indexOfTest() {
        getList();
        Assertions.assertEquals(1, list.indexOf("String 2"));
    }

    @Test
    public void listIteratorTest() {
        getList();
        ListIterator<String> itr = list.listIterator();

        Assertions.assertTrue(itr.hasNext());
        Assertions.assertFalse(itr.hasPrevious());
        Assertions.assertEquals("String 1", itr.next());
        Assertions.assertEquals("String 2", itr.next());
        Assertions.assertTrue(itr.hasPrevious());
        Assertions.assertEquals("String 1", itr.previous());
        Assertions.assertEquals(-1, itr.previousIndex());
        Assertions.assertEquals(1, itr.nextIndex());
        itr.remove();
        Assertions.assertEquals("{String 2, String 3}", itr.toString());
    }

    @Test
    public void subListTest() {
        getList();
        getList();
        List<String> subList = list.subList(0, 4);
        Assertions.assertEquals(List.of("String 1", "String 2", "String 3", "String 1"), subList);
        subList = list.subList(2, 5);

        Assertions.assertEquals(List.of("String 3", "String 1", "String 2"), subList);
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> list.subList(-1, 7));
        Assertions.assertEquals("Передан некорректный индекс", exception.getMessage());
    }

    private void getList() {
        list.addAll(List.of("String 1", "String 2", "String 3"));
    }
}
