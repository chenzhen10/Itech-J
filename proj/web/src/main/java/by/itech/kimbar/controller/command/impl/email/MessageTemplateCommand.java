package by.itech.kimbar.controller.command.impl.email;

import by.itech.kimbar.controller.command.Command;
import by.itech.kimbar.entity.Template;
import by.itech.kimbar.util.TemplateReader;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MessageTemplateCommand implements Command {
    private static final Logger log = Logger.getLogger(MessageTemplateCommand.class);
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp)  {
        ObjectMapper om = new ObjectMapper();

        List<Template> templates = null;
        try {
            templates = readTemplate();
            resp.getWriter().write(om.writeValueAsString(templates));
        } catch (IOException e) {
            log.error(e);
        }
    }


    private List<Template> readTemplate() throws IOException {
        List<Template> result = new ArrayList<>();

        result.add(new Template(0,TemplateReader.defaultTemplate()) );
        result.add(new Template(1,TemplateReader.helpTemplate()) );
        result.add(new Template(2,TemplateReader.birthdayTemplate()));
        result.add(new Template(3,TemplateReader.graduationTemplate()));
        return result;
    }
}
