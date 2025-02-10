package model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DeviceTest {

    static Device device;
    static UUID uuid;
    static User user;
    static Date date;
    static Person person;

    @BeforeEach
     void init() {

        uuid = UUID.randomUUID();
        date = new Date();
        person = new Person("urho", "kekkonen", "urho@kekkonen.gov");
        user = new User("kekkonen", "salasana", "user", person);
        device = new Device(user, uuid, "urhoDevice", true, "urhoDeviceDescription", "urhoDeviceModel", date);

    }

    @Test
    void getId() {
    }

    @Test
    void getUser() {
        assertEquals(user, device.getUser());
    }

    @Test
    void getUuid() {
        assertEquals(uuid, device.getUuid());
    }

    @Test
    void getName() {
        assertEquals("urhoDevice", device.getName());
    }

    @Test
    void getDescription() {
        assertEquals("urhoDeviceDescription", device.getDescription());
    }

    @Test
    void getModel() {
        assertEquals("urhoDeviceModel", device.getModel());
    }

    @Test
    void isOwned() {
        assertTrue(device.isOwned());
    }

    @Test
    void getRegistered() {
        assertEquals(date, device.getRegistered());
    }

    @Test
    void setId() {

    }

    @Test
    void setUser() {
        User newUser = new User("newUsername", "newPassword", "newStatus", new Person("newFirstName", "newLastName", "newEmail"));
        device.setUser(newUser);
        assertEquals(newUser, device.getUser());
    }

    @Test
    void setUuid() {
        UUID newUuid = UUID.randomUUID();
        device.setUuid(newUuid);
        assertEquals(newUuid, device.getUuid());
    }

    @Test
    void setName() {
        device.setModel("newName");
        assertEquals("newName", device.getModel());
    }

    @Test
    void setDescription() {
        device.setDescription("newDescription");
        assertEquals("newDescription", device.getDescription());
    }

    @Test
    void setModel() {
        device.setModel("newModel");
        assertEquals("newModel", device.getModel());
    }

    @Test
    void setOwned() {
        device.setOwned(false);
        assertFalse(device.isOwned());
    }

    @Test
    void setRegistered() {
        Date newDate = new Date();
        device.setRegistered(newDate);
        assertEquals(newDate, device.getRegistered());
    }
}