package by.itech.kimbar.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class User implements Serializable {


    private  static final long serialVersionUID = 42L ;

    private int id;
    private String name;
    private String surname;
    private String lastName;
    private Date birthDate;
    private Gender gender;
    private String citizenship;
    private String maritalStatus;
    private String webSite;
    private String email;
    private String workplace;
    private Address address;

    public User(){}


    // delete this
    public User(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public User(String name, String surname, String lastName, Date birthDate, Gender gender, String citizenship,
                String maritalStatus, String webSite, String email, String workplace, Address address) {

        this.name = name;
        this.surname = surname;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.citizenship = citizenship;
        this.maritalStatus = maritalStatus;
        this.webSite = webSite;
        this.email = email;
        this.workplace = workplace;
        this.address = address;
    }

    public User(int id , String name, String surname, String lastName, Date birthDate, Gender gender,
                String citizenship, String maritalStatus, String webSite, String email, String workplace, Address address) {

        this(name, surname, lastName, birthDate, gender, citizenship, maritalStatus, webSite, email, workplace, address);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }


    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(name, user.name) &&
                Objects.equals(surname, user.surname) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(birthDate, user.birthDate) &&
                gender == user.gender &&
                Objects.equals(citizenship, user.citizenship) &&
                Objects.equals(maritalStatus, user.maritalStatus) &&
                Objects.equals(webSite, user.webSite) &&
                Objects.equals(email, user.email) &&
                Objects.equals(workplace, user.workplace) &&
                Objects.equals(address, user.address);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, surname, lastName, birthDate, gender, citizenship, maritalStatus, webSite, email, workplace, address);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", gender=" + gender +
                ", citizenship='" + citizenship + '\'' +
                ", maritalStatus='" + maritalStatus + '\'' +
                ", webSite='" + webSite + '\'' +
                ", email='" + email + '\'' +
                ", workplace='" + workplace + '\'' +
                ", address=" + address +
                '}';
    }
}
