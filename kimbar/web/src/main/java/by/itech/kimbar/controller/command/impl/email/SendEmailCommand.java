package by.itech.kimbar.controller.command.impl.email;

import by.itech.kimbar.controller.command.Command;
import by.itech.kimbar.service.impl.email.EmailSender;
import by.itech.kimbar.util.NumericChecker;
import org.stringtemplate.v4.ST;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SendEmailCommand implements Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String message = req.getParameter("message");
        String topic = req.getParameter("topic");
        String receiverEmail = req.getParameter("receiverEmails");

        //template variables
        String idTemplate = req.getParameter("idTemplate");
        String recName = req.getParameter("receiversName");


        String[] receiverEmails = receiverEmail.split(",");

        String[] receiversName = recName.split(",");

        if (idTemplate != null && !idTemplate.equals("")) {
            assemblyTemplate(receiversName, idTemplate,topic,receiverEmails);
        } else {
            for (String rec : receiverEmails) {
                EmailSender.sendEmail(rec, topic, message);
            }
        }



    }

    private static void assemblyTemplate(String[] receiversName, String idTemplate,String topic,String[] receiverEmails) {
        ST congratulateTemplate = new ST("Dear <name> i want to congratulate you with graduation");
        ST helpTemplate = new ST("Hey <name> when you will be free please call me ");
        ST surpriseTemplate = new ST(" <name> i want to go somewhere what do you think about it ? Write me later");

        List<ST> template = new ArrayList<>();
        template.add(congratulateTemplate);
        template.add(helpTemplate);
        template.add(surpriseTemplate);
        int counter = 0;

        for (String names : receiversName) {
            ST currentTemplate = template.get(NumericChecker.check(idTemplate) - 1);
            currentTemplate.add("name", names);
            String message = currentTemplate.render();
            currentTemplate.remove("name");
            EmailSender.sendEmail(receiverEmails[counter], topic, message);
            counter++;
        }
    }
}
