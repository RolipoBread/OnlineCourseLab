package com.example.onlinecourseslab.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.example.onlinecourseslab.service.concurrency.CounterDemoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/counter-demo")
@RequiredArgsConstructor
@Tag(name = "Counter Demo", description = "Демонстрация потокобезопасного и непотокобезопасного счётчика")
public class CounterController {

    private final CounterDemoService service;

    @Operation(
        summary = "Небезопасный счётчик",
        description = "Запускает 1000 инкрементов в нескольких потоках без синхронизации. " +
            "Результат обычно меньше ожидаемого из-за race condition."
    )
    @PostMapping("/unsafe")
    public String unsafe() throws InterruptedException {
        return service.runUnsafe();
    }

    @Operation(
        summary = "Потокобезопасный счётчик",
        description = "Запускает 1000 инкрементов с использованием AtomicInteger. " +
            "Результат всегда корректный."
    )
    @PostMapping("/safe")
    public String safe() throws InterruptedException {
        return service.runSafe();
    }
}