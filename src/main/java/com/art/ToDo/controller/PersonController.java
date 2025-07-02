package com.art.ToDo.controller;

import com.art.ToDo.model.entity.Person;
import com.art.ToDo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/persons")
public class PersonController {

    private final PersonService PERSON_SERVICE;

    @Autowired
    public PersonController(PersonService personService) {
        this.PERSON_SERVICE = personService;
    }

    @PostMapping
    public ResponseEntity<Person> postPerson(@RequestBody Person data) {
        Person person = PERSON_SERVICE.createPerson(data);
        // return ResponseEntity.status(201).body(person);
        return new ResponseEntity<>(person, HttpStatusCode.valueOf(201));
    }

    @PostMapping("/{personId}/tasks/{taskId}")
    public ResponseEntity<Person> assingTaskToPerson(@PathVariable Long personId, @PathVariable UUID taskId) {
        try {
            Person updatedPerson = PERSON_SERVICE.assingTaskToPerson(personId, taskId);
            return new ResponseEntity<>(updatedPerson, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Person>> getAllPersons() {
        List<Person> persons = PERSON_SERVICE.getAllPersons();
        return ResponseEntity.ok(persons);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable Long id) {
        Person person = PERSON_SERVICE.getPersonById(id).orElseThrow(
                () -> new RuntimeException("Pessoa não econtrada")
        );
        return ResponseEntity.ok(person);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> putPerson(@PathVariable Long id, @RequestBody Person data) {
        try {
            Person update = PERSON_SERVICE.updatePerson(id, data);
            return ResponseEntity.ok(update); // 200
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        // ResponseEntity<String>
        // String msg = String.format("%d - deletado com sucesso!",id);
        // return ResponseEntity.status(HttpStatusCode.valueOf(204)).body(msg);

        PERSON_SERVICE.deletePerson(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{personId}/tasks/{taskId}")
    public ResponseEntity<Void> removeTaskFromPerson(@PathVariable Long personId, @PathVariable UUID taskId) {
        try {
            PERSON_SERVICE.removeTaskFromPerson(personId, taskId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
