package by.itech.kimbar.controller.command.impl.user;

import by.itech.kimbar.controller.command.Command;
import by.itech.kimbar.util.PropertyReader;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class ShowUserPhotoCommand implements Command {
    private static final Logger log = Logger.getLogger(ShowUserPhotoCommand.class);
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        String usrId = req.getParameter("userId");
        if (usrId != null){
            try {
                showUserPhoto(PropertyReader.readPhotoPath() + File.separator + usrId + File.separator + usrId + ".jpg", resp);
            } catch (IOException e) {
                try {
                    showUserPhoto("view/img/placeholder.jpg",resp);
                } catch (IOException e1) {
                    log.error(e1);
                }
            }
        }

    }


    private static void showUserPhoto(String path, HttpServletResponse resp) throws IOException {
        try (FileInputStream fin = new FileInputStream(path);
             OutputStream outStream = resp.getOutputStream();
             BufferedInputStream bin = new BufferedInputStream(fin);
             BufferedOutputStream bout = new BufferedOutputStream(outStream)) {
            int ch = 0;
            while ((ch = bin.read()) != -1) {
                bout.write(ch);
            }
        }
    }
}
