package edu.wpi.cs.cloudcomputing.controller;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClient;
import com.amazonaws.services.cognitoidp.model.*;

import edu.wpi.cs.cloudcomputing.messages.UserLoginMessage;
import edu.wpi.cs.cloudcomputing.messages.UserRegisterMessage;

import java.util.HashMap;
import java.util.Map;

import static edu.wpi.cs.cloudcomputing.utils.Common.*;

public class UserManager {

    protected AWSCognitoIdentityProviderClient cognitoClient;

    @SuppressWarnings("deprecation")
    public UserManager() {
        BasicAWSCredentials credentials = new BasicAWSCredentials(access_key, secret_key);
        this.cognitoClient = new AWSCognitoIdentityProviderClient(credentials);
    }

    public String register(UserRegisterMessage registerMessage) {

        String password = registerMessage.getPassword();
        String emailAddress = registerMessage.getEmail();
        String responseMessage = "";

        try {
            SignUpRequest cognitoRequest = new SignUpRequest()
                    .withClientId(cognito_client_id)
                    .withUsername(emailAddress)
                    .withPassword(password)
                    .withUserAttributes(
                            new AttributeType()
                                    .withName("email")
                                    .withValue(emailAddress));
            cognitoClient.signUp(cognitoRequest);
            responseMessage = USER_CREATED;
        } catch (UsernameExistsException ex) {
            responseMessage = USER_ALREADY_EXISTS;
            System.out.println(responseMessage);
        } catch (TooManyRequestsException ex) {
            responseMessage = "caught TooManyRequestsException, delaying then retrying";
            System.out.println(responseMessage);
        } catch (Exception e) {
            responseMessage = "Unexpected error";
            System.out.println(e.getMessage());
        }

        return responseMessage;
    }

    public String login(UserLoginMessage loginMessage) {
        String password = loginMessage.getPassword();
        String emailAddress = loginMessage.getEmail();
        String responseMessage = "";

        try {
            Map<String, String> authParams = new HashMap<String, String>();
            authParams.put("USERNAME", emailAddress);
            authParams.put("PASSWORD", password);
            AdminInitiateAuthRequest authRequest = new AdminInitiateAuthRequest()
                    .withAuthFlow(AuthFlowType.ADMIN_NO_SRP_AUTH)
                    .withAuthParameters(authParams)
                    .withClientId(cognito_client_id)
                    .withUserPoolId(cognito_pool_id);

            AdminInitiateAuthResult authResponse = cognitoClient.adminInitiateAuth(authRequest);

            if (authResponse.getChallengeName().length() == 0) {

            }
        } catch (UserNotFoundException ex) {
            responseMessage = NO_SUCH_USER;
            System.out.println(responseMessage);
        } catch (NotAuthorizedException ex) {
            responseMessage = NO_SUCH_USER;
            System.out.println(responseMessage);
        } catch (TooManyRequestsException ex) {
            responseMessage = "caught TooManyRequestsException, delaying then retrying";
            System.out.println(responseMessage);
        }
        return responseMessage;
    }

    public String verify() {
        String accessToken = "";
        String refreshToken = "";
        String responseMessage = "";

        try {
            GetUserRequest authRequest = new GetUserRequest().withAccessToken(accessToken);
            GetUserResult authResponse = cognitoClient.getUser(authRequest);

            System.out.printf("successful validation for %s", authResponse.getUsername());
            //tokenCache.addToken(accessToken);
            responseMessage = LOGGED_IN;
        } catch (NotAuthorizedException ex) {
            if (ex.getErrorMessage().equals("Access Token has expired")) {
                return attemptRefreshToken(refreshToken);
            } else {
                System.out.printf("exception during validation: %s", ex.getMessage());
                responseMessage = NOT_LOGGED_IN;
            }
        } catch (TooManyRequestsException ex) {
            responseMessage = "caught TooManyRequestsException, delaying then retrying";
            System.out.println(responseMessage);
        }
        return responseMessage;
    }

    public String attemptRefreshToken(String refreshToken) {
        String responseMessage = "";

        try
        {
            Map<String,String> authParams = new HashMap<String,String>();
            authParams.put("REFRESH_TOKEN", refreshToken);


            InitiateAuthRequest refreshRequest = new InitiateAuthRequest()
                    .withAuthFlow(AuthFlowType.REFRESH_TOKEN)
                    .withAuthParameters(authParams)
                    .withClientId(cognito_client_id);

            InitiateAuthResult refreshResponse = cognitoClient.initiateAuth(refreshRequest);

            if (refreshResponse.getChallengeName().length() == 0)
            {

                System.out.println("successfully refreshed token");
                //updateCredentialCookies(response, refreshResponse.getAuthenticationResult());
                responseMessage = LOGGED_IN;
            }
            else
            {
                System.out.printf("unexpected challenge when refreshing token: %s", refreshResponse.getChallengeName());
                responseMessage = NOT_LOGGED_IN;
            }
        } catch (TooManyRequestsException ex) {
            responseMessage = "caught TooManyRequestsException, delaying then retrying";
            System.out.println(responseMessage);
        }

        return responseMessage;
    }
}
