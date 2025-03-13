package dev._x6a4b.otp1.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class DeviceShare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "userid", nullable = false)
    //@JsonIgnore // so we won't return user object with PERSONAL DATA AND PASSWORD!!
    @JsonBackReference
    private User user;

    @OneToOne
    @JoinColumn(name = "deviceid", nullable = false)
    //@JsonProperty // returns this too in response
    //@JsonIgnore
    @JsonBackReference
    private Device device;

    @Temporal(TemporalType.DATE)
    private Date sharedDate;

    private String privilege;
    private String description;

    // ?
    @Column(name = "userid", insertable = false, updatable = false)
    private Long userId;
    @Column(name = "deviceid", insertable = false, updatable = false)
    private Long deviceId;

    public DeviceShare(){}
    public DeviceShare(Device device, User user, Date sharedDate, String privilege, String description){
        this.device = device;
        this.user = user;
        this.sharedDate = sharedDate;
        this.privilege = privilege;
        this.description = description;
    }

    public Long getId() { return id; }
    public Device getDevice() { return device; }
    public User getUser() { return user; };
    public Date getSharedDate() { return sharedDate; }
    public String getPrivilege() { return privilege; }
    public String getDescription() { return description; }
    public Long getUserId() { return userId; }
    public Long getDeviceId() { return deviceId; }

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
    public void setDeviceId(Long deviceId) { this.deviceId = deviceId; }
    public void setUserId(Long userId) { this.userId = userId; }
}
