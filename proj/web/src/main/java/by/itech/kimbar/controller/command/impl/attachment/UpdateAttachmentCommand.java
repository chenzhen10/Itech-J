package by.itech.kimbar.controller.command.impl.attachment;

import by.itech.kimbar.controller.command.Command;
import by.itech.kimbar.dto.deserialize.DtoProvider;
import by.itech.kimbar.dto.Dto;
import by.itech.kimbar.service.ServiceProvider;
import by.itech.kimbar.service.attachment.AttachmentService;
import by.itech.kimbar.service.exception.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UpdateAttachmentCommand implements Command {
    private static final Logger log = Logger.getLogger(AddAttachmentCommand.class);
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServiceException {
        AttachmentService as = ServiceProvider.getInstance().getAttachmentService();

        List<Dto> attachments = DtoProvider.getInstance().getAttachmentDto().deSerialize(req);

        try {
            log.debug("Parameters : " + attachments);
            log.debug( as.updateAttachment(attachments));
        } catch (ServiceException e) {
            log.error(e);
            throw new ServiceException();
        }
    }
}
