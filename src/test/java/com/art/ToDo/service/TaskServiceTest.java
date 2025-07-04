package com.art.ToDo.service;

import com.art.ToDo.model.entity.Task;
import com.art.ToDo.repository.TaskRepository;
import com.art.ToDo.service.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task task;

    @BeforeEach
    void setUp() {
        task = new Task(UUID.randomUUID(), "Test Task", "Pending", Collections.emptyList());
    }

    @Test
    void testCreateTask() {
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        Task createdTask = taskService.createTask(new Task());
        assertNotNull(createdTask);
        assertEquals(task.getDescription(), createdTask.getDescription());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void testGetAllTasks() {
        when(taskRepository.findAll()).thenReturn(List.of(task));
        List<Task> tasks = taskService.getAllTasks();
        assertFalse(tasks.isEmpty());
        assertEquals(1, tasks.size());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void testGetTaskById_Success() {
        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
        Task foundTask = taskService.getTaskById(task.getId());
        assertNotNull(foundTask);
        assertEquals(task.getId(), foundTask.getId());
        verify(taskRepository, times(1)).findById(task.getId());
    }

    @Test
    void testGetTaskById_NotFound() {
        UUID id = UUID.randomUUID();
        when(taskRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> taskService.getTaskById(id));
        verify(taskRepository, times(1)).findById(id);
    }

    @Test
    void testUpdateTask_Success() {
        Task updatedDetails = new Task();
        updatedDetails.setDescription("Updated Task");
        updatedDetails.setStatus("Completed");

        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Task updatedTask = taskService.updateTask(task.getId(), updatedDetails);

        assertNotNull(updatedTask);
        assertEquals("Updated Task", updatedTask.getDescription());
        assertEquals("Completed", updatedTask.getStatus());
        verify(taskRepository, times(1)).findById(task.getId());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void testUpdateTask_NotFound() {
        UUID id = UUID.randomUUID();
        Task updatedDetails = new Task();
        when(taskRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> taskService.updateTask(id, updatedDetails));
        verify(taskRepository, times(1)).findById(id);
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void testDeleteTask() {
        doNothing().when(taskRepository).deleteById(task.getId());
        taskService.deleteTask(task.getId());
        verify(taskRepository, times(1)).deleteById(task.getId());
    }
}
