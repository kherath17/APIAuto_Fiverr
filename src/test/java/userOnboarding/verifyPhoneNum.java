package userOnboarding;

import dataRead.read;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;

public class verifyPhoneNum {
    @Test(priority = 0)
    void verifyPhoneNum() throws IOException, ParseException {
        RestAssured.baseURI = "https://uat.halsababy.com/mobile-backend/";
        RequestSpecification req = RestAssured.
                given()
                .contentType("application/json")
                .accept("*/*")
                .header("X-API-version", read.readData("X-API-version"));
        Response resp = req.get("api/verify/+94771119706");
        int statusCode = resp.statusCode();

        System.out.println("Status code is "+resp.statusCode());
        //System.out.println("Actual Response Time is "+resp.getTimeIn(TimeUnit.MILLISECONDS)+" Milliseconds");
        System.out.println("Actual Response Time is "+(Integer.parseInt(String.valueOf(resp.getTimeIn(TimeUnit.MILLISECONDS)))/1000) + " seconds "+(Integer.parseInt(String.valueOf(resp.getTimeIn(TimeUnit.MILLISECONDS)))%1000) + " Milliseconds ");
        System.out.println(resp.getBody().asPrettyString());

        Assert.assertEquals(statusCode,200);
        Assert.assertTrue(resp.getTimeIn(TimeUnit.SECONDS)<=3);
        String jsonString = resp.getBody().asPrettyString();
        Assert.assertEquals("true",resp.getBody().asString());

    }



    @Test(priority = 1)
    void verifyPhoneNum_Negative() throws IOException, ParseException {
        RestAssured.baseURI = "https://uat.halsababy.com/mobile-backend/";
        RequestSpecification req = RestAssured.
            given()
                .contentType("application/json")
                .accept("*/*")
                .header("X-API-version", read.readData("X-API-version"));
        Response resp = req.get("api/verifyy/+94771119706");
        int statusCode = resp.statusCode();

        System.out.println("Status code is "+resp.statusCode());
       // System.out.println("Actual Response Time is "+resp.getTimeIn(TimeUnit.MILLISECONDS)+" seconds");
        System.out.println("Actual Response Time is "+(Integer.parseInt(String.valueOf(resp.getTimeIn(TimeUnit.MILLISECONDS)))/1000) + " seconds "+(Integer.parseInt(String.valueOf(resp.getTimeIn(TimeUnit.MILLISECONDS)))%1000) + " Milliseconds ");
        String jsonString = resp.getBody().asPrettyString();
        String message = JsonPath.from(jsonString).get("error");
        System.out.println(jsonString);

        Assert.assertNotEquals(statusCode,200);
        Assert.assertTrue(resp.getTimeIn(TimeUnit.SECONDS)<=3);
        Assert.assertEquals("Not Found",message);

    }
}
