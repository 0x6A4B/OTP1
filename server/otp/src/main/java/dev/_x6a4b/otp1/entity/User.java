package dev._x6a4b.otp1.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "username", unique = true)
    private String username;
    @JsonIgnore // maybe not share password with the world?
    private String password;
    private String status;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true) //, mappedBy = "userid")
    @JoinColumn(name = "personid", referencedColumnName = "id")
    private Person person;


//    public User(String username, String password){
//        this(username, password, null, null);
//    }
    public User(String username, String password, String status, Person person){
        super();
        this.username = username;
        this.password = password;
        this.status = status;
        this.person = person;
    }
    public User(){}

    public long getId(){ return id; }
    public String getUsername(){ return username; }
    public String getPassword(){ return password; }

    public void setId(int id){ this.id = id; }
    public void setUsername(String username){ this.username = username; }
    public void setPassword(String password){ this.password = password; }

    public Person getPerson(){ return this.person; }
    public void setPerson(Person person){ this.person = person; }

}
