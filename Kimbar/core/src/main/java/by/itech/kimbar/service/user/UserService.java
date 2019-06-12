package by.itech.kimbar.service.user;

import by.itech.kimbar.entity.Gender;
import by.itech.kimbar.entity.User;
import by.itech.kimbar.service.exception.ServiceException;

import java.util.Date;
import java.util.List;

public interface UserService {
    String getAllInJson() throws ServiceException;
    boolean updateUser(String parameter);
    boolean deleteUser(String parameter);

    // implement
    boolean createUser(String name, String surname, String lastName, Date date, Gender gender, String citizenship,
                       String maritalStatus, String webSite, String email, String workplace,
                       String country, String city, String street, String house, String numOfFlat, Integer index) throws ServiceException;

    List<User> findUserByParameter(String parameter);
}
