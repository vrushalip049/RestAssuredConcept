package qa.GetAPI;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;
import io.restassured.specification.RequestSpecification;

public class GetNonBDDPattern {
	// without BDD
	@Test
	public void getProductsTest_1() {

		RestAssured.baseURI = "https://thinking-tester-contact-list.herokuapp.com";

		RequestSpecification request = RestAssured.given();
		request.header("Authorization",
				"Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2N2FjMTM3ZjNlMDZiMTAwMTNmZThiNTciLCJpYXQiOjE3MzkzMzE0ODF9.SipmWQoZhBnqzvFLr2Dt05DvqaKyV176T9jEOMyiOO8");
		Response response = request.get("/contacts");

		int statusCode = response.statusCode();
		System.out.println("statusCode->" + statusCode);

		Assert.assertEquals(statusCode, 200);

		String statusLine = response.statusLine();
		System.out.println("statusLine->" + statusLine);

		Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");

		String resBody = response.prettyPrint();
		System.out.println(resBody);

	}
}
