package com.entity;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.regex.*;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;
public class Student {
	
    private static final String ALIDM_SMTP_HOST = "smtpdm.aliyun.com";
    private static final String ALIDM_SMTP_PORT= "25";
    
    public String SendEmail(String address, String msg)  
    {
    	final Properties props = new Properties();
    	props.put("mail.smtp.auth", "true");
    	props.put("mail.smtp.host", ALIDM_SMTP_HOST);
    	props.put("mail.smtp.port", ALIDM_SMTP_PORT);
    	
    	props.put("mail.user", "aelfyao@aelfyao.xyz");
    	props.put("mail.password", "LIyao530215");
    	Authenticator authenticator = new Authenticator() 
    	{
    		@Override
    		protected PasswordAuthentication getPasswordAuthentication()
    		{
                // 用户名、密码
                String userName = props.getProperty("mail.user");
                String password = props.getProperty("mail.password");
                return new PasswordAuthentication(userName, password);
    	    };
    	};
    	Session mailSession = Session.getInstance(props, authenticator);
    	mailSession.setDebug(true);
    	MimeMessage message = new MimeMessage(mailSession)
    	{
    		
    	};
        InternetAddress from;
		try {
			from = new InternetAddress("aelfyao@aelfyao.xyz", "Agilities");
			message.setFrom(from);
	        InternetAddress to = new InternetAddress(address);
	        message.setRecipient(MimeMessage.RecipientType.TO, to);
	        //InternetAddress[] adds = new InternetAddress[2];
	        //adds[0] = new InternetAddress("xxxxx@qq.com");
	        //adds[1] = new InternetAddress("xxxxx@qq.com");
	        //message.setRecipients(Message.RecipientType.TO, adds);
	        
            
            message.setSubject("The Dragon Blade");
            // 设置邮件的内容体
            message.setContent(msg, "text/html;charset=UTF-8");
            Transport.send(message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		   return "Y";
		}
    	return "Y";
    }
    public String SendEmailBatch(List<String> addr, String msg) 
    {
    	final Properties props = new Properties();
    	props.put("mail.smtp.auth", "true");
    	props.put("mail.smtp.host", ALIDM_SMTP_HOST);
    	props.put("mail.smtp.port", ALIDM_SMTP_PORT);
    	
    	props.put("mail.user", "aelfyao@aelfyao.xyz");
    	props.put("mail.password", "LIyao530215");
    	Authenticator authenticator = new Authenticator() 
    	{
    		@Override
    		protected PasswordAuthentication getPasswordAuthentication()
    		{
                // 用户名、密码
                String userName = props.getProperty("mail.user");
                String password = props.getProperty("mail.password");
                return new PasswordAuthentication(userName, password);
    	    };
    	};
    	Session mailSession = Session.getInstance(props, authenticator);
    	mailSession.setDebug(true);
    	MimeMessage message = new MimeMessage(mailSession)
    	{
    		
    	};
        InternetAddress from;
		try {
			from = new InternetAddress("aelfyao@aelfyao.xyz", "Aglities");
			message.setFrom(from);
			int size = addr.size();
	        InternetAddress[] adds = new InternetAddress[size];
	        for(int i = 0; i<size ; i++) 
	        {
	        	adds[i] = new InternetAddress(addr.get(i));
	        }
	        message.setRecipients(Message.RecipientType.TO, adds);
	        
            
            message.setSubject("The Dragon Blade");
            // 设置邮件的内容体
            message.setContent(msg, "text/html;charset=UTF-8");
            Transport.send(message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		   return "N";
		}
    	return "Y";
    }
    public String ValidateEmailAddress(String _url) 
    {
    	String input = "^[0-9a-zA-Z_]{0,19}@[0-9a-zA-Z]{1,13}\\.[com,cn,net]{1,3}$";
    	Pattern pattern = Pattern.compile(input);
    	Matcher matcher=pattern.matcher(_url);
    	if (matcher.matches())
    	{
    		return "Y";
    	}
    	return "N";
    }
}
