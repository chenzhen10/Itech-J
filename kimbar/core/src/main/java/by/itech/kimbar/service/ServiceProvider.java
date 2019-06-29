package by.itech.kimbar.service;

import by.itech.kimbar.service.attachment.AttachmentService;
import by.itech.kimbar.service.attachment.impl.AttachmentServiceImpl;
import by.itech.kimbar.service.phone.PhoneService;
import by.itech.kimbar.service.phone.impl.PhoneServiceImpl;
import by.itech.kimbar.service.user.impl.UserServiceImpl;
import by.itech.kimbar.service.user.UserService;

public class ServiceProvider {
    private static final ServiceProvider serviceProvider = new ServiceProvider();
    private final UserService usrService = new UserServiceImpl();
    private final AttachmentService attachmentService = new AttachmentServiceImpl();
    private final PhoneService phoneService = new PhoneServiceImpl();

    private ServiceProvider(){}

    public static ServiceProvider getInstance(){
        return  serviceProvider;
    }

    public UserService getUserService(){
        return usrService;
    }
    public AttachmentService getAttachmentService(){
        return attachmentService;
    }
    public PhoneService getPhoneService(){
        return phoneService;
    }

}
