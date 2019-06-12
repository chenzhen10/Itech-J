package by.itech.kimbar.dao.user.impl;

import by.itech.kimbar.dao.user.UserDao;
import by.itech.kimbar.dao.exception.DaoException;
import by.itech.kimbar.dao.impl.connection.ConnectionCloser;
import by.itech.kimbar.dao.impl.connection.ConnectionPool;
import by.itech.kimbar.entity.Address;
import by.itech.kimbar.entity.Gender;
import by.itech.kimbar.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private static final int NUMBER_OF_VALID_OPERATION = 1;

    private static final String GET_ALL_USERS = "SELECT * FROM client";
    private static final String CREATE_USER = "INSERT INTO client " +
            "(name, surname, last_name, date, gender, citizenship, marital_status, web_site, email, workplace, " +
            "country, city, street, house, num_of_house,`index`) " +
            "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private static final String DELETE_USER = "DELETE FROM client WHERE idclient in(?)";
    private static final String UPDATE_USER = "UPDATE  client SET name = ?  WHERE idclient = ?";
    private static final String FIND_USER = "SELECT * FROM client WHERE name LIKE ? or surname LIKE ? or last_name LIKE ? " +
            "or gender LIKE ? or marital_status LIKE ? or date in (?);";


    @Override
    public List<User> getAllUser() throws DaoException {
        List<User> users = new ArrayList<>();
        Connection c = ConnectionPool.getInstance().getConnection();
        Statement st = null;
        ResultSet rs = null;

        try {
            st = c.createStatement();
            rs = st.executeQuery(GET_ALL_USERS);
            while (rs.next()) {
                users.add(create(rs));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            ConnectionCloser.close(c, rs, st);
        }
        return users;
    }

    //work
    @Override
    public List<User> getPaginationUsers(int start, int total) throws DaoException {
        List<User> paginUsers = new ArrayList<>();
        List<User> allUsers = getAllUser();

        for (int i = 0; i < total; i++) {
            paginUsers.add(allUsers.get(start));
            start++;
        }
        return paginUsers;
    }


    //work
    @Override
    public List<User> findUser(String parameter) throws DaoException {
        List<User> users = new ArrayList<>();
        Connection c = ConnectionPool.getInstance().getConnection();

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = c.prepareStatement(FIND_USER);
            ps.setString(1, "%" + parameter + "%");
            ps.setString(2, "%" + parameter + "%");
            ps.setString(3, "%" + parameter + "%");
            ps.setString(4, "%" + parameter + "%");
            ps.setString(5, "%" + parameter + "%");
            ps.setString(6, "%" + parameter + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                users.add(create(rs));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            ConnectionCloser.close(c, rs, ps);
        }
        return users;
    }

    //work
    // fix if else statement
    @Override
    public boolean createUser(String name, String surname, String lastName, Date date, Gender gender, String citizenship,
                              String maritalStatus, String webSite, String email, String workplace,
                              String country, String city, String street, String house, String numOfFlat, Integer index) throws DaoException {
        Connection c = ConnectionPool.getInstance().getConnection();

        PreparedStatement st = null;
        ResultSet rs = null;
        int res = 0;
        try {
            st = c.prepareStatement(CREATE_USER);
            st.setString(1, name);
            st.setString(2, surname);
            st.setString(3, lastName);
            st.setDate(4, (java.sql.Date) date);
            st.setObject(5, gender);
            st.setString(6, citizenship);
            st.setString(7, maritalStatus);
            st.setString(8, webSite);
            st.setString(9, email);
            st.setString(10, workplace);
            st.setString(11, country);
            st.setString(12, city);
            st.setString(13, street);
            st.setString(14, house);
            st.setString(15, numOfFlat);
            st.setInt(16, index);

            res = st.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            ConnectionCloser.close(c, rs, st);
        }
        return res == NUMBER_OF_VALID_OPERATION;
    }

    @Override
    public boolean deleteUser(int[] id) throws DaoException {
        Connection c = ConnectionPool.getInstance().getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        int iterator = 0;
        int containsZero = 0;
        int[] res = null;
        try {
            ps = c.prepareStatement(DELETE_USER);

            for (int i = 1; i <= id.length; i++) {
                ps.setInt(1, id[iterator]);
                ps.addBatch();
                iterator++;
            }

            res = ps.executeBatch();

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


    //should be end
    @Override
    public boolean updateUser(String parameter) throws DaoException {
        Connection c = ConnectionPool.getInstance().getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        int res = 0;
//        try {
//            ps = c.prepareStatement(UPDATE_USER);
//            ps.setInt(1, Integer.valueOf(id));
//            ps.setInt(1, Integer.valueOf(id));
//            ps.setInt(1, Integer.valueOf(id));
//            res = ps.executeUpdate();
//        } catch (SQLException e) {
//            throw new DaoException(e);
//        } finally {
//            ConnectionCloser.close(c, rs, ps);
//        }
        return res == NUMBER_OF_VALID_OPERATION;
    }


    private User create(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("idclient"));
        user.setName(rs.getString("name"));
        user.setSurname(rs.getString("surname"));
        user.setLastName(rs.getString("last_name"));
        user.setBirthDate(rs.getDate("date"));
        user.setGender((Gender) (rs.getObject("gender")));
        user.setCitizenship(rs.getString("citizenship"));
        user.setMaritalStatus(rs.getString("marital_status"));
        user.setEmail(rs.getString("email"));
        user.setWebSite(rs.getString("web_site"));
        user.setWorkplace(rs.getString("workplace"));
        user.setAddress(new Address(rs.getString("country"),
                rs.getString("city"), rs.getString("street"), rs.getString("house"),
                rs.getString("num_of_house"), (Integer) rs.getObject(("index"))));
        return user;
    }

}
