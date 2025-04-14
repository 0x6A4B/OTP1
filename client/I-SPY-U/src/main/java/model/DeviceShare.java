package model;

import java.util.Date;

public class DeviceShare {

    private Long id;
    private User user;
    private Device device;
    private Date sharedDate;
    private String privilege = "Read";
    private String description = "Shared device";

    private long userId;
    private long deviceId;


    public DeviceShare(){}

    public DeviceShare(Device device, User user, String privilege, String description){
        this.device = device;
        this.user = user;
        this.privilege = privilege;
        this.description = description;
    }

    // TODO: Fix this => maybe using remote proxy type of pattern?
    public long getUserId() { return userId; }
    public void setUserId(long userId){ this.userId = userId; }
    public long getDeviceId() { return deviceId; }
    public void setDeviceId(long deviceId){
        this.deviceId = deviceId;
        if(this.device == null) { // We need to retrieve the device?
            this.device = new Device();
            this.device.setId(deviceId);
        }
    }

    public Long getId() { return id; }
    public Device getDevice() {
        return device;
    }
    public User getUser() { return user; }
    public Date getSharedDate() { return sharedDate; }
    public String getPrivilege() { return privilege; }
    public String getDescription() { return description; }

    public void setId(Long id) { this.id = id; }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }
    public void setSharedDate(Date sharedDate) {
        this.sharedDate = sharedDate;
    }
    public void setDevice(Device device) {
        this.device = device;
    }
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString(){
        // Return JSON
        return "{"
                + "\"id\":\"" + id + "\","
                + "\"user\":\"" + user + "\","
                + "\"device\":" + device + ","
                + "\"description\":\"" + description + "\","
                + "\"privilege\":\"" + privilege + "\""
                + "}";
    }

}
