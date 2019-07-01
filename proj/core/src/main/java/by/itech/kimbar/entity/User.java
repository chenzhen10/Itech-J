package by.itech.kimbar.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@Data
@AllArgsConstructor
public class User implements Serializable {
    private  static final long serialVersionUID = 42L ;

    private Integer id;
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
    private String photoPath;

}
