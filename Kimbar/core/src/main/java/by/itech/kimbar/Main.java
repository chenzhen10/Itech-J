package by.itech.kimbar;

import by.itech.kimbar.dao.exception.DaoException;
import by.itech.kimbar.entity.User;
import by.itech.kimbar.service.impl.email.EmailUtil;
import org.codehaus.jackson.map.ObjectMapper;
import org.quartz.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException, DaoException, SQLException, ClassNotFoundException, SchedulerException, InterruptedException {
        //Connection c = ConnectionPool.getInstance().getConnection();


        // boolean b =  usr.createUser("Jow","Robbinson",null,null,null,null,null,null,null,null,new Address("Belarus",null,null,null,null,0));
//        PhoneDaoImpl ph = new PhoneDaoImpl();
//        boolean res = ph.createPhone(375,44,4852703, String.valueOf(Phone.Type.Mobile),"Brand nem sim card",3);
//        System.out.println(res);
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
        EmailUtil.sendEmail("teekeen32@gmail.com","Help me","We gotta problem here");


        // jsonTest();
    }


    private static void jsonTest() throws IOException {
        ObjectMapper om = new ObjectMapper();

        User user = new User("John", "Williams");
        User user1 = new User("Kate", "Sapkovksy");
        User user2 = new User("Murat", "Baurman");
        ArrayList<User> users = new ArrayList<>();
        users.add(user);
        users.add(user1);
        users.add(user2);
        String result = new ObjectMapper().writeValueAsString(users);
        for (String result1 : result.split("}"))
            System.out.println(result1);


    }

}
