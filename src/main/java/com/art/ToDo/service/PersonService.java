package com.art.ToDo.service;

import com.art.ToDo.model.entity.Person;
import com.art.ToDo.model.entity.Task;
import com.art.ToDo.repository.PersonRepository;

import com.art.ToDo.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PersonService {

    private final PersonRepository PERSON_REPOSITORY;
    private final TaskRepository TASK_REPOSITORY;

    @Autowired
    public PersonService(PersonRepository personRepository, TaskRepository taskRepository) {
        this.PERSON_REPOSITORY = personRepository;
        this.TASK_REPOSITORY = taskRepository;
    }

    public Person createPerson(Person data) {
        // lógica para validar
        return PERSON_REPOSITORY.save(data);
    }

    public List<Person> getAllPersons() {
        return PERSON_REPOSITORY.findAll();
    }

    public Optional<Person> getPersonById(Long id) {
        return PERSON_REPOSITORY.findById(id);
    }

    public Person updatePerson(Long id, Person data)    {
        Person dataUpdate = PERSON_REPOSITORY.findById(id).map(
                existingPerson -> {
                    existingPerson.setName(data.getName());
                    existingPerson.setEmail(data.getEmail());
                    // existingPerson.setTasks(data.getTasks());
                    return PERSON_REPOSITORY.save(existingPerson);
                }
        ).orElseThrow(
                () -> new RuntimeException("Pessoa não encontrada")
        );
        return dataUpdate;
    }

    public void deletePerson(Long id) {
        PERSON_REPOSITORY.deleteById(id);
    }

    @Transactional
    public Person assingTaskToPerson(Long personId, UUID taskId) {
        Optional<Person> personOpt = PERSON_REPOSITORY.findById(personId);
        Optional<Task> taskOpt = TASK_REPOSITORY.findById(taskId);

        if (personOpt.isEmpty()) {
            throw new RuntimeException("Pessoa com ID " + personId + " não encontrada.");
        }
        if (taskOpt.isEmpty()) {
            throw new RuntimeException("Tarefa com ID " + taskId + " não encontrada.");
        }

        Person person = personOpt.get();
        Task task = taskOpt.get();

        if (!person.getTasks().contains(task)) {
            person.getTasks().add(task);

            if (!task.getPersons().contains(person)) {
                task.getPersons().add(person);
                return PERSON_REPOSITORY.save(person);
            }
        }
        return person;
    }

    @Transactional
    public Person removeTaskFromPerson(Long personId, UUID taskId) {
        Optional<Person> personOpt = PERSON_REPOSITORY.findById(personId);
        Optional<Task> taskOpt = TASK_REPOSITORY.findById(taskId);

        if (personOpt.isEmpty()) {
            throw new RuntimeException("Pessoa com ID " + personId + " não encontrada.");
        }
        if (taskOpt.isEmpty()) {
            throw new RuntimeException("Tarefa com ID " + taskId + " não encontrada.");
        }

        Person person = personOpt.get();
        Task task = taskOpt.get();

        if (person.getTasks().contains(task)) {
            person.getTasks().remove(task);

            if (task.getPersons().contains(person)) {
                task.getPersons().remove(person);
                return PERSON_REPOSITORY.save(person);
            }
        }
        return person;
    }
}
