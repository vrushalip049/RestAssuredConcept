package qa.Deserialization;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.response.Response;


/*Deserialization is the process of converting JSON responses into classes. By doing this we can convert JSON strings into strongly types strings that are less error-prone.


For deserializing the responses,  we create a separate class that has the same variables that are present in the JSON response like StatusCode and Message.

In the code, we can use this class object to read the JSON response as shown in the example above.

Deserialization of multiple responses like success and failure depends on the status code. 

For this, we need two POJO classes one each for success and failure. 

Depending on the value of the Status code we can call instances of the appropriate class and deserialize the response.


*/


public class ProductAPIWithTest_POJO {
	@Test
	public void getProductsTest_With_POJO_Desierlization() {

		RestAssured.baseURI = "https://fakestoreapi.com";

		Response response = given().when().get("/products");

		response.prettyPrint();

		// De-serialization: response json ---> POJO (Product)
		
		ObjectMapper mapper=new ObjectMapper();
		
		try {
			
			Product[] product=mapper.readValue(response.getBody().asString(), Product[].class);
			
			for (Product p:product)
			{
			System.out.println("p.getId()-"+p.getId());	
			System.out.println("p.getTitle()-"+p.getTitle());	
			System.out.println("p.getCategory()-"+p.getCategory());	
			System.out.println("p.getPrice()-"+p.getPrice());	
			System.out.println("p.getRate()-"+p.getRating().getRate());	
			System.out.println("p.getId()-"+p.getRating().getCount());	
			
			System.out.println("========================");	
			}
			
		}
		catch(JsonMappingException e)
		{
			e.printStackTrace();
		}
		catch(JsonProcessingException e)
		{
			e.printStackTrace();
		}
		
		
	}
}
