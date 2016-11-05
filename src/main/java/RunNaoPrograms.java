import com.aldebaran.qi.Application;
import work.ReadPropFile;
import work.facesDetection.FacesDetection;
import work.nao.NAOSetings;
import work.nao.NaoController;
import work.objectDetection.ObjectDetection;

import java.util.Properties;

public class RunNaoPrograms {
    private static Properties prop=null;
    private static boolean openObjectDetection = false;
    private static boolean openFaceDetection = false;
    private static boolean openController = false;

    public static void main(String[] args) {
        prop = new ReadPropFile().readProperties();
        openObjectDetection = Boolean.parseBoolean(prop.getProperty("openObjectDetection"));
        openFaceDetection = Boolean.parseBoolean(prop.getProperty("openFaceDetection"));
        openController = Boolean.parseBoolean(prop.getProperty("openController"));

        Application application = new NAOSetings().NAOConnect(args);
        application.start();
        System.out.println("openObjectDetection: "+openObjectDetection);
        try {
            if (openObjectDetection) {
                ObjectDetection objectDetect1 = new ObjectDetection(application,"object1");
                ObjectDetection objectDetect2 = new ObjectDetection(application,"object2");
                ObjectDetection objectDetect3 = new ObjectDetection(application,"object3");

                objectDetect1.start();
                objectDetect2.start();
                objectDetect3.start();
            }

            if (openFaceDetection) {
                FacesDetection facesDetect = new FacesDetection(application);
                facesDetect.start();
            }

            if (openController) {
                NaoController nc = new NaoController(application);
                nc.startController();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}