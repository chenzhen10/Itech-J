package by.itech.kimbar.controller.command.impl.phone;

import by.itech.kimbar.controller.command.Command;
import by.itech.kimbar.dto.deserialize.DtoProvider;
import by.itech.kimbar.dto.Dto;
import by.itech.kimbar.service.ServiceProvider;
import by.itech.kimbar.service.exception.ServiceException;
import by.itech.kimbar.service.phone.PhoneService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class UpdatePhoneCommand  implements Command  {
    private static final Logger log = Logger.getLogger(UpdatePhoneCommand.class);
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServiceException {
        PhoneService ps = ServiceProvider.getInstance().getPhoneService();

        List<Dto> phones = DtoProvider.getInstance().getPhoneDto().deSerialize(req);

        try {
            log.debug("Parameters : " + phones);
            log.debug( ps.updatePhone(phones));
        } catch (ServiceException e) {
            log.error(e);
            throw new ServiceException();
        }
    }


}
