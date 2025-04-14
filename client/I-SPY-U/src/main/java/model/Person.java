package model;


public class Person {

    private Long id;
    private String firstname;
    private String lastname;
    private String email;

    private String street;
    private String city;
    private String postalcode;


    public Person(){}
    public Person(String firstname, String lastname, String email){
        this(firstname, lastname, email, "", "", "");
    }
    public Person(String firstname, String lastname, String email, String street, String city, String postalcode){
        super();
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;

        this.street = street;
        this.city = city;
        this.postalcode = postalcode;
    }

    public Long getId(){ return id; }
    public String getFirstName(){ return firstname; }
    public String getLastName(){ return lastname; }
    public String getEmail(){ return email; }
    public String getStreet(){ return street; }
    public String getCity(){ return city; }
    public String getPostalCode(){ return postalcode; }

    public void setId(Long id){ this.id = id; }
    public void setFirstName(String firstname){ this.firstname = firstname; }
    public void setLastName(String lastname){ this.lastname = lastname; }
    public void setEmail(String email){ this.email = email; }
    public void setStreet(String street){ this.street = street; }
    public void setCity(String city){ this.city = city; }
    public void setPostalCode(String postalcode){ this.postalcode = postalcode; }

    @Override
    public String toString(){
        return "{"
                + "\"id\":\"" + id + "\","
                + "\"firstname\":\"" + firstname + "\","
                + "\"lastname\":\"" + lastname + "\","
                + "\"email\":" + email + ","
                + "\"street\":\"" + street + "\","
                + "\"postalcode\":\"" + postalcode + "\","
                + "\"city\":\"" + city + "\""
                + "}";
    }
}
