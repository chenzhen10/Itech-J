package by.itech.kimbar.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import by.itech.kimbar.dao.attachment.AttachmentDao;
import org.junit.BeforeClass;
import org.junit.Test;


public class AttachmentDaoTest {
    private static AttachmentDao pd = null;

    @BeforeClass
    public static void init (){
        pd = DaoProvider.getInstance().getAttachmentDao();
    }


}
