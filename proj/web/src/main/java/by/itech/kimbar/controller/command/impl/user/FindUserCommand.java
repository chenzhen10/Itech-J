package by.itech.kimbar.controller.command.impl.user;

import by.itech.kimbar.controller.command.Command;
import by.itech.kimbar.entity.Gender;
import by.itech.kimbar.service.ServiceProvider;
import by.itech.kimbar.service.exception.ServiceException;
import by.itech.kimbar.service.user.UserService;
import by.itech.kimbar.util.DateChecker;
import by.itech.kimbar.util.NumericChecker;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;


public class FindUserCommand implements Command {
    private static final Logger log = Logger.getLogger(FindUserCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserService us = ServiceProvider.getInstance().getUserService();
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


        try {
            resp.getWriter().write(us.findUserByParameter(name, surname, lastName, gdr, DateChecker.check(date),
                    maritalStatus, citizenship, country, city, street, house, numOfFlat, NumericChecker.check(index)));
        } catch (ServiceException | ParseException e) {
            log.error(e);
        }
    }
}
