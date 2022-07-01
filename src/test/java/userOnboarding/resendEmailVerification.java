package userOnboarding;

import dataRead.read;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;

public class resendEmailVerification {
    @Test(priority = 0)
    void resendEmailVer() throws IOException, ParseException {

        RequestSpecification req = given()
                .contentType("application/json")
                .accept("*/*")
                .header("X-API-version", read.readData("X-API-version"))
                .header("Authorization","Bearer "+fireBaseAccessToken.token);


        Response resp = req.put("api/user/resend/email");
        String jsonString = resp.getBody().asString();

        String message = JsonPath.from(jsonString).get("error");
        //System.out.println("Actual Response Time is "+resp.getTimeIn(TimeUnit.SECONDS)+" seconds");
        System.out.println("Actual Response Time is "+(Integer.parseInt(String.valueOf(resp.getTimeIn(TimeUnit.MILLISECONDS)))/1000) + " seconds "+(Integer.parseInt(String.valueOf(resp.getTimeIn(TimeUnit.MILLISECONDS)))%1000) + " Milliseconds ");
        System.out.println("Status code is "+resp.statusCode());
        System.out.println(resp.getBody().asPrettyString());

        Assert.assertEquals(resp.statusCode(),200);
        Assert.assertTrue(resp.getTimeIn(TimeUnit.SECONDS)<=1);
        //Assert.assertEquals(message,"Bad Request");
    }

    @Test(priority = 1)
    void resendEmailVer_Negative() throws IOException, ParseException {

        RequestSpecification req = given()
                .contentType("application/json")
                .accept("*/*")
                .header("X-API-version", read.readData("X-API-version"))
                .header("Authorization","Bearer "+fireBaseAccessToken.token);


        Response resp = req.put("api/user/resend/email");
        String jsonString = resp.getBody().asString();

        String message = JsonPath.from(jsonString).get("error");


        //System.out.println("Actual Response Time is "+resp.getTimeIn(TimeUnit.SECONDS)+" seconds");
        System.out.println("Actual Response Time is "+(Integer.parseInt(String.valueOf(resp.getTimeIn(TimeUnit.MILLISECONDS)))/1000) + " seconds "+(Integer.parseInt(String.valueOf(resp.getTimeIn(TimeUnit.MILLISECONDS)))%1000) + " Milliseconds ");
        System.out.println("Status code is "+resp.statusCode());
        System.out.println(resp.getBody().asPrettyString());

        Assert.assertNotEquals(resp.statusCode(),200);
        Assert.assertTrue(resp.getTimeIn(TimeUnit.SECONDS)<=1);
        Assert.assertEquals(message,"Bad Request");

    }
}
