package by.itech.kimbar.dao.phone;

import by.itech.kimbar.dao.exception.DaoException;
import by.itech.kimbar.entity.Phone;

import java.sql.SQLException;
import java.util.List;

public interface PhoneDao {
    List<Phone> getAllPhones() throws DaoException;
    boolean updatePhone(int id, int countryCode, int operatorCode, int numberCode, String type, String commentary) throws DaoException;

    boolean deletePhone(int[] id) throws DaoException, SQLException, ClassNotFoundException;
        //number must be not null
    boolean createPhone(int countryCode, int operatorCode, int number, String type, String commentary, int idClient) throws DaoException, ClassNotFoundException, SQLException;
}
