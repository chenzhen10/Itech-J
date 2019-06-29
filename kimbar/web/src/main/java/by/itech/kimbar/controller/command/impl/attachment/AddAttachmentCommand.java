package by.itech.kimbar.controller.command.impl.attachment;

import by.itech.kimbar.controller.command.Command;
import by.itech.kimbar.service.ServiceProvider;
import by.itech.kimbar.service.attachment.AttachmentService;
import by.itech.kimbar.service.exception.ServiceException;
import by.itech.kimbar.util.DirectoryCreator;
import by.itech.kimbar.util.NumericChecker;
import by.itech.kimbar.util.PropertyReader;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddAttachmentCommand implements Command {
    private static final Logger log = Logger.getLogger(AddAttachmentCommand.class);

    static {
        try {
            DirectoryCreator.createFileDirectory();
        } catch (IOException e) {
           log.error(e);
        }
    }


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        ServiceProvider sp = ServiceProvider.getInstance();
        AttachmentService as = sp.getAttachmentService();

        try {
            List<String> fileList = uploadFile(req, sp);

            String comment = fileList.get(0);
            String name = fileList.get(2);
            int userId = NumericChecker.check(fileList.get(1));
            String path = fileList.get(3);

            as.attachFile(name, comment, userId, path);
        } catch (ServiceException e) {
            log.error(e);
        } catch (Exception e) {
            log.error(e);
        }
    }

    private static List<String> uploadFile(HttpServletRequest req, ServiceProvider sp) throws Exception {
        AttachmentService as = sp.getAttachmentService();
        //param  1 commentary   2 filename  3 userId 4 pathToFile
        List<String> list = new ArrayList<>();

        StringBuilder name = new StringBuilder();
        String ext = null;
        String path = null;
        int countOfAttachments = as.getCountOfAttachments();

        String fileName = null;
        String[] fileExtension;
        if (ServletFileUpload.isMultipartContent(req)) {
            //1 is filename 2 is commentary 3 is userID
            List<FileItem> multi = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(req);
            list.add(multi.get(2).getString());
            list.add(multi.get(3).getString());
            for (FileItem item : multi) {
                // false  represents an uploaded file
                if (!item.isFormField()) {
                    fileName = item.getName();
                    fileExtension = fileName.split("\\.");

                    list.add(name.append(multi.get(1).getString()).append(".").append(fileExtension[fileExtension.length - 1]).toString());
                    ext = "." + fileExtension[fileExtension.length - 1];

                    if (!list.get(1).equals("")) {
                        DirectoryCreator.createFileSubFolderForUser(multi.get(3).getString());
                        path = PropertyReader.readFilePath() + File.separator + multi.get(3).getString()
                                + File.separator + countOfAttachments + ext;
                        list.add(path);
                        item.write(new File(path));
                    }
                }
            }

        }
        return list;
    }
}
