package dev._x6a4b.otp1.entity;

import java.util.Date;

public class DeviceShareDTO {
    private Long id;
    private Device device;
    private User user;
    private String description;
    private String privilege;
    private Date sharedDate;

    public DeviceShareDTO(){}
    public DeviceShareDTO(Device device, User user, String description, String privilege, Date sharedDate) {
        this.device = device;
        this.user = user;
        this.description = description;
        this.privilege = privilege;
        this.sharedDate = sharedDate;
    }
    public DeviceShareDTO(Long id, Device device, User user, String description, String privilege, Date sharedDate) {
        this.id = id;
        this.device = device;
        this.user = user;
        this.description = description;
        this.privilege = privilege;
        this.sharedDate = sharedDate;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Device getDevice() { return device; }
    public void setDevice(Device device) { this.device = device; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getPrivilege() { return privilege; }
    public void setPrivilege(String privilege) { this.privilege = privilege; }
    public Date getSharedDate() { return sharedDate; }
    public void setSharedDate(Date sharedDate) { this.sharedDate = sharedDate; }

}
