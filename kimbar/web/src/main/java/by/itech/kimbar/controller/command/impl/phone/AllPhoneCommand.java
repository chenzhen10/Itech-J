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

public class AllPhoneCommand implements Command {
    private static final Logger log = Logger.getLogger(AllPhoneCommand.class);
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ServiceProvider sp = ServiceProvider.getInstance();
        PhoneService ps = sp.getPhoneService();

        try {
            resp.getWriter().write(ps.getAllInJson());
        } catch (ServiceException e) {
            log.error(e);
        }
    }
}
