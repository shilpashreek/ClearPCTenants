package com.qa.clearpc.utility;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMultipart;

import org.jsoup.Jsoup;

import com.qa.clearpc.BaseClass.BaseClass;

public class MailUtils extends BaseClass {

	// port-465 host-mail.primefocus.com
	public String readTheLatestMail(final String host, String username, String password, String Expected_Sender,
			String Expected_subject, String Filepath) throws MessagingException, IOException {
		String mailBody = null;
		String encoding = "";
		// int otp=0;
		// String otpString = null;

		Properties properties = new Properties();
		properties.put("mail.pop3.host", host);
		properties.put("mail.pop3.host", "993"); // 993
		properties.put("mail.pop3.starttls.enable", "true");
		properties.put("mail.pop3.starttls.required", "true");
		properties.put("mail.pop3.disabletop", "true");
		final Session emailSession = Session.getDefaultInstance(properties);
		final Store store = emailSession.getStore("imaps"); // pop3s
		store.connect(host, username, password);
		Folder emailFolder = store.getFolder("INBOX");
		emailFolder.open(Folder.READ_ONLY);
		// emailFolder.open(1);
		final Message[] messages = emailFolder.getMessages();
		// Message[] messages = emailFolder.getMessages();

		// unseen messages
		// messages=emailFolder.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));

		// System.out.println("new messagges count " +emailFolder.getNewMessageCount());

		int messageLength = messages.length;
		log.info("started iterating through mail");
		for (int i = messages.length - 1, j = 1; j <= 10; i--, j++)
		// for(int i=messageLength-1; i>0 ;--i)
		// for(int i=messageLength-1; i<=10 ;--i)
		{
			final Message message = messages[i];
			// messages.getFrom()[i];
			String SubjectOfMail = message.getSubject().replaceAll("[^\\x20-\\x7e]", " ").trim();
			SubjectOfMail = SubjectOfMail.replaceAll("\\s+", " ");

			/*
			 * byte[] SubjectOf_Mail =
			 * message.getSubject().getBytes(StandardCharsets.UTF_8); String SubjectOfMail =
			 * new String(SubjectOf_Mail, StandardCharsets.UTF_8).trim();
			 */
			// SubjectOfMail.replace("&nbsp;", " ");
			String sender = message.getFrom()[0].toString();
			log.info("subject of the mail-->" + SubjectOfMail);
			log.info("sender of the mail-->" + sender);
			if (sender.equalsIgnoreCase(Expected_Sender) && SubjectOfMail.equalsIgnoreCase(Expected_subject)) {
				FileWriter fWriter = new FileWriter(Filepath);
				final BufferedWriter writer = new BufferedWriter(fWriter);

				System.out.println("********starting of mail body******");
				mailBody = getTextFromMessage(message).toString();

				/*
				 * byte[] mail_body = mailBody.getBytes(StandardCharsets.UTF_8); encoding = new
				 * String(mail_body, StandardCharsets.UTF_8);
				 */

				// System.out.println(mailBody);
				System.out.println(mailBody);
				System.out.println("********end of mail body******");

				writer.write(getTextFromMessage(message).toString()); // writes the contents of msg to file specified in
																		// the path
				writer.newLine();
				writer.close();
				System.out.println(
						"The email found with given sender and a html file is created with name :: " + Filepath);
				// return true;
				// extract OTP from mail body
				// otpString = getOtpFromMailBody(mailBody, otpString); // changing here for
				// shareAsset scenario ,commented on 11/11
				return mailBody;
				// return otpString;
			}

		}
		System.out.println("Unable to find any mail with given sender");
		// return false;
		// return mailBody;
		return mailBody;

	}

	public String getOtpFromMailBody(String mailBody) {
		String otpString = null;
		if (mailBody != null && !mailBody.isEmpty()) {
			String body[] = mailBody.split(":");
			String a[] = body[1].split("\\.");
			otpString = a[0].trim();
			// otp = Integer.parseInt(otpString);
		} else if (mailBody == null) {
			otpString = null;
			System.out.println("OTP is not found");
		}
		return otpString;
	}

	private static String getTextFromMessage(final Message message) throws MessagingException, IOException {
		String result = "";
		if (message.isMimeType("text/plain")) {
			result = message.getContent().toString();
		} else if (message.isMimeType("text/html")) {
			String html = (String) message.getContent();
			result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
		} else if (message.isMimeType("multipart/*")) {
			final MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
			result = getTextFromMimeMultipart(mimeMultipart);
		}
		return result;
	}

	private static String getTextFromMimeMultipart(final MimeMultipart mimeMultipart)
			throws MessagingException, IOException {
		String result = "";
		for (int count = mimeMultipart.getCount(), i = 0; i < count; ++i) {
			final BodyPart bodyPart = mimeMultipart.getBodyPart(i);
			if (bodyPart.isMimeType("text/plain")) {
				result = String.valueOf(result) + "\n" + bodyPart.getContent();
				break;
			}
			if (bodyPart.isMimeType("text/html")) {
				final String html = (String) bodyPart.getContent();
				result = String.valueOf(result) + "\n" + Jsoup.parse(html).text();
			} else if (bodyPart.getContent() instanceof MimeMultipart) {
				result = String.valueOf(result) + getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());
			}
		}
		return result;
	}

	/*
	 * public void getCurrentDateAndTime() { Calendar curDT =
	 * Calendar.getInstance(); Date curTime = curDT.getTime(); String dateInString =
	 * String.valueOf(curTime);
	 * 
	 * }
	 */

	/*
	 * public Date getUpdatedDateAndTime(Calendar curDT) { curDT.add(curDT.MINUTE,
	 * 5); return curDT.getTime();
	 * 
	 * }
	 */

}
