package by.itech.kimbar.dao.user;

import by.itech.kimbar.dao.exception.DaoException;
import by.itech.kimbar.entity.Gender;
import by.itech.kimbar.entity.MaritalStatus;
import by.itech.kimbar.entity.User;

import java.util.Date;
import java.util.List;

public interface UserDao {
    Integer countOfUser() throws DaoException;

    List<User> userPagination(Integer start, Integer total) throws DaoException;

    List<User> getAllUser() throws DaoException;

    List<User> findUser(String query) throws DaoException;


    Integer countOfFoundUsers(String query) throws DaoException;

    boolean createUser(String name, String surname, String lastName, Date date, Gender gender, String citizenship,
                       MaritalStatus maritalStatus, String webSite, String email, String workplace,
                       String country, String city, String street, String house, String numOfFlat, Integer index) throws DaoException;

    boolean deleteUser(Integer[] id) throws DaoException;
    boolean updateUser(String name, String surname, String lastName, Date date, Gender gender, String citizenship,
                       MaritalStatus maritalStatus, String webSite, String email, String workplace,
                       String country, String city, String street, String house, String numOfFlat, Integer index, String photoPath, Integer idClient) throws DaoException;

    List<User> birthdayMen() throws DaoException;
}
