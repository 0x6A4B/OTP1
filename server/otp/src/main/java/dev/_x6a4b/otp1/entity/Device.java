package dev._x6a4b.otp1.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev._x6a4b.otp1.util.UuidConverter;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userid", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore // do we need this?
    private User user;

    @Column(unique = true, updatable = false, columnDefinition = "CHAR(36)")
    @Convert(converter = UuidConverter.class)
    private UUID uuid;
    private String name;
    private Boolean owned;
    private String description;
    private String model;
    @Temporal(TemporalType.DATE)
    private Date registered;
    //@OneToMany
    //private List<LogEntry> logEntryList;


    public Device(){}
    public Device(User user, UUID uuid, String name, Boolean owned, String description, String model, Date registered){
        this.user = user;
        this.uuid = uuid;
        this.name = name;
        this.owned = owned;
        this.description = description;
        this.model = model;
        this.registered = registered;
    }

    public Long getId() { return id; }
    public User getUser() { return user; }
    public UUID getUuid(){ return uuid; }
    public String getName(){ return name; }
    public String getDescription(){ return description; }
    public String getModel(){ return model; }
    public Boolean isOwned() { return owned; }
    public Date getRegistered() { return registered; }
    //public List<LogEntry> getLogEntryList() { return logEntryList; }

    public void setId(Long id) { this.id = id; }
    public void setUser(User user) { this.user = user; }
    public void setUuid(UUID uuid) { this.uuid = uuid; }
    public void setName(String name){ this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setModel(String model) { this.model = model; }
    public void setOwned(Boolean owned) { this.owned = owned; }
    public void setRegistered(Date registered) { this.registered = registered; }
    //public void setLogEntryList(List<LogEntry> logEntryList) { this.logEntryList = logEntryList; }
}
