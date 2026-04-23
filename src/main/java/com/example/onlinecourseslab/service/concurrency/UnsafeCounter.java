package com.example.onlinecourseslab.service.concurrency;

public class UnsafeCounter {

    private int counter = 0;

    public void increment() {
        counter++;
    }

    public int getValue() {
        return counter;
    }
}