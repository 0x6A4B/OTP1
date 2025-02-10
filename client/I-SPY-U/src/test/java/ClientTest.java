import model.Device;
import model.Person;
import model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    static Client client;
    static User user;

    @BeforeAll
    static void init(){
        client = new Client();
        user = new User("kekkonen", "kekkosen_salasana", "active",
                new Person("Urho Kaleva", "Kekkonen", "urho@kekkonen.gov",
                        "Kekkosenkatu 12", "Kekkoslovakia", "2222"));
        client.register(user);
    }

    @Test
    void getDevices() {
        assertEquals(client.getDevices(user).size(), 5);
        assertTrue(client.getDevices(user).get(0).getName().contains("talo"));
        assertEquals(client.getDevices(user).getLast().getModel(), "lämpöanturi");
    }

    @Test
    void getLogEntries() {
        Device dev = client.getDevices(user).get(0);
        assertEquals(client.getLogEntries(dev).size(), 20);
        assertEquals(client.getLogEntries(dev).getLast().getLogkey(), "temperature");
        assertTrue(Double.parseDouble(client.getLogEntries(dev).getFirst().getValue()) > 0);   // lämpötila aina positiivinen
        assertTrue(Double.parseDouble(client.getLogEntries(dev).getLast().getValue()) < 50);   // lämpötila aina alle 50
    }

    @Test
    void login() {
        assertEquals(client.login(new User("kekkonen", "kekkosen_salasana", "",
                new Person())).getUsername(),user.getUsername());
    }

    @Test
    void register() {
    }
}