package com.pik.backend.services;

import com.pik.ride2work.tables.pojos.User;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class NoCredUserViewTest {

    @Test
    public void shouldGenerateUserWithoutCredentials() {
        String username = "username";
        String password = "password";
        String firstName = "Adam";
        String lastName = "Nowak";
        String email = "adam.nowak@gmail.com";
        User someUser = new User(1, username, password, firstName, lastName, email);
        User view = NoCredUserView.apply(someUser);
        assertEquals(1, (long) view.getId());
        assertEquals(username, view.getUsername());
        assertNull(view.getPassword());
        assertEquals(firstName, view.getFirstName());
        assertEquals(lastName, view.getLastName());
    }
}