package by.itech.kimbar.controller.command.impl.email;

import by.itech.kimbar.controller.command.Command;
import by.itech.kimbar.entity.Template;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MessageTemplateCommand implements Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Template congratulateTempl = new Template(1,"Dear name i want to congratulate you with graduation");
        Template helpTempl = new Template(2,"Hey name when you will be free please call me");
        Template surpriseTempl = new Template(3,"name i want to congratulate you with graduation");

        List<Template> templates = new ArrayList<>();
        templates.add(congratulateTempl);
        templates.add(helpTempl);
        templates.add(surpriseTempl);

        ObjectMapper om = new ObjectMapper();

        resp.getWriter().write(om.writeValueAsString(templates));
    }
}
