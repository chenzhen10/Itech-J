package by.itech.kimbar.dao.attachment;

import by.itech.kimbar.dao.exception.DaoException;
import by.itech.kimbar.entity.Attachment;

import java.util.Date;
import java.util.List;

public interface AttachmentDao {
    boolean attachFile(String name, String comment ,Integer id,String path) throws DaoException;
    List<Attachment> getAllAttachments() throws DaoException;

    List<Attachment> getAttachmentByUserId(Integer userId) throws DaoException;

    boolean deleteAttachment(Integer[] id) throws DaoException;

    int getCountOfAttachments() throws DaoException;

    //params to delete attachment
    boolean updateAttachment(Integer id, String name, String commentary) throws DaoException;
}
