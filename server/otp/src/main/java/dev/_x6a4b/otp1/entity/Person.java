package dev._x6a4b.otp1.entity;

import jakarta.persistence.*;

@Entity
//@Table(name="person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //@Column(name = "userid")
    //@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "userid")
    //private Long userid;
    //@OneToOne(mappedBy = "person")
    //private User user;
    //@Column(name = "firstname")
    private String firstname;
    //@Column(name = "lastname")
    private String lastname;
    private String email;

    private String street;
    private String city;
    //@Column(name = "postalcode")
    private String postalcode;

    /*@OneToOne
    @JoinColumn(name = "user_id")
    private User user;*/

    public Person(){}
    public Person(String firstname, String lastname, String email){
        this(firstname, lastname, email, "", "", "");
    }
    public Person(String firstname, String lastname, String email, String street, String city, String postalcode){
        super();
        //this.userid = userid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;

        this.street = street;
        this.city = city;
        this.postalcode = postalcode;
    }

    public Long getId(){ return id; }
    //public Long getUserId(){ return userid; }
    public String getFirstName(){ return firstname; }
    public String getLastName(){ return lastname; }
    public String getEmail(){ return email; }
    public String getStreet(){ return street; }
    public String getCity(){ return city; }
    public String getPostalCode(){ return postalcode; }

    public void setId(Long id){ this.id = id; }
    //public void setUserId(Long userid){ this.userid = userid; }
    public void setFirstName(String firstname){ this.firstname = firstname; }
    public void setLastName(String lastName){ this.lastname = lastname; }
    public void setEmail(String email){ this.email = email; }
    public void setStreet(String street){ this.street = street; }
    public void setCity(String city){ this.city = city; }
    public void setPostalCode(String postalcode){ this.postalcode = postalcode; }

}
