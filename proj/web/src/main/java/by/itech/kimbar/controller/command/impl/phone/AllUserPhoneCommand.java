package by.itech.kimbar.controller.command.impl.phone;

import by.itech.kimbar.controller.command.Command;
import by.itech.kimbar.service.ServiceProvider;
import by.itech.kimbar.service.exception.ServiceException;
import by.itech.kimbar.service.phone.PhoneService;
import by.itech.kimbar.util.NumericChecker;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AllUserPhoneCommand implements Command {
    private static final Logger log = Logger.getLogger(AllUserPhoneCommand.class);
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServiceException {
        ServiceProvider sp = ServiceProvider.getInstance();
        PhoneService ps = sp.getPhoneService();
        String id = req.getParameter("id");
        try {
            resp.getWriter().write(ps.getAllInJsonByUserId(NumericChecker.check(id)));
        } catch (ServiceException e) {
            log.error(e);
            throw new ServiceException();
        }
    }


}
