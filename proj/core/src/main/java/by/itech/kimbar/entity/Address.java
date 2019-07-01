package by.itech.kimbar.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonTypeName;

import java.io.Serializable;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Address implements Serializable {
    private  static final long serialVersionUID = 45L;

    private String country;
    private String city;
    private String street;
    private String house;
    private String numOfFlat;
    private Integer index;


}
