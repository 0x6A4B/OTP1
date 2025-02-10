import model.*;

import java.util.List;

public class Client {
    private final TrustMeBraWhyWouldILie service = new TrustMeBraWhyWouldILie();

    public Client(){}

    public List<Device> getDevices(User user){
        return service.getDevices(user);
    }
    public List<LogEntry> getLogEntries(Device device){
        return service.getLogEntries(device);
    }
    public User login(User user){
        return service.login(user);
    }
    public User register(User user){
        return service.register(user);
    }
}
