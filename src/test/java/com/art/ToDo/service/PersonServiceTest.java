package com.art.ToDo.service;

import com.art.ToDo.model.entity.Person;
import com.art.ToDo.model.entity.Task;
import com.art.ToDo.repository.PersonRepository;
import com.art.ToDo.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private PersonService personService;

    private Person person;
    private Task task;

    @BeforeEach
    void setUp() {
        person = new Person(1L, "Test Person", "test@example.com", new ArrayList<>());
        task = new Task(UUID.randomUUID(), "Test Task", "Pending", new ArrayList<>());
    }

    @Test
    void testCreatePerson() {
        when(personRepository.save(any(Person.class))).thenReturn(person);
        Person createdPerson = personService.createPerson(new Person());
        assertNotNull(createdPerson);
        assertEquals(person.getName(), createdPerson.getName());
        verify(personRepository, times(1)).save(any(Person.class));
    }

    @Test
    void testGetAllPersons() {
        when(personRepository.findAll()).thenReturn(List.of(person));
        List<Person> persons = personService.getAllPersons();
        assertFalse(persons.isEmpty());
        assertEquals(1, persons.size());
        verify(personRepository, times(1)).findAll();
    }

    @Test
    void testGetPersonById_Success() {
        when(personRepository.findById(person.getId())).thenReturn(Optional.of(person));
        Optional<Person> foundPerson = personService.getPersonById(person.getId());
        assertTrue(foundPerson.isPresent());
        assertEquals(person.getId(), foundPerson.get().getId());
        verify(personRepository, times(1)).findById(person.getId());
    }

    @Test
    void testGetPersonById_NotFound() {
        when(personRepository.findById(2L)).thenReturn(Optional.empty());
        Optional<Person> foundPerson = personService.getPersonById(2L);
        assertFalse(foundPerson.isPresent());
        verify(personRepository, times(1)).findById(2L);
    }

    @Test
    void testUpdatePerson_Success() {
        Person updatedDetails = new Person();
        updatedDetails.setName("Updated Person");
        updatedDetails.setEmail("updated@example.com");

        when(personRepository.findById(person.getId())).thenReturn(Optional.of(person));
        when(personRepository.save(any(Person.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Person updatedPerson = personService.updatePerson(person.getId(), updatedDetails);

        assertNotNull(updatedPerson);
        assertEquals("Updated Person", updatedPerson.getName());
        assertEquals("updated@example.com", updatedPerson.getEmail());
        verify(personRepository, times(1)).findById(person.getId());
        verify(personRepository, times(1)).save(any(Person.class));
    }

    @Test
    void testUpdatePerson_NotFound() {
        Person updatedDetails = new Person();
        when(personRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> personService.updatePerson(2L, updatedDetails));
        verify(personRepository, times(1)).findById(2L);
        verify(personRepository, never()).save(any(Person.class));
    }

    @Test
    void testDeletePerson() {
        doNothing().when(personRepository).deleteById(person.getId());
        personService.deletePerson(person.getId());
        verify(personRepository, times(1)).deleteById(person.getId());
    }

    @Test
    void testAssignTaskToPerson_Success() {
        when(personRepository.findById(person.getId())).thenReturn(Optional.of(person));
        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
        when(personRepository.save(any(Person.class))).thenReturn(person);

        Person result = personService.assingTaskToPerson(person.getId(), task.getId());

        assertNotNull(result);
        assertTrue(result.getTasks().contains(task));
        verify(personRepository, times(1)).save(person);
    }

    @Test
    void testAssignTaskToPerson_PersonNotFound() {
        when(personRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> personService.assingTaskToPerson(2L, task.getId()));
        verify(personRepository, never()).save(any(Person.class));
    }

    @Test
    void testAssignTaskToPerson_TaskNotFound() {
        UUID nonExistentTaskId = UUID.randomUUID();
        when(personRepository.findById(person.getId())).thenReturn(Optional.of(person));
        when(taskRepository.findById(nonExistentTaskId)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> personService.assingTaskToPerson(person.getId(), nonExistentTaskId));
        verify(personRepository, never()).save(any(Person.class));
    }

    @Test
    void testRemoveTaskFromPerson_Success() {
        person.getTasks().add(task);
        task.getPersons().add(person);

        when(personRepository.findById(person.getId())).thenReturn(Optional.of(person));
        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
        when(personRepository.save(any(Person.class))).thenReturn(person);

        Person result = personService.removeTaskFromPerson(person.getId(), task.getId());

        assertNotNull(result);
        assertFalse(result.getTasks().contains(task));
        verify(personRepository, times(1)).save(person);
    }
}
