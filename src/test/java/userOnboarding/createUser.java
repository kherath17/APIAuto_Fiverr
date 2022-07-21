package userOnboarding;


import dataRead.read;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;


public class createUser {
    @Test(priority = 0)
    void createUser() throws IOException, ParseException {

        String payload = "{\n" +
                "\"user\":{\n" +
                "\"externalId\":\"AyZtGNq9bvYn34qbEZBVtyuDejK2\",\n" +
                "\"email\":\"qa.uat22@gmail.com\",\n" +
                "\"phone\":\"+94718722812\"\n" +
                "},\n" +
                "\"password\":\"abcd@1234\"\n" +
                "}";
       RequestSpecification req = given()
                .contentType("application/json")
                .accept("*/*")
                .header("X-API-version", read.readData("X-API-version"));

        Response resp = req.body(payload).post("/api/user");
        String jsonString = resp.getBody().asPrettyString();
        String message = JsonPath.from(jsonString).get("message");
        //System.out.println("Actual Response Time is "+resp.getTimeIn(TimeUnit.SECONDS)+" seconds");//
        System.out.println("Actual Response Time is "+(Integer.parseInt(String.valueOf(resp.getTimeIn(TimeUnit.MILLISECONDS)))/1000) + " seconds "+(Integer.parseInt(String.valueOf(resp.getTimeIn(TimeUnit.MILLISECONDS)))%1000) + " Milliseconds ");
        System.out.println("Status code is "+resp.statusCode());
        System.out.println(resp.getBody().asPrettyString());

        Assert.assertEquals(resp.statusCode(),200);
        Assert.assertTrue(resp.getTimeIn(TimeUnit.SECONDS)<=1);
        Assert.assertEquals(message,"");

    }


    @Test(priority = 1)
    void createUser_Negative() throws IOException, ParseException {
        //RestAssured.baseURI = "https://uat.halsababy.com/mobile-backend/";
        RequestSpecification req = RestAssured.
                given()
                .contentType("application/json")
                .accept("*/*")
                .header("X-API-version", read.readData("X-API-version"));

        String payload = "{\n" +
                "\"user\":{\n" +
                "\"externalId\":\"AyZtGNq9bvYn34qbEZBVtyuDejK2\",\n" +
                "\"email\":\"qa.uat22@gmail.com\",\n" +
                "\"phone\":\"+94718722812\"\n" +
                "},\n" +
                "\"password\":\"abcd@1234\"\n" +
                "}";

        Response resp = req.body(payload).post("/api/user");
        String jsonString = resp.getBody().asPrettyString();

        String message = JsonPath.from(jsonString).get("message");
        //System.out.println(message);

        //System.out.println("Actual Response Time is "+resp.getTimeIn(TimeUnit.SECONDS)+" seconds");
        System.out.println("Actual Response Time is "+(Integer.parseInt(String.valueOf(resp.getTimeIn(TimeUnit.MILLISECONDS)))/1000) + " seconds "+(Integer.parseInt(String.valueOf(resp.getTimeIn(TimeUnit.MILLISECONDS)))%1000) + " Milliseconds ");
        System.out.println("Status code is "+resp.statusCode());
        System.out.println(resp.getBody().asPrettyString());

        Assert.assertNotEquals(resp.statusCode(),200);
        Assert.assertTrue(resp.getTimeIn(TimeUnit.SECONDS)<=1);
        Assert.assertEquals(message,"User with the externalId already exists");

    }
}
