package by.itech.kimbar.service.impl.schedule;

import by.itech.kimbar.service.ServiceProvider;
import by.itech.kimbar.service.exception.ServiceException;
import by.itech.kimbar.service.impl.email.EmailSender;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.IOException;

public class CongratulateJob implements Job {
    private static final Logger log = Logger.getLogger(CongratulateJob.class);
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        if (getBirthdayMen() != null && getBirthdayMen().length() > 5){
            EmailSender.sendEmail("teekeen32@gmail.com","Birthday men list",getBirthdayMen());
        }else{
            EmailSender.sendEmail("teekeen32@gmail.com","Birthday men list","No one has a birthday today");
        }

    }

    public static void sendListOfBirthdayMen() throws SchedulerException {
        JobDetail job = JobBuilder.newJob(CongratulateJob.class).build();

        Trigger t = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule(" 0 0 9 1/1 * ? *")).build();

        Scheduler sc = StdSchedulerFactory.getDefaultScheduler();

        sc.start();
        sc.scheduleJob(job,t);
    }

    private static String getBirthdayMen(){
        String result = null;
        ObjectMapper om = new ObjectMapper();
        try {
            result = om.writeValueAsString(String.valueOf(ServiceProvider.getInstance().getUserService().birthdayMen()));
        } catch (ServiceException | IOException  e) {
            log.error(e);
        }
        return  result;
    }
}
