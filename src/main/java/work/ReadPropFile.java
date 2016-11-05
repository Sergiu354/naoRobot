package work;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadPropFile {
    private static Properties prop = new Properties();
    private static InputStream input = null;

    public ReadPropFile(){
        try {
            if(input==null) {
                input = new FileInputStream("config.properties");
                prop.load(input);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Properties readProperties(){
        return prop;
    }


}