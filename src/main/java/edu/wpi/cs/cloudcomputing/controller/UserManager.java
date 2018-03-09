package edu.wpi.cs.cloudcomputing.controller;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClient;
import com.amazonaws.services.cognitoidp.model.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.wpi.cs.cloudcomputing.database.UserDAO;
import edu.wpi.cs.cloudcomputing.database.UserNetworkDAO;
import edu.wpi.cs.cloudcomputing.messages.AWSLoginResponseMessage;
import edu.wpi.cs.cloudcomputing.messages.UserLoginMessage;
import edu.wpi.cs.cloudcomputing.messages.UserRegisterMessage;
import edu.wpi.cs.cloudcomputing.model.User;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static edu.wpi.cs.cloudcomputing.utils.Common.*;

public class UserManager {

    protected AWSCognitoIdentityProviderClient cognitoClient;
    private UserDAO userDAO;
    private UserNetworkDAO userNetworkDAO;
    private Gson gson = new Gson();

    @SuppressWarnings("deprecation")
    public UserManager() {
        BasicAWSCredentials credentials = new BasicAWSCredentials(access_key, secret_key);
        this.cognitoClient = new AWSCognitoIdentityProviderClient(credentials);
        this.userDAO = new UserDAO();
    }

    public String register(UserRegisterMessage registerMessage) {

    	String username = registerMessage.getUsername();
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
            registerUserToApp(registerMessage);

        } catch (UsernameExistsException ex) {
            responseMessage = USER_ALREADY_EXISTS;
            System.out.println(responseMessage);
        } catch (TooManyRequestsException ex) {
            responseMessage = "caught TooManyRequestsException, delaying then retrying";
            System.out.println(responseMessage);
        } catch (Exception e) {
            responseMessage = "other error";
            System.out.println(e.getMessage());
        }

        return responseMessage;
    }
    
    public String registerUserToApp(UserRegisterMessage registerMessage) {
    	String username = registerMessage.getUsername();
        String emailAddress = registerMessage.getEmail();
        String responseMessage = "";

        try {
            this.userDAO.saveUser(new User(username, emailAddress));              
            responseMessage = USER_CREATED;
        } catch (Exception e) {
            responseMessage = e.getMessage();
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
            	System.out.println("");
            	System.out.println(idToken);
            	System.out.println("");
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
    
    public String getUserProfile(String myEmail, String requestEmail) throws Exception {
    	String res = "";
    	User user = userDAO.getUser(requestEmail);
        Gson gson = new GsonBuilder().create();
        String userInfo = gson.toJson(user, User.class);
    	if(myEmail.equals(requestEmail)){
            return userInfo;
        }else{
    	    userNetworkDAO = new UserNetworkDAO();
    	    String friendship = userNetworkDAO.checkFriendship(myEmail, requestEmail);
            JSONObject object=new JSONObject(user);
            object.put("isfriend",friendship);
            return object.toString();
        }
    }

    public Boolean addFriend(String fromEmail, String toEmail){
        userNetworkDAO = new UserNetworkDAO();
        try {
            userNetworkDAO.addFriend(fromEmail, toEmail);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public Boolean acceptAddFriend(String fromEmail, String toEmail){
        userNetworkDAO = new UserNetworkDAO();
        try {
            userNetworkDAO.acceptAddFriend(fromEmail, toEmail);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public Boolean rejectAddFriend(String fromEmail, String toEmail){
        userNetworkDAO = new UserNetworkDAO();
        try {
            userNetworkDAO.rejectAddFriend(fromEmail, toEmail);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

}
