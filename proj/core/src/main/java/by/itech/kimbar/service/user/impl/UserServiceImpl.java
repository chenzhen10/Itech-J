package by.itech.kimbar.service.user.impl;

import by.itech.kimbar.dao.DaoProvider;
import by.itech.kimbar.dao.exception.DaoException;
import by.itech.kimbar.dao.impl.connection.ConnectionCloser;
import by.itech.kimbar.dao.impl.connection.ConnectionPool;
import by.itech.kimbar.dao.user.UserDao;
import by.itech.kimbar.entity.Gender;
import by.itech.kimbar.entity.User;
import by.itech.kimbar.service.user.UserService;
import by.itech.kimbar.service.exception.ServiceException;
import by.itech.kimbar.service.validation.impl.ValidationImpl;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UserServiceImpl implements UserService {

    private static final DaoProvider provider = DaoProvider.getInstance();

    @Override
    public String getAllInJson() throws ServiceException {
        ObjectMapper om = new ObjectMapper();
        String res = null;
            UserDao usrDao = provider.getUserDao();
            try {
                res = om.writeValueAsString(usrDao.getAllUser());
            } catch (DaoException | IOException e) {
                throw new ServiceException(e);
            }

        return res;
    }

    @Override
    public boolean updateUser(String name, String surname, String lastName, Date date, Gender gender, String citizenship,
                              String maritalStatus, String webSite, String email, String workplace,
                              String country, String city, String street, String house, String numOfFlat, Integer index, String photoPath, Integer idClient) throws ServiceException {
        if (ValidationImpl.validateUpdateUserFields(name, surname, lastName, date, gender, citizenship, maritalStatus,
                webSite, email, workplace, country, city, street, house, numOfFlat, index, photoPath, idClient)) {

            UserDao usrDao = provider.getUserDao();
            boolean result = false;
            Connection c = ConnectionPool.getInstance().getConnection();
            try {
                c.setAutoCommit(false);
                result = usrDao.updateUser(name, surname, lastName, date, gender, citizenship,
                        maritalStatus, webSite, email, workplace, country, city, street, house, numOfFlat, index, photoPath, idClient);
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
        }else {
            throw new ServiceException("Inputted data are incorrect");
        }
    }
    @Override
    public boolean deleteUser(Integer[] id) throws ServiceException {
        if (ValidationImpl.validateDelete(id)) {

            UserDao usrDao = provider.getUserDao();
            Connection c = ConnectionPool.getInstance().getConnection();
            boolean result = false;
            try {
                c.setAutoCommit(false);
                result = usrDao.deleteUser(id);
                c.commit();
                c.setAutoCommit(true);
            } catch (DaoException | SQLException e) {
                try {
                    c.rollback();
                } catch (SQLException e1) {
                    throw new ServiceException(e);
                }
                throw new ServiceException(e);
            } finally {
                ConnectionCloser.close(c, null, null);
            }
            return result;
        }else {
            throw new ServiceException("There is no data to delete");
        }
    }

    @Override
    public int getCountOfUsers() throws ServiceException {
        UserDao usrDao = provider.getUserDao();
        int result = 0;
        try {
            result = usrDao.countOfUser();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public String getPaginationUsers(Integer start, Integer total) throws ServiceException {
        if (ValidationImpl.validatePagination(start, total)) {
            UserDao usrDao = provider.getUserDao();
            List<User> allUsers = null;
            ObjectMapper om = new ObjectMapper();
            String result = null;
            try {
                allUsers = usrDao.userPagination(start, total);
                result = om.writeValueAsString(allUsers);
            } catch (DaoException | IOException e) {
                throw new ServiceException(e);
            }
            return result;
        }else{
            throw new ServiceException("Data are incorrect");
        }
    }
    // implement validation if user want to input incorrect data
    @Override
    public boolean createUser(String name, String surname, String lastName, Date date, Gender gender, String citizenship,
                              String maritalStatus, String webSite, String email, String workplace,
                              String country, String city, String street, String house, String numOfFlat, Integer index) throws ServiceException {

        if (ValidationImpl.validateCreateUserFields(name, surname, lastName, date, gender, citizenship, maritalStatus,
                webSite, email, workplace, country, city, street, house, numOfFlat, index)) {

            boolean result = false;
            UserDao usrDao = provider.getUserDao();
            Connection c = ConnectionPool.getInstance().getConnection();
            try {
                c.setAutoCommit(false);
                result = usrDao.createUser
                        (name, surname, lastName, date, gender, citizenship, maritalStatus
                                , webSite, email, workplace, country, city, street, house, numOfFlat, index);
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
        }else{
            throw new ServiceException("Inputted data are incorrect");
        }
    }
    @Override
    public String findUserByParameter(String name, String surname, String lastName, Gender gender, Date date,
                                      String maritalStatus, String citizenship, String country, String city, String street,
                                      String house, String numOfHouse, Integer index) throws ServiceException {

        if (ValidationImpl.validateSearchFields(name, surname, lastName, gender, date, maritalStatus,
                citizenship, country, city, street, house, numOfHouse, index)) {

            UserDao ud = provider.getUserDao();
            List<User> users = null;
            ObjectMapper om = new ObjectMapper();
            String result = null;
            try {
                String query = "SELECT * FROM client WHERE 1 = 1 ";

                if (name != null && name.length() > 0) {
                    query += " AND name LIKE " + "'%" + name + "%'";
                }

                if (surname != null && surname.length() > 0) {
                    query += " AND surname LIKE " + "'%" + surname + "%'";
                }

                if (lastName != null && lastName.length() > 0) {
                    query += " AND last_name LIKE " + "'%" + lastName + "%'";
                }
                if (gender != null) {
                    query += " AND gender LIKE " + "'%" + gender + "%'";
                }
                if (maritalStatus != null && maritalStatus.length() > 0) {
                    query += " AND marital_status LIKE " + "'%" + maritalStatus + "%'";
                }
                if (date != null) {
                    query += " AND date = '" + new SimpleDateFormat("yyyy-MM-dd").format(date) + "'";
                }
                if (citizenship != null && citizenship.length() > 0) {
                    query += " AND citizenship LIKE " + "'%" + citizenship + "%'";
                }
                if (country != null && country.length() > 0) {
                    query += " AND country LIKE " + "'%" + country + "%'";
                }
                if (city != null && city.length() > 0) {
                    query += " AND city LIKE " + "'%" + city + "%'";
                }
                if (street != null && street.length() > 0) {
                    query += " AND street LIKE " + "'%" + street + "%'";
                }
                if (house != null && house.length() > 0) {
                    query += " AND house LIKE " + "'%" + house + "%'";
                }
                if (numOfHouse != null && numOfHouse.length() > 0) {
                    query += " AND num_of_house LIKE " + "'%" + numOfHouse + "%'";
                }
                if (index != null) {
                    query += " AND client.index LIKE " + "'%" + index + "%'";
                }
                users = ud.findUser(query);
                result = om.writeValueAsString(users);
            } catch (DaoException | IOException e) {
                throw new ServiceException(e);
            }
            return result;
        } else{
            throw new ServiceException("Inputted data are incorrect");
        }
    }

    @Override
    public List<User> birthdayMen() throws ServiceException {
        List<User> users = null;
        UserDao ud = provider.getUserDao();
        try {
            users = ud.birthdayMen();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return users;
    }


}
