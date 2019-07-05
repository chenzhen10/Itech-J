package by.itech.kimbar.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PathPropertyReader {
    //correct the path
    private static final String FILE_WITH_PROPERTIES = "path.properties";

    private PathPropertyReader(){}

    public static String readFilePath() throws IOException {
        Properties prop = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream fis =  loader.getResourceAsStream(FILE_WITH_PROPERTIES);
        prop.load(fis);
        return prop.getProperty("file.path");
    }

    public static String readPhotoPath() throws IOException {
        Properties prop = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream fis = loader.getResourceAsStream(FILE_WITH_PROPERTIES);
        prop.load(fis);
        return prop.getProperty("photo.path");
    }
}
