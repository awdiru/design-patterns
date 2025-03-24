package com.gridnine.custom_classes.custom_list;

import java.lang.reflect.Array;
import java.util.*;

public class CustomLinkedList<V> implements List<V> {
    private static final String ERROR_INDEX = "Передан некорректный индекс";
    Node<V> first;
    Node<V> last;
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
        for (V v : this) if (v.equals(o)) return true;
        return false;
    }

    @Override
    public Iterator<V> iterator() {
        return new Itr<>(new Node<>(null, null, first));
    }


    @Override
    public Object[] toArray() {
        Object[] values = new Object[size];
        int i = 0;
        for (V v : this)
            values[i++] = v;

        return values;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size)
            a = (T[]) Array.newInstance(a.getClass().getComponentType(), size);
        int i = 0;
        Object[] result = a;
        for (V v : this)
            result[i++] = v;

        if (a.length > size)
            a[size] = null;

        return a;
    }

    @Override
    public boolean add(V v) {
        if (size == 0) {
            first = new Node<>(v, null, null);
            last = first;
        } else {
            Node<V> oldLast = last;
            last = new Node<>(v, oldLast, null);
            oldLast.next = last;
        }
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (size != 0) {
            int i = 0;
            for (Node<V> point = first; point != null; point = point.next, i++) {
                if (point.value.equals(o)) {
                    if (removeFirstOrLastOrNothing(i) != null) return true;
                    point.prev.next = point.next;
                    point.next.prev = point.prev;
                    size--;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (size < c.size()) return false;
        for (Object o : c) if (!contains(o)) return false;
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends V> c) {
        for (V v : c) add(v);
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends V> c) {
        if (index == size) return addAll(c);

        Node<V> point = getNode(index - 1);
        Node<V> oldNext = point.next;
        for (V v : c) {
            point.next = new Node<>(v, point, null);
            point = point.next;
        }
        point.next = oldNext;
        oldNext.prev = point;
        size += c.size();
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        int oldSize = size;
        for (Object o : c) while (remove(o)) ;
        return size != oldSize;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        int oldSize = size;
        for (V v : this) if (!c.contains(v)) remove(v);
        return size != oldSize;
    }

    @Override
    public void clear() {
        first = null;
        last = null;
        size = 0;
    }

    @Override
    public V get(int index) {
        Node<V> point = getNode(index);
        return point.value;
    }

    @Override
    public V set(int index, V element) {
        Node<V> point = getNode(index);
        return point.value = element;
    }

    @Override
    public void add(int index, V element) {
        if (index == size) add(element);
        else if (index == 0) {
            Node<V> oldFirst = first;
            first = new Node<>(element, null, oldFirst);
            oldFirst.prev = first;
            size++;
        } else {
            Node<V> prev = getNode(index - 1);
            Node<V> next = prev.next;
            prev.next = new Node<>(element, prev, next);
            next.prev = prev.next;
            size++;
        }
    }

    @Override
    public V remove(int index) {
        V v = removeFirstOrLastOrNothing(index);
        if (v == null) {
            Node<V> point = getNode(index);
            v = point.value;
            point.prev.next = point.next;
            point.next.prev = point.prev;
            size--;
        }
        return v;
    }

    @Override
    public int indexOf(Object o) {
        int index = 0;
        for (V v : this) {
            if (v.equals(o)) return index;
            index++;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int index = 0;
        int resultIndex = -1;
        for (V v : this) {
            if (v.equals(o)) resultIndex = index;
            index++;
        }
        return resultIndex;
    }

    @Override
    public ListIterator<V> listIterator() {
        return new CustomListIterator<>(new Node<>(null, null, first), -1);
    }

    @Override
    public ListIterator<V> listIterator(int index) {
        return new CustomListIterator<>(getNode(index), index);
    }

    @Override
    public List<V> subList(int fromIndex, int toIndex) {
        if (fromIndex > toIndex)
            throw new IllegalArgumentException(ERROR_INDEX);

        checkIndex(toIndex);
        Node<V> point = getNode(fromIndex);

        List<V> subList = new ArrayList<>(toIndex - fromIndex);
        for (int i = fromIndex; i < toIndex; point = point.next, i++)
            subList.add(point.value);
        return subList;
    }

    @Override
    public String toString() {
        if (first == null) return "{size=" + size + "}";

        StringBuilder builder = new StringBuilder();
        for (Node<V> point = first; point.next != null; point = point.next)
            builder.append(point.value).append(", ");
        builder.append(last);
        return "{" +
                "size=" + size + "; " +
                builder +
                '}';
    }

    private Node<V> getNode(int index) {
        checkIndex(index);
        Node<V> point = first;
        for (int i = 0; i < index; i++) point = point.next;
        return point;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0)
            throw new IllegalArgumentException(ERROR_INDEX);
    }

    private V removeFirstOrLastOrNothing(int index) {
        V v = null;
        if (index == 0) {
            v = first.value;
            first = first.next;
            if (first != null)
                first.prev = null;
        }
        if (index == size - 1) {
            v = last.value;
            last = last.prev;
            if (last != null)
                last.next = null;
        }
        size--;
        return v;
    }

    private static class Node<V> {
        Node<V> next;
        Node<V> prev;
        V value;

        public Node(V value, Node<V> prev, Node<V> next) {
            this.next = next;
            this.prev = prev;
            this.value = value;
        }

        @Override
        public String toString() {
            if (value != null)
                return value.toString();
            return null;
        }
    }

    private static class Itr<V> implements Iterator<V> {
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

    private static class CustomListIterator<V> implements ListIterator<V> {
        Node<V> point;
        int index;

        public CustomListIterator(Node<V> point, int index) {
            this.point = point;
            this.index = index;
        }

        @Override
        public boolean hasNext() {
            return point.next != null;
        }

        @Override
        public V next() {
            if (point.next == null)
                throw new RuntimeException("Последний элемент");
            point = point.next;
            index++;
            return point.value;
        }

        @Override
        public boolean hasPrevious() {
            return point.prev != null;
        }

        @Override
        public V previous() {
            if (point.prev == null)
                throw new RuntimeException("Первый элемент");
            point = point.prev;
            index--;
            return point.value;
        }

        @Override
        public int nextIndex() {
            return index + 1;
        }

        @Override
        public int previousIndex() {
            return index - 1;
        }

        @Override
        public void remove() {
            if (point.prev != null)
                point.prev.next = point.next;
            if (point.next != null)
                point.next.prev = point.prev;

            point = point.next;
        }

        @Override
        public void set(V v) {
            point.value = v;
        }

        @Override
        public void add(V v) {
            Node<V> oldNext = point.next;
            point.next = new Node<>(v, point, oldNext);
            if (oldNext != null)
                oldNext.prev = point.next;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            while (hasNext()) {
                if (point != null)
                    builder.append(point).append(", ");
                next();
            }
            builder.append(point);
            return "{" +
                    builder +
                    "}";
        }
    }
}
