package by.itech.kimbar.controller.command.impl.user;

import by.itech.kimbar.controller.command.Command;
import by.itech.kimbar.service.ServiceProvider;
import by.itech.kimbar.service.exception.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AllUserCommand implements Command {
    private static final Logger log = Logger.getLogger(AddUserCommand.class);
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        ServiceProvider sp = ServiceProvider.getInstance();
        try {
            resp.getWriter().write(sp.getUserService().getAllInJson());
        } catch (IOException | ServiceException e) {
            log.error(e);
        }
    }
}
