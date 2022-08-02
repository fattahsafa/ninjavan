package co.ninja.qa.tests.api;

import co.ninja.qa.api.Order;
import co.ninja.qa.api.beans.AuthenticationResponse;
import com.aventstack.extentreports.Status;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class CreateOrderTests extends BaseTest{

    private static final Logger logger = Logger.getLogger(CreateOrderTests.class);

    @Parameters({"clientID", "clientSecret"})
    @Test(suiteName = "Functional", groups = "Positive Flows", enabled = true)
    private void testCreateOrder(String clientID, String clientSecret)
    {
        int stepNumber = 0;
        String step;
        Response response;
        String token;
        AuthenticationResponse authenticationResponse;
        Order order;

        /* read order from some place */
        order = new Order();

        logger.info("Test: Create Order");
        reportUtils.addTest("Test: Print Airway Bill");

        /* step 1: Authenticate */
        stepNumber++;
        step = "step"+stepNumber+": authenticate using client ID and secret";
        logger.info(step);
        reportUtils.addTestLog(Status.INFO, step);
        response = ninjaAPI.authenticate(clientID, clientSecret);
        response.then().statusCode(200);
        authenticationResponse = response.then().extract().as(AuthenticationResponse.class);
        token = authenticationResponse.getAccess_token();

        /* step 2: Create order */
        stepNumber++;
        step = "step"+stepNumber+": create order";
        logger.info(step);
        reportUtils.addTestLog(Status.INFO, step);
        response = ninjaAPI.createOrder(token, order);
        response.then().statusCode(200);
    }
}
