package by.itech.kimbar.controller.command.impl.user;

import by.itech.kimbar.controller.command.Command;
import by.itech.kimbar.util.PathPropertyReader;
import by.itech.kimbar.util.PhotoReader;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class ShowUserPhotoCommand implements Command {
    private static final Logger log = Logger.getLogger(ShowUserPhotoCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        String usrId = req.getParameter("userId");
        String extension = req.getParameter("extension");

        if (usrId != null) {
            try {
                PhotoReader.read(PathPropertyReader.readPhotoPath() + File.separator + usrId + File.separator + usrId + "." +  extension, resp);
            } catch (IOException e) {
                try {
                    PhotoReader.read("placeholder.jpg", resp);
                } catch (IOException e1) {
                    log.error(e1);
                }
            }
        }else{
            try {
                PhotoReader.read("placeholder.jpg", resp);
            } catch (IOException e) {
                log.error(e);
            }
        }
    }
}
