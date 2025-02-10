package model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrustMeBraWhyWouldILieTest {

    static TrustMeBraWhyWouldILie service;
    static User user;

    @BeforeAll
    static void init(){
        service = new TrustMeBraWhyWouldILie();
        user = new User("kekkonen", "kekkosen_salasana", "active",
                new Person("Urho Kaleva", "Kekkonen", "urho@kekkonen.gov",
                        "Kekkosenkatu 12", "Kekkoslovakia", "2222"));
        service.register(user);
    }

    @Test
    void getDevices() {
        assertEquals(service.getDevices(user).size(), 5);
        assertTrue(service.getDevices(user).get(0).getName().contains("talo"));
        assertEquals(service.getDevices(user).getLast().getModel(), "lämpöanturi");
    }

    @Test
    void logEntries() {
        Device dev = service.getDevices(user).get(0);
        assertEquals(service.getLogEntries(dev).size(), 20);
        assertEquals(service.getLogEntries(dev).getLast().getLogkey(), "temperature");
        assertTrue(Double.parseDouble(service.getLogEntries(dev).getFirst().getValue()) > 0);   // lämpötila aina positiivinen
        assertTrue(Double.parseDouble(service.getLogEntries(dev).getLast().getValue()) < 50);   // lämpötila aina alle 50

    }

    @Test
    void login() {
        assertEquals(service.login(new User("kekkonen", "kekkosen_salasana", "",
                new Person())).getUsername(),user.getUsername());
    }

    @Test
    void register() {
        // miten tämä testataan? jos luomme uuden käyttäjän tulevat seuraavat feilaamaan?
        // käyttämällä @BeforeEach:ia tuon @BeforeAll sijaan?
    }
}