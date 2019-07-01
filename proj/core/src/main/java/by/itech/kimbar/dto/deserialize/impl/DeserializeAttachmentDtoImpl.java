package by.itech.kimbar.dto.deserialize.impl;

import by.itech.kimbar.dto.AttachmentDto;
import by.itech.kimbar.dto.deserialize.DeserializeDto;
import by.itech.kimbar.dto.Dto;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class DeserializeAttachmentDtoImpl implements DeserializeDto {
    @Override
    public List<Dto> deSerialize(HttpServletRequest req) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(req.getInputStream(), "UTF8"));
        ObjectMapper om = new ObjectMapper();
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line);
        }
        List<Dto> res = Arrays.asList(om.readValue(sb.toString(), AttachmentDto[].class));
        return res;
    }
}
