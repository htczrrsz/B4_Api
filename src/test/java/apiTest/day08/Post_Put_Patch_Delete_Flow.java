package apiTest.day08;

import static io.restassured.RestAssured.*;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Post_Put_Patch_Delete_Flow {

    static String email="miamia@gmail.com";
    static String password="Mia123456";
    static String endPointAddExperience="/experience/add";
    static String endPointUpdateWithPut="/experience/updateput/";
    static String endPointUpdateWithPatch="/experience/updatepatch";
    static String endPointDelete="/experience/delete";
    static Integer experienceId;


    @BeforeClass
    public void setUp(){
        baseURI="https://www.krafttechexlab.com/sw/api/v1";
    }

    @Test(priority = 1)
    public void addExperience(){
        String jsonBody="{\n" +
                "  \"job\": \"Junior QA engineer\",\n" +
                "  \"company\": \"Amazon\",\n" +
                "  \"location\": \"Seattle\",\n" +
                "  \"fromdate\": \"2020-10-10\",\n" +
                "  \"todate\": \"2022-10-10\",\n" +
                "  \"current\": \"false\",\n" +
                "  \"description\": \"Description\"\n" +
                "}";

        Response response = given()
                .header("token", Authorization.getToken(email, password))
                .body(jsonBody)
                .when()//.log().all()
                .post(endPointAddExperience)
                .prettyPeek();

        //verify that status code is 200
        Assert.assertEquals(response.statusCode(),200);

        // get the experience id
        experienceId= response.path("id");

    }


    @Test(priority = 2)
    public void updateExperience_Put(){
        String jsonBodyForUpdate="{\n" +
                "  \"job\": \"QA Automation Engineer\",\n" +
                "  \"company\": \"Amazon\",\n" +
                "  \"location\": \"Seattle\",\n" +
                "  \"fromdate\": \"2020-10-10\",\n" +
                "  \"todate\": \"2021-10-10\",\n" +
                "  \"current\": \"false\",\n" +
                "  \"description\": \"update with PUT method\"\n" +
                "}";

        Response response = given()
                .header("token", Authorization.getToken(email, password))
                .queryParam("id", experienceId)
                .body(jsonBodyForUpdate).log().all()
                .when()
                .put(endPointUpdateWithPut)
                .prettyPeek();

        Assert.assertEquals(response.statusCode(),200);
    }

    @Test(priority = 3)
    public void updateExperience_Patch(){
        String jsonBodyForPatch="{\n" +
                "  \"company\": \"New Company with PATCH method in POSTMAN\"\n" +
                "}";

        Response response = given()
                .header("token", Authorization.getToken(email, password))
                .body(jsonBodyForPatch).log().all()
                .when()
                .patch(endPointUpdateWithPatch + "/" + experienceId)
                .prettyPeek();

        Assert.assertEquals(response.statusCode(),200);
    }


    @Test(priority = 4)
    public void deleteExperience(){
        Response response = given()
                .header("token", Authorization.getToken(email, password)).log().all()
             //   .pathParam("id",experienceId)
                .when()
               // .delete(endPointDelete+"/{id}")
                .delete(endPointDelete + "/" + experienceId)
                .prettyPeek();

        Assert.assertEquals(response.statusCode(),200);
    }

}
