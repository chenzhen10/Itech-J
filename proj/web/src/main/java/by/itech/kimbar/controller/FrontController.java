package by.itech.kimbar.controller;


import by.itech.kimbar.controller.command.Command;
import by.itech.kimbar.service.exception.ServiceException;
import by.itech.kimbar.service.impl.schedule.CongratulateJob;
import org.apache.log4j.Logger;
import org.quartz.SchedulerException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class FrontController extends HttpServlet {
    private static final Logger log = Logger.getLogger(FrontController.class);

    @Override
    public void init() {
        try {
            CongratulateJob.sendListOfBirthdayMen();
        } catch (SchedulerException e) {
            log.error(e);
        }

    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CommandProvider cm = CommandProvider.getInstance();
        String param = req.getPathInfo().replaceFirst("/","");


        System.out.println(param);

        if (param.length() != 0) {
            Command c = cm.getCommand(param);
            try {
                c.execute(req, resp);
            } catch (ServiceException e) {
                resp.getWriter().write("{ \"error\" : \"Data was ignored . Reason : incorrect data was inputted\" }");
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                log.error(e);
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CommandProvider cm = CommandProvider.getInstance();

        String param = req.getPathInfo().replaceFirst("/","");


        System.out.println(param);

        if (param.length() != 0) {
            Command c = cm.getCommand(param);
            try {
                c.execute(req, resp);
            } catch (ServiceException e) {
                resp.getWriter().write("{ \"error\" : \"Data was ignored . Reason : incorrect data was inputted\" }");
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                log.error(e);
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CommandProvider cm = CommandProvider.getInstance();

        String param = request.getPathInfo().replaceFirst("/","");


        System.out.println(param);


        if (param.length() != 0) {
            Command c = cm.getCommand(param);
            try {
                c.execute(request, response);
            } catch (ServiceException e) {
                response.getWriter().write("{ \"error\" : \"Data was ignored . Reason : incorrect data was inputted\" }");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                log.error(e);
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CommandProvider cm = CommandProvider.getInstance();
        String param = request.getParameter("command");

        if (param != null && !param.equals("")) {
            Command c = cm.getCommand(param);
            try {
                c.execute(request, response);
            } catch (ServiceException e) {
                response.getWriter().write("{ \"error\" : \"Data was ignored . Reason : incorrect data was inputted\" }");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                log.error(e);
            }

        }

    }

}



