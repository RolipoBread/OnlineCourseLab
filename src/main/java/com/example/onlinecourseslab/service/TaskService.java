package com.example.onlinecourseslab.service;

import com.example.onlinecourseslab.domain.TaskStatus;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TaskService {

    private final Map<String, TaskStatus> tasks = new ConcurrentHashMap<>();

    public String createTask() {
        String taskId = UUID.randomUUID().toString();
        tasks.put(taskId, TaskStatus.CREATED);
        return taskId;
    }

    public TaskStatus getStatus(String taskId) {
        return tasks.get(taskId);
    }

    public void setStatus(String taskId, TaskStatus status) {
        tasks.put(taskId, status);
    }
}