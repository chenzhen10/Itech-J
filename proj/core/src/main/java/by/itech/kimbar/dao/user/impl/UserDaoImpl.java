package by.itech.kimbar.dao.user.impl;

import by.itech.kimbar.dao.exception.DaoException;
import by.itech.kimbar.dao.impl.connection.ConnectionCloser;
import by.itech.kimbar.dao.impl.connection.ConnectionPool;
import by.itech.kimbar.dao.user.UserDao;
import by.itech.kimbar.entity.Address;
import by.itech.kimbar.entity.Gender;
import by.itech.kimbar.entity.MaritalStatus;
import by.itech.kimbar.entity.User;
import by.itech.kimbar.util.EnumChecker;
import by.itech.kimbar.util.PathPropertyReader;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private static final Logger log = Logger.getLogger(UserDaoImpl.class);

    private static final int NUMBER_OF_VALID_OPERATION = 1;

    private static final String GET_ALL_USERS = "SELECT * FROM client";
    private static final String CREATE_USER = "INSERT INTO client " +
            "(name, surname, last_name, date, gender, citizenship, marital_status, web_site, email, workplace, " +
            "country, city, street, house, num_of_house,`index`) " +
            "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    //pagination
    private static final String PAGINATION_USER = "SELECT * FROM client ORDER BY surname LIMIT  ? , ? ";
    private static final String USER_COUNT = "SELECT COUNT(*) FROM client";

    private static final String DELETE_USER = "DELETE FROM client WHERE idclient in(?)";
    private static final String UPDATE_USER = "UPDATE  client SET name = ? ,surname = ? , last_name = ? , date = ? , " +
            "gender = ?, citizenship = ?, marital_status = ?, web_site = ?,email = ?,workplace = ?,country = ?," +
            "city = ?,street = ?,house = ?,num_of_house = ?,`index` = ?,photo_path = ?  WHERE idclient = ?";


    private static final String BIRTHDAY_MEN_LIST = "SELECT * FROM kim.client " +
            " WHERE DAYOFMONTH(CURDATE()) = DAYOFMONTH(date) AND MONTH(CURDATE()) = MONTH(date) ";


    @Override
    public Integer countOfUser() throws DaoException {
        return getCount(USER_COUNT);
    }

    @Override
    public Integer countOfFoundUsers(String query) throws DaoException {
        return getCount(query);
    }




    @Override
    public List<User> userPagination(Integer start, Integer total) throws DaoException {
        List<User> users = new ArrayList<>();
        Connection c = ConnectionPool.getInstance().getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = c.prepareStatement(PAGINATION_USER);
            ps.setInt(1, start);
            ps.setInt(2, total);

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

    @Override
    public List<User> findUser(String query) throws DaoException {
        List<User> users = new ArrayList<>();
        Connection c = ConnectionPool.getInstance().getConnection();
        Statement ps = null;
        ResultSet rs = null;
        try {
            ps = c.createStatement();
            rs = ps.executeQuery(query);

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



    @Override
    public boolean createUser(String name, String surname, String lastName, Date date, Gender gender, String citizenship,
                              MaritalStatus maritalStatus, String webSite, String email, String workplace,
                              String country, String city, String street, String house, String numOfFlat,
                              Integer index) throws DaoException {
        Connection c = ConnectionPool.getInstance().getConnection();
        PreparedStatement st = null;
        int res = 0;
        try {
            st = c.prepareStatement(CREATE_USER);
            st.setString(1, name);
            st.setString(2, surname);
            st.setString(3, lastName);
            st.setObject(4, date);
            st.setString(5, EnumChecker.checkIfEnumExist(maritalStatus,gender).get(1));
            st.setString(6, citizenship);
            st.setString(7, EnumChecker.checkIfEnumExist(maritalStatus,gender).get(0));
            st.setString(8, webSite);
            st.setString(9, email);
            st.setString(10, workplace);
            st.setString(11, country);
            st.setString(12, city);
            st.setString(13, street);
            st.setString(14, house);
            st.setString(15, numOfFlat);
            st.setObject(16, index);

            res = st.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            ConnectionCloser.close(c, null, st);
        }
        return res == NUMBER_OF_VALID_OPERATION;
    }

    @Override
    public boolean deleteUser(Integer[] id) throws DaoException {
        Connection c = ConnectionPool.getInstance().getConnection();
        PreparedStatement ps = null;
        int iterator = 0;
        int containsZero = 0;
        int[] res = null;
        try {
            ps = c.prepareStatement(DELETE_USER);
            for (int i = 1; i <= id.length; i++) {
                ps.setInt(1, id[iterator]);
                ps.addBatch();
                FileUtils.deleteDirectory(new File(PathPropertyReader.readFilePath() + File.separator + id[iterator]));
                iterator++;
            }

            res = ps.executeBatch();

            //convert if all users delete 0 false 1 true
            for (int i : res) {
                if (i == 0) {
                    containsZero++;
                }
            }
        } catch (SQLException | IOException e) {
            throw new DaoException(e);
        } finally {
            ConnectionCloser.close(c, null, ps);
        }
        return containsZero == 0;
    }


    @Override
    public boolean updateUser(String name, String surname, String lastName, Date date, Gender gender, String citizenship,
                              MaritalStatus maritalStatus, String webSite, String email, String workplace,
                              String country, String city, String street, String house, String numOfFlat,
                              Integer index, String photoPath, Integer idClient) throws DaoException {
        Connection c = ConnectionPool.getInstance().getConnection();
        PreparedStatement ps = null;
        int res = 0;
        try {


            ps = c.prepareStatement(UPDATE_USER);
            ps.setString(1, name);
            ps.setString(2, surname);
            ps.setString(3, lastName);
            ps.setObject(4, date);
            ps.setString(5, EnumChecker.checkIfEnumExist(maritalStatus,gender).get(1));
            ps.setString(6, citizenship);
            ps.setString(7, EnumChecker.checkIfEnumExist(maritalStatus,gender).get(0));
            ps.setString(8, webSite);
            ps.setString(9, email);
            ps.setString(10, workplace);
            ps.setString(11, country);
            ps.setString(12, city);
            ps.setString(13, street);
            ps.setString(14, house);
            ps.setString(15, numOfFlat);
            ps.setObject(16, index);
            ps.setString(17, photoPath);
            ps.setInt(18, idClient);
            res = ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            ConnectionCloser.close(c, null, ps);
        }
        return res == NUMBER_OF_VALID_OPERATION;
    }

    @Override
    public List<User> birthdayMen() throws DaoException {
        Connection c = ConnectionPool.getInstance().getConnection();
        List<User> user = new ArrayList<>();
        Statement st = null;
        ResultSet rs = null;
        try {
            st = c.createStatement();
            rs = st.executeQuery(BIRTHDAY_MEN_LIST);
            while (rs.next()) {
                user.add(create(rs));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            ConnectionCloser.close(c, rs, st);
        }

        return user;
    }


    private User create(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("idclient"));
        user.setName(rs.getString("name"));
        user.setSurname(rs.getString("surname"));
        user.setLastName(rs.getString("last_name"));
        user.setBirthDate(rs.getDate("date"));

        String gender = rs.getString("gender");
        if (gender != null) {
            user.setGender(Gender.valueOf(gender));
        }
        user.setCitizenship(rs.getString("citizenship"));

        String mStatus = rs.getString("marital_status");
        if (mStatus != null) {
            user.setMaritalStatus(MaritalStatus.valueOf(mStatus));
        }
        user.setEmail(rs.getString("email"));
        user.setWebSite(rs.getString("web_site"));
        user.setWorkplace(rs.getString("workplace"));
        user.setAddress(new Address(rs.getString("country"),
                rs.getString("city"), rs.getString("street"), rs.getString("house"),
                rs.getString("num_of_house"), (Integer) rs.getObject(("index"))));
        user.setPhotoPath(rs.getString("photo_path"));

        return user;
    }

    private Integer getCount(String query) throws DaoException {
        Connection c = ConnectionPool.getInstance().getConnection();
        Statement st = null;
        int count = 0;
        ResultSet rs = null;
        try {
            st = c.createStatement();
            rs = st.executeQuery(query);
            rs.next();
            count = rs.getInt(1);
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            ConnectionCloser.close(c, rs, st);
        }
        return count;
    }

}
