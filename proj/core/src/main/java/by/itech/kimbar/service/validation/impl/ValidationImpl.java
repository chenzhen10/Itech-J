package by.itech.kimbar.service.validation.impl;

import by.itech.kimbar.dto.Dto;
import by.itech.kimbar.service.validation.Validation;

import java.util.List;

public class ValidationImpl implements Validation {
    private ValidationImpl() {
    }

    //attachment
    public static boolean validateAttachFile(String name, String comment, Integer id, String path) {
        boolean commentValidation = true;
        if (comment != null  && comment.length() > 254){
            commentValidation = false;
        }
        return name != null &&  name.length() < 44  && id != null && path != null && commentValidation;
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
    public static boolean validateUpdateUserFields(String name, String surname, String lastName, String citizenship,
                                             String webSite, String email, String workplace,
                                             String country, String city, String street, String house,
                                             String numOfFlat, Integer idClient) {

        return name != null && name.length() < 25 && surname != null && surname.length() < 34 && idClient != null
                && checkUserField(lastName, citizenship, webSite, email, workplace,
                country, city, street, house, numOfFlat);
    }


    public static boolean validateCreateUserFields(String name, String surname, String lastName,
                                                   String citizenship,String webSite, String email,
                                                   String workplace,String country, String city, String street, String house,
                                                   String numOfFlat) {

        return  name != null && name.length() < 10 && surname != null && surname.length() < 34
                && checkUserField(lastName, citizenship, webSite, email, workplace,
                country, city, street, house, numOfFlat) ;
    }

    public static boolean validatePagination(Integer startValue,Integer total){
        return startValue != null && total != null;
    }

    public static boolean validateSearchFields(String name, String surname, String lastName,String citizenship,
                                               String country, String city, String street, String house,
                                               String numOfFlat){

        boolean result = true;

        if (lastName != null && lastName.length() > 14){
            result = false;
        }else if(citizenship != null && citizenship.length() > 64){
            result = false;
        }else if(country != null && country.length() > 44){
            result = false;
        }else if(city != null && city.length() > 44 ){
            result = false;
        }else if(street != null && street.length() > 44 ){
            result = false;
        }else if( house != null && house.length() > 44){
            result = false;
        }else if(numOfFlat != null && numOfFlat.length() > 44 ){
            result = false;
        }



       return name != null && name.length() < 25 && surname != null && surname.length() < 34 && result;
    }

    private static boolean checkUserField(String lastName,
                                   String citizenship,String webSite, String email,
                                   String workplace,String country, String city, String street, String house,
                                   String numOfFlat){
        boolean result = true;
        if (lastName != null && lastName.length() > 14){
            result = false;
        }else if(citizenship != null && citizenship.length() > 64){
            result = false;
        }else if(webSite != null && webSite.length() > 74){
            result = false;
        }else if( email != null && email.length() > 45){
            result = false;
        }else if(workplace != null && workplace.length() > 74){
            result = false;
        }else if(country != null && country.length() > 44){
            result = false;
        }else if(city != null && city.length() > 44 ){
            result = false;
        }else if(street != null && street.length() > 44 ){
            result = false;
        }else if( house != null && house.length() > 44){
            result = false;
        }else if(numOfFlat != null && numOfFlat.length() > 44 ){
            result = false;
        }
        return  result;
    }
}
