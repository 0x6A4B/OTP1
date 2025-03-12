package model;

import java.util.Date;

public class DeviceShare {

    private User user;
    private Device device;
    private Date sharedDate;
    private String privilege = "Read";
    private String description = "Shared device";

    public DeviceShare(){}

    public DeviceShare(Device device, User user, String privilege, String description){
        this.device = device;
        this.user = user;
        this.sharedDate = sharedDate;
        this.privilege = privilege;
        this.description = description;
    }

    public Device getDevice() {
        return device;
    }
    public User getUser() { return user; };
    public Date getSharedDate() { return sharedDate; }
    public String getPrivilege() { return privilege; }
    public String getDescription() { return description; }

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
}
