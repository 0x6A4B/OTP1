package dev._x6a4b.otp1.entity;

import jakarta.persistence.*;

@Entity
//@Table(name="person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "user_id")
    private long userId;
    @Column(name = "first_name")
    String firstName;
    @Column(name = "last_name")
    String lastName;
    String email;

    String street;
    String city;
    @Column(name = "postal_code")
    String postalCode;

    /*@OneToOne
    @JoinColumn(name = "user_id")
    private User user;*/

    public Person(){}
    public Person(long userId, String firstName, String lastName, String email){
        this(userId, firstName, lastName, email, "", "", "");
    }
    public Person(long userId, String firstName, String lastName, String email, String street, String city, String postalCode){
        super();
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;

        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
    }

    public long getId(){ return id; }
    public long getUserId(){ return userId; }
    public String getFirstName(){ return firstName; }
    public String getLastName(){ return lastName; }
    public String getEmail(){ return email; }
    public String getStreet(){ return street; }
    public String getCity(){ return city; }
    public String getPostalCode(){ return postalCode; }

    public void setId(long id){ this.id = id; }
    public void setUserId(int userId){ this.userId = userId; }
    public void setFirstName(String firstName){ this.firstName = firstName; }
    public void setLastName(String lastName){ this.lastName = lastName; }
    public void setEmail(String email){ this.email = email; }
    public void setStreet(String street){ this.street = street; }
    public void setCity(String city){ this.city = city; }
    public void setPostalCode(String postalCode){ this.postalCode = postalCode; }

}
