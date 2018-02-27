package edu.wpi.cs.cloudcomputing.database;

import edu.wpi.cs.cloudcomputing.utils.Common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by tonggezhu on 2/25/18.
 */
public class DatabaseUtil {

    Connection conn;

    protected void initDBConnection() throws Exception {
        try {
            System.out.println("start connecting......");
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(
                    Common.jdbcTag + Common.rdsMySqlDatabaseUrl + ":" + Common.rdsMySqlDatabasePort + "/" + Common.dbName,
                    Common.dbUsername,
                    Common.dbPassword
            );
            System.out.println("Database has been connected successfully.");
        } catch (Exception ex) {
            throw new Exception("Failed in database connection");
        }
    }

    public String currentDate() {
        Date date = new Date();

        SimpleDateFormat ft =
                new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String dateTO = "STR_TO_DATE('" + ft.format(date) + "', '%Y-%m-%d %H:%i:%s')";

        return dateTO;
    }

}
