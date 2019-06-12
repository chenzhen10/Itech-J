package by.itech.kimbar.controller.impl;

import by.itech.kimbar.controller.Command;
import by.itech.kimbar.service.ServiceProvider;
import by.itech.kimbar.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AllContactsCommand implements Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        ServiceProvider sp = ServiceProvider.getInstance();
        try {
            resp.getWriter().write(sp.getUserService().getAllInJson());
        } catch (IOException | ServiceException e) {
            //logger here
            e.printStackTrace();
        }
    }
}
