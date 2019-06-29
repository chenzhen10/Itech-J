package by.itech.kimbar.service.user;

import by.itech.kimbar.dao.exception.DaoException;
import by.itech.kimbar.entity.Gender;
import by.itech.kimbar.entity.User;
import by.itech.kimbar.service.exception.ServiceException;

import java.util.Date;
import java.util.List;

public interface UserService {
    String getAllInJson() throws ServiceException;
    boolean updateUser(String name, String surname, String lastName, Date date, Gender gender, String citizenship,
                       String maritalStatus, String webSite, String email, String workplace,
                       String country, String city, String street, String house, String numOfFlat,
                       Integer index, String photoPath, int idClient) throws ServiceException;
    boolean deleteUser(int[] id) throws  ServiceException;

    int getCountOfUsers() throws ServiceException;

    String getPaginationUsers(int start , int total) throws ServiceException;

    boolean createUser(String name, String surname, String lastName, Date date, Gender gender, String citizenship,
                       String maritalStatus, String webSite, String email, String workplace,
                       String country, String city, String street, String house, String numOfFlat, Integer index) throws ServiceException;

    String findUserByParameter(String name, String surname, String lastName, Gender gender, Date startDate,
                                   String maritalStatus, String citizenship, String country, String city, String street,
                                   String house, String numOfHouse, Integer index) throws ServiceException;

    List<User> birthdayMen() throws ServiceException;
}
