package qa.JaywayJsonPath;

import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

import java.util.List;
import java.util.Map;

public class JsonPathTest {
	@Test
	public void getProductAPITest_JsonPath() {
		RestAssured.baseURI = "https://fakestoreapi.com";

		Response response = given().when().get("/products");

		String jsonResponse = response.asString();
		System.out.println(jsonResponse);

		/*
		 * [{"id":1, "title":"Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops",
		 * "price":109.95,
		 * "description":"Your perfect pack for everyday use and walks in the forest. Stash your laptop (up to 15 inches) in the padded sleeve, your everyday"
		 * ,"category":"men's clothing","image":
		 * "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg", "rating":
		 * {"rate":3.9,"count":120} }
		 */

		ReadContext ctx = JsonPath.parse(jsonResponse);

		List<Number> prices = ctx.read("$[?(@.price>50)].price");
		System.out.println(prices);

		List<Integer> id = ctx.read("$[?(@.price>50)].id");
		System.out.println(id);

		List<Double> rates = ctx.read("$[?(@.price>50)].rating.rate");
		System.out.println(rates);

		List<Integer> counts = ctx.read("$[?(@.price>50)].rating.count");
		System.out.println(counts);

		for (int i = 0; i < id.size(); i++) {
			System.out.println(id.get(i) + " " + prices.get(i));
		}
		// $[?(@.rating.rate < 3)].id
	}

	@Test
	public void productApiTest_ConditionalExamples_WithTwoAttributes() {
		RestAssured.baseURI = "https://fakestoreapi.com";

		Response response = given().when().get("/products");

		String jsonResponse = response.asString();
		System.out.println(jsonResponse);
		ReadContext ctx = JsonPath.parse(jsonResponse);

		List<Map<String, Object>> jewleryList = ctx.read("$[?(@.category =='jewelery')].['title','price']");
		System.out.println(jewleryList);
		System.out.println(jewleryList.size());

		for (Map<String, Object> e : jewleryList) {
			String title = (String) e.get("title");
			Number price = (Number) e.get("price");
			System.out.println("title-" + title);
			System.out.println("price-" + price);
			System.out.println("------------");
		}

	}

	@Test
	public void productApiTest_ConditionalExamples_WithThreeAttributes() {
		RestAssured.baseURI = "https://fakestoreapi.com";

		Response response = given().when().get("/products");

		String jsonResponse = response.asString();
		System.out.println(jsonResponse);

		ReadContext ctx = JsonPath.parse(jsonResponse);

		// single attribute:
		// $[?(@.rating.rate < 3)].id

		// with two attributes:
		List<Map<String, Object>> jewleryList = ctx.read("$[?(@.category == 'jewelery')].['title','price','id']");
		System.out.println(jewleryList);
		System.out.println(jewleryList.size());

		for (Map<String, Object> product : jewleryList) {
			String title = (String) product.get("title");
			Number price = (Number) product.get("price");
			Integer id = (Integer) product.get("id");

			System.out.println("title:" + title);
			System.out.println("price:" + price);
			System.out.println("id:" + id);
			System.out.println("-----------");
		}
	}
}

//single attribute: List<?>
// multiple attributes: List<Map<String, Object>>
