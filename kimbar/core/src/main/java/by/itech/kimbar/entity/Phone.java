package by.itech.kimbar.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@Data
@AllArgsConstructor

public class Phone implements Serializable {

    public enum Type {
        Mobile,Home;
    }
    private  static final long serialVersionUID = 43L ;


    private Integer id;
    private Integer countryCode;
    private Integer operatorCode;
    private Integer number;
    private Type type;
    private String commentary ;

}
