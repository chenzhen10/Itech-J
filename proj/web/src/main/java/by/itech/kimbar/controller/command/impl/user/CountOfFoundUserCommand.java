package by.itech.kimbar.controller.command.impl.user;

import by.itech.kimbar.controller.command.Command;
import by.itech.kimbar.entity.Gender;
import by.itech.kimbar.entity.MaritalStatus;
import by.itech.kimbar.service.ServiceProvider;
import by.itech.kimbar.service.exception.ServiceException;
import by.itech.kimbar.service.user.UserService;
import by.itech.kimbar.util.DateConverter;
import by.itech.kimbar.util.NumericChecker;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

public class CountOfFoundUserCommand implements Command {
    private static final Logger log = Logger.getLogger(CountOfFoundUserCommand.class);
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException, IOException {
        UserService us = ServiceProvider.getInstance().getUserService();
        ObjectMapper om = new ObjectMapper();

        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String lastName = req.getParameter("lastName");
        String date = req.getParameter("date");
        String gender = req.getParameter("gender");
        String citizenship = req.getParameter("citizenship");
        String maritalStatus = req.getParameter("maritalStatus");
        String country = req.getParameter("country");
        String city = req.getParameter("city");
        String street = req.getParameter("street");
        String house = req.getParameter("house");
        String numOfFlat = req.getParameter("numOfFlat");
        String index = req.getParameter("index");


        Gender gdr = null;
        if (gender.equals("Male") || gender.equals("Female") ) {
            gdr = Gender.valueOf(gender);
        }

        MaritalStatus mS = null;
        if (maritalStatus.equals("Single") || maritalStatus.equals("Married")){
            mS = MaritalStatus.valueOf(maritalStatus);
        }
        try {
           resp.getWriter().write(om.writeValueAsString(us.countUserByParameter(name, surname, lastName, gdr, DateConverter.convert(date),
                    mS, citizenship, country, city, street, house, numOfFlat, NumericChecker.check(index))));
        } catch (ParseException e) {
            log.error(e);
            throw new ServiceException();
        }
    }
}
