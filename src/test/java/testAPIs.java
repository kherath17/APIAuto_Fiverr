//made static and added * to call the baseurl and append it to get in next test case
import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

public class testAPIs {
    String token;

    @Test(priority = 0)
    void verifyPhoneNum() {
        RestAssured.baseURI = "https://uat.halsababy.com/mobile-backend/";
        ValidatableResponse resp = RestAssured.given()
                .contentType("application/json")
                .accept("*/*")
                .header("X-API-version","0.6.5")
               // .pathParam("phoneNumber", "+94771119706")
                .get("api/verify/+94771119706")
                .then()
                .statusCode(200);

    }

    @Test(priority = 1)
    void createUser(){

        String payload = "{\n" +
                "\"user\":{\n" +
                "\"externalId\":\"AyZtGNq9bvYn34qbEZBVtyuDejK2\",\n" +
                "\"email\":\"qa.uat22@gmail.com\",\n" +
                "\"phone\":\"+94718722812\"\n" +
                "},\n" +
                "\"password\":\"abcd@1234\"\n" +
                "}";
        given()
                .contentType("application/json")
                .accept("*/*")
                .header("X-API-version","0.6.5")
                .body(payload)
                .when()
                .post("api/user")
                .then()
                .statusCode(200);
    }


    @Test(priority = 2)
    void generateToken(){
        RestAssured.baseURI="";
        RequestSpecification req = RestAssured.given();


        JSONObject jsob = new JSONObject();
        jsob.put("email","shjew@gmail1.com");
        jsob.put("password","abcd@1234");
        jsob.put("returnSecureToken","true");

        req.header("Accept","*/*");
        req.header("contentType","application/json");
        req.header("X-API-version","0.6.5");

        Response getToken = req.body(jsob).post("https://www.googleapis.com/identitytoolkit/v3/relyingparty/verifyPassword?key=AIzaSyCct-2nyLRhV1dZEzvBBOE2PLZfSbIETBA");
        String jsonString = getToken.getBody().asString();

        token = JsonPath.from(jsonString).get("idToken");
        System.out.println(token);
    }

    @Test(priority = 3)
    void verifyEmail(){
        RestAssured.baseURI = "https://uat.halsababy.com/mobile-backend/";
        given()
                .contentType("application/json")
                .accept("*/*")
                .header("X-API-version","0.6.5")
                .header("Authorization","Bearer "+token)
                .when()
                .put("api/user/verify/email")
                .then()
                .statusCode(200);
    }
    @Test(priority = 4)
    void updateVerify(){
        given()
                .contentType("application/json")
                .accept("*/*")
                .header("X-API-version","0.6.5")
                .header("Authorization","Bearer "+token)
                .when()
                .put("api/user/update/is-verified")
                .then()
                .statusCode(200);
    }

    @Test(priority = 5)
    void updateUserPro(){

        JSONObject jsob = new JSONObject();
        jsob.put("birthday","13/10/1990");
        jsob.put("firstName","Name1");
        jsob.put("lastName","Name23");
        jsob.put("photoUrl","http://hjdw.chje.com");

        given()
                .contentType("application/json")
                .accept("*/*")
                .header("X-API-version","0.6.5")
                .header("Authorization","Bearer "+token)
                .body(jsob)
                .when()
                .put("api/user/update/profile")
                .then()
                .statusCode(200);
    }

    @Test(priority = 6)
    void removePhoto(){

        given()
                .contentType("application/json")
                .accept("*/*")
                .header("X-API-version","0.6.5")
                .header("Authorization","Bearer "+token)
                .when()
                .put("api/user/remove/photourl")
                .then()
                .statusCode(200);
    }

    @Test(priority = 7)
    void updatePhone(){

        JSONObject jsob = new JSONObject();
        jsob.put("phone","+94778229122");

        given()
                .contentType("application/json")
                .accept("*/*")
                .header("X-API-version","0.6.5")
                .header("Authorization","Bearer "+token)
                .body(jsob)
                .when()
                .put("api/user/update/phone")
                .then()
                .statusCode(200);
    }

    @Test(priority = 8)
    void updateEmail(){

        JSONObject jsob = new JSONObject();
        jsob.put("email","shjew@gmail.com");

        given()
                .contentType("application/json")
                .accept("*/*")
                .header("X-API-version","0.6.5")
                .header("Authorization","Bearer "+token)
                .body(jsob)
                .when()
                .put("api/user/update/email")
                .then()
                .statusCode(200);
    }

    @Test(priority = 9)
    void resendEmailVer(){

        given()
                .contentType("application/json")
                .accept("*/*")
                .header("X-API-version","0.6.5")
                .header("Authorization","Bearer "+token)
                .when()
                .put("api/user/resend/email")
                .then()
                .statusCode(200);
    }

    @Test(priority = 10)
    void getCurrentUser(){

        given()
                .contentType("application/json")
                .accept("*/*")
                .header("X-API-version","0.6.5")
                .header("Authorization","Bearer "+token)
                .when()
                .get("api/user/current")
                .then()
                .statusCode(200);
    }
}