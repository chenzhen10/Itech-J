package by.itech.kimbar.service.attachment.impl;


import by.itech.kimbar.dao.DaoProvider;
import by.itech.kimbar.dao.attachment.AttachmentDao;
import by.itech.kimbar.dao.exception.DaoException;
import by.itech.kimbar.dao.impl.connection.ConnectionCloser;
import by.itech.kimbar.dao.impl.connection.ConnectionPool;
import by.itech.kimbar.dto.AttachmentDto;
import by.itech.kimbar.dto.Dto;
import by.itech.kimbar.service.attachment.AttachmentService;
import by.itech.kimbar.service.exception.ServiceException;
import by.itech.kimbar.util.FileEraser;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AttachmentServiceImpl implements AttachmentService {

    private static final DaoProvider provider = DaoProvider.getInstance();

    @Override
    public boolean attachFile(String name, String comment, Integer id,String path) throws ServiceException {
        AttachmentDao ad = provider.getAttachmentDao();
        Connection c = ConnectionPool.getInstance().getConnection();
        boolean result = false;
        try {
            c.setAutoCommit(false);
           result = ad.attachFile(name,comment,id,path);
            c.commit();
            c.setAutoCommit(true);
        } catch (DaoException | SQLException e) {
            try {
                c.rollback();
            } catch (SQLException e1) {
                throw new ServiceException(e1);
            }
            throw new ServiceException(e);
        } finally {
            ConnectionCloser.close(c, null, null);
        }

        return result;
    }

    @Override
    public String getAllInJson() throws ServiceException {
        ObjectMapper om = new ObjectMapper();
        AttachmentDao ad = provider.getAttachmentDao();
        String res ;
        try {
           res =  om.writeValueAsString(ad.getAllAttachments());
        } catch (DaoException | IOException e) {
            throw  new ServiceException(e);
        }

        return res;
    }

    @Override
    public boolean deleteAttachment(Integer[] id,String[] fileNames,String[] paths) throws ServiceException {
        AttachmentDao ad = provider.getAttachmentDao();
        boolean result = false;
        Connection c = ConnectionPool.getInstance().getConnection();
        try {
            c.setAutoCommit(false);
            result = ad.deleteAttachment(id);
            for (String path : paths){
                FileEraser.erase(path);
            }
            c.commit();
            c.setAutoCommit(true);
        } catch (DaoException  | SQLException e) {
            try {
                c.rollback();
            } catch (SQLException e1) {
                throw new ServiceException(e1);
            }
            throw new ServiceException(e);
        }  finally {
            ConnectionCloser.close(c, null, null);
        }
        return result;
    }

    @Override
    public boolean updateAttachment(List<Dto> attachments) throws ServiceException {
        boolean result = false;
        AttachmentDao pd = provider.getAttachmentDao();
        Connection c = ConnectionPool.getInstance().getConnection();
        try {
            c.setAutoCommit(false);
            for (Dto attachment : attachments){
                AttachmentDto at = (AttachmentDto) attachment;
                result = pd.updateAttachment(at.getId(),at.getName(),at.getCommentary());
            }
            c.commit();
            c.setAutoCommit(true);
        } catch (DaoException | SQLException e) {
            try {
                c.rollback();
            } catch (SQLException e1) {
                throw new ServiceException(e1);
            }
            throw new ServiceException(e);
        } finally {
            ConnectionCloser.close(c, null, null);
        }
        return result;
    }

    @Override
    public int getCountOfAttachments() throws ServiceException {
        AttachmentDao ad = provider.getAttachmentDao();
        int result = 0;
        try {
           result =  ad.getCountOfAttachments();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        //if there is no attachments it will start from 0
        return ++result ;
    }

    @Override
    public String getAllInJsonByUserId(Integer userId) throws ServiceException {
        ObjectMapper om = new ObjectMapper();
        String res = null;
            AttachmentDao phoneDao = provider.getAttachmentDao();
            try {
                res = om.writeValueAsString(phoneDao.getAttachmentByUserId(userId));
            } catch (DaoException | IOException e) {
                throw new ServiceException(e);
            }
        return res;
    }
}
