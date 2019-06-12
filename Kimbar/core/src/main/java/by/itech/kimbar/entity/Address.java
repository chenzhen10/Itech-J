package by.itech.kimbar.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Address implements Serializable {
    private  static final long serialVersionUID = 45L;

    private String country;
    private String city;
    private String street;
    private String house;
    private String numOfFlat;
    private Integer index;

    public Address(){}

    public Address(String country, String city, String street, String house, String numOfFlat, Integer index) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.house = house;
        this.numOfFlat = numOfFlat;
        this.index = index;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getNumOfFlat() {
        return numOfFlat;
    }

    public void setNumOfFlat(String numOfFlat) {
        this.numOfFlat = numOfFlat;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(country, address.country) &&
                Objects.equals(city, address.city) &&
                Objects.equals(street, address.street) &&
                Objects.equals(house, address.house) &&
                Objects.equals(numOfFlat, address.numOfFlat) &&
                Objects.equals(index, address.index);
    }

    @Override
    public int hashCode() {

        return Objects.hash(country, city, street, house, numOfFlat, index);
    }

    @Override
    public String toString() {
        return "Address{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", house='" + house + '\'' +
                ", numOfFlat='" + numOfFlat + '\'' +
                ", index=" + index +
                '}';
    }
}
