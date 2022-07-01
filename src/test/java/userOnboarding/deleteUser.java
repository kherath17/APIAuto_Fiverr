package userOnboarding;

import dataRead.read;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class deleteUser {
    @Test(priority = 0)
    void verifyDeleteUser() throws IOException, ParseException {
        RestAssured.baseURI = "https://uat.halsababy.com/mobile-backend/";
        RequestSpecification req = RestAssured.
                given()
                .contentType("application/json")
                .accept("*/*")
                .header("X-API-version", read.readData("X-API-version"))
                .header("Authorization","Bearer "+fireBaseAccessToken.token);
        Response resp = req.delete("api/user/delete");
        int statusCode = resp.statusCode();

        //System.out.println("Actual Response Time is "+resp.getTimeIn(TimeUnit.SECONDS)+" seconds");
        System.out.println("Actual Response Time is "+(Integer.parseInt(String.valueOf(resp.getTimeIn(TimeUnit.MILLISECONDS)))/1000) + " seconds "+(Integer.parseInt(String.valueOf(resp.getTimeIn(TimeUnit.MILLISECONDS)))%1000) + " Milliseconds ");
        System.out.println("Status code is "+resp.statusCode());
        System.out.println(resp.getBody().asPrettyString());

        Assert.assertEquals(statusCode,200);
        Assert.assertTrue(resp.getTimeIn(TimeUnit.SECONDS)<=1);
    }

    @Test(priority = 1)
    void verifyDeleteUser_Negative() throws IOException, ParseException {
        RestAssured.baseURI = "https://uat.halsababy.com/mobile-backend/";
        RequestSpecification req = RestAssured.
                given()
                .contentType("application/json")
                .accept("*/*")
                .header("X-API-version", read.readData("X-API-version"))
                .header("Authorization","Bearer "+fireBaseAccessToken.token);
        Response resp = req.delete("api/user/delete");
        int statusCode = resp.statusCode();

        //System.out.println("Actual Response Time is "+resp.getTimeIn(TimeUnit.SECONDS)+" seconds");
        System.out.println("Actual Response Time is "+(Integer.parseInt(String.valueOf(resp.getTimeIn(TimeUnit.MILLISECONDS)))/1000) + " seconds "+(Integer.parseInt(String.valueOf(resp.getTimeIn(TimeUnit.MILLISECONDS)))%1000) + " Milliseconds ");
        System.out.println("Status code is "+resp.statusCode());
        System.out.println(resp.getBody().asPrettyString());

        Assert.assertNotEquals(statusCode,200);
        Assert.assertTrue(resp.getTimeIn(TimeUnit.SECONDS)<=1);


    }
}
