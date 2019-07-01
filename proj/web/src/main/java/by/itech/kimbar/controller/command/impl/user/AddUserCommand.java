package by.itech.kimbar.controller.command.impl.user;

import by.itech.kimbar.controller.command.Command;
import by.itech.kimbar.entity.Gender;
import by.itech.kimbar.service.ServiceProvider;
import by.itech.kimbar.service.exception.ServiceException;
import by.itech.kimbar.util.DateChecker;
import by.itech.kimbar.util.NumericChecker;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;


public class AddUserCommand implements Command {
    private static final Logger log = Logger.getLogger(AddUserCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        ServiceProvider sp = ServiceProvider.getInstance();
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String lastName = req.getParameter("lastName");
        String date = req.getParameter("date");
        String gender = req.getParameter("gender");
        String citizenship = req.getParameter("citizenship");
        String maritalStatus = req.getParameter("maritalStatus");
        String webSite = req.getParameter("webSite");
        String email = req.getParameter("email");
        String workplace = req.getParameter("workplace");
        String country = req.getParameter("country");
        String city = req.getParameter("city");
        String street = req.getParameter("street");
        String house = req.getParameter("house");
        String numOfFlat = req.getParameter("numOfFlat");
        String index = req.getParameter("index");



        try {
            sp.getUserService().createUser(name, surname, lastName, DateChecker.check(date), Gender.valueOf(gender), citizenship, maritalStatus,
                    webSite, email, workplace, country, city, street, house, numOfFlat, NumericChecker.check(index));
        } catch (ServiceException | ParseException e) {
            log.error(e);
        }


    }


}


