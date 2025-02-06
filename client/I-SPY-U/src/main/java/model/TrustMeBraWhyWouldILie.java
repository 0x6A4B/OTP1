package model;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TrustMeBraWhyWouldILie {
    private List<Device> devices = new ArrayList<>();
    private User user;
    //Person person;
    private List<LogEntry> logEntries = new ArrayList<>();

    private final int DEVICES = 5;
    private final int DEVICELOGS = 20;


    public TrustMeBraWhyWouldILie(){};

    public List<Device> getDevices(User user){

        return devices;
    }
    public List<LogEntry> getLogEntries(Device device){
        //if(logEntries == null)
        //    return null;
        return logEntries.stream().filter(e -> e.getDevice().equals(device)).toList();
    }
    public User login(User user){
        //
        // this.user = user;
        if(this.user != null
                && this.user.getUsername().equals(user.getUsername())
                && this.user.getPassword().equals(user.getPassword())) {
            //generateDevices(this.user);
            return this.user;
        }
        return null;
    }
    public User register(User user){
        if(user != null && user.getPerson() != null) {
            this.user = user;
            if(devices.isEmpty())
                generateDevices(user);
        }
        return user;
    }

    private void generateDevices(User user){
        for(int i = 0; i < DEVICES; i++){
            devices.add(new Device(user, UUID.randomUUID(), "talo_" + i,
                    true, "anturi #" + i,
                    "lämpöanturi", new Date())
            );
        }
        for(Device d : devices){
            for (int i = 0; i < DEVICELOGS; i++){
                logEntries.add(new LogEntry(d, "temperature",
                        Double.toString(Math.random() * 50))
                );
            }
        }
    }



}
