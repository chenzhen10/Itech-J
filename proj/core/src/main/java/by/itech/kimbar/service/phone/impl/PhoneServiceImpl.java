package by.itech.kimbar.service.phone.impl;

import by.itech.kimbar.dao.DaoProvider;
import by.itech.kimbar.dao.exception.DaoException;
import by.itech.kimbar.dao.impl.connection.ConnectionCloser;
import by.itech.kimbar.dao.impl.connection.ConnectionPool;
import by.itech.kimbar.dao.phone.PhoneDao;
import by.itech.kimbar.dto.Dto;
import by.itech.kimbar.dto.PhoneDto;
import by.itech.kimbar.entity.Phone;
import by.itech.kimbar.service.exception.ServiceException;
import by.itech.kimbar.service.phone.PhoneService;
import by.itech.kimbar.service.validation.impl.ValidationImpl;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PhoneServiceImpl implements PhoneService {
    private static final DaoProvider provider = DaoProvider.getInstance();


    @Override
    public String getAllInJson() throws ServiceException {
        ObjectMapper om = new ObjectMapper();
        String res = null;
        PhoneDao phoneDao = provider.getPhoneDao();
        try {
            res = om.writeValueAsString(phoneDao.getAll());
        } catch (DaoException | IOException e) {
            throw new ServiceException(e);
        }

        return res;
    }

    @Override
    public String getAllInJsonByUserId(Integer userId) throws ServiceException {
        if (ValidationImpl.validateGetAllInJsonByUserId(userId)) {
            ObjectMapper om = new ObjectMapper();
            String res = null;
            PhoneDao phoneDao = provider.getPhoneDao();

            try {
                res = om.writeValueAsString(phoneDao.getPhoneByUserId(userId));
            } catch (DaoException | IOException e) {
                throw new ServiceException(e);
            }
            return res;
        } else {
            throw new ServiceException("Absent user id");
        }
    }

    @Override
    public boolean deletePhone(Integer[] id) throws ServiceException {
        if (ValidationImpl.validateDelete(id)) {
            PhoneDao usrDao = provider.getPhoneDao();
            boolean result = false;
            Connection c = ConnectionPool.getInstance().getConnection();
            try {
                c.setAutoCommit(false);
                result = usrDao.deletePhone(id);
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
        } else {
            throw new ServiceException("There is no id to delete");
        }

    }

    @Override
    public boolean createPhone(Integer countryCode, Integer operatorCode, Integer number, Phone.Type type, String commentary,
                               Integer idClient) throws ServiceException {
        boolean result = false;
        if (ValidationImpl.validateCreatePhone(countryCode, operatorCode, number, idClient)) {
            PhoneDao pd = provider.getPhoneDao();
            Connection c = ConnectionPool.getInstance().getConnection();
            try {
                c.setAutoCommit(false);
                result = pd.createPhone(countryCode, operatorCode, number, type, commentary, idClient);
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
        } else throw new ServiceException("Inputted data are incorrect");
        return result;
    }

    @Override
    public boolean updatePhone(List<Dto> phones) throws ServiceException {
        if (ValidationImpl.validateUpdatePhone(phones)) {
            boolean result = false;
            PhoneDao pd = provider.getPhoneDao();
            Connection c = ConnectionPool.getInstance().getConnection();
            try {
                c.setAutoCommit(false);
                for (Dto phone : phones) {
                    PhoneDto ph = (PhoneDto) phone;
                    result = pd.updatePhone(ph.getCountryCode(), ph.getOperatorCode(),
                            ph.getNumber(), ph.getType(), ph.getCommentary(), ph.getId());
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
        } else {
            throw new ServiceException("Data is empty");
        }
    }

    @Override
    public boolean findDuplicatePhone(Integer countryCode,Integer operatorCode,Integer number) throws ServiceException {
        boolean res = false;
        PhoneDao phoneDao = provider.getPhoneDao();
        try {
            res = phoneDao.findDuplicatePhone(countryCode,operatorCode,number);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return res;
    }

}
