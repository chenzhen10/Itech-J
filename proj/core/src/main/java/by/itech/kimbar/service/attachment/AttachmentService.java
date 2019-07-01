package by.itech.kimbar.service.attachment;

import by.itech.kimbar.dto.Dto;
import by.itech.kimbar.service.exception.ServiceException;

import java.util.List;

public interface AttachmentService {
    boolean attachFile(String name , String comment , Integer id,String path) throws ServiceException;

    String getAllInJson() throws ServiceException;

    boolean deleteAttachment(Integer[] id,String[] fileNames,String[] path) throws ServiceException;

    boolean updateAttachment(List<Dto> attachments) throws ServiceException;

    int getCountOfAttachments() throws ServiceException;

    String getAllInJsonByUserId(Integer userId) throws ServiceException;
}
