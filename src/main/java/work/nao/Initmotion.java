package work.nao;

import com.aldebaran.qi.Application;
import com.aldebaran.qi.CallError;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.ALAudioPlayer;
import com.aldebaran.qi.helper.proxies.ALMotion;
import com.aldebaran.qi.helper.proxies.ALTextToSpeech;
import work.nao.NAOSetings;

public class Initmotion {
    ALMotion motion;
    ALAudioPlayer audioPlayer;
    AudioFile audioFile;
    //private static ALMotion motion;

    Initmotion(Application application) throws Exception {
        this.motion = new ALMotion(application.session());
        this.audioPlayer = new ALAudioPlayer(application.session());

    }

    public void resetMotion() throws Exception {
        final float speed = 0.3f;


        motion.setAngles("LShoulderPitch", 1.5201521f, speed);
        motion.setAngles("LShoulderRoll", 0.200912f, speed);
        motion.setAngles("LElbowYaw", -1.221106f, speed);
        motion.setAngles("LElbowRoll", -0.66111207f, speed);
        motion.setAngles("LWristYaw", 0.10580397f, speed);
        motion.setAngles("LHand", 0.24479997f, speed);

        motion.setAngles("RShoulderPitch", 1.4880219f, speed);
        motion.setAngles("RShoulderRoll", -0.16111207f, speed);
        motion.setAngles("RElbowYaw", 1.328402f, speed);
        motion.setAngles("RElbowRoll", 0.4740479f, speed);
        motion.setAngles("RWristYaw", 0.1656301f, speed);
        motion.setAngles("RHand", 0.2576f, speed);
    }

    public void alertation() throws Exception {
        final float speed = 0.4f;
        ALTextToSpeech tts;
        motion.killAll();

        motion.setAngles("LElbowRoll", -1.54, speed);
        motion.setAngles("LShoulderRoll", -0.2f, speed);
        motion.setAngles("RElbowRoll", 1.54f, speed);
        motion.setAngles("RShoulderRoll", 0.2f, speed);

    }

    public void leftOwer() throws Exception {
        final float speed = 0.4f;
        ALTextToSpeech tts;

        motion.killAll();
        motion.setAngles("LShoulderPitch", 0.2f, speed);
        motion.setAngles("LElbowRoll", -1.40, speed);
        motion.setAngles("LWristYaw", -1.4358659f, speed);
        motion.setAngles("LHand", 0, speed);
        Thread.sleep(800);
        motion.setAngles("LShoulderPitch", 1.5201521f, speed);
        motion.setAngles("LElbowYaw", -1.221106f, speed);
        motion.setAngles("LElbowRoll", -1.54f, speed);
        motion.setAngles("LWristYaw", 0.10580397f, speed);
        motion.setAngles("LHand", 0.24479997f, speed);
    }

    public void RightOwer() throws Exception {
        final float speed = 0.4f;

        motion.killAll();
        motion.setAngles("RShoulderPitch", -0f, speed);
        motion.setAngles("RElbowRoll", 1.40, speed);
        motion.setAngles("RWristYaw", 1.4358659f, speed);
        motion.setAngles("RHand", 0, speed);
        Thread.sleep(800);
        motion.setAngles("RShoulderPitch", 1.4880219f, speed);
        motion.setAngles("RElbowYaw", 1.328402f, speed);
        motion.setAngles("RElbowRoll", 1.54f, speed);
        motion.setAngles("RWristYaw", 0.1656301f, speed);
        motion.setAngles("RHand", 0.2576f, speed);
    }

    public void leftB() throws Exception {
        final float speed = 0.4f;

        motion.killAll();
        float n = -1.4f;
        for(int i=0; i<=9; i++){
            // System.out.println(n);
            n = n + 0.1f;
            motion.setAngles("LElbowRoll", n, speed);
        }
        motion.setAngles("LShoulderPitch", 0f, speed);
        motion.setAngles("LHand", 0f, speed);
        motion.setAngles("LShoulderRoll", -0.2f, speed);
        motion.setAngles("LWristYaw", 1f, speed);
        Thread.sleep(800);
        motion.setAngles("LShoulderPitch", 1.5201521f, speed);
        //motion.setAngles("LShoulderRoll", 0.20f, speed);
        motion.setAngles("LElbowYaw", -1.221106f, speed);
        motion.setAngles("LElbowRoll", -1.54f, speed);
        motion.setAngles("LWristYaw", 0.10580397f, speed);
        motion.setAngles("LHand", 0.24479997f, speed);
    }

    public void rightB() throws Exception {
        final float speed = 0.4f;

        motion.killAll();
        float n = 1.4f;
        for(int i=0; i<=9; i++){
            // System.out.println(n);
            n = n - 0.1f;
            motion.setAngles("RElbowRoll", n, speed);
        }
        motion.setAngles("RShoulderPitch", 0f, speed);
        motion.setAngles("RHand", 0f, speed);
        motion.setAngles("RShoulderRoll", 0.2f, speed);
        motion.setAngles("RWristYaw", -1f, speed);
        Thread.sleep(800);
        motion.setAngles("RShoulderPitch", 1.4880219f, speed);
        //motion.setAngles("RShoulderRoll", -0.16111207f, speed);
        motion.setAngles("RElbowYaw", 1.328402f, speed);
        motion.setAngles("RElbowRoll", 1.54f, speed);
        motion.setAngles("RWristYaw", 0.1656301f, speed);
        motion.setAngles("RHand", 0.2576f, speed);
    }

    public void ridica() throws Exception {
        final float speed = 0.4f;

        motion.killAll();
        float n = 1.4f;
        for(int i=0; i<=9; i++){
            // System.out.println(n);
            n = n - 0.1f;
            motion.setAngles("RElbowRoll", n, speed);
        }
        motion.setAngles("RShoulderPitch", 0f, speed);
        motion.setAngles("RHand", 0f, speed);
        motion.setAngles("RShoulderRoll", 0.2f, speed);
        motion.setAngles("RWristYaw", 0f, speed);
    }

    public void jos() throws Exception {
        final float speed = 0.4f;
        motion.setAngles("RShoulderPitch", 1.4880219f, speed);
        motion.setAngles("RShoulderRoll", -0.16111207f, speed);
        motion.setAngles("RElbowYaw", 1.328402f, speed);
        motion.setAngles("RElbowRoll", 0.4740479f, speed);
        motion.setAngles("RWristYaw", 0.1656301f, speed);
        motion.setAngles("RHand", 0f, speed);
    }

    public void stringe() throws Exception {
        final float speed = 0.4f;
        motion.setAngles("RHand", 0f, speed);
    }

    public void desface() throws Exception {
        final float speed = 0.4f;
        motion.setAngles("RHand", 1f, speed);
    }


    public void hellowB() throws Exception {
        final float speed = 0.3f;
        motion.killAll();
        audioPlayer.stopAll();

        //[-0.8789401, -0.021517992, 1.475666, 0.4970579, -1.5309739, 0.34679997]
        //[-0.866668, -0.24394798, 1.478734, 0.4970579, -1.52177, 0.34679997]
        //[-0.842124, 0.23005795, 1.478734, 0.4970579, -1.4987602, 0.34679997]
        motion.setAngles("RShoulderPitch", -0.8789401f, speed);
        motion.setAngles("RShoulderRoll", -0.2600f, speed);
        motion.setAngles("RElbowYaw", 1.475666f, speed);
        motion.setAngles("RElbowRoll", 0.4970579f, speed);
        Thread.sleep(600);
        motion.setAngles("RWristYaw", -1.5309739f, speed);
        motion.setAngles("RHand", 1, speed);

        for(int i=0; i<=2; i++) {
            motion.setAngles("RShoulderRoll", -0.26f, 0.2f);
            Thread.sleep(600);
            motion.setAngles("RShoulderRoll", 0.230f, 0.2f);
            Thread.sleep(600);
            /*if (i==0) {
                audioFile=new AudioFile(audioPlayer);
                audioFile.run();
            }*/
        }
        Thread.sleep(600);
        motion.setAngles("RShoulderPitch", 1.4880219f, speed);
        motion.setAngles("RShoulderRoll", -0.16111207f, speed);
        motion.setAngles("RElbowYaw", 1.328402f, speed);
        motion.setAngles("RElbowRoll", 0.4740479f, speed);
        motion.setAngles("RWristYaw", 0.1656301f, speed);
        motion.setAngles("RHand", 0.2576f, speed);
    }
}