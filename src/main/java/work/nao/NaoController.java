package work.nao;

import com.aldebaran.qi.Application;
import com.aldebaran.qi.CallError;
import com.aldebaran.qi.helper.proxies.ALMotion;
import com.aldebaran.qi.helper.proxies.ALRobotPosture;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;
import work.nao.NAOSetings;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergiu on 2/6/16.
 */


class NaoControler extends Thread {
    static Controller controller;
    static ALMotion motion;
    static Initmotion restM;
    static ALRobotPosture posture;

    public NaoControler(Controller controll, ALMotion motion,Initmotion restMMM, ALRobotPosture posture){
        this.motion = motion;
        this.controller = controll;
        this.restM = restMMM;
        this.posture = posture;
    }

    public void run() {
        int contor = 1;
        List<String> names = new ArrayList<String>();
        names.add("HeadYaw");
        names.add("HeadPitch");
        List<Float> angles = new ArrayList<Float>(2);
        angles.add(0f);  // <- ->
        angles.add(0f); // /\  \/

        while(true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            controller.poll();
            if (controller.getAxisValue(6) != (float) -1) { //RT

                System.out.println("RT start");

                try {
                    motion.walkTo(10f, 0f, 0f);

                } catch (CallError callError) {
                    callError.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            if (controller.getAxisValue(3) != (float) -1) { //LT
                System.out.println("LT start");
                try {
                    motion.walkTo(-10f, 0f, 0f);
                } catch (CallError callError) {
                    callError.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (controller.getAxisValue(1) != 0) { //LL LR
                System.out.println("LL LR");
                try {
                    motion.walkTo(0f, 0f, -(controller.getAxisValue(1))*5);
                } catch (CallError callError) {
                    callError.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (controller.isButtonPressed(5)) //RB
                try {
                    if(contor == 1){
                        //tts.say("Attention");
                        restM.alertation();
                        contor = 0;
                    }else{
                        //tts.say("Finish Him");
                        restM.resetMotion();
                        contor = 1;
                    }
                } catch (CallError callError) {
                    callError.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            if (controller.isButtonPressed(7)){
                try {
                    motion.killAll();
                    posture.getPostureList();
                    posture.goToPosture("Stand", 1.0f);
                    angles.set(0, 0f);
                    angles.set(1, -0.2f);

                    System.out.println("xBox");
                } catch (CallError callError) {
                    callError.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


            if(contor == 0) {
                if (controller.isButtonPressed(3)) //Y
                    try {
                        restM.leftOwer();
                    } catch (CallError callError) {
                        callError.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                if (controller.isButtonPressed(0)) //A
                    try {
                        restM.RightOwer();
                    } catch (CallError callError) {
                        callError.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                if (controller.isButtonPressed(1)) //B
                    try {
                        restM.leftB();
                    } catch (CallError callError) {
                        callError.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                if (controller.isButtonPressed(2)) //X
                    try {
                        restM.rightB();
                    } catch (CallError callError) {
                        callError.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            } else {

                if (controller.isButtonPressed(2)) //X
                    try {
                        restM.ridica();
                    } catch (CallError callError) {
                        callError.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                if (controller.isButtonPressed(1)) //B
                    try {
                        restM.jos();
                    } catch (CallError callError) {
                        callError.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                if (controller.isButtonPressed(3)) //Y
                    try {
                        restM.stringe();
                    } catch (CallError callError) {
                        callError.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                if (controller.isButtonPressed(0)) //Y
                    try {
                        restM.desface();
                    } catch (CallError callError) {
                        callError.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                if (controller.isButtonPressed(4)) //LB
                    try {
                        restM.hellowB();
                    } catch (CallError callError) {
                        callError.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }

            if (controller.getAxisValue(4)!=0) { //RL RR   stinga dreapta => 2.0  stinga
                if (controller.getAxisValue(4)>0.2f && angles.get(0)>-1.5) {
                    angles.set(0, angles.get(0) - 0.1f);
                    //System.out.println("-- " + angles);
                    try {
                        motion.setAngles(names, angles, 0.1f);
                    } catch (CallError callError) {
                        callError.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                if (controller.getAxisValue(4)<0.2f && angles.get(0)<1.5) { // dreapta
                    angles.set(0, angles.get(0) + 0.1f);
                    //System.out.println("++ "+angles);
                    try {
                        motion.setAngles(names, angles, 0.1f);
                    } catch (CallError callError) {
                        callError.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            if (controller.getAxisValue(5)!=0) {//RT RB    sus jos 0.67  ++sus
                //System.out.println(controller.getAxisValue(5));
                if (controller.getAxisValue(5) > 0.2f && angles.get(1)<0.6) {
                    angles.set(1, angles.get(1) + 0.1f);
                    //System.out.println("++ " + angles);
                    try {
                        motion.setAngles(names, angles, 0.1f);
                    } catch (CallError callError) {
                        callError.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                if (controller.getAxisValue(5) < 0.2f && angles.get(1)>-0.6) {  //-- jos
                    angles.set(1, angles.get(1) - 0.1f);
                    //System.out.println("-- " + angles);

                    try {
                        motion.setAngles(names, angles, 0.1f);
                    } catch (CallError callError) {
                        callError.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }
}


class NaoControlerStop extends Thread {
    static Controller controller;
    static ALMotion motion;

    public NaoControlerStop(Controller controll, ALMotion motion){
        this.motion = motion;
        this.controller = controll;
    }

    public void run() {
        boolean rt=false,lt=false,lllr=false;

        while(true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            controller.poll();

            if (controller.getAxisValue(6) ==-1) { //RT
                if (rt==true) {
                    System.out.println("RT stop");
                    try {
                        motion.stopWalk();
                    } catch (CallError callError) {
                        callError.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    rt=false;
                }
            }

            if (controller.getAxisValue(6) != (float) -1) {  //LT
                rt=true;
            }

            if (controller.getAxisValue(3) ==-1) { //LT
                if (lt==true) {
                    System.out.println("LT stop");
                    try {
                        motion.stopWalk();
                    } catch (CallError callError) {
                        callError.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    lt=false;
                }

            }

            if (controller.getAxisValue(3) != (float) -1) {  //LT
                lt=true;
            }

            if (controller.getAxisValue(1) == 0 ) { //LL LR
                if (lllr==true) {
                    System.out.println("LL LR stop");
                    try {
                        motion.stopWalk();
                    } catch (CallError callError) {
                        callError.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    lllr=false;
                }
            }

            if (controller.getAxisValue(1) !=0 ) { //LL LR
                lllr=true;
            }
        }
    }
}

public class NaoController {
    static Controller controller;
    private ALMotion motion;
    private Initmotion restM;
    private ALRobotPosture posture;

    public NaoController(Application application) {
        try {
            motion = new ALMotion(application.session());
            restM = new Initmotion(application);
            posture = new ALRobotPosture(application.session());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startController(){
    //public static void main(String[] args) throws Exception {
        try {
            Controllers.create();
        }
        catch(LWJGLException e) {
            e.printStackTrace();
        }
        Controllers.poll();
        controller=Controllers.getController(0);

        NaoControler naoController = new NaoControler(controller,motion,restM, posture);
        naoController.start();
        NaoControlerStop naoControllerStop = new NaoControlerStop(controller,motion);
        naoControllerStop.start();
    }
}