package com.mcally.userservice.controller;

import com.mcally.userservice.model.Person;
import com.mcally.userservice.repository.PersonRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/person")
public class PersonController {

    private final PersonRepository personRepository;
    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping
    public ResponseEntity<Iterable<Person>> retrievePeople() {
        Iterable<Person> personIterable = personRepository.findAll();
        return ResponseEntity.ok(personIterable);
    }

}
