package qa.GetAPI;

import org.testng.annotations.Test;

import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
public class ProductAPIs {

	// BDD style test: given --> when --> then

	@Test
	public void getProductsTest_1() {
		RestAssured.baseURI = "https://fakestoreapi.com";
		given().log().all().get("/products").then().assertThat().statusCode(200);

	}
	
	
	@Test
	public void getProductsTest_2() {
		
		RestAssured.baseURI = "https://fakestoreapi.com";
		
		given().log().all()
			.get("/products")
				.then().log().all()
					.assertThat()
						.statusCode(200)
							.body("$.size()", equalTo(20));

}}
