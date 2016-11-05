package work.nao;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.helper.proxies.ALAudioPlayer;

import java.util.Random;

/**
 * Created by sergiu on 4/9/16.
 */
public class AudioFile extends Thread {
    ALAudioPlayer audioPlayer;
    public AudioFile(ALAudioPlayer audioPlayer){
        this.audioPlayer=audioPlayer;
    }

    public void run() {
        Random random=new Random();
        try {
            audioPlayer.playFile("/home/nao/sounds/" + random.nextInt(6)+".mp3");
        } catch (CallError callError) {
            callError.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
