import model.Client;
import model.Device;
import model.Person;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    static Client client;
    static User user;

    @BeforeEach
    void init(){
        client = new Client();
        user = new User("jarppi", "jarppi", "active",
                new Person("Urho Kaleva", "Kekkonen", "urho@kekkonen.gov",
                        "Kekkosenkatu 12", "Kekkoslovakia", "2222"));
        //client.register(user);
        client.login(user);
    }

    @Test
    void getDevices() {
        //assertEquals(5, client.getDevices(user).size());
        assertTrue(client.getDevices(user).get(0).getName().contains("AntsaAnturi"));
        assertEquals("antero_v1", client.getDevices(user).getFirst().getModel());
    }

    @Test
    void getLogEntries() {
        Device dev = client.getDevices(user).get(0);
        //assertEquals(1, client.getLogEntries(dev).size());
        assertEquals("temperature", client.getLogEntries(dev).getLast().getLogkey());
        assertTrue(Double.parseDouble(client.getLogEntries(dev).getFirst().getValue()) > 0);   // lämpötila aina positiivinen
        assertTrue(Double.parseDouble(client.getLogEntries(dev).getLast().getValue()) < 50);   // lämpötila aina alle 50
    }

    @Test
    void createAndRemoveDevice(){
        Device device = new Device(user, UUID.randomUUID(), "junit-test", true, "junit-test-device", "junit", new Date());
        Device createdDevice = client.addDevice(device);
        assertEquals(device.toString(), createdDevice.toString());
        assertTrue(client.removeDevice(createdDevice));
        assertNotEquals(device, client.getDevices(user).getLast());
    }

    @Test
    void login() {
        assertEquals(user.getUsername(),
                client.login(
                        new User("jarppi",
                                "jarppi",
                                "",
                                new Person()
                        )).getUsername()
        );
    }

    @Test
    void register() {
        // We can't just keep registering new users every test => need a test DB
        /*
        user = new User("mannerheim", "marskin_salasana", "active",
                new Person("Marsalkka", "Mannerheim", "marski@marski.gov",
                        "Mannerheimintie 12", "Marskila", "2222"));
        assertEquals("mannerheim", client.register(user).getUsername());
       */
    }
}