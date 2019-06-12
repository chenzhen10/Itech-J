package by.itech.kimbar.dao.user;

import by.itech.kimbar.dao.exception.DaoException;
import by.itech.kimbar.entity.Address;
import by.itech.kimbar.entity.Gender;
import by.itech.kimbar.entity.User;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface UserDao {
    List<User> getAllUser() throws DaoException;
    List<User> getPaginationUsers(int start ,int total) throws DaoException;
    List<User> findUser(String parameter) throws DaoException;

    //work
    // fix if else statement
    boolean createUser(String name, String surname, String lastName, Date date, Gender gender, String citizenship,
                       String maritalStatus, String webSite, String email, String workplace,
                       String country, String city, String street, String house, String numOfFlat, Integer index) throws DaoException;

    boolean deleteUser(int[] id) throws DaoException;
    boolean updateUser(String parameter) throws DaoException;
}
