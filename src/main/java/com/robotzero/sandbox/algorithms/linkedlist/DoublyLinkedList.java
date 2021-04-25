package com.robotzero.sandbox.algorithms.linkedlist;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class DoublyLinkedList<T> implements Iterable<T> {
  private int size = 0;
  private Node<T> head = null;
  private Node<T> tail = null;

  public void clear() {
    Node<T> pointer = head;
    while (pointer != null) {
      Node<T> next = pointer.next;
      pointer.data = null;
      pointer.prev = pointer.next = null;
      pointer = next;
    }
    this.head = null;
    this.tail = null;
    this.size = 0;
  }

  public int size() {
    return size;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public void addLast(T elem) {
    if (isEmpty()) {
      tail = head = new Node<>(elem, null, null);
    } else {
      tail.next = new Node<>(elem, tail, null);
      tail = tail.next;
    }
    size++;
  }

  public void addFirst(T elem) {
    if (isEmpty()) {
      tail = head = new Node<>(elem, null, null);
    } else {
      head.prev = new Node<>(elem, null, head);
    }
    size++;
  }

  public void addAt(int index, T data) throws Exception {
    if (index < 0) {
      throw new Exception("Illegal index");
    }

    if (index == 0) {
      addFirst(data);
      return;
    }

    if (index == size) {
      addLast(data);
      return;
    }

    Node<T> temp = head;
    for (int i = 0; i < index - 1; i++) {
      temp = temp.next;
    }

    Node<T> newElement = new Node<>(data, temp, temp.next);
    temp.next = newElement;
    size++;
  }

  public T peekFirst() {
    if (isEmpty()) {
      throw new RuntimeException("Empty list");
    }

    return head.data;
  }

  public T peekLast() {
    if (isEmpty()) {
      throw new RuntimeException("Empty list");
    }

    return tail.data;
  }

  public T removeFirst() {
    if (isEmpty()) {
      throw new RuntimeException("Empty list");
    }

    if (size == 1) {
      T toRemove = head.data;
      clear();
      return toRemove;
    }
    T toRemove = head.data;
    head = head.next;
    --size;
    if (isEmpty()) {
      tail = null;
    } else {
      head.prev = null;
    }
    return toRemove;
  }

  public T removeLast() {
    if (isEmpty()) {
      throw new RuntimeException("Empty list");
    }

    if (size == 1) {
      T toRemove = tail.data;
      clear();
      return toRemove;
    }

    T toRemove = tail.data;
    tail = tail.prev;
    --size;
    if (isEmpty()) {
      head = null;
    } else {
      tail.next = null;
    }
    return toRemove;
  }

  public T remove(Node<T> node) {
    if (isEmpty()) {
      throw new RuntimeException("Empty list");
    }

    if (node.prev == null) {
      return removeFirst();
    }

    if (node.next == null) {
      return removeLast();
    }

    Node<T> temp = head;
    while (temp != node) {
      temp = temp.next;
    }
    T toRemove = temp.data;
    Node<T> prev = temp.prev;
    Node<T> next = temp.next;

    temp.next.prev = prev;
    temp.prev.next = next;
    temp.data = null;
    temp = temp.next = temp.prev = null;
    --size;
    return toRemove;
  }

  public T removeAt(int index) {
    if (index < 0 || index >= size) {
      throw new IllegalArgumentException();
    }

    if (index == 0) {
      return removeFirst();
    }

    if (index == size - 1) {
      return removeLast();
    }

    int i;
    Node<T> trav;

    if (index < size / 2) {
      for (i = 0, trav = head; i != index; i++) {
        trav = trav.next;
      }
    } else {
      for (i = size - 1, trav = head; i != index; i--) {
        trav = trav.prev;
      }
    }

    return remove(trav);
  }

  public boolean remove(Object obj) {
    Node<T> pointer = head;
    while (!pointer.data.equals(obj)) {
      pointer = pointer.next;
    }

    remove(pointer);
    return true;
  }

  public int indexOf(Object obj) {
    Node<T> pointer = head;
    int index = 0;

    for (; pointer != null; pointer = pointer.next, index++) {
      if (obj.equals(pointer.data)) {
        return index;
      }
    }
    return -1;
  }

  public boolean contains(Object obj) {
    return indexOf(obj) != -1;
  }

  public void add(T elem) {
    addLast(elem);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[ ");
    Node<T> trav = head;
    while (trav != null) {
      sb.append(trav.data);
      if (trav.next != null) {
        sb.append(", ");
      }
      trav = trav.next;
    }
    sb.append(" ]");
    return sb.toString();
  }

  @Override
  public Iterator<T> iterator() {
    return new Iterator<T>() {
      private Node<T> pointer = head;
      @Override
      public boolean hasNext() {
        return pointer.next != null;
      }

      @Override
      public T next() {
        T data = pointer.data;
        pointer = pointer.next;
        return data;
      }
    };
  }

  @Override
  public void forEach(Consumer<? super T> action) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Spliterator<T> spliterator() {
    throw new UnsupportedOperationException();
  }

  private static class Node<T> {
    private Node<T> next = null;
    private Node<T> prev = null;
    private T data;

    public Node(T data, Node<T> prev, Node<T> next) {
      this.data = data;
      this.prev = prev;
      this.next = next;
    }

    @Override
    public String toString() {
      return data.toString();
    }
  }
}
