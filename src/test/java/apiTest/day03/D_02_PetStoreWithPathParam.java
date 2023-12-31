package apiTest.day03;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.*;
import static io.restassured.RestAssured.baseURI;

public class D_02_PetStoreWithPathParam {

    @BeforeClass
    public void beforeClass() {
        baseURI = "https://petstore.swagger.io/v2";
    }


    @Test
    public void petStoreFindPetByID1() {
        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .when()
                .get("/pet/566");
        response.prettyPrint();

        assertEquals(response.statusCode(), 200);
    }


    @Test
    public void petStoreFindPetByID2() {
        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .and()
                .pathParam("petId", 10)
                .when()
                .get("/pet/{petId}");
        response.prettyPrint();
        assertEquals(response.statusCode(),200);

    }



    @Test
    public void petStoreFindPetByID3() {
        int petID=566;
        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .and()
                .pathParam("id", petID)
                .when()
                .get("/pet/{id}");
        response.prettyPrint();
        assertEquals(response.statusCode(),200);

    }



}