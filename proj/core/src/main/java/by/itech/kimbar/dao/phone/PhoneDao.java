package by.itech.kimbar.dao.phone;

import by.itech.kimbar.dao.exception.DaoException;
import by.itech.kimbar.entity.Phone;

import java.util.List;

public interface PhoneDao {
    List<Phone> getAll() throws DaoException;
    List<Phone> getPhoneByUserId(Integer userId) throws DaoException;


    boolean updatePhone(Integer countryCode, Integer operatorCode, Integer numberCode, Phone.Type type, String commentary,Integer idPhone) throws DaoException;

    boolean deletePhone(Integer[] id) throws DaoException;

    boolean createPhone(Integer countryCode, Integer operatorCode, Integer number, Phone.Type type, String commentary, Integer idClient) throws DaoException;

    boolean findDuplicatePhone(Integer countryCode,Integer operatorCode,Integer number) throws DaoException;
}
