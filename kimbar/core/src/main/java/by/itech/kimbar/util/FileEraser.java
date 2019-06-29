package by.itech.kimbar.util;

import java.io.File;
import java.io.IOException;

public class FileEraser {
    private FileEraser (){}
    //improve system due to folder of id
    public static boolean erase(String path) {
        File file = new File(path);
        return file.delete();
    }

}
