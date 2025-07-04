package com.art.ToDo.controller;

import com.art.ToDo.model.entity.Task;
import com.art.ToDo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService TASK_SERVICE;

    @Autowired
    public TaskController(TaskService taskService) {
        this.TASK_SERVICE = taskService;
    }

    @PostMapping
    public ResponseEntity<Task> postTask(@RequestBody Task data) {
        Task task = TASK_SERVICE.createTask(data);
        return new ResponseEntity<>(task, HttpStatusCode.valueOf(201));
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = TASK_SERVICE.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable UUID id) {
        Task task = TASK_SERVICE.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> putTask(@PathVariable UUID id, @RequestBody Task data) {
        try {
            Task update = TASK_SERVICE.updateTask(id, data);
            return ResponseEntity.ok((update));
        } catch ( RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID id) {
     TASK_SERVICE.deleteTask(id);
     return ResponseEntity.noContent().build();
    }

}
