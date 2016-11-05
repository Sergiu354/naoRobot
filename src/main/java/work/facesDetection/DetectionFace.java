package work.facesDetection;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.json.simple.parser.ParseException;
import org.openimaj.feature.FloatFV;
import org.openimaj.feature.FloatFVComparison;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.processing.face.detection.DetectedFace;
import org.openimaj.image.processing.face.detection.HaarCascadeDetector;
import org.openimaj.image.processing.face.detection.keypoints.FKEFaceDetector;
import org.openimaj.image.processing.face.detection.keypoints.KEDetectedFace;
import org.openimaj.image.processing.face.feature.FacePatchFeature;
import org.openimaj.image.processing.face.feature.comparison.FaceFVComparator;
import org.openimaj.image.processing.face.similarity.FaceSimilarityEngine;
import work.JsonParse;
import work.nao.NaoSpeakeSee;


public class DetectionFace{
    FImage image1;
    FImage[] image2 = new FImage[30];
    Map seeFaces = new HashMap<String, Double>();
    Map detectFace = new TreeMap<Double,String>();
    HaarCascadeDetector detector;
    HaarCascadeDetector detectoreye;
    FKEFaceDetector kedetector;
    FKEFaceDetector kedetectorEYE;
    FacePatchFeature.Extractor extractor;
    FaceFVComparator<FacePatchFeature, FloatFV> comparator;
    FaceSimilarityEngine<KEDetectedFace, FacePatchFeature, FImage> engine;
    File myFile;
    File[] files;
    int fileListSize;
    JsonParse faceJSON = new JsonParse();

    public DetectionFace() throws IOException {
        myFile = new File("/home/sergiu/NAO/faces/");
        files = myFile.listFiles();
        fileListSize=files.length;
        for (int i = 0; i <fileListSize; i++) {
            System.err.println(files[i]);
            image2[i]= ImageUtilities.readF(files[i]);
        }

        detector = HaarCascadeDetector.BuiltInCascade.frontalface_alt_tree.load();
        kedetector = new FKEFaceDetector(detector);

        detectoreye = HaarCascadeDetector.BuiltInCascade.frontalface_default.load();
        kedetectorEYE = new FKEFaceDetector(detectoreye);


        extractor = new FacePatchFeature.Extractor();

        comparator = new FaceFVComparator<FacePatchFeature, FloatFV>(FloatFVComparison.SUM_SQUARE);  //CITY_BLOCK ,COSINE_DIST , EUCLIDEAN , SUM_SQUARE
    }

    public void detection(FImage imag, NaoSpeakeSee speakeObject) throws Exception {
        image1 = imag;
        engine = new FaceSimilarityEngine<KEDetectedFace, FacePatchFeature, FImage>(kedetector, extractor, comparator);
        for (int i = 0; i < fileListSize; i++) {
            engine.setQuery(image1, "image1");
            engine.setTest(image2[i], "image2");
            engine.performTest();
            for (Map.Entry<String, Map<String, Double>> e : engine.getSimilarityDictionary().entrySet()) {
                double bestScore = Double.MAX_VALUE;
                for (Map.Entry<String, Double> matches : e.getValue().entrySet()) {
                    if (matches.getValue() < bestScore) {
                        bestScore = matches.getValue();
                        if(bestScore<2500) {   // SUM_SQUARE control
                        //System.err.println(bestScore + "  face " + files[i].getName());
                        seeFaces.put(files[i].getName(), bestScore);
                        }
                    }
                }
            }
        }
        System.out.println("\t\t\t:::> "+seeFaces);

        engine = new FaceSimilarityEngine<KEDetectedFace, FacePatchFeature, FImage>(kedetectorEYE, extractor, comparator);
        for (int i = 0; i < fileListSize; i++) {
            if (seeFaces.containsKey(files[i].getName())) {
                engine.setQuery(image1, "image1");
                engine.setTest(image2[i], "image2");
                engine.performTest();

                for (Map.Entry<String, Map<String, Double>> ee : engine.getSimilarityDictionary().entrySet()) {
                    double bestScoree = Double.MAX_VALUE;
                    for (Map.Entry<String, Double> matchess : ee.getValue().entrySet()) {
                        if (matchess.getValue() < bestScoree) {
                            bestScoree = matchess.getValue();
                            //System.out.println(bestScoree +"   "+ files[i].getName());
                            //System.err.println(bestScoree + " nose  " + files[i].getName());
                            bestScoree = ((Double) seeFaces.get(files[i].getName()) + bestScoree) / 2;
                            seeFaces.put(files[i].getName(),bestScoree);
                        }
                    }
                }
            }
        }


        Set<Map.Entry<String, Double>> set = seeFaces.entrySet();
        for (Map.Entry<String, Double> me : set) {
            detectFace.put(me.getValue(),me.getKey());
        }

        if (!detectFace.isEmpty()) {
            System.err.println(detectFace);
            if (faceJSON.getFacesReadJSON(detectFace.get(detectFace.keySet().iterator().next()).toString())) {
                System.err.println("\tFace see: " + detectFace.get(detectFace.keySet().iterator().next()));
                speakeObject.speakeFace((detectFace.get(detectFace.keySet().iterator().next())).toString());
                faceJSON.getFacesReadJSON(detectFace.get(detectFace.keySet().iterator().next()).toString());
            }
        }
        detectFace.clear();
        seeFaces.clear();
    }


}