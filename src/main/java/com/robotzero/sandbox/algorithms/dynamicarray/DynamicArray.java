package com.robotzero.sandbox.algorithms.dynamicarray;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

@SuppressWarnings("unchecked")
public class DynamicArray<T> implements Iterable<T> {
  /**
   * What user thinks length is (data length)
   */
  private int len = 0;

  /**
   * Actual array capacity (memory reserved)
   */
  private int capacity = 0;

  /**
   * Data container
   */
  private T[] data;

  public DynamicArray(int capacity) {
    if (capacity < 0) {
      throw new IllegalArgumentException("Illegal capacity: " + capacity);
    }
    this.capacity = capacity;
    data = (T[]) new Object[capacity];
  }

  public DynamicArray() {
    this(16);
  }

  public int size() {
    return len;
  }

  public boolean isEmpty() {
    return len == 0;
  }

  public T get(int index) {
    if (index > len || index < 0) {
      throw new IndexOutOfBoundsException();
    }
    return data[index];
  }

  public void set(int index, T elem) {
    if (index > len || index < 0) {
      throw new IndexOutOfBoundsException();
    }
    data[index] = elem;
    ++len;
  }

  public void clear() {
    for (int i = 0; i < len; i++) {
      data[i] = null;
    }
    len = 0;
  }

  public void add(T elem) {
    if (len + 1 >= capacity) {
      if (capacity == 0) {
        capacity = 1;
      } else {
        capacity *= 2;
      }
      T[] newArr = (T[]) new Object[capacity];
      for (int i = 0; i < len; i++) {
        newArr[i] = data[i];
      }
      data = newArr;
    }
    data[len++] = elem;
  }

  public T removeAt(int rm_index) {
    if (rm_index > len || rm_index < 0) {
      throw new IndexOutOfBoundsException();
    }
    T removedData = data[rm_index];
    T[] newArr = (T[]) new Object[len - 1];

    for (int i = 0, j = 0; i < len; i++, j++) {
      if (rm_index == i) {
        j--;
      } else {
        newArr[j] = newArr[i];
      }
    }
    data = newArr;
    capacity = --len;
    return removedData;
  }

  public boolean remove(T obj) {
    int index = indexOf(obj);
    if (index == -1) {
      return false;
    }
    removeAt(index);
    return true;
  }

  public int indexOf(T obj) {
    for (int i = 0; i < len; i++) {
      if (obj == null) {
        if (data[i] == null) {
          return i;
        }
      } else {
        if (data[i].equals(obj)) {
          return i;
        }
      }
    }
    return -1;
  }

  public boolean contains(T obj) {
    return indexOf(obj) != -1;
  }

  @Override
  public Iterator iterator() {
    return new Iterator<>() {
      int index = 0;

      @Override
      public boolean hasNext() {
        return index < len;
      }

      @Override
      public Object next() {
        return get(index++);
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }

      @Override
      public void forEachRemaining(Consumer<? super Object> action) {
        throw new UnsupportedOperationException();
      }
    };
  }

  @Override
  public void forEach(Consumer action) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Spliterator spliterator() {
    throw new UnsupportedOperationException();
  }

  @Override
  public String toString() {
    if (len == 0) return "[]";
    else {
      StringBuilder sb = new StringBuilder(len).append("[");
      for (int i = 0; i < len - 1; i++) sb.append(data[i]).append(", ");
      return sb.append(data[len - 1]).append("]").toString();
    }
  }
}
