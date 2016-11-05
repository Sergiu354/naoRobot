package work;

import java.io.*;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonParse {

    private String filePath;
    private JSONParser jsonParser;
    private JSONObject jsonObject;

/*    public JsonParse() throws IOException, ParseException {
        // read the json file
        FileReader reader = new FileReader(filePath);
        jsonParser = new JSONParser();
        jsonObject = (JSONObject) jsonParser.parse(reader);

    }*/

    public boolean getObjectReadNamoJSON(String comparObject) throws IOException, ParseException {
        filePath = "objectReadNamoJSON.json";
        FileReader reader = new FileReader(filePath);
        jsonParser = new JSONParser();
        jsonObject = (JSONObject) jsonParser.parse(reader);

        ArrayList<String> nameObject = (ArrayList) jsonObject.get("object");
        //System.out.println("The first name is: " + nameObject);

        if (!nameObject.contains(comparObject)) {
            nameObject.add(comparObject);
            FileWriter file = new FileWriter("objectReadNamoJSON.json");
            file.write(jsonObject.toJSONString());
            file.flush();
            file.close();
            return true;
        }else {
            //System.err.println("\tObiectul\" "+comparObject+" \" a fost gasit");
            return false;
        }
    }

    public void resetJSONObject() throws IOException, ParseException {
        filePath = "objectReadNamoJSON.json";
        FileReader reader = new FileReader(filePath);
        jsonParser = new JSONParser();
        jsonObject = (JSONObject) jsonParser.parse(reader);

        ArrayList<String> nameObject = (ArrayList) jsonObject.get("object");
        nameObject.clear();
        FileWriter file = new FileWriter("objectReadNamoJSON.json");
        file.write(jsonObject.toJSONString());
        file.flush();
        file.close();
    }

    public boolean getFacesReadJSON(String faces) throws IOException, ParseException {
        filePath = "facesReadJSON.json";
        FileReader reader = new FileReader(filePath);
        jsonParser = new JSONParser();
        jsonObject = (JSONObject) jsonParser.parse(reader);

        ArrayList<String> nameObject = (ArrayList) jsonObject.get("faces");
        //System.out.println("The first name is: " + nameObject);

        if (!nameObject.contains(faces)) {
            nameObject.add(faces);
            FileWriter file = new FileWriter("facesReadJSON.json");
            file.write(jsonObject.toJSONString());
            file.flush();
            file.close();
            return true;
        }else {
            //System.err.println("\tFaces \" "+faces+" \" a fost gasit");
            return false;
        }
    }

    public void resetJSONFaces() throws IOException, ParseException {
        filePath = "facesReadJSON.json";
        FileReader reader = new FileReader(filePath);
        jsonParser = new JSONParser();
        jsonObject = (JSONObject) jsonParser.parse(reader);

        ArrayList<String> nameObject = (ArrayList) jsonObject.get("faces");
        nameObject.clear();
        FileWriter file = new FileWriter("facesReadJSON.json");
        file.write(jsonObject.toJSONString());
        file.flush();
        file.close();

    }

}