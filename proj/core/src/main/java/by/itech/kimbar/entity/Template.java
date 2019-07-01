package by.itech.kimbar.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Template implements Serializable {
    private  static final long serialVersionUID = 44L ;


    private Integer idTemplate;
    private String template;
}
