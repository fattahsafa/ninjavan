package co.ninja.qa.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class NinjaAPI {

    private String apiEndpoint;

    public NinjaAPI(String apiEndpoint)
    {
        RestAssured.baseURI = apiEndpoint;
    }

    /**
     *
     * @param clientID
     * @param clientSecret
     * @return
     */
    public Response authenticate(String clientID, String clientSecret)
    {
        Response response;
        Map<String, Object> headerParameters;
        JSONObject body;
        String uri = "/oauth/access_token";


        /* Construct the head parameters */
        headerParameters = new HashMap<>();
        headerParameters.put("Cache-Control", "no-cache");
        headerParameters.put("Connection: keep-alive", "keep-alive");
        headerParameters.put("accept-encoding", "gzip, deflate");
        headerParameters.put("cache-control", "no-cache");

        /* Construct the body */
        body = new JSONObject();
        body.put("client_id", clientID);
        body.put("client_secret", clientSecret);

        response = given().contentType(ContentType.JSON)
                .headers(headerParameters).body(body)
                .post(uri);
        return response;
    }

    /**
     *
     * @param token
     * @param order
     * @return
     */
    public Response createOrder(String token, Order order)
    {

        String uri = "/order-create/orders";
        Response response;
        Map<String, Object> headerParameters;


        /* Construct the head parameters */
        headerParameters = new HashMap<>();
        headerParameters.put("Authorization", "Bearer "+token);

        response = given().contentType(ContentType.JSON).accept("*/*").headers(headerParameters).body(order)
                .post(uri);
        return response;
    }



}
