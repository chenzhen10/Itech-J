package by.itech.kimbar.dao.impl.connection;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {
    private static final String CONNECTION_POOL_NAME = "java:comp/env/jdbc/kim";
    private static final Logger log = Logger.getLogger(ConnectionPool.class);
    private static ConnectionPool instance = null;


    private ConnectionPool() {
    }



    public static ConnectionPool getInstance() {
        if (instance == null)
            instance = new ConnectionPool();
        return instance;
    }


    public Connection getConnection() {
        Context ctx;
        Connection c = null;
        try {
            ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup(CONNECTION_POOL_NAME);
            c = ds.getConnection();
        } catch (NamingException | SQLException e) {
            // log.error(e);
        }
        return c;
    }
}
