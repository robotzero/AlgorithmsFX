package com.robotzero.sandbox.algorithms.stack;

public class ListStack<T> implements Stack<T> {
  private final java.util.LinkedList<T> data = new java.util.LinkedList<>();

  public ListStack() {
  };

  public ListStack(T elem) {
    push(elem);
  }

  @Override
  public int size() {
    return data.size();
  }

  @Override
  public boolean isEmpty() {
    return size() == 0;
  }

  @Override
  public void push(T elem) {
    data.addLast(elem);
  }

  @Override
  public T pop() {
    return data.removeLast();
  }

  @Override
  public T peek() {
    return data.peekLast();
  }
}
