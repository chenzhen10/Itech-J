package by.itech.kimbar.controller.command.impl.phone;

import by.itech.kimbar.controller.command.Command;
import by.itech.kimbar.service.ServiceProvider;
import by.itech.kimbar.service.exception.ServiceException;
import by.itech.kimbar.service.phone.PhoneService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeletePhoneCommand implements Command {
    private static final Logger log = Logger.getLogger(DeletePhoneCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PhoneService ps = ServiceProvider.getInstance().getPhoneService();

        String data = req.getParameter("id");

        String[] splitId = data.split(",");
        Integer[] id = new Integer[splitId.length];

        for (int i = 0; i < splitId.length; i++) {
            id[i] = Integer.valueOf(splitId[i]);
        }
        try {
            ps.deletePhone(id);
        } catch (ServiceException e) {
            log.error(e);
        }
    }
}

