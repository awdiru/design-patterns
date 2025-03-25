package com.gridnine.custom_classes.custom_map;

import java.util.*;

public class LinkedMap<K, V> implements Map<K, V> {
    private Node<K, V> first;
    private Node<K, V> last;
    private int size = 0;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return get(key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        for (V v : this.values()) if (v.equals(value)) return true;
        return false;
    }

    @Override
    public V get(Object key) {
        if (key == null) {
            for (Node<K, V> point = first; point != null; point = point.next)
                if (point.key == null) return point.value;

        } else for (Node<K, V> point = first; point != null; point = point.next)
            if (point.key.equals(key)) return point.value;

        return null;
    }

    @Override
    public V put(K key, V value) {
        Node<K, V> point = getNode(key);
        if (point == null) {
            point = new Node<>(key, value);
            if (size == 0) {
                first = point;
                last = first;

            } else if (size == 1) {
                last = point;
                last.prev = first;
                first.next = last;

            } else {
                Node<K, V> oldLast = last;
                last = point;
                oldLast.next = last;
                last.prev = oldLast;
            }
            size++;
        } else {
            point.value = value;
        }
        return point.value;
    }

    @Override
    public V remove(Object key) {
        Node<K, V> point = getNode(key);
        if (point == null) return null;
        else if (first.key.equals(key)) {
            Node<K, V> oldFirst = first;
            first = oldFirst.next;
            if (first != null) first.prev = null;
            size--;
            return oldFirst.value;

        } else if (last.key.equals(key)) {
            Node<K, V> oldLast = last;
            last = oldLast.prev;
            if (last != null) last.next = null;
            size--;
            return oldLast.value;

        } else {
            Node<K, V> prevPoint = point.prev;
            Node<K, V> nextPoint = point.next;
            prevPoint.next = nextPoint;
            nextPoint.prev = prevPoint;
            size--;
            return point.value;
        }
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        for (Entry<? extends K, ? extends V> e : map.entrySet())
            put(e.getKey(), e.getValue());
    }

    @Override
    public void clear() {
        first = null;
        last = null;
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>(size);
        for (Node<K, V> point = first; point != null; point = point.next)
            keySet.add(point.key);
        return keySet;
    }

    @Override
    public List<V> values() {
        List<V> values = new ArrayList<>(size);
        for (Node<K, V> point = first; point != null; point = point.next)
            values.add(point.value);
        return values;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> entrySet = new HashSet<>(size);
        for (Node<K, V> point = first; point != null; point = point.next)
            entrySet.add(new CustomEntry(point.key, point.value));
        return entrySet;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        Node<K, V> point;
        for (point = first; point != null && point.next != null; point = point.next)
            result.append(point).append(", ");
        result.append(point);
        return result.toString().equals("null") ? "{}" : "{" + result + "}";
    }

    private Node<K, V> getNode(Object key) {
        for (Node<K, V> point = first; point != null; point = point.next)
            if (point.key.equals(key)) return point;
        return null;
    }

    private static class Node<K, V> {
        private final K key;
        private V value;
        private Node<K, V> prev = null;
        private Node<K, V> next = null;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return key.toString() + ": " + value.toString();
        }
    }

    private class CustomEntry implements Entry<K, V> {
        private final K key;
        private V value;

        public CustomEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            this.value = value;
            return this.value;
        }
    }
}
