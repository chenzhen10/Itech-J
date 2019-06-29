package by.itech.kimbar.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Attachment implements Serializable {
    private  static final long serialVersionUID = 44L ;

    private int id ;
    private String name;
    private Date date;
    private String commentary;
    private int userId;
    private String path;

}
