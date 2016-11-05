package work;

/**
 * Created by sergiu on 4/15/16.
 */
import com.aldebaran.qi.Application;
import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import work.nao.NAOImage;
import work.nao.NAOSetings;

public class PhotoNao {
    public static void main(String[] args) throws Exception {
        Application application = new NAOSetings().NAOConnect(args);
        application.start();
        System.out.println("Start");
        while (true) {
            NAOImage naoImage = new NAOImage(application);
            MBFImage query = ImageUtilities.createMBFImage(naoImage.getNAOImage("SavePhoto"), true);
            DisplayUtilities.displayName(query, "Nao");
        }
    }
}