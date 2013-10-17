package com.eadmarket.pangu.util.email.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import lombok.Setter;

import org.apache.commons.mail.HtmlEmail;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eadmarket.pangu.util.email.EmailException;
import com.eadmarket.pangu.util.email.EmailService;
import com.google.common.collect.Maps;

/**
 * 邮件服务的默认实现
 * 
 * @author liuyongpo@gmail.com
 */
class EmailServiceImpl implements EmailService {

	private final static Logger LOG = LoggerFactory.getLogger(EmailServiceImpl.class);
	
	private Map<String, TemplateConfig> templateIdToTemplateConfig = Maps.newHashMap();
	
	@Setter private String emailConfigLocation = "email/email-config.xml";
	
	@Setter private String charset = "UTF-8";
	@Setter private String hostName = "smtp.exmail.qq.com";
	@Setter private String fromEmail = "system@eadmarket.com";
	@Setter private int smtpport = 465;
	@Setter private String username = "system@eadmarket.com";
	@Setter private String password = "1admarket";
	
	@Override
	public void sendEmail(String templateId, String email,
			Map<String, Object> content) throws EmailException {
		
		if (!templateIdToTemplateConfig.containsKey(templateId)) {
			throw new EmailException("No such a templateId in emailConfig :" + templateId);
		}
		
		TemplateConfig templateConfig = templateIdToTemplateConfig.get(templateId);
		
		try {
			HtmlEmail htmlEmail = new HtmlEmail();
			
			htmlEmail.setCharset(charset);
			htmlEmail.setHostName(hostName);
			htmlEmail.setSmtpPort(smtpport);
			htmlEmail.setSSLOnConnect(true);
			htmlEmail.setAuthentication(username, password);
			
			htmlEmail.setFrom(fromEmail, "eadmarket系统邮件");
			htmlEmail.addTo(email);
			htmlEmail.setHtmlMsg(VelocityUtil.rendVmFile(content, templateConfig.contentPath));
			htmlEmail.setSubject(templateConfig.subject);
			
			htmlEmail.send();
		} catch (org.apache.commons.mail.EmailException ex) {
			throw new EmailException("failed to send email to " + email, ex);
		}
	}
	
	public void initConfig() {
		
		SAXReader reader = new SAXReader();
		InputStream emailConfigStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(emailConfigLocation);
		try {
			Document doc = reader.read(emailConfigStream);
			@SuppressWarnings("unchecked")
			List<Element> selectNodes = doc.selectNodes("/emailConfig/template");
			for (Element element : selectNodes) {
				TemplateConfig config = convertTemplateNodeToTemplateConfig(element);
				templateIdToTemplateConfig.put(config.id, config);
			}
			LOG.warn("Got emailConfig : " + templateIdToTemplateConfig);
		} catch (DocumentException ex) {
			throw new RuntimeException(ex);
		} finally {
			try {
				emailConfigStream.close();
			} catch (IOException ex) {
				throw new RuntimeException("failed to close file stream : " + emailConfigLocation, ex);
			}
		}
	}
	
	static final TemplateConfig convertTemplateNodeToTemplateConfig(Element templateNode) {
		Attribute id = templateNode.attribute("id");
		Attribute subject = templateNode.attribute("subject");
		Attribute contentPath = templateNode.attribute("contentPath");
		TemplateConfig config = new TemplateConfig();
		config.id = id.getData().toString();
		config.subject = String.valueOf(subject.getData());
		config.contentPath = String.valueOf(contentPath.getData());
		return config;
	}
	
	static class TemplateConfig {
		String id;
		
		String subject;
		
		String contentPath;
	}
	
	public static void main(String[] args) throws EmailException {
		EmailServiceImpl emailService = new EmailServiceImpl();
		emailService.initConfig();
		Map<String, Object> newHashMap = Maps.newHashMap();
		newHashMap.put("name", "百度嘟嘟嘟");
		newHashMap.put("title", "高流量高回报广告位");
		emailService.sendEmail("201410141717", "594974221@qq.com", newHashMap);
	}
}
