package com.art.ToDo.repository;

import com.art.ToDo.model.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    // O Spring Data JPA já fornece métodos CRUD prontos aqui para serem chamados no ...Service!

}
