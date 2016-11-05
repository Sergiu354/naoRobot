package work.nao;

import com.aldebaran.qi.Application;
import com.aldebaran.qi.helper.proxies.ALMotion;
import com.aldebaran.qi.helper.proxies.ALRobotPosture;
import com.aldebaran.qi.helper.proxies.ALTextToSpeech;
import work.nao.NAOSetings;

/**
 * Created by sergiu on 12/9/15.
 */
public class NAOMotion {


    public static void main(String[] args) throws Exception {
        Application application = new NAOSetings().NAOConnect(args);

        // Start your application 3966
        application.start();
        ALMotion motion = new ALMotion(application.session());
        ALTextToSpeech tts = new ALTextToSpeech(application.session());
        tts.say("Connection");
        motion.wakeUp();
        motion.rest();

        //motion.setStiffnesses("Body",1.0);
        //motion.setStiffnesses("Head",0.0);


        //ALRobotPosture posture = new ALRobotPosture(application.session());
        //posture.goToPosture("LyingBack", 1.0f);
        //posture.stopMove();
        //posture.goToPosture("Stand",1.0f);
        //posture.stopMove();

    }
}