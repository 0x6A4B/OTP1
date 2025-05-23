package dev._x6a4b.otp1.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Entity
public class LogEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String logkey; // these could be replaced with tuples or hashmap or list<pair>?
    private String value;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToOne //(fetch = FetchType.LAZY)
    @JoinColumn(name = "deviceid", nullable = false) // no null deviceids
    @OnDelete(action = OnDeleteAction.CASCADE)  // if device deleted delete all logentries
    @JsonIgnore     // do we need this?
    private Device device;


    public LogEntry(){ this.date = new Date(); }
    /*public LogEntry(Device device, String logkey, String value){
        this.device = device;
        this.date = new Date();
        this.logkey = logkey;
        this.value = value;
    }*/

    public Long getId() { return id; }
    public Device getDevice() { return device; }
    public String getLogkey() { return logkey; }
    public String getValue() { return value; }
    public Date getDate() { return date; }

    public void setId(Long id) { this.id = id; }
    public void setDevice(Device device) { this.device = device; }
    public void setDate(Date date) { this.date = date; }
    public void setLogkey(String logkey) { this.logkey = logkey; }
    public void setValue(String value) { this.value = value; }
}
