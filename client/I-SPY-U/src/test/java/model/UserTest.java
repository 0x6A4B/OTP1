package model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    static User user;

    @BeforeEach
    void init(){
        user = new User("kekkonen", "salasana", "user",
                new Person("urho", "kekkonen", "urho@kekkonen.gov"));
        user.setId(1);
    }

    @Test
    void getId() {
        assertEquals(1, user.getId());
    }

    @Test
    void getUsername() {
        assertEquals("kekkonen", user.getUsername());
    }

    @Test
    void getPassword() {
        assertEquals("salasana", user.getPassword());
    }

    @Test
    void setId() {

    }

    @Test
    void setUsername() {
        user.setUsername("paasikivi");
        assertEquals("paasikivi", user.getUsername());
    }

    @Test
    void setPassword() {
        user.setPassword("salasana2");
        assertEquals("salasana2", user.getPassword());
    }

    @Test
    void getPerson() {
        assertEquals("urho", user.getPerson().getFirstName());
    }

    @Test
    void setPerson() {
        user.setPerson(new Person("urho", "kekkonen", "urho@kekkonen.fi"));
    }
}