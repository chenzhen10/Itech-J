package by.itech.kimbar.util;

import org.apache.commons.lang3.StringUtils;

public class NumericChecker {
    private NumericChecker(){}

    //check if string is number
    public static Integer check(String number){
        Integer result = null;
        if(StringUtils.isNumeric(number)){
            result = Integer.parseInt(number);
        }
        return result;
    }
}
