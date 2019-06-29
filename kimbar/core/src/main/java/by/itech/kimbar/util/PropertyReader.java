package by.itech.kimbar.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {
    //correct the path
    private static final String FILE_WITH_PROPERTIES = "path.properties";

    private PropertyReader(){}

    public static String readFilePath() throws IOException {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream(new File(FILE_WITH_PROPERTIES));
        prop.load(fis);
        return prop.getProperty("file.path");
    }

    public static String readPhotoPath() throws IOException {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream(new File(FILE_WITH_PROPERTIES));
        prop.load(fis);
        return prop.getProperty("photo.path");
    }
}
