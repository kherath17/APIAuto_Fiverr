package userOnboarding;

import dataRead.read;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import java.io.IOException;

public class fireBaseAccessToken {
    public static String token;
    read read = new read();
    @Test
    void generateToken() throws IOException, ParseException {
        RestAssured.baseURI="";
        RequestSpecification req = RestAssured.given();

        JSONObject jsob = new JSONObject();
        jsob.put("email",read.readData("tokenEmail"));
        jsob.put("password","abcd@1234");
        jsob.put("returnSecureToken","true");

        req.header("Accept","*/*");
        req.header("contentType","application/json");
        req.header("X-API-version",read.readData("X-API-version"));

        Response getToken = req.body(jsob).post("https://www.googleapis.com/identitytoolkit/v3/relyingparty/verifyPassword?key="+read.readData("key"));
        String jsonString = getToken.getBody().asString();

        token = JsonPath.from(jsonString).get("idToken");
        System.out.println(token);
        //return token;
    }

}

