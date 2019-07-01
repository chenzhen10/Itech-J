package by.itech.kimbar.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateChecker {
    private DateChecker(){}

    //check if string is date in proper format
    public static Date check(String date) throws ParseException {
        if (date != null && !date.equals("") ) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.parse(date.trim());
        } else {
            return null;
        }

    }
}
