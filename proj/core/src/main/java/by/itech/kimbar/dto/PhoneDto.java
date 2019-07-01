package by.itech.kimbar.dto;

import by.itech.kimbar.entity.Phone;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PhoneDto implements Dto {
    private Integer countryCode;
    private Integer operatorCode;
    private Integer number;
    private String commentary;
    private Phone.Type type;
    private Integer id;
}
