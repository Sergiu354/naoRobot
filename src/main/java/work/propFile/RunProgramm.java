package work.propFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Sergiu on 8/8/2016.
 */

public class RunProgramm {
    public static String ipRobot;
    public static String fileToObjectCompare;
    public static String fileToFacesCompare;
    public static int bestScoreFaceDetection;
    public static String saveImageToDirectory;
    public static int processingImageCamera;
    public static boolean openFaceDetection;
    public static boolean openObjectDetection;
    public static boolean openController;




    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static void main(String[] args) {
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream("config.properties");
            prop.load(input);

            //Load properties
            ipRobot = prop.getProperty("ipRobot");
            fileToObjectCompare = prop.getProperty("fileToObjectCompare");
            fileToFacesCompare = prop.getProperty("fileToFacesCompare");
            bestScoreFaceDetection = Integer.parseInt(prop.getProperty("bestScoreFaceDetection"));
            saveImageToDirectory = prop.getProperty("saveImageToDirectory");
            processingImageCamera = Integer.parseInt(prop.getProperty("processingImageCamera"));
            openObjectDetection = Boolean.parseBoolean(prop.getProperty("openObjectDetection"));
            openFaceDetection = Boolean.parseBoolean(prop.getProperty("openFaceDetection"));
            openController = Boolean.parseBoolean(prop.getProperty("openController"));


            System.out.println(ANSI_BLUE + "Properties:" + ANSI_YELLOW);
             new PrintProperties().print();

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}