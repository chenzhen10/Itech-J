package by.itech.kimbar.service.validation.impl;

import by.itech.kimbar.dto.Dto;
import by.itech.kimbar.entity.Gender;
import by.itech.kimbar.service.validation.Validation;

import java.util.Date;
import java.util.List;

public class ValidationImpl implements Validation {
    private ValidationImpl() {
    }

    //attachment
    public static boolean validateAttachFile(String name, String comment, Integer id, String path) {
        return name != null &&  name.length() < 44  && id != null && path != null;
    }

    public static boolean validateDeleteAttachments(Integer[] id, String[] fileNames, String[] paths) {
        return id != null &&  id.length > 0 && fileNames != null && fileNames.length > 0 && paths != null && paths.length > 0;
    }

    public static boolean validateUpdateAttachment(List<Dto> attachments) {
        return attachments != null && !attachments.isEmpty();
    }

    // attachment & phone
    public static boolean validateGetAllInJsonByUserId(Integer userId) {
        return userId != null;
    }

//phone

    public static boolean validateDelete(Integer[] id) {
        return id.length > 0;
    }

    public static boolean validateCreatePhone(Integer countryCode, Integer operatorCode, Integer number,
                                              Integer idClient) {
        return countryCode != null && operatorCode != null && number != null && idClient != null;
    }

    public static boolean validateUpdatePhone(List<Dto> phone) {
        return !phone.isEmpty();
    }

    //user
    public static boolean validateUpdateUserFields(String name, String surname, String lastName, Date date, Gender gender, String citizenship,
                                             String maritalStatus, String webSite, String email, String workplace,
                                             String country, String city, String street, String house,
                                             String numOfFlat, Integer index, String photoPath, Integer idClient) {

        return name != null && name.length() < 84 && surname != null && surname.length() < 84 && idClient != null;
    }
    //&& lastName.length() < 44
    //                && citizenship.length() < 64 && maritalStatus.length() < 45 && webSite.length() < 74
    //                && email.length() < 45 && workplace.length() < 75 && country.length() < 44 && city.length() < 44
    //                && street.length() < 44 && house.length() < 44 && numOfFlat.length() < 44 && photoPath.length() < 254

    public static boolean validateCreateUserFields(String name, String surname, String lastName, Date date, Gender gender,
                                                   String citizenship,String maritalStatus, String webSite, String email,
                                                   String workplace,String country, String city, String street, String house,
                                                   String numOfFlat, Integer index) {

        return  name != null && name.length() < 84 && surname != null && surname.length() < 84 ;
    }
    //name.length() < 84 && surname.length() < 84 && lastName.length() < 44
    //                && citizenship.length() < 64 && maritalStatus.length() < 45 && webSite.length() < 74
    //                && email.length() < 45 && workplace.length() < 75 && country.length() < 44 && city.length() < 44
    //                && street.length() < 44 && house.length() < 44 && numOfFlat.length() < 44

    public static boolean validatePagination(Integer startValue,Integer total){
        return startValue != null && total != null;
    }

    public static boolean validateSearchFields(String name, String surname, String lastName, Gender gender, Date date,
                                               String maritalStatus,String citizenship,
                                               String country, String city, String street, String house,
                                               String numOfFlat, Integer index){
       return name != null && name.length() < 84 && surname != null && surname.length() < 84;
    }
    // name.length() < 84 && surname.length() < 84 && lastName.length() < 44
    //              && citizenship.length() < 64 && maritalStatus.length() < 45 && country.length() < 44 && city.length() < 44
    //              && street.length() < 44 && house.length() < 44 && numOfFlat.length() < 44

}
