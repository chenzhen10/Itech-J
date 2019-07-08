package by.itech.kimbar.controller.command.impl.user;

import by.itech.kimbar.controller.command.Command;
import by.itech.kimbar.entity.Gender;
import by.itech.kimbar.entity.MaritalStatus;
import by.itech.kimbar.service.ServiceProvider;
import by.itech.kimbar.service.exception.ServiceException;
import by.itech.kimbar.service.user.UserService;
import by.itech.kimbar.util.DateConverter;
import by.itech.kimbar.util.NumericChecker;
import by.itech.kimbar.util.RequestBodyReader;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;


public class AddUserCommand implements Command {
    private static final Logger log = Logger.getLogger(AddUserCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException, IOException {
        UserService sp = ServiceProvider.getInstance().getUserService();

        List<String> list = RequestBodyReader.extractDataFromBody(req, ServiceProvider.getInstance());

        String content = null;
        String path = null;
        String name = null;
        String surname = null;
        String lastName = null;
        String date = null;
        String gender = null;
        String citizenship = null;
        String maritalStatus = null;
        String webSite = null;
        String email = null;
        String country = null;
        String city = null;
        String street = null;
        String house = null;
        String numOfFlat = null;
        String index = null;
        String workplace = null;

        if (list.size() == 18) {
            content = list.get(0);
            name = list.get(1);
            surname = list.get(2);
            lastName = list.get(3);
            date = list.get(4);
            gender = list.get(5);
            citizenship = list.get(6);
            maritalStatus = list.get(7);
            webSite = list.get(8);
            email = list.get(9);
            country = list.get(10);
            city = list.get(11);
            street = list.get(12);
            house = list.get(13);
            numOfFlat = list.get(14);
            index = list.get(15);
            workplace = list.get(16);
            path = list.get(17);
        } else {
            name = list.get(0);
            surname = list.get(1);
            lastName = list.get(2);
            date = list.get(3);
            gender = list.get(4);
            citizenship = list.get(5);
            maritalStatus = list.get(6);
            webSite = list.get(7);
            email = list.get(8);
            country = list.get(9);
            city = list.get(10);
            street = list.get(11);
            house = list.get(12);
            numOfFlat = list.get(13);
            index = list.get(14);
            workplace = list.get(15);
        }

        Gender gdr = null;
        if (gender.equals("Male") || gender.equals("Female")) {
            gdr = Gender.valueOf(gender);
        }

        MaritalStatus mS = null;
        if (maritalStatus.equals("Single") || maritalStatus.equals("Married")) {
            mS = MaritalStatus.valueOf(maritalStatus);
        }


        try {
            log.debug("Parameters : " + name + surname + lastName + DateConverter.convert(date) + gdr + citizenship + mS +
                    webSite + email + workplace + country + city + street + house + numOfFlat + NumericChecker.check(index));

            log.debug(sp.createUser(name, surname, lastName, DateConverter.convert(date), gdr, citizenship, mS,
                    webSite, email, workplace, country, city, street, house, numOfFlat, NumericChecker.check(index), path, content));
        } catch (ServiceException | ParseException e) {
            log.error(e);
            throw new ServiceException("Data which you inputted too long try to trim it as much as it possible");
        }
    }
}


