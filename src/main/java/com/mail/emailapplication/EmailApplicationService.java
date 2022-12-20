package com.mail.emailapplication;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Properties;

@Service
//@ConfigurationProperties(prefix=)
public class EmailApplicationService {
	private String messageBody;

	@Value("${from}")
	private String from;

	@Value("${from_name}")
	private String from_name;

	@Value("${smtp_username}")
	private String smtp_username;

	@Value("${smtp_password}")
	private String smtp_password;

	@Value("${host}")
	private String host;

	@Value("${port}")
	private int port;

	public String getMessageBody() {
		return messageBody;
	}

	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}


	public Boolean sendEmail(String messageBody, ArrayList<String> recipients) throws UnsupportedEncodingException, MessagingException {

		String FROM = from;
		String FROMNAME = from_name;

		String SMTP_USERNAME = smtp_username;
		String SMTP_PASSWORD = smtp_password;
		String HOST = host;

		int PORT = port;
		String SUBJECT = "Amazon SES mail test";
		String str = org.apache.commons.text.StringEscapeUtils.unescapeJava(messageBody);
		System.out.println("str"+str);
		String BODY = str;

		Properties props = System.getProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.port", PORT);
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props);

		MimeMessage msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(FROM, FROMNAME));

		for (String emailId : recipients) {
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(emailId));
		}

		msg.setSubject(SUBJECT);
		msg.setContent(BODY, "text/html");

		Transport transport = session.getTransport();

		try {
			System.out.println("from : "+from+", " + "port : "+port);
			System.out.println("Sending Email Using AWS SNS...");
			transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
			transport.sendMessage(msg, msg.getAllRecipients());
			System.out.println("Email sent!");
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("The email was not sent.");
			System.out.println("Error message: " + ex.getMessage());
			return false;

		} finally {
			transport.close();
		}
	}

}
