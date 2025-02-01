package dev._x6a4b.otp1.entity;

import jakarta.persistence.*;

@Entity
//@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "username", unique = true)
    private String userName;
    private String password;
    private String status;

    //@OneToOne(mappedBy = "user_id")
    //private Person person;


    public User(String userName, String password){
        this(userName, password, null);
    }
    public User(String userName, String password, String status){
        super();
        this.userName = userName;
        this.password = password;
        this.status = status;
    }
    public User(){}

    public long getId(){ return id; }
    public String getUserName(){ return userName; }
    public String getPassword(){ return password; }

    public void setId(int id){ this.id = id; }
    public void setUserName(String userName){ this.userName = userName; }
    public void setPassword(String password){ this.password = password; }

}
