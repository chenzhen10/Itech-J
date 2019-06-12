package by.itech.kimbar.controller.impl;

import by.itech.kimbar.controller.Command;
import by.itech.kimbar.entity.Gender;
import by.itech.kimbar.service.ServiceProvider;
import by.itech.kimbar.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class AddContactCommand implements Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        ServiceProvider sp = ServiceProvider.getInstance();
        String name = (String) req.getAttribute("name");
        String surname = (String) req.getAttribute("surname");
        String lastName = (String) req.getAttribute("lastName");
        Date date = (Date) req.getAttribute("date");
        Gender gender = (Gender) req.getAttribute("gender");
        String citizenship = (String) req.getAttribute("citizenship");
        String maritalStatus = (String) req.getAttribute("maritalStatus");
        String webSite = (String) req.getAttribute("webSite");
        String email = (String) req.getAttribute("email");
        String workplace = (String) req.getAttribute("workplace");
        String country = (String) req.getAttribute("country");
        String city = (String) req.getAttribute("city");
        String street = (String) req.getAttribute("street");
        String house = (String) req.getAttribute("house");
        String numOfFlat = (String) req.getAttribute("numOfFlat");
        Integer index = (Integer) req.getAttribute("index");

        System.out.println(name);
        System.out.println(surname);
        try {
            sp.getUserService().createUser(name,surname,lastName,date,gender,citizenship,maritalStatus,webSite,email,workplace,country,city,street,house,numOfFlat,index);

            resp.sendRedirect("client?command=all_users");
        } catch (ServiceException | IOException e) {
            //log
            e.printStackTrace();
        }

    }
}
