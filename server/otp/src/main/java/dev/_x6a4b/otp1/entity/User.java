package dev._x6a4b.otp1.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "username", unique = true)
    private String username;
    private String password;
    private String status;

    //@OneToOne(mappedBy = "user_id")
    //private Person person;


    public User(String username, String password){
        this(username, password, null);
    }
    public User(String username, String password, String status){
        super();
        this.username = username;
        this.password = password;
        this.status = status;
    }
    public User(){}

    public long getId(){ return id; }
    public String getUsername(){ return username; }
    public String getPassword(){ return password; }

    public void setId(int id){ this.id = id; }
    public void setUsername(String username){ this.username = username; }
    public void setPassword(String password){ this.password = password; }

}
