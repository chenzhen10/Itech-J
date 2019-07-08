package by.itech.kimbar.service.user.impl;

import by.itech.kimbar.dao.DaoProvider;
import by.itech.kimbar.dao.exception.DaoException;
import by.itech.kimbar.dao.impl.connection.ConnectionCloser;
import by.itech.kimbar.dao.impl.connection.ConnectionPool;
import by.itech.kimbar.dao.user.UserDao;
import by.itech.kimbar.entity.Gender;
import by.itech.kimbar.entity.MaritalStatus;
import by.itech.kimbar.entity.User;
import by.itech.kimbar.service.exception.ServiceException;
import by.itech.kimbar.service.user.UserService;
import by.itech.kimbar.service.validation.impl.ValidationImpl;
import by.itech.kimbar.util.DirectoryCreator;
import by.itech.kimbar.util.PathPropertyReader;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.map.ObjectMapper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class UserServiceImpl implements UserService {

    private static final DaoProvider provider = DaoProvider.getInstance();

    @Override
    public String getAllInJson() throws ServiceException {
        ObjectMapper om = new ObjectMapper();
        String res = null;
        UserDao usrDao = provider.getUserDao();
        try {
            res = om.writeValueAsString(usrDao.getAllUser());
        } catch (DaoException | IOException e) {
            throw new ServiceException(e);
        }

        return res;
    }

    @Override
    public boolean updateUser(String name, String surname, String lastName, Date date, Gender gender, String citizenship,
                              MaritalStatus maritalStatus, String webSite, String email, String workplace,
                              String country, String city, String street, String house, String numOfFlat, Integer index, String photoPath, Integer idClient) throws ServiceException {
        if (ValidationImpl.validateUpdateUserFields(name, surname, lastName, citizenship,
                webSite, email, workplace, country, city, street, house, numOfFlat, idClient)) {

            UserDao usrDao = provider.getUserDao();
            boolean result = false;
            Connection c = ConnectionPool.getInstance().getConnection();
            try {
                c.setAutoCommit(false);
                result = usrDao.updateUser(name, surname, lastName, date, gender, citizenship,
                        maritalStatus, webSite, email, workplace, country, city, street, house, numOfFlat, index, photoPath, idClient);
                c.commit();
                c.setAutoCommit(true);
            } catch (DaoException | SQLException e) {
                try {
                    c.rollback();
                } catch (SQLException e1) {
                    throw new ServiceException(e1);
                }
                throw new ServiceException(e);
            } finally {
                ConnectionCloser.close(c, null, null);
            }
            return result;
        } else {
            throw new ServiceException("Inputted data are incorrect");
        }
    }

    @Override
    public boolean deleteUser(Integer[] id) throws ServiceException {
        if (ValidationImpl.validateDelete(id)) {

            UserDao usrDao = provider.getUserDao();
            Connection c = ConnectionPool.getInstance().getConnection();
            boolean result = false;
            try {
                c.setAutoCommit(false);
                result = usrDao.deleteUser(id);
                c.commit();
                c.setAutoCommit(true);
            } catch (DaoException | SQLException e) {
                try {
                    c.rollback();
                } catch (SQLException e1) {
                    throw new ServiceException(e);
                }
                throw new ServiceException(e);
            } finally {
                ConnectionCloser.close(c, null, null);
            }
            return result;
        } else {
            throw new ServiceException("There is no data to delete");
        }
    }

    @Override
    public int getCountOfUsers() throws ServiceException {
        UserDao usrDao = provider.getUserDao();
        int result = 0;
        try {
            result = usrDao.countOfUser();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }


    @Override
    public String getPaginationUsers(Integer start, Integer total) throws ServiceException {
        if (ValidationImpl.validatePagination(start, total)) {
            UserDao usrDao = provider.getUserDao();
            List<User> allUsers = null;
            ObjectMapper om = new ObjectMapper();
            String result = null;
            try {
                allUsers = usrDao.userPagination(start, total);
                result = om.writeValueAsString(allUsers);
            } catch (DaoException | IOException e) {
                throw new ServiceException(e);
            }
            return result;
        } else {
            throw new ServiceException("Data are incorrect");
        }
    }


    @Override
    public boolean createUser(String name, String surname, String lastName, Date date, Gender gender, String citizenship,
                              MaritalStatus maritalStatus, String webSite, String email, String workplace,
                              String country, String city, String street, String house, String numOfFlat, Integer index, String photoPath, String img) throws ServiceException {

        if (ValidationImpl.validateCreateUserFields(name, surname, lastName, citizenship,
                webSite, email, workplace, country, city, street, house, numOfFlat)) {

            boolean result = false;
            UserDao usrDao = provider.getUserDao();
            Connection c = ConnectionPool.getInstance().getConnection();
            try {
                c.setAutoCommit(false);
                result = usrDao.createUser
                        (name, surname, lastName, date, gender, citizenship, maritalStatus
                                , webSite, email, workplace, country, city, street, house, numOfFlat, index, photoPath);
                c.commit();

                createFolderWithUserPhoto(String.valueOf(extractNextUserId()), photoPath, img);
                c.setAutoCommit(true);
            } catch (DaoException | SQLException | IOException e) {
                try {
                    c.rollback();
                } catch (SQLException e1) {
                    throw new ServiceException(e1);
                }
                throw new ServiceException(e);
            } finally {
                ConnectionCloser.close(c, null, null);
            }
            return result;
        } else {
            throw new ServiceException("Inputted data are incorrect");
        }
    }

    @Override
    public String findUserByParameter(String name, String surname, String lastName, Gender gender, String year, String month, String day,
                                      MaritalStatus maritalStatus, String citizenship, String country, String city, String street,
                                      String house, String numOfHouse, Integer index, Integer start, Integer total) throws ServiceException {

        if (ValidationImpl.validateSearchFields(name, surname, lastName,
                citizenship, country, city, street, house, numOfHouse)) {

            UserDao ud = provider.getUserDao();
            List<User> users = null;
            ObjectMapper om = new ObjectMapper();
            String result = null;
            try {
                String query = "SELECT * FROM client WHERE 1 = 1 ";


                if (name != null && name.length() > 0) {
                    query += " AND name LIKE " + "'%" + name + "%'";
                }

                if (surname != null && surname.length() > 0) {
                    query += " AND surname LIKE " + "'%" + surname + "%'";
                }

                if (lastName != null && lastName.length() > 0) {
                    query += " AND last_name LIKE " + "'%" + lastName + "%'";
                }
                if (gender != null) {
                    query += " AND gender = " + "'" + gender + "'";
                }
                if (maritalStatus != null) {
                    query += " AND marital_status = " + "'" + maritalStatus + "'";
                }
                if (year != null && year.length() > 0) {
                    query += " AND YEAR(date) = '" + year + "'";
                }
                if (month != null && month.length() > 0) {
                    query += " AND MONTH(date) = '" + month + "'";
                }
                if (day != null && day.length() > 0) {
                    query += " AND DAY(date) = '" + day + "'";
                }
                if (citizenship != null && citizenship.length() > 0) {
                    query += " AND citizenship LIKE " + "'%" + citizenship + "%'";
                }
                if (country != null && country.length() > 0) {
                    query += " AND country LIKE " + "'%" + country + "%'";
                }
                if (city != null && city.length() > 0) {
                    query += " AND city LIKE " + "'%" + city + "%'";
                }
                if (street != null && street.length() > 0) {
                    query += " AND street LIKE " + "'%" + street + "%'";
                }
                if (house != null && house.length() > 0) {
                    query += " AND house LIKE " + "'%" + house + "%'";
                }
                if (numOfHouse != null && numOfHouse.length() > 0) {
                    query += " AND num_of_house LIKE " + "'%" + numOfHouse + "%'";
                }
                if (index != null) {
                    query += " AND client.index LIKE " + "'%" + index + "%'";
                }
                users = ud.findUser(query + "ORDER BY surname LIMIT " + start + "," + total);
                result = om.writeValueAsString(users);
            } catch (DaoException | IOException e) {
                throw new ServiceException(e);
            }
            return result;
        } else {
            throw new ServiceException("Inputted data are incorrect");
        }
    }


    @Override
    public String countUserByParameter(String name, String surname, String lastName, Gender gender, String year, String month, String day,
                                       MaritalStatus maritalStatus, String citizenship, String country, String city, String street,
                                       String house, String numOfHouse, Integer index) throws ServiceException {

        if (ValidationImpl.validateSearchFields(name, surname, lastName,
                citizenship, country, city, street, house, numOfHouse)) {

            UserDao ud = provider.getUserDao();
            ObjectMapper om = new ObjectMapper();
            String result = null;
            try {

                String countOfUsers = "SELECT COUNT(*) FROM client WHERE 1 = 1 ";

                if (name != null && name.length() > 0) {
                    countOfUsers += " AND name LIKE " + "'%" + name + "%'";
                }

                if (surname != null && surname.length() > 0) {
                    countOfUsers += " AND surname LIKE " + "'%" + surname + "%'";
                }

                if (lastName != null && lastName.length() > 0) {
                    countOfUsers += " AND last_name LIKE " + "'%" + lastName + "%'";
                }
                if (gender != null) {
                    countOfUsers += " AND gender = " + "'" + gender + "'";
                }
                if (maritalStatus != null) {
                    countOfUsers += " AND marital_status = " + "'" + maritalStatus + "'";
                }
                if (year != null && year.length() > 0) {
                    countOfUsers += " AND YEAR(date) = '" + year + "'";
                }
                if (month != null && month.length() > 0) {
                    countOfUsers += " AND MONTH(date) = '" + month + "'";
                }
                if (day != null && day.length() > 0) {
                    countOfUsers += " AND DAY(date) = '" + day + "'";
                }
                if (citizenship != null && citizenship.length() > 0) {
                    countOfUsers += " AND citizenship LIKE " + "'%" + citizenship + "%'";
                }
                if (country != null && country.length() > 0) {
                    countOfUsers += " AND country LIKE " + "'%" + country + "%'";
                }
                if (city != null && city.length() > 0) {
                    countOfUsers += " AND city LIKE " + "'%" + city + "%'";
                }
                if (street != null && street.length() > 0) {
                    countOfUsers += " AND street LIKE " + "'%" + street + "%'";
                }
                if (house != null && house.length() > 0) {
                    countOfUsers += " AND house LIKE " + "'%" + house + "%'";
                }
                if (numOfHouse != null && numOfHouse.length() > 0) {
                    countOfUsers += " AND num_of_house LIKE " + "'%" + numOfHouse + "%'";
                }
                if (index != null) {
                    countOfUsers += " AND client.index LIKE " + "'%" + index + "%'";
                }
                int res = ud.countOfFoundUsers(countOfUsers);
                result = om.writeValueAsString(res);
            } catch (DaoException | IOException e) {
                throw new ServiceException(e);
            }
            return result;
        } else {
            throw new ServiceException("Inputted data are incorrect");
        }
    }

    @Override
    public List<User> birthdayMen() throws ServiceException {
        List<User> users = null;
        UserDao ud = provider.getUserDao();
        try {
            users = ud.birthdayMen();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return users;
    }


    @Override
    public int extractNextUserId() throws ServiceException {
        UserDao usrDao = provider.getUserDao();
        int result = 0;
        try {
            result = usrDao.getNextUserId();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    private String createFolderWithUserPhoto(String usrId, String ext, String img) throws IOException {
        String pathToPhoto = null;
        if (ext != null && !ext.equals("null")) {
            FileUtils.deleteDirectory(new File(PathPropertyReader.readPhotoPath() + File.separator + usrId));
            DirectoryCreator.createPhotoSubFolderForUser(usrId);
            String path = PathPropertyReader.readPhotoPath() + File.separator + usrId + File.separator + usrId + ext;

            InputStream in = new ByteArrayInputStream(decodeImage(img));
            BufferedImage bImageFromConvert = ImageIO.read(in);
            ImageIO.write(bImageFromConvert, ext.replace(".",""), new File(path));
            pathToPhoto = PathPropertyReader.readPhotoPath() + File.separator + usrId + File.separator + usrId + ext;
            in.close();
        }
        return pathToPhoto;
    }

    private static byte[] decodeImage(String imageDataString) {
        return Base64.decodeBase64(imageDataString);
    }
}
