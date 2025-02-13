package qa.GetAPI;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.equalTo;

import java.util.HashMap;
import java.util.List;

import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.given;

public class FetchResponseDataConcept {
	@Test
	public void getContactsAPITest_WithInvalidToken() {
		RestAssured.baseURI = "https://thinking-tester-contact-list.herokuapp.com";
		given().log().all().header("Authorization", "ABC").when().log().all().get("/contacts").then().log().all()
				.assertThat().statusCode(401).and().statusLine("HTTP/1.1 401 Unauthorized").and()
				.body("error", equalTo("Please authenticate."));

	}

	@Test
	public void getContactsAPITest_WithInvalidToken_WithExtract() {
		RestAssured.baseURI = "https://thinking-tester-contact-list.herokuapp.com";
		String errorMessage = given().log().all().header("Authorization", "ABC").when().log().all().get("/contacts")
				.then().log().all().assertThat().statusCode(401).and().statusLine("HTTP/1.1 401 Unauthorized").and()
				.extract().path("error");
		System.out.println("errorMessage->" + errorMessage);
		Assert.assertEquals(errorMessage, "Please authenticate.");
	}

	@Test
	public void getSingleUser_FetchSingleUSerData() {

		RestAssured.baseURI = "https://gorest.co.in";

		Response response = given()
				.header("Authorization", "Bearer 5637cb7b5cd298ac786fd59b9f004d741f0af10cf59372288638f00ebea47bcc")
				.when().get("/public/v2/users/7691428");
		System.out.println("response statuscode->" + response.statusCode());
		System.out.println("response statusLine->" + response.statusLine());
		System.out.println("response prettyPrint->" + response.prettyPrint());

		JsonPath js = response.jsonPath();
		int id = js.getInt("id");
		System.out.println("userid=" + id);

		String userName = js.getString("name");
		System.out.println("userid=" + userName);

		String status = js.getString("status");
		System.out.println("status=" + status);

	}

	@Test
	public void getSingleUser_FetchFullUSerData() {

		RestAssured.baseURI = "https://gorest.co.in";

		Response response = given()
				.header("Authorization", "Bearer 5637cb7b5cd298ac786fd59b9f004d741f0af10cf59372288638f00ebea47bcc")
				.when().get("/public/v2/users");

		JsonPath js = response.jsonPath();
		List<Integer> ids = js.getList("id");
		System.out.println(ids);

		List<String> names = js.getList("name");
		System.out.println(names);

		for (int e : ids) {
			System.out.println(e);
		}

	}

	@Test
	public void getProduct_FetchNestedData() {

		RestAssured.baseURI = "https://fakestoreapi.com";

		Response response = given().when().get("/products");

		System.out.println("response.statusCode()->" + response.statusCode());
		System.out.println("response.statusLine()->" + response.statusLine());
		System.out.println("response.prettyPrint()->" + response.prettyPrint());

		JsonPath js = response.jsonPath();

		List<Integer> ids = js.getList("id");
		System.out.println(ids);

		List<String> title = js.getList("title");
		System.out.println(title);

		List<Object> price = js.getList("price");
		System.out.println(price);

		List<Object> rate = js.getList("rating.rate");
		System.out.println(rate);

		List<Integer> count = js.getList("rating.count");
		System.out.println(count);

		for (int i = 0; i < ids.size(); i++) {
			int id = ids.get(i);
			String titles = title.get(i);
			Object rates = rate.get(i);
			Object counts = count.get(i);

			System.out.println("ID: " + id + " titles : " + titles + " rate : " + rates + " count : " + counts);
		}
		Assert.assertTrue(rate.contains(4.7f));
	}

	@Test
	public void getPathParmExample() {
		
		HashMap<String, String> pathParams = new HashMap<>();
        pathParams.put("firstPath", "api");
        pathParams.put("secondPath", "users");
        RestAssured
        .given()
            .baseUri("https://reqres.in")
            .pathParams(pathParams)  
            .queryParam("page", 2)    
        .when().log().all()
            .get("/{firstPath}/{secondPath}") 
        .then()
            .statusCode(200)
            .log().all();  
	}

}
