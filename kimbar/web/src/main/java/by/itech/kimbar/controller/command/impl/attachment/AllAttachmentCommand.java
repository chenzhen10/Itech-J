package by.itech.kimbar.controller.command.impl.attachment;

import by.itech.kimbar.controller.command.Command;
import by.itech.kimbar.service.ServiceProvider;
import by.itech.kimbar.service.exception.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AllAttachmentCommand implements Command {
    private static final Logger log = Logger.getLogger(AllAttachmentCommand.class);
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        ServiceProvider sp = ServiceProvider.getInstance();
        try {
            resp.getWriter().write(sp.getAttachmentService().getAllInJson());
        } catch (ServiceException | IOException e) {
            log.error(e);
        }
    }
}
