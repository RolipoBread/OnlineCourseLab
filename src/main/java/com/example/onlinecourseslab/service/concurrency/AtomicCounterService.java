package com.example.onlinecourseslab.service.concurrency;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class AtomicCounterService implements CounterService {

    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public int increment() {
        return counter.incrementAndGet();
    }

    @Override
    public int getValue() {
        return counter.get();
    }

    @Override
    public void reset() {
        counter.set(0);
    }
}