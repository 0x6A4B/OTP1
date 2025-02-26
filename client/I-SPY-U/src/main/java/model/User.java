package model;


public class User {

    private long id;
    private String username;
    private String password;
    private String status;
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

    public User(String username, String password, Person person){
        super();
        this.username = username;
        this.password = password;
        this.status = "";
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
