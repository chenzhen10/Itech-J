package by.itech.kimbar.controller.command.impl.user;

import by.itech.kimbar.controller.command.Command;
import by.itech.kimbar.service.ServiceProvider;
import by.itech.kimbar.service.exception.ServiceException;
import by.itech.kimbar.service.user.UserService;
import by.itech.kimbar.util.NumericChecker;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PaginationUserCommand implements Command {
    private static final Logger log = Logger.getLogger(PaginationUserCommand.class);
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServiceException {
        UserService us = ServiceProvider.getInstance().getUserService();

        String startValue = req.getParameter("page");

        String total = req.getParameter("total");

        try {
            resp.getWriter().write(us.getPaginationUsers(NumericChecker.check(startValue),NumericChecker.check(total)));
        } catch (ServiceException e) {
           log.error(e);
           throw new ServiceException();
        }

    }
}
