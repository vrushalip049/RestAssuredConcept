package qa.GetAPI;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;

public class ContactsAPIsTest {
	/*
	 * POST:https://thinking-tester-contact-list.herokuapp.com/users/login {"email":
	 * "vrushalip049@gmail.com", "password": "Vrush@123"}
	 */

	@BeforeMethod
	public void setup() {
		RestAssured.baseURI = "https://thinking-tester-contact-list.herokuapp.com";

	}

	@Test
	public void getContactAPITest() {
		given().log().all().header("Authorization",
				"Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2N2FjMTM3ZjNlMDZiMTAwMTNmZThiNTciLCJpYXQiOjE3MzkzMzE0ODF9.SipmWQoZhBnqzvFLr2Dt05DvqaKyV176T9jEOMyiOO8")
				.when().log().all().get("/contacts").then().log().all().assertThat().statusCode(200);

	}

	@Test
	public void getContactsAPITest_WithInvalidToken() {

		given().log().all().header("Authorization", "Bearer 11111").when().log().all().get("/contacts").then().log()
				.all().assertThat().statusCode(401);

	}
}
