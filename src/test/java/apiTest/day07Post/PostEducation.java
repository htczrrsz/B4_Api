package apiTest.day07Post;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class PostEducation {
    @BeforeClass
    public void beforeClass() {
        baseURI = "https://www.krafttechexlab.com/sw/api/v1";
    }

    @Test
    public void postNewUser4_withEducation(){
        NewUserInfo newUserInfo= new NewUserInfo("deneme6","deneme6@kraftexlab.com","Test1234","About me","5");

        Response response = given().accept(ContentType.JSON) // hey api send me body as json format
                .and()
                .contentType(ContentType.JSON) // hey api I am sending json body
                .and()
                .body(newUserInfo) // Serialization
                .when()
                .post("/allusers/register");

        assertEquals(response.statusCode(),200);
        response.prettyPrint();

        String user_token= response.path("token");

        String educationBody="{\n" +
                "  \"school\": \"School12\",\n" +
                "  \"degree\": \"Degree12\",\n" +
                "  \"study\": \"Study12\",\n" +
                "  \"fromdate\": \"2019-03-03\",\n" +
                "  \"todate\": \"2020-03-03\",\n" +
                "  \"current\": \"false\",\n" +
                "  \"description\": \"Description\"\n" +
                "}";

       response= given().accept(ContentType.JSON)
                .and()
                .contentType(ContentType.JSON)
                .and()
                .body(educationBody)
                .header("token",user_token)
                .when()
                .post("/education/add");

        assertEquals(response.statusCode(),200);
        response.prettyPrint();


    }

    @Test
    public void postNewUserAndVerify(){

        String name="deneme3";
        String email="deneme3@kraftexlab.com";
        String password="Test1234";
        String about="SDET";
        String terms="4";

        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("name",name);
        requestMap.put("email",email);
        requestMap.put("password",password);
        requestMap.put("about",about);
        requestMap.put("terms",terms);


        Response response = given().accept(ContentType.JSON) // hey api send me body as json format
                .and()
                .contentType(ContentType.JSON) // hey api I am sending json body
                .and()
                .body(requestMap)
                .when()
                .post("/allusers/register");

        assertEquals(response.statusCode(),200);
        response.prettyPrint();

        String user_token= response.path("token");
        int userID=response.path("id");

        Map<String,Object> educationBody= new HashMap<>();
        educationBody.put("school","KraftTech");
        educationBody.put("degree","SDET");
        educationBody.put("fromdate","2019-03-03");
        educationBody.put("todate","2020-03-03");
        educationBody.put("current","false");
        educationBody.put("description","Description");


        response= given().accept(ContentType.JSON)
                .and()
                .contentType(ContentType.JSON)
                .and()
                .body(educationBody)
                .header("token",user_token)
                .when()
                .post("/education/add");

        assertEquals(response.statusCode(),200);
        response.prettyPrint();

        String user_school= response.path("school");

       /** {                         --> bu yapida geldi.
            "id": 1655,
                "userid": 1999,
                "school": "School12",
                "degree": "Degree12",
                "study": "Study12",
                "description": "Description",
                "fromdate": "2019-03-03",
                "todate": "2020-03-03"
        } **/

        // verify body
        int edu_id = response.path("id");

         response = given().accept(ContentType.JSON)
                .and()
                .header("token", user_token)
                .pathParam("id", edu_id)
                .when()
                .get("education/getbyid/{id}");

         response.prettyPrint();

         // verify with path
        assertEquals((int) response.path("userid"),userID);
        assertEquals(response.path("school"),user_school);


        // verify using hamcrest matcher
        given().accept(ContentType.JSON)
                .and()
                .header("token",user_token)
                .and()
                .pathParam("id",edu_id)
                .when()
                .get("education/getbyid/{id}")
                .then()
                .assertThat()
                .body("school",equalTo(user_school),
                        "userid",equalTo(userID),
                        "degree",equalTo("SDET")).log().all();

    }

}
