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
    private static final String GET_PHONES_BY_USER_ID = "SELECT idphone,country_code,operator_code , number, type, " +
            "commentary, phone.idclient FROM client \n" +
            "INNER JOIN phone\n" +
            "ON client.idclient = phone.idclient WHERE phone.idclient = ? ";
    private static final String DELETE_PHONE = "DELETE FROM phone WHERE idphone in(?)";
    private static final String SEARCH_DUPLICATE_PHONE = "SELECT COUNT(*) as count " +
            "FROM phone  " +
            "WHERE country_code = ? AND operator_code = ? AND number = ?  " +
            "GROUP BY country_code, operator_code,number HAVING COUNT(*) > 1";
    private static final String UPDATE_PHONE = "UPDATE phone SET country_code = ? , operator_code = ? , number  = ?, type = ? , commentary = ?  WHERE idphone = ? ";
    private static final String CREATE_PHONE = "INSERT INTO phone (country_code, operator_code,number, type, commentary, idclient) VALUES (?,?,?,?,?,?)";


    @Override
    public List<Phone> getAll() throws DaoException{
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
    public List<Phone> getPhoneByUserId(Integer userId) throws DaoException {
        Connection c = ConnectionPool.getInstance().getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Phone> phones = new ArrayList<>();
        try {
            st = c.prepareStatement(GET_PHONES_BY_USER_ID);

            st.setInt(1, userId);

            rs = st.executeQuery();

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
    public boolean updatePhone(Integer countryCode, Integer operatorCode, Integer numberCode,
                               Phone.Type type, String commentary, Integer idPhone) throws DaoException {
        Connection c = ConnectionPool.getInstance().getConnection();
        PreparedStatement ps = null;
        int res = 0;
        try {
            ps = c.prepareStatement(UPDATE_PHONE);

            ps.setObject(1, countryCode);
            ps.setObject(2, operatorCode);
            ps.setObject(3, numberCode);
            ps.setString(4, String.valueOf(type));
            ps.setString(5, commentary);
            ps.setInt(6, idPhone);
            res = ps.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            ConnectionCloser.close(c, null, ps);
        }
        return res == NUMBER_OF_VALID_OPERATION;
    }

    @Override
    public boolean deletePhone(Integer[] id) throws DaoException {
        Connection c = ConnectionPool.getInstance().getConnection();
        PreparedStatement ps = null;
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

            // convert if all statements execute correctly
            for (int i : res) {
                if (i == 0) {
                    containsZero++;
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            ConnectionCloser.close(c, null, ps);
        }
        return containsZero == 0;
    }


    //number must be not null
    @Override
    public boolean createPhone(Integer countryCode, Integer operatorCode, Integer number, Phone.Type type, String commentary,
                               Integer idClient) throws DaoException {
        Connection c = ConnectionPool.getInstance().getConnection();
        PreparedStatement st = null;
        int res = 0;
        try {
            st = c.prepareStatement(CREATE_PHONE);
            st.setObject(1, countryCode);
            st.setObject(2, operatorCode);
            st.setObject(3, number);
            st.setString(4, String.valueOf(type));
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

    @Override
    public boolean findDuplicatePhone(Integer countryCode,Integer operatorCode,Integer number) throws DaoException {
        int result = 0;
        Connection c = ConnectionPool.getInstance().getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = c.prepareStatement(SEARCH_DUPLICATE_PHONE);
            st.setObject(1,countryCode);
            st.setObject(2,operatorCode);
            st.setObject(3,number);

            rs = st.executeQuery();

            while (rs.next()){
                result = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            ConnectionCloser.close(c, rs, st);
        }
        return result != 0;
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
