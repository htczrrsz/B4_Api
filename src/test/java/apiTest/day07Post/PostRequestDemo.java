package apiTest.day07Post;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class PostRequestDemo {
    @BeforeClass
    public void beforeClass() {
        baseURI = "https://www.krafttechexlab.com/sw/api/v1";
    }

    @Test
    public void postNewUser() {
        String jsonBody="{\n" +
                "  \"name\": \"symanski\",\n" +
                "  \"email\": \"symanski@kraftexlab.com\",\n" +
                "  \"password\": \"Test1234\",\n" +
                "  \"about\": \"About Me\",\n" +
                "  \"terms\": \"10\"\n" +
                "}";
        String jsonBody2="""
                {
                "name": "symanski" 
                "email": "symanski@kraftexlab.com" 
                "password": "Test1234" 
                "about": "About Me" 
                "terms": "10" 
                }
                """;

        Response response = given().accept(ContentType.JSON) // hey api send me body as json format
                .and()
                .contentType(ContentType.JSON) // hey api I am sending json body
                .and()
                .body(jsonBody)
                .when()
                .post("/allusers/register");

        assertEquals(response.statusCode(),200);
        response.prettyPrint();
        assertTrue(response.body().asString().contains("token"));

    }

    @Test
    public void postNewUser2(){
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("name","1symanski");
        requestMap.put("email","symanski1@kraftexlab.com");
        requestMap.put("password","Test1234");
        requestMap.put("about","About me");
        requestMap.put("terms","5");

        Response response = given().accept(ContentType.JSON) // hey api send me body as json format
                .and()
                .contentType(ContentType.JSON) // hey api I am sending json body
                .and()
                .body(requestMap)
                .when()
                .post("/allusers/register");

        assertEquals(response.statusCode(),200);
        response.prettyPrint();
        assertTrue(response.body().asString().contains("token"));
    }


    @Test
    public void postNewUser3(){
        NewUserInfo newUserInfo= new NewUserInfo();
        newUserInfo.setName("2symanski");
        newUserInfo.setEmail("symanski2@kraftexlab.com");
        newUserInfo.setPassword("Test1234");
        newUserInfo.setAbout("About me");
        newUserInfo.setTerms("5");


        Response response = given().accept(ContentType.JSON) // hey api send me body as json format
                .and()
                .contentType(ContentType.JSON) // hey api I am sending json body
                .and()
                .body(newUserInfo) // Serialization
                .when()
                .post("/allusers/register");

        assertEquals(response.statusCode(),200);
        response.prettyPrint();
        assertTrue(response.body().asString().contains("token"));
    }



    @Test
    public void postNewUser4(){
        NewUserInfo newUserInfo= new NewUserInfo("3Symanski","symanski3@kraftexlab.com","Test1234","About me","5");

        Response response = given().accept(ContentType.JSON) // hey api send me body as json format
                .and()
                .contentType(ContentType.JSON) // hey api I am sending json body
                .and()
                .body(newUserInfo) // Serialization
                .when()
                .post("/allusers/register");

        assertEquals(response.statusCode(),200);
        response.prettyPrint();
        assertTrue(response.body().asString().contains("token"));
    }


}
