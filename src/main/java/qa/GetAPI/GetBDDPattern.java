package qa.GetAPI;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class GetBDDPattern {

	// GorestAPI application

	@Test
	public static void getUser() {

		RestAssured.baseURI = "https://gorest.co.in";
		given().log().all().when().log().all().get("/public/v2/users/").then().statusCode(200)
				.statusLine("HTTP/1.1 200 OK");

	}

	@Test
	public static void getOneUser() {

		RestAssured.baseURI = "https://gorest.co.in";
		given().log().all().when().log().all().get("/public/v2/users/7691428").then().statusCode(200)
				.statusLine("HTTP/1.1 200 OK");

	}

	@Test
	public static void getUserwithResponse() {

		RestAssured.baseURI = "https://gorest.co.in";
		Response response = given().log().all().when().log().all().get("/public/v2/users/");
		String getResponse = response.getBody().asPrettyString().toString();
		System.out.println("getResponse=>" + getResponse);

	}
	@Test
	public static void getUserwith_QueryParm() {

		RestAssured.baseURI = "https://gorest.co.in";
		Response response = given().log().all().queryParam("name", "Niro Banerjee").when().log().all()
				.get("/public/v2/users/");
		String getResponse = response.getBody().asPrettyString().toString();
		System.out.println("getResponse=>" + getResponse);

	}
	
	@Test
	public static void getUserwith_MultipleQueryParm() {

		RestAssured.baseURI = "https://gorest.co.in";
		Response response = given().log().all().
				queryParam("name", "Niro Banerjee").
				queryParam("status", "inactive").
				when().log().all()
				.get("/public/v2/users/");
		String getResponse = response.getBody().asPrettyString().toString();
		System.out.println("getResponse=>" + getResponse);

	}
	
	@Test
	public static void getUserwith_MapQueryParm() {

		Map<String,String> map=new HashMap<String, String>();
		map.put("name", "Niro Banerjee");
		map.put("status", "inactive");
		
		RestAssured.baseURI = "https://gorest.co.in";
		Response response = given().log().all()
				.queryParams(map)
				.when().log().all()
				.get("/public/v2/users/");
		String getResponse = response.getBody().asPrettyString().toString();
		System.out.println("getResponse=>" + getResponse);

	}
}
