/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.4.29
  Time: 23:46
*/

package justdj.top.util.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * <p>简单的邮件工具类，实现了简单邮件、附件和html邮件的发送</p>
 * @author hanchao 2018/1/13 11:48
 **/
@Component
public class MailUtils {
	
	@Autowired(required = false)
	private JavaMailSenderImpl javaMailSender;
	
	/**
	 * <p>通过JavaMailSenderImpl.send(SimpleMailMessage)发送简单消息</p>
	 * @author hanchao 2018/1/13 9:56
	 **/
	public void sendSimpleMail(String from,String to,String subject,String message)throws MailException {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom(from);
		simpleMailMessage.setTo(to);
		simpleMailMessage.setSubject(subject);
		simpleMailMessage.setText(message);
		//发送邮件
		javaMailSender.send(simpleMailMessage);
	}
	
	/**
	 * <p>通过JavaMailSenderImpl.send(MimeMessage)以及MimeMessageHelper发送HTML内容和附件</p>
	 * @author hanchao 2018/1/13 11:37
	 **/
	public void sendMimeMail(String from,String to,String subject,String message,int template,String attachPath) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		//通过MimeMessageHelper进行邮件内容的设置
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
		mimeMessageHelper.setFrom(from);
		mimeMessageHelper.setTo(to);
		mimeMessageHelper.setSubject(subject);
		//发送html内容
		mimeMessageHelper.setText(MailTemplate.getText(MailTemplate.TEMPLATE_TEST,message),true);
		//发送附件
		File testAttach = new File(attachPath);
		if (testAttach.exists()){
			mimeMessageHelper.addAttachment("test.jpg",testAttach);
		}
		//发送邮件
		javaMailSender.send(mimeMessage);
	}
}
