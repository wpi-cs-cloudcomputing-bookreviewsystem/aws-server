package edu.wpi.cs.cloudcomputing.utils;

public class Common {
    public static final String access_key = "AKIAIPDYTGIEF25CQ3EQ";
    public static final String secret_key = "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";
    public static final String cognito_pool_id = "us-east-1_0RyMdDKts";
    public static final String cognito_client_id = "m8rrod98dvdpvue0r8uhl8vo0";

    public final static String NOT_LOGGED_IN = "NOT LOGGED IN";
    public final static String LOGGED_IN = "LOGGED IN";
    public final static String INVALID_REQUEST = "INVALID REQUEST";
    public final static String NO_SUCH_USER = "NO SUCH USER";
    public final static String USER_ALREADY_EXISTS = "USER ALREADY EXISTS";
    public final static String USER_CREATED = "USER CREATED";

    public final static String SUCCESS = "SUCCESS";
    public final static String FAILURE = "FAILURE";
    public final static String BAD_REQUEST = "BAD REQUEST";

    //message status
    public final static String READ = "READ";
    public final static String IGNORE = "IGNORE";
    public final static String UNOPEN = "UNOPEN";

    //message type
    public final static String FRIENDSHIP = "Friendship";
    public final static String MESSAGE = "Message";
    public final static String RECOMMANDATION = "Recommendation";
    public final static String ADD_FRIEND_REQUEST = "ADD_FRIEND_REQUEST";
    public final static String ADD_FRIEND_ACCEPT_RESPONSE = "ADD_FRIEND_ACCEPT_RESPONSE";
    public final static String ADD_FRIEND_REJECT_RESPONSE = "ADD_FRIEND_REJECT_RESPONSE";

    //message content
    public final static String ADD_FREIEND_MESSAGE = "Hello, I want to be your friend!";
    public final static String ADD_FREIEND_ACCEPT_MESSAGE = "Hello, your add friend request has been accepted.";
    public final static String ADD_FREIEND_REJECT_MESSAGE = "Sorry, your add friend request has been rejected.";

    //message title
    public final static String RECOMMANDATION_BOOK = "You must to read this book!";



    // database
    public final static String jdbcTag = "jdbc:mysql://";
    public final static String rdsMySqlDatabaseUrl = "book-review-final-instance.crb32rqtyjdg.us-east-2.rds.amazonaws.com";
    public final static String rdsMySqlDatabasePort = "3306";
    public final static String dbName = "BookReviewDB";
    public final static String multiQuerirs = "?allowMultiQueries=true";
    public final static String dbUsername = "admin";
    public final static String dbPassword = "Bookreviewadmin";

}
