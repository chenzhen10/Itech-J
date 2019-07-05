package by.itech.kimbar.util;



import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TemplateReader {
    private static final String PROPERTY_FILE  = "template.properties";

    private TemplateReader() {
    }


    public static String birthdayTemplate() throws IOException {
        Properties prop = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream fis = loader.getResourceAsStream(PROPERTY_FILE);
        prop.load(fis);

        return prop.getProperty("template.birthday");
    }

    public static String graduationTemplate() throws IOException {
        Properties prop = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream fis = loader.getResourceAsStream(PROPERTY_FILE);
        prop.load(fis);
        return prop.getProperty("template.graduation");
    }

    public static String helpTemplate() throws IOException {
        Properties prop = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream fis = loader.getResourceAsStream(PROPERTY_FILE);
        prop.load(fis);
        return prop.getProperty("template.help");
    }

    public static String defaultTemplate() throws IOException {
        Properties prop = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream fis = loader.getResourceAsStream(PROPERTY_FILE);
        prop.load(fis);
        return prop.getProperty("template.default");
    }
}
