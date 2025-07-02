package com.art.ToDo.service;

import com.art.ToDo.model.entity.Task;
import com.art.ToDo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {

    private final TaskRepository TASK_REPOSITORY;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.TASK_REPOSITORY = taskRepository;
    }

    public Task createTask(Task data) {
        return TASK_REPOSITORY.save(data);
    }

    public List<Task> getAllTasks() {
        return TASK_REPOSITORY.findAll();
    }

    public Optional<Task> getTaskById(UUID id) {
        return TASK_REPOSITORY.findById(id);
    }

    public Task updateTask(UUID id, Task data) {
        Task update = TASK_REPOSITORY.findById(id).map(
                updateTask -> {
                    updateTask.setDescription(data.getDescription());
                    updateTask.setStatus(data.getStatus());
                    // updateTask.setPersons(data.getPersons());
                    return TASK_REPOSITORY.save(updateTask);
                }
        ).orElseThrow(
                () -> new RuntimeException("Tarefas não econtrada")
        );
        return update;
    }

    public void deleteTask(UUID id) {
        TASK_REPOSITORY.deleteById(id);
    }
}
