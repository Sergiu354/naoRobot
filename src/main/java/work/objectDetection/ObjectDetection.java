package work.objectDetection;

import com.aldebaran.qi.Application;
import org.openimaj.feature.local.list.LocalFeatureList;
import org.openimaj.feature.local.matcher.FastBasicKeypointMatcher;
import org.openimaj.feature.local.matcher.LocalFeatureMatcher;
import org.openimaj.feature.local.matcher.MatchingUtilities;
import org.openimaj.feature.local.matcher.consistent.ConsistentLocalFeatureMatcher2d;
import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.RGBColour;
import org.openimaj.image.feature.local.engine.DoGSIFTEngine;
import org.openimaj.image.feature.local.keypoints.Keypoint;
import org.openimaj.math.geometry.transforms.estimation.RobustAffineTransformEstimator;
import org.openimaj.math.model.fit.RANSAC;
import org.openimaj.util.pair.IndependentPair;
import org.openimaj.video.capture.VideoCapture;
import work.JsonParse;
import work.ReadPropFile;
import work.nao.NAOImage;
import work.nao.NaoSpeakeSee;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

public class ObjectDetection extends Thread {
    String fileDirect;
    Iterator itInliners;
    IndependentPair getInlinersToIt,getInlinersToIt2;
    boolean imageError=false;
    boolean compar=false;
    int nawImage=0;
    private ArrayList<String> object = new ArrayList<String>();
    private DisplayUtilities imageToDisplay = new DisplayUtilities();
    private int fileListSize;
    private VideoCapture capture;
    private MBFImage query;
    private MBFImage[] target= new MBFImage[100];
    private DoGSIFTEngine engine;
    private LocalFeatureList<Keypoint> queryKeypoints;
    private LocalFeatureList<Keypoint>[] targetKeypoints = new LocalFeatureList [100];
    private LocalFeatureMatcher<Keypoint> matcher;
    private RobustAffineTransformEstimator modelFitter;
    private MBFImage consistentMatches;
    private NAOImage naoImage;
    private JsonParse readJSON;
    private NaoSpeakeSee speakeObject;
    private Properties prop=null;

    public ObjectDetection(Application application, String file) throws Exception {
        naoImage = new NAOImage(application);
        speakeObject = new NaoSpeakeSee(application);

        readJSON = new JsonParse();

        this.fileDirect = file;

        if (prop==null){
            prop = new ReadPropFile().readProperties();
        }
    }

    public void run() {
        int i=0;
        engine = new DoGSIFTEngine();
        modelFitter = new RobustAffineTransformEstimator(5.0, 1500, new RANSAC.PercentageInliersStoppingCondition(0.5));
        File myFile = new File(prop.getProperty("fileToObjectCompare")+fileDirect+"/");
        File[] files = myFile.listFiles();
        fileListSize=files.length;

        for (i = 0; i <fileListSize; i++) {
            System.err.println(files[i]);
            try {
                target[i] = ImageUtilities.readMBF(files[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            targetKeypoints[i] = engine.findFeatures(target[i].flatten());
        }

        while (true) {
            //query = capture.getNextFrame();
            try {
                query = ImageUtilities.createMBFImage(naoImage.getNAOImage(fileDirect),true);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //DisplayUtilities.display(query);
            DisplayUtilities.displayName(query,"Nao_"+fileDirect);


            for (i = 0; i < fileListSize; i++) {
                System.out.println("\tnew image: " + files[i]);
                queryKeypoints = engine.findFeatures(query.flatten());

                matcher = new ConsistentLocalFeatureMatcher2d<Keypoint>(new FastBasicKeypointMatcher<Keypoint>(8), modelFitter);
                matcher.setModelFeatures(queryKeypoints);
                matcher.findMatches(targetKeypoints[i]);

                itInliners = modelFitter.getInliers().iterator();
                compar=false;
                imageError=false;
                while(itInliners.hasNext()){
                    getInlinersToIt = (IndependentPair) itInliners.next();
                    for(int iter=0;iter<3 && itInliners.hasNext();iter++) {
                        if (itInliners.hasNext())
                            getInlinersToIt2 = (IndependentPair) itInliners.next();
                        if (getInlinersToIt2 != null)
                            compar = getInlinersToIt.secondObject().equals(getInlinersToIt2.secondObject());
                        if (compar == true) {
                            if (imageError == compar)
                                break;
                            imageError = true;
                        }
                    }
                    //System.err.println(">>>>>> "+getInlinersToIt.secondObject());
                }

                /*if(imageError==true)
                    System.err.println(">>> Image error compare");*/

                if (matcher.getMatches().size() > 10) {
                    if(imageError!=true){
                        // JSON compare file
                        try {
                            //if (readJSON.getObjectReadNamoJSON(files[i].getName())) {
                            //if (!(modelFitter.getInliers().get(1).getSecondObject().equals(modelFitter.getInliers().get(3).getSecondObject())) == true) {

                            consistentMatches = MatchingUtilities.drawMatches(query, target[i], matcher.getMatches(), RGBColour.RED);
                            //imageToDisplay.display(consistentMatches);
                            DisplayUtilities.displayName(consistentMatches, "NaoDetect");

                            System.out.println("In  "+modelFitter.getInliers());

                            object.add(files[i].toString());
                            //System.out.println("matcher.getMatches():   " + matcher.getMatches().size());
                            nawImage++;
                            ImageUtilities.write(query, new File(prop.getProperty("saveImageToDirectory")+"image[" + files[i].getName() + "] - (" + nawImage + ").png"));
                            System.out.println("Obiectul a fost gasit: " + files[i]);
                            speakeObject.speakeObject(files[i].getName().toString());
                            //}
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                queryKeypoints.clear();
                itInliners.remove();
                consistentMatches=null;
            }
        }
    }
}
