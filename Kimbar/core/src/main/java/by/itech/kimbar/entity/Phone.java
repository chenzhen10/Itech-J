package by.itech.kimbar.entity;

import java.io.Serializable;
import java.util.Objects;

public class Phone implements Serializable {
    public enum Type {
        Mobile,Home
    }

    private  static final long serialVersionUID = 43L ;

    private int id;
    private int countryCode;
    private int operatorCode;
    private int number;
    private Type type;
    private String commentary ;


    public Phone(){}

    public Phone(int countryCode, int operatorCode,int number, Type type, String commentary) {
        this.countryCode = countryCode;
        this.number = number;
        this.operatorCode = operatorCode;
        this.type = type;
        this.commentary = commentary;
    }

    public Phone(int id ,int countryCode, int operatorCode,int number, Type type, String commentary) {
        this(countryCode, operatorCode, number, type, commentary);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(int countryCode) {
        this.countryCode = countryCode;
    }

    public int getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(int operatorCode) {
        this.operatorCode = operatorCode;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phone phone = (Phone) o;
        return id == phone.id &&
                countryCode == phone.countryCode &&
                operatorCode == phone.operatorCode &&
                number == phone.number &&
                type == phone.type &&
                Objects.equals(commentary, phone.commentary);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, countryCode, operatorCode, number, type, commentary);
    }

    @Override
    public String toString() {
        return "Phone{" +
                "countryCode=" + countryCode +
                ", operatorCode=" + operatorCode +
                ", number=" + number +
                ", type=" + type +
                ", commentary='" + commentary + '\'' +
                '}';
    }
}
