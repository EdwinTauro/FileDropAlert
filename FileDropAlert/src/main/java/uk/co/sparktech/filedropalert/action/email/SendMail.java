package uk.co.sparktech.filedropalert.action.email;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Properties;
 
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import uk.co.sparktech.filedropalert.action.ActionPayload;
import uk.co.sparktech.filedropalert.util.property.EmailPropertyReader;
import uk.co.sparktech.filedropalert.util.property.EmailPropertyReader.FILEDROPALERT_EMAIL_ACTION;
 
public class SendMail {
	private final static EmailPropertyReader PROPERTY = EmailPropertyReader.getInstance();

	public static void mail(ActionPayload payload) {
		final String username = PROPERTY.getValue(FILEDROPALERT_EMAIL_ACTION.MAIL_USERNAME);
		final String password = PROPERTY.getValue(FILEDROPALERT_EMAIL_ACTION.MAIL_PASSWORD);
 
		Properties props = new Properties();
		props.put("mail.smtp.auth", PROPERTY.getValue(FILEDROPALERT_EMAIL_ACTION.MAIL_SMTP_AUTH));
		props.put("mail.smtp.starttls.enable", PROPERTY.getValue(FILEDROPALERT_EMAIL_ACTION.MAIL_SMTP_STARTTLS_ENABLE));
		props.put("mail.smtp.host", PROPERTY.getValue(FILEDROPALERT_EMAIL_ACTION.MAIL_SMTP_HOST));
		props.put("mail.smtp.port", PROPERTY.getValue(FILEDROPALERT_EMAIL_ACTION.MAIL_SMTP_PORT));
 
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
 
		try {
 
			Message message = new MimeMessage(session);
			String recepients = PROPERTY.getValue(FILEDROPALERT_EMAIL_ACTION.MAIL_MESSAGE_RECEPIENTS);
			InternetAddress[] recepientsList = InternetAddress.parse(recepients);
			message.setRecipients(Message.RecipientType.TO, recepientsList);
			String subject = PROPERTY.getValue(FILEDROPALERT_EMAIL_ACTION.MAIL_MESSAGE_SUBJECT);
			boolean requireTimestamp = Boolean.parseBoolean(PROPERTY.getValue(FILEDROPALERT_EMAIL_ACTION.MAIL_MESSAGE_INCLUDE_TIMESTAMP));
			
			if (requireTimestamp) {
				subject = subject.concat(DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));
				System.out.println(subject);
			}
			message.setSubject(subject);
			
			// create the message part   
            MimeBodyPart messageBodyPart = new MimeBodyPart();  
            // fill message  
            messageBodyPart.setText(PROPERTY.getValue(FILEDROPALERT_EMAIL_ACTION.MAIL_MESSAGE_BODY));
            
            Multipart multipart = new MimeMultipart();  
            multipart.addBodyPart(messageBodyPart);  
            // Part two is attachment  
            messageBodyPart = new MimeBodyPart();  
            DataSource source = new FileDataSource(payload.getFile());  
            messageBodyPart.setDataHandler(new DataHandler(source));  
            messageBodyPart.setFileName(payload.getName());  
            multipart.addBodyPart(messageBodyPart);  
            // Put parts in message  
            message.setContent(multipart);  
         
			Transport.send(message);
 
            System.out.println("email success....................................");  
			
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}