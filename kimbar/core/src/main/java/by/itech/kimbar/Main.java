package by.itech.kimbar;

import by.itech.kimbar.dao.exception.DaoException;
import by.itech.kimbar.dto.AttachmentDto;
import org.codehaus.jackson.map.ObjectMapper;
import org.quartz.SchedulerException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, DaoException, SQLException, ClassNotFoundException, SchedulerException, InterruptedException {


//
//        JobDetail job1 = JobBuilder.newJob(SimpleJob.class)
//                .withIdentity("job", "group").build();
//
//        Trigger trig1 = TriggerBuilder.newTrigger().withIdentity("simpleTrigger", "group")
//                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5)).build();
//        Scheduler scheduler1 = new StdSchedulerFactory().getScheduler();
//
//
//
//        scheduler1.scheduleJob(job1, trig1);
//        scheduler1.start();
        // EmailUtil.sendEmail("teekeen32@gmail.com","Help me","We gotta problem here");


        AttachmentDto at = new AttachmentDto();
        at.setCommentary("BB");
        at.setId(2);
        at.setName("2");

        AttachmentDto at1 = new AttachmentDto();
        at1.setCommentary("AA");
        at1.setId(1);
        at1.setName("1");

        AttachmentDto[] attachmentDtos = new AttachmentDto[2];
        attachmentDtos[0] = at;
        attachmentDtos[1] = at1;

        String data = "[{\"id\":1,\"commentary\":\"AA\",\"name\":\"1\"},{\"id\":2,\"commentary\":null,\"name\":null}]" ;
        System.out.println(serialize(attachmentDtos));
        System.out.println(deSerialize(data));

    }


    public static String serialize(AttachmentDto[] attachmentDtos) throws IOException {
        ObjectMapper om = new ObjectMapper();

        String res = om.writeValueAsString(attachmentDtos);

        return res;
    }

    public static List deSerialize(String des) throws IOException {
        ObjectMapper om = new ObjectMapper();

        List res = Arrays.asList(om.readValue(des, AttachmentDto[].class));


        return res;
    }
}