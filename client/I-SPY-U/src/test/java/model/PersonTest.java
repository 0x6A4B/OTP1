package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    static Person person;

    @BeforeEach
    void init() {
        person = new Person("Urho", "Kekkonen", "urho.kekkonen@email.fi",
                            "Kekkosenkatu 12", "Kekkoslovakia", "2222");
    }

    @Test
    void getId() {
        assertEquals(null, person.getId());
    }

    @Test
    void getFirstName() {
        assertEquals("Urho", person.getFirstName());
    }

    @Test
    void getLastName() {
        assertEquals("Kekkonen", person.getLastName());
    }

    @Test
    void getEmail() {
        assertEquals("urho.kekkonen@email.fi", person.getEmail());
    }

    @Test
    void getStreet() {
        assertEquals("Kekkosenkatu 12", person.getStreet());
    }

    @Test
    void getCity() {
        assertEquals("Kekkoslovakia", person.getCity());
    }

    @Test
    void getPostalCode() {
        assertEquals("2222", person.getPostalCode());
    }

    @Test
    void setId() {
        person.setId(1L);
        assertEquals(1, person.getId());
    }

    @Test
    void setFirstName() {
        person.setFirstName("Martti");
        assertEquals("Martti", person.getFirstName());
    }

    @Test
    void setLastName() {
        person.setLastName("Ahtisaari");
        assertEquals("Ahtisaari", person.getLastName());
    }

    @Test
    void setEmail() {
        person.setEmail("martti.ahtisaari@email.fi");
        assertEquals("martti.ahtisaari@email.fi", person.getEmail());
    }

    @Test
    void setStreet() {
        person.setStreet("Ahtisaarentie 12");
        assertEquals("Ahtisaarentie 12", person.getStreet());
    }

    @Test
    void setCity() {
        person.setCity("Ahtisaarilandia");
        assertEquals("Ahtisaarilandia", person.getCity());
    }

    @Test
    void setPostalCode() {
        person.setPostalCode("1111");
        assertEquals("1111", person.getPostalCode());
    }
}