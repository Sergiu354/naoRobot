package work.nao;

import com.aldebaran.qi.Application;
import work.ReadPropFile;

import java.util.Properties;

/**
 * Created by sergiu on 12/9/15.
 */

public class NAOSetings {
    private static Properties prop=null;


    public NAOSetings(){
        if (prop==null){
            prop = new ReadPropFile().readProperties();
        }
    }

    public Application NAOConnect(String [] args){
        //String robotUrl = "tcp://169.254.211.93:9559"; // wi-fi
        String robotUrl = "tcp://"+prop.getProperty("ipRobot")+":9559";
        //String robotUrl = "tcp://169.254.6.217:9559"; // cablu
        // Create a new application
        Application application = new Application(args, robotUrl);
        return application;
    }
}
