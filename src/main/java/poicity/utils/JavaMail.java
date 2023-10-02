package poicity.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.apache.catalina.filters.ExpiresFilter.XServletOutputStream;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import poicity.entity.User;

public class JavaMail {

	private static String fromEmail = "christian.llovera@telsone.it";
	private static String fromPass = "telsone_87620";

	private static Session inizializzaSession() {
		Properties props = new Properties();

		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.ssl.trust", "*");
		props.put("mail.smtp.host", "mail.telsone.it");
		props.put("mail.smtp.port", "465");

		Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, fromPass);
			}
		});

		return session;
	}

	public static void mandaEmailXresetPass(String emailTo, String newPass, String nomeUtente) {

		String body = "Hi " + nomeUtente + ",\r\n" + "\r\n"
				+ "You recently requested to reset the password for your PoiCity account. We have reset your password, you can find it below:\r\n"
				+ "\r\n" + newPass + "\r\n" + "\r\n" + "Please change this password as soon as possible." + "\r\n"
				+ "If you did not request a password reset, please ignore this email or reply to let us know.\r\n"
				+ "\r\n" + "Thanks," + "\n" + "The PoiCity team";

		try {
			Message message = new MimeMessage(inizializzaSession());
			message.setFrom(new InternetAddress(fromEmail, "PoiCity Services"));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
			message.setSubject("Password reset");
			message.setText(body);
			Transport.send(message);
			System.out.println("Email Message Sent Successfully");
		} catch (MessagingException | UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public static void mandaEmailXresetPass2(User user, String newPass) {

		try {
			Message message = new MimeMessage(inizializzaSession());
			message.setFrom(new InternetAddress(fromEmail, "PoiCity Services"));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
			message.setRecipient(Message.RecipientType.BCC, new InternetAddress("christian.llovera@telsone.it"));
			message.setSubject("Password reset");
			message.setContent(getStringFromHtml2(user, newPass), "text/html; charset=utf-8");
			Transport.send(message);
			System.out.println("Email Message Sent Successfully to: " + user.getEmail());
		} catch (MessagingException | UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	private static String getStringFromHtml2(User user, String newPass) {
		String content = "";
		try {
			InputStream stream = JavaMail.class.getResourceAsStream("mail.html");
			content = new String(stream.readAllBytes());
			
			if (user.getUsername() != null) {
				if(!user.getUsername().equals("")) {
					content = content.replace("[nomeUtente]", user.getUsername());
				} else {
					content = content.replace("[nomeUtente]", "Customer");
				}
			} else if(user.getName() != null){
				if(!user.getName().equals("")) {
					content = content.replace("[nomeUtente]", user.getName());
				} else {
					content = content.replace("[nomeUtente]", "Customer");
				}
			} else {
				content = content.replace("[nomeUtente]", "Customer");
			}
			
			content = content.replace("[newPass]", newPass);
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		return content;
	}

	private static String getStringFromHtml(String newPass, String nomeUtente) {
		StringBuilder contentBuilder = new StringBuilder();

		try {
			BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\Telsone\\Desktop\\mail\\mail.html"));
//		    BufferedReader in = new BufferedReader(new FileReader("file://src/main/java/poicity/utils/mail.html"));

			String str;
			while ((str = in.readLine()) != null) {
				contentBuilder.append(str);
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String content = contentBuilder.toString();
		if (nomeUtente != null) {
			content = content.replace("[nomeUtente]", nomeUtente);
		} else {
			content = content.replace("[nomeUtente]", "Customer");
		}
		content = content.replace("[newPass]", newPass);

		return content;
	}

}
