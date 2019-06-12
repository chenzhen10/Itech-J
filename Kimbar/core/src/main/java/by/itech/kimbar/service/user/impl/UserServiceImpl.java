package by.itech.kimbar.service.user.impl;

import by.itech.kimbar.dao.DaoProvider;
import by.itech.kimbar.dao.exception.DaoException;
import by.itech.kimbar.dao.user.UserDao;
import by.itech.kimbar.entity.Gender;
import by.itech.kimbar.entity.User;
import by.itech.kimbar.service.user.UserService;
import by.itech.kimbar.service.exception.ServiceException;
import by.itech.kimbar.service.validation.UserValidation;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class UserServiceImpl implements UserService {

    private static  final DaoProvider provider = DaoProvider.getInstance();

    @Override
    public String getAllInJson() throws ServiceException {
        ObjectMapper om = new ObjectMapper();
        String res  = null;

        if(UserValidation.validate()){
            UserDao usrDao = provider.getUserDao();
            try {
                res = om.writeValueAsString(usrDao.getAllUser());
            } catch (DaoException | IOException e) {
                throw new ServiceException(e);
            }
        }
        return res;
    }

    @Override
    public boolean updateUser(String parameter) {
        return true;
    }

    @Override
    public boolean deleteUser(String parameter) {
        return true;
    }

    // implement
    @Override
    public boolean createUser(String name, String surname, String lastName, Date date, Gender gender, String citizenship,
                              String maritalStatus, String webSite, String email, String workplace,
                              String country, String city, String street, String house, String numOfFlat, Integer index) throws ServiceException {
        boolean result = false;
        UserDao usrDao = provider.getUserDao();
        try {
           result =  usrDao.createUser
                    (name,surname,lastName,date,gender,citizenship,maritalStatus
                            ,webSite,email,workplace,country,city,street,house,numOfFlat,index);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public List<User> findUserByParameter(String parameter) {
        return null;
    }
}
