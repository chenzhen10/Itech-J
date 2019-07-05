package by.itech.kimbar.controller.command.impl.user;

import by.itech.kimbar.controller.command.Command;
import by.itech.kimbar.entity.Gender;
import by.itech.kimbar.entity.MaritalStatus;
import by.itech.kimbar.service.ServiceProvider;
import by.itech.kimbar.service.exception.ServiceException;
import by.itech.kimbar.service.user.UserService;
import by.itech.kimbar.util.DateConverter;
import by.itech.kimbar.util.DirectoryCreator;
import by.itech.kimbar.util.NumericChecker;
import by.itech.kimbar.util.PathPropertyReader;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class UpdateUserCommand implements Command {
    private static final Logger log = Logger.getLogger(UpdateUserCommand.class);

    static {
        try {
            DirectoryCreator.createPhotoDirectory();
        } catch (IOException e) {
            log.error(e);
        }
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServiceException {
        UserService us = ServiceProvider.getInstance().getUserService();

        List<String> list = extractDataFromBody(req);

        String userId = null;
        String path = null;
        String name = null;
        String surname = null;
        String lastName = null;
        String date = null;
        String gender = null;
        String citizenship = null;
        String maritalStatus = null;
        String webSite = null;
        String email = null;
        String country = null;
        String city = null;
        String street = null;
        String house = null;
        String numOfFlat = null;
        String index = null;
        String workplace = null;

        //with pictures 18 without 17
        if (list.size() == 17) {
            userId = list.get(0);
            //replace with normal impl
            path = PathPropertyReader.readPhotoPath() + File.separator + list.get(0) + File.separator + list.get(0) + ".jpg";
            name = list.get(1);
            surname = list.get(2);
            lastName = list.get(3);
            date = list.get(4);
            gender = list.get(5);
            citizenship = list.get(6);
            maritalStatus = list.get(7);
            webSite = list.get(8);
            email = list.get(9);
            country = list.get(10);
            city = list.get(11);
            street = list.get(12);
            house = list.get(13);
            numOfFlat = list.get(14);
            index = list.get(15);
            workplace = list.get(16);
        } else {
            userId = list.get(0);
            path = PathPropertyReader.readPhotoPath() + File.separator + list.get(0) + File.separator + list.get(1);
            name = list.get(2);
            surname = list.get(3);
            lastName = list.get(4);
            date = list.get(5);
            gender = list.get(6);
            citizenship = list.get(7);
            maritalStatus = list.get(8);
            webSite = list.get(9);
            email = list.get(10);
            country = list.get(11);
            city = list.get(12);
            street = list.get(13);
            house = list.get(14);
            numOfFlat = list.get(15);
            index = list.get(16);
            workplace = list.get(17);
        }


        //exctract in method
        Gender gdr = null;
        if (gender.equals("Male") || gender.equals("Female")){
            gdr = Gender.valueOf(gender);
        }

        MaritalStatus ms = null;
        if (maritalStatus.equals(MaritalStatus.Married.toString()) || maritalStatus.equals(MaritalStatus.Single.toString())){
            ms = MaritalStatus.valueOf(maritalStatus);
        }


        try {
            log.debug("Parameters : " +  name+ surname+lastName+DateConverter.convert(date)+gdr+
                    citizenship+ms+ webSite+ email+ workplace+
                    country+city+street+
                    house+numOfFlat+NumericChecker.check(index)+
                    path+ NumericChecker.check(userId) );

           log.debug(us.updateUser(name, surname,lastName,DateConverter.convert(date),gdr,
                    citizenship,ms, webSite, email, workplace,
                    country,city,street,
                    house,numOfFlat,NumericChecker.check(index),
                    path, NumericChecker.check(userId)));
        } catch (ServiceException | ParseException e) {
            log.error(e);
            throw new ServiceException();
        }
    }

    private static List<String> extractDataFromBody(HttpServletRequest req) {
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
                        result.add(String.valueOf(rName.append(usrId + ".").append(fileExtension[fileExtension.length - 1])));
                        ext = "." + fileExtension[fileExtension.length - 1];

                        if (!result.get(1).equals("undefined")) {
                            FileUtils.deleteDirectory(new File(PathPropertyReader.readPhotoPath() + File.separator + usrId));
                            DirectoryCreator.createPhotoSubFolderForUser(usrId);
                            path = PathPropertyReader.readPhotoPath() + File.separator + usrId + File.separator + usrId + ext;
                            item.write(new File(path));
                        }
                    }
                }
                result.add(multi.get(1).getString( "UTF-8" ));
                result.add(multi.get(2).getString( "UTF-8" ) );
                result.add(multi.get(3).getString( "UTF-8" ) );
                result.add(multi.get(4).getString( "UTF-8" ) );
                result.add(multi.get(5).getString( "UTF-8" ) );
                result.add(multi.get(6).getString( "UTF-8" ) );
                result.add(multi.get(7).getString( "UTF-8" ) );
                result.add(multi.get(8).getString( "UTF-8" ) );
                result.add(multi.get(9).getString( "UTF-8" ) );
                result.add(multi.get(10).getString( "UTF-8" ) );
                result.add(multi.get(11).getString( "UTF-8" ) );
                result.add(multi.get(12).getString( "UTF-8" ) );
                result.add(multi.get(13).getString( "UTF-8" ) );
                result.add(multi.get(14).getString( "UTF-8" ) );
                result.add(multi.get(15).getString( "UTF-8" ) );
                result.add(multi.get(16).getString( "UTF-8" ) );
            } catch (Exception e) {
                log.error(e);
            }
        }
        return result;
    }
}
