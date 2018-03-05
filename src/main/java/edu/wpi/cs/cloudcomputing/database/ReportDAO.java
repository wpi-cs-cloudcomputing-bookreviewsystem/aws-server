package edu.wpi.cs.cloudcomputing.database;

import edu.wpi.cs.cloudcomputing.model.Report;
import edu.wpi.cs.cloudcomputing.model.User;

import java.sql.Statement;

/**
 * Created by tonggezhu on 3/1/18.
 */
public class ReportDAO {
    DatabaseUtil databaseUtil;

    public ReportDAO() {
        this.databaseUtil = new DatabaseUtil();
    }

    public boolean addReport(Report report) throws Exception{
        if (databaseUtil.conn == null || databaseUtil.conn.isClosed()) {
            databaseUtil.initDBConnection();
        }
        try {
            Statement statement = databaseUtil.conn.createStatement();
            String query = "UPDATE Report SET report_process = 1 WHERE report_id='" + report.getReportId() +"';";
            statement.execute(query);
            return true;
        } catch (Exception e) {
            throw new Exception("Failed too insert report: " + e.getMessage());
        }
    }

    public boolean updateReport(Report report) throws Exception{
        if (databaseUtil.conn == null || databaseUtil.conn.isClosed()) {
            databaseUtil.initDBConnection();
        }
        Boolean res = false;
        try {
            Statement statement = databaseUtil.conn.createStatement();
            String query = insertReportQuery(report);
            res = statement.execute(query);
            return res;
        } catch (Exception e) {
            throw new Exception("Failed too insert report: " + e.getMessage());
        }
    }



    private String insertReportQuery(Report report){
        String Date = databaseUtil.currentDate();
        String columns = "INSERT INTO Report (report_id, report_from_user, report_to_user, report_reason, report_datetime, report_process)";
        String values = " values ('" + report.getReportId() + "', '" + report.getFromUserId() + "', '" + report.getToUserId() + "', '"
                + report.getReason()+ "', '" + Date + "'," + 0 + ");";
        System.out.println(columns + values);
        return columns + values;

    }
}
