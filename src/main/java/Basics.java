import files.ReUsableMethods;
import files.payload;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Basics {

    public static void main(String[] args) {
        //validate if Add Place API is working as expected
        //Add Place -> Update Place with New Address -> Get Place to validate if New address is present in response

        //given - all input details
        //when - Submit the API - resource, http method
        //then - validate the response

        baseURI = "https://rahulshettyacademy.com";
        String response = given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
                .body(payload.AddPlace()).when().post("maps/api/place/add/json")
                .then().assertThat().statusCode(200).body("scope", equalTo("APP"))
                .header("server","Apache/2.4.18 (Ubuntu)").extract().response().asString();

        System.out.println(response);

        JsonPath js=new JsonPath(response);
        String placeId = js.getString("place_id");
        System.out.println(placeId);

        String newAddress = "70 winter walk street, UK";
        //Update Place
        given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
                .body("{\n" +
                        "\"place_id\":\""+placeId+"\",\n" +
                        "\"address\":\""+newAddress+"\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}\n")
                .when().put("maps/api/place/update/json")
                .then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));


        //Get Place
      String getPlaceResponse = given().log().all().queryParam("key","qaclick123")
                .queryParam("place_id",placeId)
                .when().get("maps/api/place/get/json")
                .then().assertThat().log().all().statusCode(200).extract().response().asString();

      JsonPath js1 = ReUsableMethods.rawToJson(getPlaceResponse);
      String ActualAddress = js1.getString("address");
      System.out.println(ActualAddress);

      //Cucumber-Junit,TestNG
        Assert.assertEquals(ActualAddress, newAddress);
    }
}