package work.nao;

import com.aldebaran.qi.Application;
import com.aldebaran.qi.helper.proxies.ALTextToSpeech;

/**
 * Created by sergiu on 2/3/16.
 */
public class NaoSpeakeSee {
    ALTextToSpeech tts;

    public NaoSpeakeSee(Application application) throws Exception {
        tts = new ALTextToSpeech(application.session());
    }

    public void speakeObject(String seeObject) throws Exception {
        tts.say("I'm see " + seeObject.substring(0, seeObject.lastIndexOf(".")));
    }

    public void speakeFace(String seeFace) throws Exception {
        tts.say("I'm see " + seeFace.substring(0, seeFace.lastIndexOf(".")));
    }
}
