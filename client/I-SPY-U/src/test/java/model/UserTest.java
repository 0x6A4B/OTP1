package model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    static User user;

    @BeforeAll
    static void init(){
        user = new User("kekkonen", "salasana", "user",
                new Person("urho", "kekkonen", "urho@kekkonen.gov"));
        user.setId(1);
    }

    @Test
    void getId() {
        assertEquals(user.getId(), 1);
    }

    @Test
    void getUsername() {
        assertEquals(user.getUsername(), "kekkonen");
    }

    @Test
    void getPassword() {
        assertEquals(user.getPassword(), "salasana");
    }

    @Test
    void setId() {
    }

    @Test
    void setUsername() {
    }

    @Test
    void setPassword() {
    }

    @Test
    void getPerson() {
    }

    @Test
    void setPerson() {
    }
}