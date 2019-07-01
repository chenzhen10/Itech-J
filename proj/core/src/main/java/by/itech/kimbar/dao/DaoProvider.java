package by.itech.kimbar.dao;

import by.itech.kimbar.dao.attachment.AttachmentDao;
import by.itech.kimbar.dao.attachment.impl.AttachmentDaoImpl;
import by.itech.kimbar.dao.phone.impl.PhoneDaoImpl;
import by.itech.kimbar.dao.user.impl.UserDaoImpl;
import by.itech.kimbar.dao.phone.PhoneDao;
import by.itech.kimbar.dao.user.UserDao;

public class DaoProvider {
    private static final DaoProvider  instance = new DaoProvider();
    private final UserDao usrDao = new UserDaoImpl();
    private final AttachmentDao attachmentDao = new AttachmentDaoImpl();
    private final PhoneDao phoneDao = new PhoneDaoImpl();

    private DaoProvider() {
    }

    public static DaoProvider getInstance() {
        return instance;
    }

    public UserDao getUserDao() {
        return usrDao;
    }

    public PhoneDao getPhoneDao() {
        return phoneDao;
    }

    public AttachmentDao getAttachmentDao() {
        return attachmentDao;
    }


}
