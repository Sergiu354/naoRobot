package work.facesDetection;

import com.aldebaran.qi.Application;
import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import work.nao.NAOImage;
import work.nao.NaoSpeakeSee;

public class FacesDetection extends Thread{
    private DetectionFace detectFace;
    private MBFImage query;
    private NaoSpeakeSee speakeObject;
    private NAOImage naoImage;

    public FacesDetection(Application application) throws Exception {
        detectFace = new DetectionFace();
        speakeObject = new NaoSpeakeSee(application);
        naoImage = new NAOImage(application);
    }

    public void run() {
        while (true) {
            try {
                query = ImageUtilities.createMBFImage(naoImage.getNAOImage("Faces"), true);
                DisplayUtilities.displayName(query,"faces detections");

            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                //DisplayUtilities.display(query);
                detectFace.detection(query.flatten(), speakeObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}