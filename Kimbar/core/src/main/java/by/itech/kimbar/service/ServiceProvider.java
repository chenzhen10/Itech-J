package by.itech.kimbar.service;

import by.itech.kimbar.service.user.impl.UserServiceImpl;
import by.itech.kimbar.service.user.UserService;

public class ServiceProvider {
    private static final ServiceProvider serviceProvider = new ServiceProvider();
    private final UserService usrService = new UserServiceImpl();

    private ServiceProvider(){}

    public static ServiceProvider getInstance(){
        return  serviceProvider;
    }

    public UserService getUserService(){
        return usrService;
    }

}
