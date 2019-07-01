package by.itech.kimbar.controller.command.impl.attachment;

import by.itech.kimbar.controller.command.Command;
import by.itech.kimbar.service.ServiceProvider;
import by.itech.kimbar.service.exception.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

public class DeleteAttachmentCommand implements Command {
    private static final Logger log = Logger.getLogger(DeleteAttachmentCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ServiceProvider sp = ServiceProvider.getInstance();

        String id = req.getParameter("id");
        String fileName = req.getParameter("fName");
        String path ;
        StringBuilder sb = new StringBuilder();
        BufferedReader bf = req.getReader();
        while ((path = bf.readLine()) != null) {
            sb.append(path);
        }



        String[] splitName = fileName.split(",");

        String[] splitId = id.split(",");

        String[] paths = sb.toString().split(",");

        Integer[] attachmentId = new Integer[splitId.length];

        for (int i = 0; i < splitId.length; i++) {
            attachmentId[i] = Integer.valueOf(splitId[i]);
        }
        try {
            sp.getAttachmentService().deleteAttachment(attachmentId, splitName, paths);
            //redirect to refreshAllTables data
        } catch (ServiceException e) {
            log.error(e);
        }
    }
}
