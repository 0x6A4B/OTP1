import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.ConfigSingleton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    /* Integration test for device share */
    /* TODO: REFACTOR THIS AND CREATE UNIT TESTS WITH SOME MOCK API */
    @Test
    void testSharing(){
        //login as jarppi
        User jarppi = client.login(new User("jarppi", "jarppi"));

        // Create device to be shared
        UUID uuid = UUID.randomUUID();
        Device device = client.addDevice(new Device(uuid, "TESTI", true, "description", "model"));
        assertEquals(uuid, device.getUuid());   // device created successfully
        // Create a user that device is shared with
        User wasdi = new User("wasdi", "wasdi");

        // Create a device share object
        DeviceShare share = new DeviceShare( device, wasdi, "GODLY", "random desc" );
        //assertEquals( share.toString(), client.shareDevice(share).toString() );
        // device created and updated with id and share created and updated with id


        //share device with wasdi
        DeviceShare shareResponse = client.shareDevice(share);
        assertNotNull(shareResponse);   // share created
        // id created for device and matches with device id got for share
        assertEquals(device.getId(), shareResponse.getDevice().getId());
        assertEquals(share.getDescription(), shareResponse.getDescription());



        //getdevice for jarppi => getlast
        // this should be the device we created
        Device deviceResponse = client.getDevices(jarppi).getLast();
        // we are getting correct device id
        assertEquals(shareResponse.getDevice().getId(), deviceResponse.getId());

        //get shares for device / get the last share
        DeviceShare lastShare = client.getDeviceShares(deviceResponse).getLast();
        // the share from GET response matches the POST response
        assertEquals(lastShare.getId(), shareResponse.getId());


        // the person it is shared with
        //login as wasdi
        client.login(wasdi);

        //getshares
        // last share made and retrieved by sharer matches last share retrieved by sharee
        DeviceShare wasdisShare = client.getDeviceShares().getLast();
        assertEquals(lastShare.getId(), wasdisShare.getId());


        //update description
        // description matches the one set when creating the share
        assertEquals(share.getDescription(), wasdisShare.getDescription());

        String newDescription = "new description";
        wasdisShare.setDescription(newDescription);
        DeviceShare wasdisShareResponse = client.updateDeviceShare(wasdisShare);
        // let's see description is in the response
        assertEquals(newDescription, wasdisShareResponse.getDescription());

        // now let's check the description is also saved in the DB
        String getResponseDesc = client.getDeviceShares(deviceResponse).getLast().getDescription();
        assertEquals(newDescription, getResponseDesc);


        //delete share
        boolean successfulDelete = client.removeDeviceShare(wasdisShare);
        //assertNotEquals(null , client.getDevices(user).getLast());
        // get shares, last isn't our share
        // if no shares this will fail?
        List<DeviceShare> sharesAfterDelete = new ArrayList<>();
        sharesAfterDelete = client.getDeviceShares();

        if(sharesAfterDelete.size() > 0)
            assertNotEquals(wasdisShareResponse.getId(), sharesAfterDelete.getLast().getId());
        //else success


        //login as jarppi and delete device
        client.login(new User("jarppi", "jarppi"));
        assertTrue(client.removeDevice(device));    // supposedly success
        // will fail if no devices => TODO: FIX THIS
        assertNotEquals(device, client.getDevices(user).getLast());


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