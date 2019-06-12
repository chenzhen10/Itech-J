package by.itech.kimbar.controller;




import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FrontController extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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



