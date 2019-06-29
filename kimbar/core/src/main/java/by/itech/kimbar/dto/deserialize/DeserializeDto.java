package by.itech.kimbar.dto.deserialize;


import by.itech.kimbar.dto.Dto;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public interface DeserializeDto {
      List<Dto> deSerialize(HttpServletRequest req) throws IOException ;
}
