package com.syard.common.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailUtil {
	private final Logger logger = LoggerFactory.getLogger(MailUtil.class);	
	private Properties ps;

	public MailUtil() {
		InputStream in=null;
		try {
			String path = Thread.currentThread().getContextClassLoader()
					.getResource("").getPath();
			logger.info(path);
			in = new BufferedInputStream(
					new FileInputStream(path.replace("%20", " ")+"/mail.properties"));
			ps = new Properties();
			ps.load(in);
			for (Object key : ps.keySet()) {
				logger.info(key + ":" + ps.getProperty((String) key));
			}
		} catch (Exception e) {
			e.printStackTrace();
			ps = null;
		}finally{
			try {
				if(in!=null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**开始投递邮件
	 * @throws Exception */
	@SuppressWarnings("rawtypes")
	public void sendMail(ArrayList bodypartArrayList,List<String> mailTo,String subject,String mailType) throws Exception{
		Multipart multipart = new MimeMultipart();
		for(int i=0;i<bodypartArrayList.size();i++){
			multipart.addBodyPart((BodyPart) bodypartArrayList.get(i));
		}
		Session mailSession = Session.getInstance(ps);
		MimeMessage mailMessage = new MimeMessage(mailSession);
		for(int i=0;i<mailTo.size();i++){
			InternetAddress mailToAddress = new InternetAddress(mailTo.get(i));
			if(mailType.equalsIgnoreCase("to")){
				mailMessage.addRecipient(Message.RecipientType.TO, mailToAddress);
			}else if(mailType.equalsIgnoreCase("cc")){
				mailMessage.addRecipient(Message.RecipientType.CC, mailToAddress);
			}else if(mailType.equalsIgnoreCase("bcc")){
				mailMessage.addRecipient(Message.RecipientType.BCC, mailToAddress);
			}else{
				throw new Exception("Unknown mailType: "+mailType+"!");
			}
		}
		mailMessage.setFrom(new InternetAddress(ps.getProperty("username")));
		mailMessage.setSubject(subject);
		mailMessage.setSentDate(new Date());
		mailMessage.setContent(multipart);
		mailMessage.saveChanges();
		Transport transport = mailSession.getTransport("smtp");
		//以smtp方式登录邮箱
		transport.connect(ps.getProperty("mail.smtp.host"),
				ps.getProperty("username"),
				ps.getProperty("password"));
		//发送邮件，其中第二个参数是所有已设好的收件人地址
		transport.sendMessage(mailMessage, mailMessage.getAllRecipients());
		transport.close();
	}

	public boolean send(String[] to, String[] cc, String[] bcc, String subject,
			String body) throws Exception {
		if (ps != null) {
			Session smtpSession = Session.getInstance(ps);
			Message mail = new MimeMessage(smtpSession);
			if ((to != null) && (to.length > 0)) {
				InternetAddress[] tos = new InternetAddress[to.length];
				for (int i = 0; i < to.length; i++) {
					tos[i] = new InternetAddress(to[i]);
				}
				mail.setRecipients(Message.RecipientType.TO, tos);

				if ((cc != null) && (cc.length > 0)) {
					InternetAddress[] ccs = new InternetAddress[cc.length];
					for (int i = 0; i < cc.length; i++) {
						ccs[i] = new InternetAddress(cc[i]);
					}
					mail.setRecipients(Message.RecipientType.CC, tos);
				}
				if ((bcc != null) && (bcc.length > 0)) {
					InternetAddress[] bccs = new InternetAddress[bcc.length];
					for (int i = 0; i < bcc.length; i++) {
						bccs[i] = new InternetAddress(bcc[i]);
					}
					mail.setRecipients(Message.RecipientType.BCC, tos);
				}
				mail.setFrom(new InternetAddress(ps.getProperty("username")));
				mail.setSubject(subject);

				MimeMultipart allParts = new MimeMultipart();
				MimeBodyPart textBody = new MimeBodyPart();
				textBody.setContent(body, ps.getProperty("encoding"));
				allParts.addBodyPart(textBody);
				Transport transport = smtpSession.getTransport();
				mail.setContent(allParts);
				mail.saveChanges();
				try {
					transport.connect(ps.getProperty("mail.smtp.host"),
							ps.getProperty("username"),
							ps.getProperty("password"));
					transport.sendMessage(mail,
							mail.getRecipients(Message.RecipientType.TO));
					return true;
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				} finally {
					transport.close();
				}
			}
		} else {
			logger.info("Could not load the property file");
		}
		return false;
	}
	
	public String getValue(String key){
		String value = (String) ps.get(key);
		return value;
	}

	/**
	 * 将byte[]作为文件添加作为附件
	 * @param filebyte附件文件的字节数组
	 * @param displayFileName 在邮件中想要显示的文件名
	 * @throws UnsupportedEncodingException 
	 * @throws MessagingException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList addAttachmentFrombyte(byte[] filebyte,String displayFileName) throws UnsupportedEncodingException, MessagingException{
		ArrayList bodypartArrayList = new ArrayList();
		BodyPart bodyPart = new MimeBodyPart();
		ByteDataSource fds = new ByteDataSource(filebyte,displayFileName);
		DataHandler dh = new DataHandler(fds);
		String displayfinename = "";
		displayfinename = MimeUtility.encodeWord(displayFileName,"gb2312", null);
		bodyPart.setFileName(displayfinename);
		bodyPart.setDataHandler(dh);
		bodypartArrayList.add(bodyPart);
		return bodypartArrayList;
	}

}
