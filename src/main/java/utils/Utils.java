package utils;

import com.scraper.Main;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Utils {

    public static Properties properties;

    static {
        try {
            properties = readProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String configParameter){
        String property = properties.getProperty(configParameter);
        return  property;

    }

    private static Properties readProperties() throws IOException {
        try (InputStream input = Main.class.getClassLoader().getResourceAsStream("config.properties")) {

            Properties prop = new Properties();

            if (input == null) {
                throw new NullPointerException("No properties file");
            }

            prop.load(input);

            return prop;


        } catch (IOException ex) {
            ex.printStackTrace();
            throw new IOException(String.valueOf(ex.getStackTrace()));
        }
    }

    public static String getOneString(String string, int i){
        String[] arrayS = string.split("<br>");
        String oneAddress = arrayS[i];
        return oneAddress;
    }

    public static String removeHTML(String string){
        String substring = string.replaceAll("<[^>]*>", "");
        return substring;
    }

}
