package edu.wpi.cs.cloudcomputing.controller;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClient;
import com.amazonaws.services.cognitoidp.model.*;

import edu.wpi.cs.cloudcomputing.messages.AWSLoginResponseMessage;
import edu.wpi.cs.cloudcomputing.messages.UserLoginMessage;
import edu.wpi.cs.cloudcomputing.messages.UserRegisterMessage;
import edu.wpi.cs.cloudcomputing.utils.Common;

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

    public AWSLoginResponseMessage login(UserLoginMessage loginMessage) {
        String password = loginMessage.getPassword();
        String emailAddress = loginMessage.getEmail();
        AWSLoginResponseMessage responseMessage = new AWSLoginResponseMessage();

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
            
            if (authResponse.getChallengeName() == null) {
            	String accessToken = authResponse.getAuthenticationResult().getAccessToken();
            	String refreshToken = authResponse.getAuthenticationResult().getRefreshToken();
            	String idToken = authResponse.getAuthenticationResult().getIdToken();
            	System.out.println(idToken);
            	responseMessage.setStatus(LOGGED_IN);
            	responseMessage.setAccessToken(accessToken);
            	responseMessage.setRefreshToken(refreshToken);
            	responseMessage.setIdToken(idToken);
            }
            
        } catch (UserNotFoundException ex) {
            responseMessage.setStatus(NO_SUCH_USER);;
            System.out.println(responseMessage);
        } catch (NotAuthorizedException ex) {
        	responseMessage.setStatus(NO_SUCH_USER);
            System.out.println(responseMessage);
        } catch (TooManyRequestsException ex) {
        	responseMessage.setStatus("caught TooManyRequestsException, delaying then retrying");
            System.out.println(responseMessage);
        } catch (Exception e) {
        	responseMessage.setStatus("Unexpected error");
            System.out.println(e.getMessage());
		}
        return responseMessage;
    }
    
    
//    We dont need the following verify function and attemptRefreshToken function because 
//    if these two are used for authentication in each lambda function, each lambda function has to be in POST method 
//    Instead, we put the iDtoken in http headers, which can be validated in API gateway. In this case, users that have reach lambda function are already validated.
    
    /**
    public AWSLoginResponseMessage verify() {
        String accessToken = "";
        String refreshToken = "";
        AWSLoginResponseMessage responseMessage = new AWSLoginResponseMessage();

        try {
            GetUserRequest authRequest = new GetUserRequest().withAccessToken(accessToken);
            GetUserResult authResponse = cognitoClient.getUser(authRequest);

            System.out.printf("successful validation for %s", authResponse.getUsername());
            //tokenCache.addToken(accessToken);
            responseMessage.setStatus(LOGGED_IN);
        } catch (NotAuthorizedException ex) {
            if (ex.getErrorMessage().equals("Access Token has expired")) {
                return attemptRefreshToken(refreshToken);
            } else {
                System.out.printf("exception during validation: %s", ex.getMessage());
                responseMessage.setStatus(NOT_LOGGED_IN);
            }
        } catch (TooManyRequestsException ex) {
        	responseMessage.setStatus("caught TooManyRequestsException, delaying then retrying");
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
        	responseMessage.setStatus("unexpected error");
            System.out.println(ex.getMessage());
		}
        return responseMessage;
    }

    public AWSLoginResponseMessage attemptRefreshToken(String refreshToken) {
    	AWSLoginResponseMessage responseMessage = new AWSLoginResponseMessage();

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
                
                String newAccessToken = refreshResponse.getAuthenticationResult().getAccessToken();
                String newRefreshToken = refreshResponse.getAuthenticationResult().getRefreshToken();
                responseMessage.setStatus(LOGGED_IN);
                responseMessage.setAccessToken(newAccessToken);
                responseMessage.setRefreshToken(newRefreshToken);
            }
            else
            {
                System.out.printf("unexpected challenge when refreshing token: %s", refreshResponse.getChallengeName());
                responseMessage.setStatus(NOT_LOGGED_IN);
            }
        } catch (TooManyRequestsException ex) {
            responseMessage.setStatus("caught TooManyRequestsException, delaying then retrying");
            System.out.println(ex.getMessage());
        }

        return responseMessage;
    }
    */
}
