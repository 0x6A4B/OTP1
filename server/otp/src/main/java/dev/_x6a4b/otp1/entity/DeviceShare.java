package dev._x6a4b.otp1.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class DeviceShare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "userid", nullable = false)
    @JsonIgnore // do we need this?
    private User user;

    @OneToOne
    @JoinColumn(name = "deviceid", nullable = false)
    @JsonIgnore // do we need this?
    private Device device;

    @Temporal(TemporalType.DATE)
    private Date sharedDate;

    private String privilege;
    private String description;

    public DeviceShare(){}
    public DeviceShare(Device device, User user, Date sharedDate, String privilege, String description){
        this.device = device;
        this.user = user;
        this.sharedDate = sharedDate;
        this.privilege = privilege;
        this.description = description;
    }

    public Long getId() {
        return id;
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
