package com.company.listing4_16;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Кдасс ImprovedList предполагает, что однажды передав
 * список в его конструктор, клиент не будет использовать базовый список
 * напрямую, но получая к нему доступ только через класс ImprovedList.)
 *
 * Класс ImprovedList добавляет дополнительный уровень блокировки с
 * использованием собственной встроенной блокировки synchronized - оно же synchronized(this)
 */
public class ImprovedList<T> implements List<T> {
    private final List<T> list;

    public ImprovedList(List<T> list) {
        this.list = list;
    }

    public synchronized boolean putIfAbsent(T x) {
        boolean contains = list.contains(x);
        if (contains)
            list.add(x);
        return !contains;
    }

    @Override
    public synchronized int size() {
        return 0;
    }

    @Override
    public synchronized boolean isEmpty() {
        return false;
    }

    @Override
    public synchronized boolean contains(Object o) {
        return false;
    }

    @Override
    public synchronized Iterator<T> iterator() {
        return null;
    }

    @Override
    public synchronized Object[] toArray() {
        return new Object[0];
    }

    @Override
    public synchronized <T1> T1[] toArray(T1[] a) {
        return null;
    }

    @Override
    public synchronized boolean add(T t) {
        return false;
    }

    @Override
    public synchronized boolean remove(Object o) {
        return false;
    }

    @Override
    public synchronized boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public synchronized boolean addAll(Collection<? extends T> c) {
        return false;
    }

    @Override
    public synchronized boolean addAll(int index, Collection<? extends T> c) {
        return false;
    }

    @Override
    public synchronized boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public synchronized boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public synchronized void clear() { list.clear(); }

    @Override
    public synchronized T get(int index) {
        return null;
    }

    @Override
    public T set(int index, T element) {
        return null;
    }

    @Override
    public void add(int index, T element) {

    }

    @Override
    public T remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<T> listIterator() {
        return null;
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return null;
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return null;
    }
// ... similarly delegate other List methods



}
