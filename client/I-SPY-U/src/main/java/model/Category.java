package model;

public class Category {
    private long id;
    private String name;
    private String description;
    private String localeCode;

    public Category() {}
    public Category(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getLocaleCode() { return localeCode; }
    public void setLocaleCode(String localeCode) { this.localeCode = localeCode; }
}
