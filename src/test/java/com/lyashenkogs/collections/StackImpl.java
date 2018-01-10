package com.lyashenkogs.collections;


public class StackImpl implements Stack {
    //Implement array resizing or use ArrayList
    private Object[] items = new Object[5];
    private int itemsCount = 0;

    @Override
    public Object pop() {
        Object returned = items[itemsCount - 1];
        itemsCount--;
        return returned;
    }

    @Override
    public int size() {
        return itemsCount;
    }

    @Override
    public void push(Object o) {
        items[itemsCount] = o;
        itemsCount++;
    }
}