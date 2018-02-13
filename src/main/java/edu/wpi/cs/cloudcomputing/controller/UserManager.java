package edu.wpi.cs.cloudcomputing.controller;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClient;
import com.amazonaws.services.cognitoidp.model.*;

import edu.wpi.cs.cloudcomputing.messages.UserLoginMessage;
import edu.wpi.cs.cloudcomputing.messages.UserRegisterMessage;

public class UserManager {

	protected AWSCognitoIdentityProviderClient cognitoClient;
	
	@SuppressWarnings("deprecation")
	public UserManager() {
		this.cognitoClient = new AWSCognitoIdentityProviderClient();
	}
	
	public String register(UserRegisterMessage registerMessage) {
		
		String username = registerMessage.getUsername();
		String password = registerMessage.getPassword();
		String emailAddress = registerMessage.getEmail();
		String responseMessage = "";
        try
        {       	
        	AdminCreateUserRequest cognitoRequest = new AdminCreateUserRequest()
                    .withUserPoolId("us-east-1_0RyMdDKts")
                    .withUsername(emailAddress)
                    .withUserAttributes(
                            new AttributeType()
                                .withName("email")
                                .withValue(emailAddress),
                            new AttributeType()
                                .withName("email_verified")
                                .withValue("true"))
                    .withDesiredDeliveryMediums(DeliveryMediumType.EMAIL)
                    .withForceAliasCreation(Boolean.FALSE);

            cognitoClient.adminCreateUser(cognitoRequest);
            responseMessage = "success";
        }
        catch (UsernameExistsException ex)
        {
        	responseMessage = "user already exists";
        	System.out.println(responseMessage);
        }
        catch (TooManyRequestsException ex)
        {
        	responseMessage = "caught TooManyRequestsException, delaying then retrying";
        	System.out.println(responseMessage);
        }
        catch (Exception e) {
        	responseMessage = "Unexpected error"; 
        	System.out.println(e.getMessage());
		}
		
		return responseMessage;
	}
	
	public String login(UserLoginMessage loginMessage) {
		return "";
	}
	
	public String verify() {
		return "";
	}
}
