package com.gridnine.structure.linked_map;

import java.util.*;

public class LinkedMap<K, V> implements Map<K, V> {
    private Node<K, V> first;
    private Node<K, V> end;
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
        Node<K, V> point = first;
        while (point != null) {
            if (point.value.equals(value)) return true;
            point = point.next;
        }
        return false;
    }

    @Override
    public V get(Object key) {
        Iterator<Entry<K, V>> iterator = entrySet().iterator();
        if (key == null) {
            while (iterator.hasNext()) {
                Entry<K, V> next = iterator.next();
                if (next.getKey() == null)
                    return next.getValue();
            }
        } else {
            while (iterator.hasNext()) {
                Entry<K, V> next = iterator.next();
                if (key.equals(next.getKey()))
                    return next.getValue();
            }
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        Node<K, V> point = getNode((K) key);
        if (point == null) {
            point = new Node<>(key, value);
            if (first == null) first = point;
            else if (size == 1) {
                end = point;
                end.prev = first;
                first.next = end;
            } else {
                Node<K, V> oldEnd = end;
                end = point;
                oldEnd.next = end;
                end.prev = oldEnd;
            }
            size++;
        } else {
            point.value = (V) value;
        }
        return point.value;
    }

    @Override
    public V remove(Object key) {
        Node<K, V> point = first;
        while (point != null) {
            if (point.key.equals(key)) break;
            point = point.next;
        }
        if (point == null) return null;
        else if (first.key.equals(key)) {
            Node<K, V> oldFirst = first;
            first = oldFirst.next;
            if (first != null && first.prev != null) first.prev = null;
            size--;
            return oldFirst.value;

        } else if (end.key.equals(key)) {
            Node<K, V> oldEnd = end;
            end = oldEnd.prev;
            if (end != null && end.next != null) end.next = null;
            size--;
            return oldEnd.value;

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
        end = null;
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        Node<K, V> point = first;
        Set<K> keySet = new HashSet<>(size);
        while (point != null) {
            keySet.add(point.key);
            point = point.next;
        }
        return keySet;
    }

    @Override
    public List<V> values() {
        Node<K, V> point = first;
        List<V> values = new ArrayList<>(size);
        while (point != null) {
            values.add(point.value);
            point = point.next;
        }
        return values;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Node<K, V> point = first;
        Set<Entry<K, V>> entrySet = new HashSet<>(size);
        while (point != null) {
            entrySet.add(new CustomEntry(point.key, point.value));
            point = point.next;
        }
        return entrySet;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        Node<K, V> point = first;
        while (point != null && point.next != null) {
            result.append(point).append("; ");
            point = point.next;
        }
        result.append(point);
        if (result.toString().equals("null")) return "{}";
        return "{" + result + "}";
    }

    private Node<K, V> getNode(K key) {
        Node<K, V> point = first;
        while (point != null) {
            if (point.key.equals(key)) return point;
            point = point.next;
        }
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
