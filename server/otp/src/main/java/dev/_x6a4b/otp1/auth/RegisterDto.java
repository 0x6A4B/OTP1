package dev._x6a4b.otp1.auth;

import dev._x6a4b.otp1.entity.Person;

public class RegisterDto {

    private String username;
    private String password;

    //person
    private Person person;
    private String firstname;
    private String lastname;
    private String email;
    private String street;
    private String city;
    private String postalcode;


    public RegisterDto(){}
//    public RegisterDto(String username, String password, String firstname, String lastname, String email, String street, String city, String postalCode){
//        //user
//        this.username = username;
//        this.password = password;
//        //person
//        System.out.println("creating person: " + lastname);
//        person = new Person(0L, firstname, lastname, email, street, city, postalCode);
//        System.out.println(person.getLastName());
////        this.firstName = firstName;
////        this.lastName = lastName;
////        this.email = email;
////
////        this.street = street;
////        this.city = city;
////        this.postalCode = postalCode;
//    }

    public String getUsername(){ return username; }
    public String getPassword() { return password; }

    public void setUsername(String username){ this.username = username; }
    public void setPassword(String password) { this.password = password; }

    public Person getPerson(){
        return new Person(firstname, lastname, email, street, city, postalcode);
    }

    public void setFirstname(String firstname){ this.firstname = firstname; }
    public void setLastname(String lastname) { this.lastname = lastname; }
    public void setEmail(String email) { this.email = email; }
    public void setCity(String city) { this.city = city; }
    public void setPostalcode(String postalcode) { this.postalcode = postalcode; }
    public void setStreet(String street) { this.street = street; }
}
