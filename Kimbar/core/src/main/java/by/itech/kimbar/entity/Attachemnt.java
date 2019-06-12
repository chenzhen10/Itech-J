package by.itech.kimbar.entity;

import java.io.Serializable;
import java.util.Objects;

public class Attachemnt implements Serializable {
    private  static final long serialVersionUID = 44L ;

    private int id ;
    private String name;
    private String date;
    private String comment;

    public Attachemnt() {
    }

    public Attachemnt(int id ,String name, String date, String comment) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.comment = comment;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attachemnt that = (Attachemnt) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(date, that.date) &&
                Objects.equals(comment, that.comment);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, date, comment);
    }

    @Override
    public String toString() {
        return "Attachemnt{" +
                " name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
