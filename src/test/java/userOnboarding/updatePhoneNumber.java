package userOnboarding;

import dataRead.read;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.put;

public class updatePhoneNumber {

    Random rand = new Random();
    int randomNum = rand.nextInt((10 - 1) + 1) + 1;
    String random = Integer.toString(randomNum);

    @Test(priority = 0)
    void updatePhone() throws IOException, ParseException {

        JSONObject jsob = new JSONObject();
        jsob.put("phone","+9477822512"+random);

        RequestSpecification req = given()
                .contentType("application/json")
                .accept("*/*")
                .header("X-API-version", read.readData("X-API-version"))
                .header("Authorization","Bearer "+fireBaseAccessToken.token);


        Response resp = req.body(jsob).put("api/user/update/phone");
        String jsonString = resp.getBody().asString();
        String message = JsonPath.from(jsonString).get("email");

        //System.out.println("Actual Response Time is "+resp.getTimeIn(TimeUnit.SECONDS)+" seconds");
        System.out.println("Actual Response Time is "+(Integer.parseInt(String.valueOf(resp.getTimeIn(TimeUnit.MILLISECONDS)))/1000) + " seconds "+(Integer.parseInt(String.valueOf(resp.getTimeIn(TimeUnit.MILLISECONDS)))%1000) + " Milliseconds ");
        System.out.println("Status code is "+resp.statusCode());
        System.out.println(resp.getBody().asPrettyString());

        Assert.assertEquals(resp.statusCode(),200);
        Assert.assertTrue(resp.getTimeIn(TimeUnit.SECONDS)<=1);
        Assert.assertEquals(message,read.readData("tokenEmail"));

    }

    @Test(priority = 1)
    void updatePhone_Negative() throws IOException, ParseException {

        JSONObject jsob = new JSONObject();
        jsob.put("phone","+9477822512"+random);

        RequestSpecification req = given()
                .contentType("application/json")
                .accept("*/*")
                .header("X-API-version", read.readData("X-API-version"))
                .header("Authorization","Bearer "+fireBaseAccessToken.token);

        Response resp = req.body(jsob).put("api/user/update/phone");
        String jsonString = resp.getBody().asString();
        String message = JsonPath.from(jsonString).get("message");


        //System.out.println("Actual Response Time is "+resp.getTimeIn(TimeUnit.SECONDS)+" seconds");
        System.out.println("Actual Response Time is "+(Integer.parseInt(String.valueOf(resp.getTimeIn(TimeUnit.MILLISECONDS)))/1000) + " seconds "+(Integer.parseInt(String.valueOf(resp.getTimeIn(TimeUnit.MILLISECONDS)))%1000) + " Milliseconds ");
        System.out.println("Status code is "+resp.statusCode());
        System.out.println(resp.getBody().asPrettyString());

        Assert.assertNotEquals(resp.statusCode(),200);
        Assert.assertTrue(resp.getTimeIn(TimeUnit.SECONDS)<=1);
        Assert.assertTrue(message.contains("is already existing in the system"));
    }
}
