package com.mcally.userservice.batch;

import com.mcally.userservice.model.Person;
import com.mcally.userservice.model.User;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class PersonToUserItemProcessor implements ItemProcessor<Person, User> {
    @Override
    public User process(Person person) throws Exception {
        return new User(person.getFirstName(), person.getLastName());
    }
}
