/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.4.29
  Time: 22:58
*/

package justdj.top.JMSListener;


import justdj.top.pojo.User;
import justdj.top.util.mail.MailUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Component;

import javax.jms.*;

@Component("mySessionAwareMessageListener")
public class MySessionAwareMessageListener implements SessionAwareMessageListener<ObjectMessage>{
	
	@Autowired
	private MailUtils mailUtils;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@Override
	public void onMessage(ObjectMessage message, Session session) throws JMSException {
		User user;
		MessageProducer producer = session.createProducer( message.getJMSReplyTo() );
		Message msg = session.createTextMessage("message receive error! mail send filed! ");
		try{
			
			user = (User)(((ObjectMessage)message).getObject());
			logger.info("即将向邮箱 " + user.getEmail()+ " 发送邮件");
			try{
				mailUtils.sendSimpleMail("justdjtop@163.com",user.getEmail(),"无限考试系统注册验证码",getMailBody(user));
			}catch (MailException e){
				logger.error("邮件发送失败！" + user.getAccount());
				throw new JMSException("邮件发送失败！" + user.getAccount());
			}
			
			logger.info("已向邮箱 " + user.getEmail()+ " 发送邮件");
			
		}catch (JMSException e){
			logger.info("消息队列信息接收异常:"+ e.getMessage());
		}
	}
	
	
	private String getMailBody(User user){
		return "验证码为" +user.getCode() +",该邮箱正在被尝试绑定无限考试系统账号，如不是本人操作，请忽略。";
	}
	//看这里可以执行异步策略

	
}
