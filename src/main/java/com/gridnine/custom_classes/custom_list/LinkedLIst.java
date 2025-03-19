package com.gridnine.custom_classes.custom_list;

import java.lang.reflect.Array;
import java.util.*;

public class LinkedLIst<V> implements List<V> {
    Node<V> first;
    Node<V> end;
    int size = 0;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        Node<V> point = first;
        while (point != null) {
            if (point.value.equals(o)) return true;
            point = point.next;
        }
        return false;
    }

    @Override
    public Iterator<V> iterator() {
        return new Itr<>(first);
    }


    @Override
    public Object[] toArray() {
        Object[] values = new Object[size];
        Node<V> point = first;
        int i = 0;
        while (point != null) {
            values[i++] = point.value;
            point = point.next;
        }
        return values;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size)
            a = (T[]) Array.newInstance(a.getClass().getComponentType(), size);

        Object[] values = a;
        Node<V> point = first;
        int i = 0;

        while (point != null) {
            values[i++] = point.value;
            point = point.next;
        }

        if (a.length > size)
            a[size] = null;

        return a;
    }

    @Override
    public boolean add(V v) {
        if (size == 0) {
            first = new Node<>(v);
            end = first;
            size++;
            return true;

        } else if (size == 1) {
            end = new Node<>(v);
            first.next = end;
            size++;
            return true;

        } else {
            Node<V> oldEnd = end;
            end = new Node<>(v);
            oldEnd.next = end;
            return true;
        }
    }

    @Override
    public boolean remove(Object o) {
        if (size == 0) return false;

        else if (size == 1) {
            if (first.value.equals(o)) {
                first = null;
                end = null;
                size--;
                return true;
            } else return false;

        } else {
            Node<V> point = first;
            if (first.value.equals(o)) {
                first = point.next;
                size--;
                return true;
            }
            Node<V> prev = point;
            point = point.next;
            while (point != null) {
                if (point.value.equals(o)) {
                    prev.next = point.next;
                    size--;
                    return true;
                }
                prev = point;
                point = point.next;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (size == 0 || size < c.size()) return false;
        for (Object o : c) if (!contains(o)) return false;
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends V> c) {
        if (size == 0) {
            V[] a = (V[]) c.toArray();
            Node<V> point = new Node<>(a[0]);
            first = point;
            for (int i = 1; i < a.length - 1; i++) {
                point.next = new Node<>(a[i]);
                point = point.next;
            }
            point = new Node<>(a[a.length - 1]);
            end = point;

        } else {
            Node<V> point = end;
            for (V v : c) {
                point.next = new Node<>(v);
                point = point.next;
            }
            end = point;
        }

        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends V> c) {
        if (index > size || index < 0) return false;
        if (index == size) return addAll(c);

        Node<V> point = first;
        for (int i = 0; i < index; i++) point = point.next;

        Node<V> oldNext = point.next;
        for (V v : c) {
            point.next = new Node<>(v);
            point = point.next;
        }
        point.next = oldNext;
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        int i = 0;
        for (Object v : c)
            if (remove(v)) {
                i++;
                size--;
            }
        return i > 0;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        Node<V> point = first;
        int i = 0;
        while (point != null) {
            if (!c.contains(point.value)) {
                remove(i);
                size--;
            }
            point = point.next;
            i++;
        }
        return i > 0;
    }

    @Override
    public void clear() {
        first = null;
        end = null;
        size = 0;
    }

    @Override
    public V get(int index) {
        if (index >= size || index < 0) return null;
        Node<V> point = getNode(index);
        if (point == null) return null;
        return point.value;
    }

    @Override
    public V set(int index, V element) {
        if (index >= size || index < 0) return null;
        Node<V> point = getNode(index);
        if (point == null) return null;
        point.value = element;
        return point.value;
    }

    @Override
    public void add(int index, V element) {
        if (index > size || index < 0) return;
        else if (index == size) add(element);
        else {
            Node<V> point = first;
            for (int i = 0; i < index; i++) point = point.next;

            Node<V> oldNext = point.next;
            point.next = new Node<>(element);
            point = point.next;
            point.next = oldNext;
        }
    }

    @Override
    public V remove(int index) {
        if (index >= size || index < 0) return null;
        if (index == 0) {
            V v = first.value;
            first = first.next;
            size--;
            return v;
        }
        Node<V> point = first.next;
        Node<V> prev = first;
        for (int i = 1; i < index; i++) {
            prev = point;
            point = point.next;
        }
        prev.next = point.next;
        size--;
        return point.value;
    }

    @Override
    public int indexOf(Object o) {
        Node<V> point = first;
        int index = 0;
        while (point != null) {
            if (point.value.equals(o)) return index;
            point = point.next;
            index++;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        Node<V> point = first;
        int index = 0;
        int resultIndex = -1;
        while (point != null) {
            if (point.value.equals(o)) resultIndex = index;
            point = point.next;
            index++;
        }
        return resultIndex;
    }

    @Override
    public ListIterator<V> listIterator() {
        return null;
    }

    @Override
    public ListIterator<V> listIterator(int index) {
        return null;
    }

    @Override
    public List<V> subList(int fromIndex, int toIndex) {
        if (fromIndex >= size
                || fromIndex < 0
                || toIndex >= size
                || toIndex < 0
                || fromIndex > toIndex)
            return null;

        Node<V> point = first;
        for (int i = 0; i < fromIndex; i++) point = point.next;

        List<V> subList = new ArrayList<>(toIndex - fromIndex);
        for (int i = fromIndex; i < toIndex; i++) {
            subList.add(point.value);
            point = point.next;
        }
        return subList;
    }

    @Override
    public String toString() {
        Node<V> point = first;
        if (point == null) return "{size=" + size + "}";
        StringBuilder builder = new StringBuilder();
        while (point.next != null) builder.append(point.value).append(", ");
        builder.append(point);
        return "{" +
                "size=" + size + "; " +
                builder +
                '}';
    }

    private Node<V> getNode(int index) {
        if (index >= size || index < 0) return null;
        Node<V> point = first;

        for (int i = 0; i < index; i++) point = point.next;

        return point;
    }

    private static class Node<V> {
        Node<V> next;
        V value;

        public Node(V value) {
            this.next = null;
            this.value = value;
        }

        @Override
        public String toString() {
            return value.toString();
        }
    }

    private class Itr<V> implements Iterator<V> {
        Node<V> point;

        public Itr(Node<V> point) {
            this.point = point;
        }

        @Override
        public boolean hasNext() {
            return point.next != null;
        }

        @Override
        public V next() {
            point = point.next;
            return point.value;
        }
    }
}
