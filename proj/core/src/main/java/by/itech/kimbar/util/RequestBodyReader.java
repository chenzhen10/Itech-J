package by.itech.kimbar.util;

import by.itech.kimbar.service.ServiceProvider;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RequestBodyReader {
    private static final Logger log = Logger.getLogger(RequestBodyReader.class);

    private RequestBodyReader() {
    }

    public static List<String> extractDataFromBody(HttpServletRequest req) {
        List<String> result = new ArrayList<>();
        String named;

        StringBuilder rName = new StringBuilder();
        String[] fileExtension;
        String ext;
        String path = null;

        if (ServletFileUpload.isMultipartContent(req)) {
            try {
                List<FileItem> multi = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(req);

                String usrId = multi.get(17).getString();

                result.add(usrId);

                for (FileItem item : multi) {
                    // false  represents an uploaded file
                    if (!item.isFormField()) {
                        named = item.getName();
                        fileExtension = named.split("\\.");

                        //if file wasn't added 17 size if added 18
                        result.add(String.valueOf(rName.append(usrId).append(".").append(fileExtension[fileExtension.length - 1])));
                        ext = "." + fileExtension[fileExtension.length - 1];

                        if (!result.get(1).equals("undefined")) {
                            FileUtils.deleteDirectory(new File(PathPropertyReader.readPhotoPath() + File.separator + usrId));
                            DirectoryCreator.createPhotoSubFolderForUser(usrId);
                            path = PathPropertyReader.readPhotoPath() + File.separator + usrId + File.separator + usrId + ext;
                            item.write(new File(path));
                        }
                    }
                }
                result.add(multi.get(1).getString("UTF-8"));
                result.add(multi.get(2).getString("UTF-8"));
                result.add(multi.get(3).getString("UTF-8"));
                result.add(multi.get(4).getString("UTF-8"));
                result.add(multi.get(5).getString("UTF-8"));
                result.add(multi.get(6).getString("UTF-8"));
                result.add(multi.get(7).getString("UTF-8"));
                result.add(multi.get(8).getString("UTF-8"));
                result.add(multi.get(9).getString("UTF-8"));
                result.add(multi.get(10).getString("UTF-8"));
                result.add(multi.get(11).getString("UTF-8"));
                result.add(multi.get(12).getString("UTF-8"));
                result.add(multi.get(13).getString("UTF-8"));
                result.add(multi.get(14).getString("UTF-8"));
                result.add(multi.get(15).getString("UTF-8"));
                result.add(multi.get(16).getString("UTF-8"));
                result.add(multi.get(18).getString("UTF-8"));
            } catch (Exception e) {
                log.error(e);
            }
        }
        return result;
    }

    public static List<String> extractDataFromBody(HttpServletRequest req,ServiceProvider sp) {
        List<String> result = new ArrayList<>();
        String named;

        String[] fileExtension;
        String ext = null;
        if (ServletFileUpload.isMultipartContent(req)) {
            try {
                List<FileItem> multi = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(req);

                for (FileItem item : multi) {
                    // false  represents an uploaded file
                    if (!item.isFormField()) {
                        named = item.getName();
                        fileExtension = named.split("\\.");
                        ext = "." + fileExtension[fileExtension.length - 1];
                        result.add(encodeImage(item.get()));
                    }
                }
                result.add(multi.get(1).getString("UTF-8"));
                result.add(multi.get(2).getString("UTF-8"));
                result.add(multi.get(3).getString("UTF-8"));
                result.add(multi.get(4).getString("UTF-8"));
                result.add(multi.get(5).getString("UTF-8"));
                result.add(multi.get(6).getString("UTF-8"));
                result.add(multi.get(7).getString("UTF-8"));
                result.add(multi.get(8).getString("UTF-8"));
                result.add(multi.get(9).getString("UTF-8"));
                result.add(multi.get(10).getString("UTF-8"));
                result.add(multi.get(11).getString("UTF-8"));
                result.add(multi.get(12).getString("UTF-8"));
                result.add(multi.get(13).getString("UTF-8"));
                result.add(multi.get(14).getString("UTF-8"));
                result.add(multi.get(15).getString("UTF-8"));
                result.add(multi.get(16).getString("UTF-8"));
                result.add(ext);
            } catch (Exception e) {
                log.error(e);
            }
        }
        return result;
    }

    private static String encodeImage(byte[] imageByteArray) {
        return Base64.encodeBase64URLSafeString(imageByteArray);
    }

}
