package model;

public class DeviceSetting {

    private long id;
    private Device device;
    private User user;
    private Category category;

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
