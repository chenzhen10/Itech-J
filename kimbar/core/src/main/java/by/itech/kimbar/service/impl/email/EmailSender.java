package by.itech.kimbar.service.impl.email;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class EmailSender {

    private static final String fromEmail = "teekeen33@gmail.com";
    private static final String password = "2201946az";

    private EmailSender(){}

    public static void sendEmail(String toEmail, String subject, String body){
        try
        {

            Authenticator auth = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            };

            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
            props.put("mail.smtp.port", "587"); //TLS Port
            props.put("mail.smtp.auth", "true"); //enable authentication
            props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

            Session session = Session.getInstance(props, auth);
            MimeMessage msg = new MimeMessage(session);


            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");


            msg.setFrom(new InternetAddress(fromEmail, "NoReply"));

            msg.setReplyTo(InternetAddress.parse(fromEmail, false));

            msg.setSubject(subject, "UTF-8");

            msg.setText(body, "UTF-8");

            msg.setSentDate(new Date());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            Transport.send(msg);

        }
        catch (Exception e) {
            // log
            e.printStackTrace();
        }
    }
}