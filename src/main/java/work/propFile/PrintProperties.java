package work.propFile;

/**
 * Created by sergiu on 8/10/16.
 */
public class PrintProperties {
    void print(){
        System.out.println("IP Robot: " + RunProgramm.ipRobot);
        System.out.println("File directory for object compare: " + RunProgramm.fileToObjectCompare);
        System.out.println("File directory for faces compare: " + RunProgramm.fileToFacesCompare);
        System.out.println("Percent for face compare: " + RunProgramm.bestScoreFaceDetection);
        System.out.println("Image directory to save: " + RunProgramm.saveImageToDirectory);
        System.out.println("Open processing camera: "  + RunProgramm.processingImageCamera );
        System.out.println("Run object detection? \'" + RunProgramm.openObjectDetection + "\'");
        System.out.println("Run Face Detection? \'" + RunProgramm.openFaceDetection + "\'");
        System.out.println("Run Controller? \'" + RunProgramm.openController + "\'");
    }
}
