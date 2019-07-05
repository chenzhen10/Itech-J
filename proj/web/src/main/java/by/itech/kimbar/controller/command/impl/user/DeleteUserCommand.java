package by.itech.kimbar.controller.command.impl.user;

import by.itech.kimbar.controller.command.Command;
import by.itech.kimbar.service.ServiceProvider;
import by.itech.kimbar.service.exception.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class DeleteUserCommand implements Command {
    private static final Logger log = Logger.getLogger(DeleteUserCommand.class);


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        ServiceProvider sp = ServiceProvider.getInstance();
        String data = req.getParameter("id");



        String[] splitId = data.split(",");
        Integer[] userId = new Integer[splitId.length];

        for (int i = 0; i < splitId.length ; i++) {
            userId[i] = Integer.valueOf(splitId[i]);
        }
        try {
            log.debug("Parameters : " + Arrays.toString(userId));
            log.debug( sp.getUserService().deleteUser(userId));
        } catch (ServiceException  e) {
           log.error(e);
            throw new ServiceException();
        }


    }
}
