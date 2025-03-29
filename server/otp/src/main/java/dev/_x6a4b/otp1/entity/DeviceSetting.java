package dev._x6a4b.otp1.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class DeviceSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "device_id")
    @JsonBackReference
    private Device device;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;  // Or should this point to a device share?

    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public DeviceSetting() {}
    public DeviceSetting(Device device, User user, Category category) {
        this.device = device;
        this.user = user;
        this.category = category;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public Device getDevice() { return device; }
    public void setDevice(Device device) { this.device = device; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
}
