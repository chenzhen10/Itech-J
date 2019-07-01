package by.itech.kimbar.dao.impl.connection;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionCloser {
    private static final Logger log = Logger.getLogger(ConnectionCloser.class);

    private static final String RESULT_SET_ERROR_MESSAGE = "ResultSet close exception ";
    private static final String CONNECTION_ERROR_MESSAGE = "Connection close exception ";
    private static final String STATEMENT_ERROR_MESSAGE = "PreparedStatement close exception ";

    public static void close(Connection con, ResultSet rs, Statement prep) {
        if (con != null) {
            try {
                con.close();
            }
            catch (SQLException e) {
                log.error(CONNECTION_ERROR_MESSAGE + e);
            }
        }if(rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                log.error(RESULT_SET_ERROR_MESSAGE + e);
            }
        }if(prep != null){
            try {
                prep.close();
            } catch (SQLException e) {
                log.error(STATEMENT_ERROR_MESSAGE + e);
            }
        }
    }
}
