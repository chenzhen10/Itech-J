package by.itech.kimbar.dao.attachment.impl;

import by.itech.kimbar.dao.attachment.AttachmentDao;
import by.itech.kimbar.dao.exception.DaoException;
import by.itech.kimbar.dao.impl.connection.ConnectionCloser;
import by.itech.kimbar.dao.impl.connection.ConnectionPool;
import by.itech.kimbar.entity.Attachment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttachmentDaoImpl implements AttachmentDao {
    private static final int NUMBER_OF_VALID_OPERATION = 1;
    private static final String ADD_ATTACHMENT = "INSERT INTO attachment (name,date,commentary,idclient,path) VALUES(?,CURRENT_DATE(),?,?,?) ";
    private static final String GET_ALL_ATTACHMENTS = "SELECT * FROM attachment";
    private static final String GET_ATTACHMENTS_BY_USER_ID = "SELECT  idattachment, attachment.name,attachment.date , " +
            "commentary, attachment.idclient, path FROM client \n" +
            "INNER JOIN attachment \n" +
            "ON client.idclient = attachment.idclient WHERE attachment.idclient = ?";

    private static final String UPDATE_ATTACHMENT = "UPDATE attachment SET commentary = ? , name = ?  WHERE idattachment = ? ";
    private static final String GET_COUNT_ATTACHMENTS = "SELECT  idattachment FROM attachment ORDER BY idattachment DESC LIMIT 0, 1 ;";
    private static final String DELETE_ATTACHMENT = "DELETE FROM attachment WHERE idattachment in(?)";

    @Override
    public boolean attachFile(String name, String comment, Integer id, String path) throws DaoException {
        Connection c = ConnectionPool.getInstance().getConnection();
        PreparedStatement ps = null;
        int res;
        try {
            ps = c.prepareStatement(ADD_ATTACHMENT);
            ps.setString(1, name);
            ps.setString(2, comment);
            ps.setInt(3, id);
            ps.setString(4, path);
            res = ps.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            ConnectionCloser.close(c, null, ps);
        }
        return res == NUMBER_OF_VALID_OPERATION;
    }

    @Override
    public List<Attachment> getAllAttachments() throws DaoException {
        Connection c = ConnectionPool.getInstance().getConnection();
        List<Attachment> attachments = new ArrayList<>();
        Statement st = null;
        ResultSet rs = null;

        try {
            st = c.createStatement();
            rs = st.executeQuery(GET_ALL_ATTACHMENTS);
            while (rs.next()) {
                attachments.add(create(rs));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            ConnectionCloser.close(c, rs, st);
        }
        return attachments;
    }

    @Override
    public List<Attachment> getAttachmentByUserId(Integer userId) throws DaoException {
        Connection c = ConnectionPool.getInstance().getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Attachment> attachments = new ArrayList<>();
        try {
            st = c.prepareStatement(GET_ATTACHMENTS_BY_USER_ID);

            st.setInt(1, userId);

            rs = st.executeQuery();

            while (rs.next()) {
                attachments.add(create(rs));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            ConnectionCloser.close(c, rs, st);
        }
        return attachments;
    }

    @Override
    public boolean deleteAttachment(Integer[] id) throws DaoException {
        Connection c = ConnectionPool.getInstance().getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        int iterator = 0;
        int containsZero = 0;
        int[] res = null;
        try {
            ps = c.prepareStatement(DELETE_ATTACHMENT);

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

    @Override
    public int getCountOfAttachments() throws DaoException {
        Connection c = ConnectionPool.getInstance().getConnection();
        Statement st = null;
        ResultSet rs = null;
        int result = 0;
        try {
            st = c.createStatement();
            rs = st.executeQuery(GET_COUNT_ATTACHMENTS);
            while (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            ConnectionCloser.close(c, rs, st);
        }
        return result;
    }

    @Override
    public boolean updateAttachment(Integer id, String name, String commentary) throws DaoException {
        Connection c = ConnectionPool.getInstance().getConnection();
        PreparedStatement ps = null;
        int res;
        try {
            ps = c.prepareStatement(UPDATE_ATTACHMENT);

            ps.setString(1, commentary);
            ps.setString(2, name);
            ps.setInt(3, id);

            res = ps.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            ConnectionCloser.close(c, null, ps);
        }
        return res == NUMBER_OF_VALID_OPERATION;
    }


    private Attachment create(ResultSet rs) throws SQLException {
        Attachment attachment = new Attachment();
        attachment.setId(rs.getInt("idattachment"));
        attachment.setName(rs.getString("name"));
        attachment.setDate(rs.getDate("date"));
        attachment.setCommentary(rs.getString("commentary"));
        attachment.setUserId(rs.getInt("idclient"));
        attachment.setPath(rs.getString("path"));
        return attachment;
    }
}
