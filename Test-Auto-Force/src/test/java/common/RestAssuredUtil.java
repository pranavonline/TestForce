package common;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import java.util.Map;

import org.junit.Assert;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class RestAssuredUtil {

	public static ValidatableResponse send_request(String requestType, String url, Map<String, Object> param) {
		switch (requestType.toLowerCase().trim()) {
		case "get":
			if (param == null || param.isEmpty())
				return given().when().get(url).then();
			else
				return given().params(param).when().get(url).then();

		case "put":
		case "update":
			return given().params(param).when().put(url).then();

		case "delete":
			return given().params(param).when().delete(url).then();

		case "post":
		case "create":
			return given().params(param).when().post(url).then();

		default:
			return null;
		}
	}
	
	public static  ValidatableResponse send_request_payload(String requestType, String url,JsonObject payload) {
		switch (requestType.toLowerCase().trim()) {
		case "post":
		case "create":
			return given().body(payload).when().post(url).then();

		default:
			return null;
		}
	}
	
	//// Validate the requests using below mentioned code

//	public static void main(String[] args) {
//
//		// Specify the base URL to the RESTful web service
//		RestAssured.baseURI = "https://TestAutoForce.com/BookStore/v1/Books";
//		// Get the RequestSpecification of the request to be sent to the server.
//		RequestSpecification httpRequest = RestAssured.given();
//		// specify the method type (GET) and the parameters if any.
//		// In this case the request does not take any parameters
//		Response response = httpRequest.request(Method.GET, "");
//		// Print the status and message body of the response received from the server
//		System.out.println("Status received => " + response.getStatusLine());
//		System.out.println("Response=>" + response.prettyPrint());
//		System.out.println("==================================");
//		RestAssuredUtil.send_request("GET", RestAssured.baseURI, null).log().body();
//		System.out.println("==================================");
//		String url = "https://TestAutoForce.com/BookStore/v1/Books/insert/";
//		String jsonString = "{\"name\":\"The Test Automation Force\","
//				+ "\"author\":\"Pranav Gupta\""
//				+ "}";
//		JsonObject payload =new Gson().fromJson(jsonString, JsonObject.class);
//		Assert.assertEquals(send_request_payload("POST", url , payload).extract().response().getStatusCode(),200);
//		send_request_payload("POST", url , payload)
//			.assertThat()
//			.statusCode(200)
//			.body("name", equalTo("The Test Automation Force"));
//	}
}
