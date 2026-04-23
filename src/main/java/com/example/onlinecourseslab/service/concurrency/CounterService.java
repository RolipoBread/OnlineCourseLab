package com.example.onlinecourseslab.service.concurrency;

public interface CounterService {

    int increment();

    int getValue();

    void reset();
}