package dataRead;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class read {
    public static String readData(String Key) throws IOException, ParseException {
        JSONParser js = new JSONParser();
        FileReader file = new FileReader("src/main/resources/data.json");
        Object obj = js.parse(file);
        JSONObject json = (JSONObject) obj;
        String value = json.get(Key).toString();
        return value;
    }
}
