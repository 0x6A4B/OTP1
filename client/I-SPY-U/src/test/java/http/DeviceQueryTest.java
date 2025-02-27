package http;

import model.Client;
import model.Device;
import model.User;
import org.junit.jupiter.api.Test;
import service.ConnectionManager;
import util.ConfigSingleton;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DeviceQueryTest {

    @Test
    void getDevices() {
    }

    @Test
    void createDevice() {
        Client cl = new Client();
        ConfigSingleton.getInstance().setToken("TESTI");

        //cm.login(new User("jaa", "juu"));
        //cl.getDevices(new User());
        cl.addDevice(new Device(UUID.randomUUID(), "name", true, "desc", "model"));

    }

    @Test
    void removeDevice() {
    }

    @Test
    void removeAllDevices() {
    }
}