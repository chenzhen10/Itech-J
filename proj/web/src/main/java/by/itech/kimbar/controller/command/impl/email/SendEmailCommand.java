package by.itech.kimbar.controller.command.impl.email;

import by.itech.kimbar.controller.command.Command;
import by.itech.kimbar.service.impl.email.EmailSender;
import by.itech.kimbar.util.DateConverter;
import by.itech.kimbar.util.NumericChecker;
import by.itech.kimbar.util.TemplateReader;
import org.apache.log4j.Logger;
import org.stringtemplate.v4.ST;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SendEmailCommand implements Command {
    private static final Logger log = Logger.getLogger(SendEmailCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String message = req.getParameter("message");
        String topic = req.getParameter("topic");
        String receiverEmail = req.getParameter("receiverEmails");
        String param = req.getParameter("parameter");

        //template variables
        String idTemplate = req.getParameter("idTemplate");
        String recName = req.getParameter("receiversName");


        String[] receiverEmails = receiverEmail.split(",");

        String[] receiversName = recName.split(",");

        String[] params = param.split(",");

        log.debug("Parameters  : " + Arrays.toString(receiversName) + idTemplate + topic + Arrays.toString(receiverEmails) + Arrays.toString(params) + message);

        if (idTemplate != null && !idTemplate.equals("undefined")) {
            assemblyTemplate(receiversName, idTemplate, topic, receiverEmails, params);
        } else {
            for (String rec : receiverEmails) {
                EmailSender.sendEmail(rec, topic, message);
            }
        }
    }

    private static void assemblyTemplate(String[] receiversName, String idTemplate, String topic, String[] receiverEmails, String[] param) throws IOException {

        List<String> templates = new ArrayList<>();
        templates.add(TemplateReader.defaultTemplate());
        templates.add(TemplateReader.helpTemplate());
        templates.add(TemplateReader.birthdayTemplate());
        templates.add(TemplateReader.graduationTemplate());

        Integer id = NumericChecker.check(idTemplate);
        int counter = 0;

        String parameter = null;
        int length = param.length;
        for (String names : receiversName) {
            ST template = new ST(templates.get(id));
            if (length == 0) {
                parameter = "";
            } else {
                parameter = param[counter];
            }
            String message = fillMessage(id, template, parameter, names);
            cleanTemplate(template, id);
            EmailSender.sendEmail(receiverEmails[counter], topic, message);
            counter++;
        }
    }

    private static String fillMessage(Integer idTemplate, ST template, String param, String name) {

        template.add("name", name);

        if (idTemplate == 1) {
            template.add("maritalStatus", param);
        } else if (idTemplate == 2) {
            template.add("date", param);
        } else if (idTemplate == 3) {
            template.add("workplace", param);
        }
        return template.render();
    }

    private static void cleanTemplate(ST template, Integer idTemplate) {
        if (idTemplate == 1) {
            template.remove("maritalStatus");
        } else if (idTemplate == 2) {
            template.remove("date");
        } else if (idTemplate == 3) {
            template.remove("workplace");
        }
        template.remove("name");
    }
}
