package by.itech.kimbar.service.phone;

import by.itech.kimbar.dto.Dto;
import by.itech.kimbar.entity.Phone;
import by.itech.kimbar.service.exception.ServiceException;

import java.util.List;

public interface PhoneService {
    String getAllInJsonByUserId(Integer userId) throws ServiceException;
    String getAllInJson() throws ServiceException;
    boolean deletePhone(Integer[] id) throws  ServiceException;
    boolean createPhone(Integer countryCode, Integer operatorCode, Integer number, Phone.Type type, String commentary,
                        Integer idClient ) throws  ServiceException;

    boolean updatePhone(List<Dto> phones) throws  ServiceException;

    boolean findDuplicatePhone(Integer countryCode,Integer operatorCode,Integer number) throws ServiceException;
}
