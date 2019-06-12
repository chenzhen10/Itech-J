package by.itech.kimbar.dao.phone.impl;

import by.itech.kimbar.dao.exception.DaoException;
import by.itech.kimbar.dao.impl.connection.ConnectionCloser;
import by.itech.kimbar.dao.impl.connection.ConnectionPool;
import by.itech.kimbar.dao.phone.PhoneDao;
import by.itech.kimbar.entity.Phone;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhoneDaoImpl implements PhoneDao {
    private static final int NUMBER_OF_VALID_OPERATION = 1;

    private static final String GET_ALL_PHONES = "SELECT * FROM phone";
    private static final String DELETE_PHONE = "DELETE FROM phone WHERE idphone = ? ";
    private static final String UPDATE_PHONE = "UPDATE phone SET country_code = ? , operator_code = ? , number  = ?, type = ? , commentary = ?  WHERE idphone = ? ";
    private static final String CREATE_PHONE = "INSERT INTO phone (country_code, operator_code,number, type, commentary, idclient) VALUES (?,?,?,?,?,?)";


    @Override
    public List<Phone> getAllPhones() throws DaoException {
        Connection c = ConnectionPool.getInstance().getConnection();
        Statement st = null;
        ResultSet rs = null;
        List<Phone> phones = new ArrayList<>();
        try {
            st = c.createStatement();
            rs = st.executeQuery(GET_ALL_PHONES);
            while (rs.next()) {
                phones.add(create(rs));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            ConnectionCloser.close(c, rs, st);
        }
        return phones;
    }

    @Override
    public boolean updatePhone(int id, int countryCode, int operatorCode, int numberCode, String type, String commentary) throws DaoException {
        Connection c = ConnectionPool.getInstance().getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        int res = 0;
        try {
            ps = c.prepareStatement(UPDATE_PHONE);

            ps.setInt(1,countryCode);
            ps.setInt(2,operatorCode);
            ps.setInt(3,numberCode);
            ps.setString(4,type);
            ps.setString(5,commentary);
            ps.setInt(6,id);

            res = ps.executeUpdate();


        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            ConnectionCloser.close(c, rs, ps);
        }
        return res == NUMBER_OF_VALID_OPERATION;
    }

    @Override
    public boolean deletePhone(int[] id) throws DaoException, SQLException, ClassNotFoundException {
        // Connection c = ConnectionPool.getInstance().getConnection();
        Class.forName("com.mysql.jdbc.Driver");
        Connection c = DriverManager.getConnection("jdbc:mysql://localhost/kim", "root", "root");
        PreparedStatement ps = null;
        ResultSet rs = null;
        int[] res = null;
        int iterator = 0;
        int containsZero = 0;
        try {
            ps = c.prepareStatement(DELETE_PHONE);

            //here we are accumulate queries
            for (int i = 1; i <= id.length; i++) {
                ps.setInt(1, id[iterator]);
                ps.addBatch();
                iterator++;
            }

            res = ps.executeBatch();

            // check if all statements execute correctly
            for (int i : res) {
                if (i == 0) {
                    containsZero++;
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            ConnectionCloser.close(c, rs, ps);
        }
        return containsZero == 0;
    }


    //number must be not null
    @Override
    public boolean createPhone(int countryCode, int operatorCode, int number, String type, String commentary,
                               int idClient) throws DaoException, ClassNotFoundException, SQLException {
        //Connection c = ConnectionPool.getInstance().getConnection();
        Class.forName("com.mysql.jdbc.Driver");
        Connection c = DriverManager.getConnection("jdbc:mysql://localhost/kim", "root", "root");

        PreparedStatement st = null;
        int res = 0;
        try {
            st = c.prepareStatement(CREATE_PHONE);
            st.setInt(1, countryCode);
            st.setInt(2, operatorCode);
            st.setInt(3, number);
            st.setString(4, type);
            st.setString(5, commentary);
            st.setInt(6, idClient);

            res = st.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            ConnectionCloser.close(c, null, st);
        }
        return res == NUMBER_OF_VALID_OPERATION;
    }

    //via id we can manipulate our date in future in proper way
    private Phone create(ResultSet rs) throws SQLException {
        Phone phone = new Phone();
        phone.setId(rs.getInt("idphone"));
        phone.setCountryCode(rs.getInt("country_code"));
        phone.setOperatorCode(rs.getInt("operator_code"));
        phone.setNumber(rs.getInt("number"));
        phone.setType(Phone.Type.valueOf(rs.getString("type")));
        phone.setCommentary(rs.getString("commentary"));

        return phone;
    }
}
