package by.itech.kimbar.controller.command.impl.phone;

import by.itech.kimbar.controller.command.Command;
import by.itech.kimbar.entity.Phone;
import by.itech.kimbar.service.ServiceProvider;
import by.itech.kimbar.service.exception.ServiceException;
import by.itech.kimbar.service.phone.PhoneService;
import by.itech.kimbar.util.NumericChecker;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddPhoneCommand implements Command {
    private static final Logger log = Logger.getLogger(AddPhoneCommand.class);
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ServiceProvider sp = ServiceProvider.getInstance();
        PhoneService ps = sp.getPhoneService();

        String countryCode = req.getParameter("countryCode");
        String operatorCode = req.getParameter("operatorCode");
        String number = req.getParameter("number");
        String type = req.getParameter("type");
        String commentary = req.getParameter("commentary");
        String idclient = req.getParameter("idclient");


        int realId = Integer.parseInt(idclient);

        try {
            ps.createPhone(NumericChecker.check(countryCode),NumericChecker.check(operatorCode),NumericChecker.check(number),Phone.Type.valueOf(type),commentary,realId);
        } catch (ServiceException e) {
            log.error(e);
        }

    }
}
