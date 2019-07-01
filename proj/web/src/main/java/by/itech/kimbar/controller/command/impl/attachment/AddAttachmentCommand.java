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
            String comment = null;
            String name = null;
            Integer userId = null;
            String path = null;

            if (fileList.size() == 4) {
                userId = NumericChecker.check(fileList.get(0));
                comment = new String (fileList.get(1).getBytes ("iso-8859-1"), "UTF-8");
                name = new String (fileList.get(2).getBytes ("iso-8859-1"), "UTF-8");
                path = fileList.get(3);
            }else {
                userId = NumericChecker.check(fileList.get(0));
                name = new String (fileList.get(1).getBytes ("iso-8859-1"), "UTF-8");
                path = fileList.get(2);
            }

            as.attachFile(name, comment, userId, path);
        } catch (ServiceException e) {
            log.error(e);
        } catch (Exception e) {
            log.error(e);
        }
    }

    private static List<String> uploadFile(HttpServletRequest req, ServiceProvider sp) throws Exception {
        AttachmentService as = sp.getAttachmentService();
        //param  1 commentary if exists   2 filename  3 userId 4 pathToFile
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
            int maxLength = multi.size() - 1;
            //with commentary / without
            if (maxLength == 3) {
                list.add(multi.get(3).getString());
                list.add(multi.get(2).getString());
            } else {
                list.add(multi.get(2).getString());
            }

            for (FileItem item : multi) {
                // false  represents an uploaded file
                if (!item.isFormField()) {
                    fileName = item.getName();
                    fileExtension = fileName.split("\\.");

                    list.add(name.append(multi.get(1).getString()).append(".").append(fileExtension[fileExtension.length - 1]).toString());
                    ext = "." + fileExtension[fileExtension.length - 1];

                    if (!list.get(1).equals("")) {
                        DirectoryCreator.createFileSubFolderForUser(multi.get(maxLength).getString());
                        path = PropertyReader.readFilePath() + File.separator + multi.get(maxLength).getString()
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
