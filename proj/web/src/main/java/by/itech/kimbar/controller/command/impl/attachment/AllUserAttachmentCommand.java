package by.itech.kimbar.controller.command.impl.attachment;

import by.itech.kimbar.controller.command.Command;
import by.itech.kimbar.service.ServiceProvider;
import by.itech.kimbar.service.attachment.AttachmentService;
import by.itech.kimbar.service.exception.ServiceException;
import by.itech.kimbar.util.NumericChecker;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AllUserAttachmentCommand implements Command {
    private static final Logger log = Logger.getLogger(AllUserAttachmentCommand.class);
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServiceException {
        ServiceProvider sp = ServiceProvider.getInstance();
        AttachmentService ps = sp.getAttachmentService();
        String id = req.getParameter("id");

        try {
            resp.getWriter().write(ps.getAllInJsonByUserId(NumericChecker.check(id)));
        } catch (ServiceException e) {
            log.error(e);
            throw new ServiceException();
        }
    }

}
