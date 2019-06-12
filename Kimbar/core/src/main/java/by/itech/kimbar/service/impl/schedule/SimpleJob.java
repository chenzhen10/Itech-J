package by.itech.kimbar.service.impl.schedule;

import by.itech.kimbar.service.impl.email.EmailUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class SimpleJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        EmailUtil.sendEmail("teekeen32@gmail.com","Help me","We gotta problem here");
        System.out.println("Job done");
    }
}
