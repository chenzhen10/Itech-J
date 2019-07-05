package by.itech.kimbar.util;

import by.itech.kimbar.entity.Gender;
import by.itech.kimbar.entity.MaritalStatus;

import java.util.ArrayList;
import java.util.List;

public class EnumChecker {
    private EnumChecker(){}

    // 1 param is MaritalStatus 2 is Gender
    public static List<String> checkIfEnumExist(MaritalStatus status, Gender gender){
        List <String> result = new ArrayList<>();

        if (status == null){
            result.add(null);
        }else{
            result.add(status.toString());
        }

        if (gender == null){
            result.add(null);
        }else{
            result.add(gender.toString());
        }

       return result ;
    }

}
