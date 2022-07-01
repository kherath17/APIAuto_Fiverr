package userOnboarding;

import dataRead.read;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;

public class verifyEmail extends fireBaseAccessToken {

    @Test(priority = 0)
    void verifyEmail() throws IOException, ParseException {
        RestAssured.baseURI = "https://uat.halsababy.com/mobile-backend/";

        JSONObject jsob = new JSONObject();
        jsob.put("newEmail",read.readData("newEmail"));
        jsob.put("oldEmail",read.readData("tokenEmail"));

       RequestSpecification req = given()
                .contentType("application/json")
                .accept("*/*")
                .header("X-API-version", dataRead.read.readData("X-API-version"))
                .header("Authorization","Bearer "+fireBaseAccessToken.token);

        Response resp = req.body(jsob).put("api/user/verify/email");
        String jsonString = resp.getBody().asString();
        System.out.println(resp.getBody().asPrettyString());
        //System.out.println("Actual Response Time is "+resp.getTimeIn(TimeUnit.SECONDS)+" seconds");
        System.out.println("Actual Response Time is "+(Integer.parseInt(String.valueOf(resp.getTimeIn(TimeUnit.MILLISECONDS)))/1000) + " seconds "+(Integer.parseInt(String.valueOf(resp.getTimeIn(TimeUnit.MILLISECONDS)))%1000) + " Milliseconds ");
        System.out.println("Status code is "+resp.statusCode());
        Assert.assertEquals(resp.statusCode(),200);
        Assert.assertTrue(resp.getTimeIn(TimeUnit.SECONDS)<=5);

    }

    @Test(priority = 1)
    void verifyEmail_Negative() throws IOException, ParseException {
        JSONObject jsob = new JSONObject();
        jsob.put("newEmail",read.readData("newEmail"));
        jsob.put("oldEmail",read.readData("tokenEmail"));

        RequestSpecification req = given()
                .contentType("application/json")
                .accept("*/*")
                .header("X-API-version", read.readData("X-API-version"))
                .header("Authorization","Bearer "+fireBaseAccessToken.token);

        Response resp = req.body(jsob).put("api/user/verify/email");
        String jsonString = resp.getBody().asString();
        String message = JsonPath.from(jsonString).get("message");
        //System.out.println("Actual Response Time is "+resp.getTimeIn(TimeUnit.SECONDS)+" seconds");
        System.out.println("Actual Response Time is "+(Integer.parseInt(String.valueOf(resp.getTimeIn(TimeUnit.MILLISECONDS)))/1000) + " seconds "+(Integer.parseInt(String.valueOf(resp.getTimeIn(TimeUnit.MILLISECONDS)))%1000) + " Milliseconds ");
        System.out.println("Status code is "+resp.statusCode());
        System.out.println(resp.getBody().asPrettyString());

        Assert.assertNotEquals(resp.statusCode(),200);
        Assert.assertNotEquals(message,"true");
        Assert.assertTrue(resp.getTimeIn(TimeUnit.SECONDS)<=1);
    }
}
