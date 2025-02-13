package qa.SchemaValidation;
import static io.restassured.RestAssured.given;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static org.hamcrest.Matchers.equalTo;

import java.io.File;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class SchemaValidationTest {
	@Test
	public void addUserSchemaTest() {
		RestAssured.baseURI = "https://gorest.co.in";
		//1. add user - POST
		given().log().all()
			.contentType(ContentType.JSON)
			.body(new File(".\\src\\main\\java\\qa\\SchemaValidation\\adduser.json"))
			.header("Authorization", "Bearer 5637cb7b5cd298ac786fd59b9f004d741f0af10cf59372288638f00ebea47bcc").
		when()
			.post("/public/v2/users/").
		then().log().all()
			.assertThat()
				.statusCode(201)
				.body(matchesJsonSchemaInClasspath("C:\\Users\\Admin\\2025_eclipse-Automationworkspace\\RestAssuredConcept\\src\\main\\java\\qa\\SchemaValidation\\createuserschema.json"));
	}
	/*
	 * java.lang.IllegalArgumentException: Schema to use cannot be null at
	 * io.restassured.module.jsv.JsonSchemaValidator.validateSchemaIsNotNull(
	 * JsonSchemaValidator.java:270)
	 */

	@Test
	public void getUserSchemaTest() {
		RestAssured.baseURI = "https://gorest.co.in";
		
		//1. add user - POST
		given().log().all()
			.contentType(ContentType.JSON)
			.header("Authorization", "Bearer 5637cb7b5cd298ac786fd59b9f004d741f0af10cf59372288638f00ebea47bcc").
		when()
			.get("/public/v2/users/").
		then().log().all()
			.assertThat()
				.statusCode(200)
				.body(matchesJsonSchemaInClasspath("getuserschema.json"));
	}
}
