package by.itech.kimbar.util;

import javax.servlet.http.HttpServletResponse;
import java.io.*;


public class PhotoReader {
    private PhotoReader(){}

    public static void read(String path , HttpServletResponse resp) throws IOException {
        InputStream fin = null;
        if (!path.equals("placeholder.jpg")){
            fin = new FileInputStream(path);
        }
        else {
            fin = Thread.currentThread().getContextClassLoader().getResourceAsStream("placeholder.jpg");
        }
        try (OutputStream outStream = resp.getOutputStream();
             BufferedInputStream bin = new BufferedInputStream(fin);
             BufferedOutputStream bout = new BufferedOutputStream(outStream)) {
            int ch = 0;
            while ((ch = bin.read()) != -1) {
                bout.write(ch);
            }
        }finally {
            fin.close();
        }
    }
}
