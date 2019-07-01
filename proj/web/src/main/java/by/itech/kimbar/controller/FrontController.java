package by.itech.kimbar.controller;


import by.itech.kimbar.controller.command.Command;
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
    public void init()  {
        try {
            CongratulateJob.sendListOfBirthdayMen();
        } catch (SchedulerException e) {
           log.error(e);
        }

    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder sb = new StringBuilder();
        CommandProvider cm = CommandProvider.getInstance();

        String[] param = req.getPathInfo().split("/");

        sb.append(param[1]).append(param[2].substring(0,1).toUpperCase()).append(param[2].substring(1));

        System.out.println(sb);

        if (sb.length() != 0 ) {
            Command c = cm.getCommand(sb.toString());
            c.execute(req, resp);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder sb = new StringBuilder();
        CommandProvider cm = CommandProvider.getInstance();

        String[] param = req.getPathInfo().split("/");
        sb.append(param[1]).append(param[2].substring(0,1).toUpperCase()).append(param[2].substring(1));

        System.out.println(sb);

        if (sb.length() != 0 ) {
            Command c = cm.getCommand(sb.toString());
            c.execute(req, resp);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder sb = new StringBuilder();
        CommandProvider cm = CommandProvider.getInstance();

        String[] param = request.getPathInfo().split("/");
        sb.append(param[2]);

        System.out.println(sb);

        if (sb.length() != 0 ) {
            Command c = cm.getCommand(sb.toString());
            c.execute(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        CommandProvider cm = CommandProvider.getInstance();
        String param = request.getParameter("command");

        System.out.println(param);
        if (param != null && !param.equals("")) {
            Command c = cm.getCommand(param);
            c.execute(request, response);
        }

    }

}



