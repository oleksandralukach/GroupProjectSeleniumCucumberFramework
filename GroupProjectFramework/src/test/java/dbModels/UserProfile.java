package dbModels;

import java.sql.ResultSet;

//POJO(bean) class- only fields and their getter and setter
public class UserProfile {
    private int id;
    private String address;
    private String country;
    private String first_name;
    private String last_name;
    private String region;
    private String ssn;
    private String title;

    public UserProfile() {

    }

    public UserProfile(int id, String address, String country, String first_name, String last_name, String region, String ssn, String title) {
        this.id = id;
        this.address = address;
        this.country = country;
        this.first_name = first_name;
        this.last_name = last_name;
        this.region = region;
        this.ssn = ssn;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", country='" + country + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", region='" + region + '\'' +
                ", ssn='" + ssn + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
