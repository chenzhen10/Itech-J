package by.itech.kimbar.service.validation;

import by.itech.kimbar.dto.AttachmentDto;
import by.itech.kimbar.dto.Dto;
import by.itech.kimbar.service.validation.impl.ValidationImpl;
import org.apache.log4j.Logger;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ValidationTest {
    private static final Logger log = Logger.getLogger(ValidationTest.class);


    @Test
    public void validateAttachFileTest() {
        String name = "Test";
        String comment = "";
        Integer id = 2;
        String path = "custompath";
        assertTrue(ValidationImpl.validateAttachFile(name, comment, id, path));
    }

    @Test
    public void validateAttachFileTest1() {
        String name = null;
        String comment = "";
        Integer id = 2;
        String path = "custompath";
       assertFalse(ValidationImpl.validateAttachFile(name, comment, id, path));
    }


    @Test
    public void validateAttachFileTest2() {
        String name = "ss";
        String comment = "";
        Integer id = null;
        String path = "custompath";
        assertFalse(ValidationImpl.validateAttachFile(name, comment, id, path));
    }

    @Test
    public void validateDeleteAttachmentsTest(){
        String[] paths = null;
        String[] name = null;
        Integer[] id = null;
        assertFalse(ValidationImpl.validateDeleteAttachments(id,name,paths));
    }

    @Test
    public void validateDeleteAttachmentsTest1(){
        String[] paths = {"","",""};
        String[] name = {"","",""};
        Integer[] id = {1,4,2};
        assertTrue(ValidationImpl.validateDeleteAttachments(id,name,paths));
    }

    @Test
    public void validateUpdateAttachmentTest (){
        List<Dto> attachments = new ArrayList<>();
        assertFalse(ValidationImpl.validateUpdateAttachment(attachments));
    }

    @Test
    public void validateUpdateAttachmentTest1 (){
        List<Dto>  attachments = new ArrayList<Dto>(){{
                add(new AttachmentDto());
        }};
        assertTrue(ValidationImpl.validateUpdateAttachment(attachments));
    }


}
