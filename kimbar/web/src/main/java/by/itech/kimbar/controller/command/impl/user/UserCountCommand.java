package by.itech.kimbar.controller.command.impl.user;

import by.itech.kimbar.controller.command.Command;
import by.itech.kimbar.service.ServiceProvider;
import by.itech.kimbar.service.exception.ServiceException;
import by.itech.kimbar.service.user.UserService;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserCountCommand implements Command {
    private static final Logger log = Logger.getLogger(PaginationUserCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserService us = ServiceProvider.getInstance().getUserService();
        ObjectMapper om = new ObjectMapper();
        try {
            resp.getWriter().write(om.writeValueAsString(us.getCountOfUsers()));
        } catch (ServiceException e) {
            log.error(e);
        }

    }
}

