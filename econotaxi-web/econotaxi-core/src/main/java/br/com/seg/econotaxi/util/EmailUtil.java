/**
 * 
 */
package br.com.seg.econotaxi.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import br.com.seg.econotaxi.vo.EmailVO;

/**
 * @author Bruno rocha
 *
 */
public class EmailUtil {

	/**
	 * Método responsável por enviar e-mail
	 * 
	 * @param email
	 * @throws MessagingException 
	 * @throws UnsupportedEncodingException 
	 */
	public static void sendEmail(EmailVO email) throws UnsupportedEncodingException, MessagingException {
		/**
		 * @author Iago Ferreira & Obede Doreto
		 * [fix]: Memory leak, recursive invocation in MonitoraSchedule.java
		 * */
		if(email == null || email.getDestinatarios() == null) {
			return;
		}
		String[] dests = email.getDestinatarios().split(",");
		List<String[]> destinatarios = new ArrayList<String[]>();
		for (String dest : dests) {
			destinatarios.add(new String[]{dest, dest});
		}
		StringBuilder message = new StringBuilder();
		message.append(email.getMensagem());
		sendEmail(email.getServidorSMTP(), email.getUsuarioEmail(), email.getSenhaEmail(), 
				email.getNomeRemetente(), email.getPortaServidorSMTP(), email.getAssunto(), message, 
				destinatarios, email.getTitulo());
	}
	
	/**
	 * Método responsável por enviar e-mail
	 * 
	 * @param host
	 * @param emailSource
	 * @param password
	 * @param destinatario
	 * @param port
	 * @param subject
	 * @param message
	 * @param recipients
	 * @param destName
	 * @throws UnsupportedEncodingException
	 * @throws MessagingException
	 */
	public static final void sendEmail(String host, String emailSource, String password, String destinatario, String port,
			String subject, StringBuilder message, List<String[]> recipients, String destName) throws UnsupportedEncodingException, MessagingException {
		
		MimeMessage email = new MimeMessage(getSession(emailSource, password, host, port));
		for (String[] recipient : recipients) {
			email.addRecipient(RecipientType.TO, new InternetAddress(recipient[0], recipient[1]));
		}
		email.setFrom(new InternetAddress(destinatario, destName));
		email.setSubject(subject, "UTF-8");
		email.setHeader("Content-Type", "text/plain; charset=UTF-8");
		email.setContent(message.toString(), "text/html; charset=UTF-8");
		Transport.send(email);
	}
		
	/**
	 * Método responsável por recuperar a sessão do e-mail
	 * 
	 * @return a sessão do e-mail
	 */
	public static Session getSession(String user, String pass, String host, String port) {
		
		Session sessao = null;
		Properties properties = new Properties();
		properties.setProperty("mail.smtp.host", host);
		properties.setProperty("mail.smtp.port", port);
		properties.setProperty("mail.smtp.socketFactory.port", port);
		if (pass != null && !pass.isEmpty()) {
			Authenticator authenticator = new Authenticator(user, pass);
			properties.setProperty("mail.smtp.submitter", 
					authenticator.getPasswordAuthentication().getUserName());
			properties.setProperty("mail.smtp.auth", "true");
			properties.setProperty("mail.smtp.starttls.enable", "true");
			sessao = Session.getInstance(properties, authenticator);
		} else {
			properties.setProperty("mail.smtp.auth", "false");
			sessao = Session.getInstance(properties);
		}
		return sessao;
	}

	/**
	 *Método responsável por recuperar dados de autenticação do e-mail
	 */
	private static class Authenticator extends javax.mail.Authenticator {
		
		private PasswordAuthentication authentication;
		
		public Authenticator(String user, String pass) {
			String username = user;
			String password = pass;
			authentication = new PasswordAuthentication(username, password);
		}
		
		protected PasswordAuthentication getPasswordAuthentication() {
			return authentication;
		}
	}
	
}