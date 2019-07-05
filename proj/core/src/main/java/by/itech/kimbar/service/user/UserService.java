package by.itech.kimbar.service.user;

import by.itech.kimbar.entity.Gender;
import by.itech.kimbar.entity.MaritalStatus;
import by.itech.kimbar.entity.User;
import by.itech.kimbar.service.exception.ServiceException;

import java.util.Date;
import java.util.List;

public interface UserService {
    String getAllInJson() throws ServiceException;
    boolean updateUser(String name, String surname, String lastName, Date date, Gender gender, String citizenship,
                       MaritalStatus maritalStatus, String webSite, String email, String workplace,
                       String country, String city, String street, String house, String numOfFlat,
                       Integer index, String photoPath, Integer idClient) throws ServiceException;
    boolean deleteUser(Integer[] id) throws  ServiceException;

    int getCountOfUsers() throws ServiceException;

    String getPaginationUsers(Integer start , Integer total) throws ServiceException;

    boolean createUser(String name, String surname, String lastName, Date date, Gender gender, String citizenship,
                       MaritalStatus maritalStatus, String webSite, String email, String workplace,
                       String country, String city, String street, String house, String numOfFlat, Integer index) throws ServiceException;

    String findUserByParameter(String name, String surname, String lastName, Gender gender, Date startDate,
                                   MaritalStatus maritalStatus, String citizenship, String country, String city, String street,
                                   String house, String numOfHouse, Integer index,Integer start, Integer total) throws ServiceException;

    String countUserByParameter(String name, String surname, String lastName, Gender gender, Date date,
                                MaritalStatus maritalStatus, String citizenship, String country, String city, String street,
                                String house, String numOfHouse, Integer index) throws ServiceException;

    List<User> birthdayMen() throws ServiceException;
}
