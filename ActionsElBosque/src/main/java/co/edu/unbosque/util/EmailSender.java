package co.edu.unbosque.util;

import javax.mail.Session;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Message;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;
import javax.mail.Transport;
import javax.mail.MessagingException;
import java.util.Properties;

public class EmailSender {

	public static void sendEmail(String toEmail, String subject, String content) {
		final String username = "jpbotmjpu@gmail.com";
		final String password = "ljfw pnna nlpm qgwq";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "465");
		props.put("mail.smtp.starttls.required", "true");
		props.put("mail.smtp.ssl.protocols", "TLSv1.2");

		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
			message.setSubject(subject);
			message.setText(content);

			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		System.out.println("Correo enviado.");
	}

	public static void main(String[] args) {
		EmailSender.sendEmail("juanpuc004@gmail.com", "Verificar 2", "Funciono 2");
	}

}