package by.itech.kimbar.controller.command.impl.attachment;

import by.itech.kimbar.controller.command.Command;
import by.itech.kimbar.util.PathPropertyReader;

import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class DownloadAttachmentCommand implements Command  {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {


        String fileName = req.getParameter("fName");
        String userId = req.getParameter("userId");
        String downloadLink = req.getParameter("downloadLink");


        File file = new File(PathPropertyReader.readFilePath() + File.separator + userId + File.separator  + downloadLink);
        FileInputStream in = new FileInputStream(file);

        OutputStream out = resp.getOutputStream();

        resp.setContentType("application/octet-stream");
        resp.setContentLength((int) file.length());

        String hKey = "Content-Disposition";
        String hValue = String.format("attachment; filename=\"%s\"", MimeUtility.encodeWord(fileName,"UTF-8","Q") );
        resp.setHeader(hKey,hValue);

        byte[] buffer = new byte[4096];
        int length;
        while ((length = in.read(buffer)) > 0){
            out.write(buffer, 0, length);
        }
        in.close();
        out.flush();
    }
}
