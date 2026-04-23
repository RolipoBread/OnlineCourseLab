package com.example.onlinecourseslab.service.concurrency;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class CounterDemoService {

    private final CounterService counterService;

    public String runUnsafe() throws InterruptedException {

        int threads = 1000;
        UnsafeCounter counter = new UnsafeCounter();

        ExecutorService executor = Executors.newFixedThreadPool(50);

        try {
            for (int i = 0; i < threads; i++) {
                executor.submit(counter::increment);
            }
        } finally {
            shutdownExecutor(executor);
        }

        return "UNSAFE RESULT: " + counter.getValue();
    }

    public String runSafe() throws InterruptedException {

        int threads = 1000;

        counterService.reset();

        ExecutorService executor = Executors.newFixedThreadPool(50);

        try {
            for (int i = 0; i < threads; i++) {
                executor.submit(counterService::increment);
            }
        } finally {
            shutdownExecutor(executor);
        }

        return "SAFE RESULT: " + counterService.getValue();
    }

    private void shutdownExecutor(ExecutorService executor) throws InterruptedException {
        executor.shutdown();

        if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
            executor.shutdownNow();
        }
    }
}