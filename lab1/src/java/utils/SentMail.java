package utils;

/**
 *
 * @author pc
 */
import account.AccountDTO;
import com.sun.mail.smtp.SMTPTransport;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;

public class SentMail {

    public boolean sentEmail(String code, AccountDTO acc) {
        boolean sent = false;

        String fromEmail = "chienphse140586@fpt.edu.vn";
        String password = "Cdt3216549";

        try {
            Properties pr = new Properties();
            pr.put("mail.smtp.auth", "true");
            pr.put("mail.smtp.starttls.enable", "true");
            pr.put("mail.smtp.host", "smtp.gmail.com");
            pr.put("mail.smtp.port", 587);

            Session session = Session.getInstance(pr, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            });

            Message mess = new MimeMessage(session);

            mess.setFrom(new InternetAddress(fromEmail));
            mess.setRecipient(Message.RecipientType.TO, new InternetAddress(acc.getUserid()));

            mess.setSubject("Verify account to Manager Resource");
            mess.setText("Go to this link to active your account: localhost:8081/lab1/MainController?username=" + acc.getUserid() + "&key=" + code + "&action=VerifyAccount");

            Transport.send(mess);

            sent = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return sent;
    }

}
