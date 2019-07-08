package by.itech.kimbar.controller.command.impl.user;

import by.itech.kimbar.controller.command.Command;
import by.itech.kimbar.entity.Gender;
import by.itech.kimbar.entity.MaritalStatus;
import by.itech.kimbar.service.ServiceProvider;
import by.itech.kimbar.service.exception.ServiceException;
import by.itech.kimbar.service.user.UserService;
import by.itech.kimbar.util.*;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class UpdateUserCommand implements Command {
    private static final Logger log = Logger.getLogger(UpdateUserCommand.class);

    static {
        try {
            DirectoryCreator.createPhotoDirectory();
        } catch (IOException e) {
            log.error(e);
        }
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServiceException {
        UserService us = ServiceProvider.getInstance().getUserService();

        List<String> list = RequestBodyReader.extractDataFromBody(req);

        String userId = null;
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
        String extension = null;

        //with pictures 19 without 18
        if (list.size() == 18) {
            userId = list.get(0);
            //replace with normal impl
            path = PathPropertyReader.readPhotoPath() + File.separator + list.get(0) + File.separator + list.get(0) +  "." + list.get(17);
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
        } else {
            userId = list.get(0);
            path = PathPropertyReader.readPhotoPath() + File.separator + list.get(0) + File.separator + list.get(1);
            name = list.get(2);
            surname = list.get(3);
            lastName = list.get(4);
            date = list.get(5);
            gender = list.get(6);
            citizenship = list.get(7);
            maritalStatus = list.get(8);
            webSite = list.get(9);
            email = list.get(10);
            country = list.get(11);
            city = list.get(12);
            street = list.get(13);
            house = list.get(14);
            numOfFlat = list.get(15);
            index = list.get(16);
            workplace = list.get(17);
        }


        //exctract in method
        Gender gdr = null;
        if (gender.equals("Male") || gender.equals("Female")) {
            gdr = Gender.valueOf(gender);
        }

        MaritalStatus ms = null;
        if (maritalStatus.equals(MaritalStatus.Married.toString()) || maritalStatus.equals(MaritalStatus.Single.toString())) {
            ms = MaritalStatus.valueOf(maritalStatus);
        }


        try {
            log.debug("Parameters : " + name + surname + lastName + DateConverter.convert(date) + gdr +
                    citizenship + ms + webSite + email + workplace +
                    country + city + street +
                    house + numOfFlat + NumericChecker.check(index) +
                    path + NumericChecker.check(userId));

            log.debug(us.updateUser(name, surname, lastName, DateConverter.convert(date), gdr,
                    citizenship, ms, webSite, email, workplace,
                    country, city, street,
                    house, numOfFlat, NumericChecker.check(index),
                    path, NumericChecker.check(userId)));
        } catch (ServiceException | ParseException e) {
            log.error(e);
            throw new ServiceException("Data which you inputted too long try to trim it as much as it possible");
        }
    }

}
