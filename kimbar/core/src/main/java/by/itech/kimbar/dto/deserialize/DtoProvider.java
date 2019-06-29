package by.itech.kimbar.dto.deserialize;

import by.itech.kimbar.dto.deserialize.impl.DeserializeAttachmentDtoImpl;
import by.itech.kimbar.dto.deserialize.impl.DeserializePhoneDtoImpl;

public class DtoProvider {
    private static final DtoProvider instance = new DtoProvider();
    private static final DeserializePhoneDtoImpl phoneDto = new DeserializePhoneDtoImpl();
    private static final DeserializeAttachmentDtoImpl attachmentDto = new DeserializeAttachmentDtoImpl();



    private DtoProvider(){}


    public static DtoProvider getInstance(){
        return instance;
    }

    public DeserializeDto getPhoneDto(){
        return phoneDto;
    }
    public DeserializeDto getAttachmentDto(){
        return attachmentDto;
    }

}
