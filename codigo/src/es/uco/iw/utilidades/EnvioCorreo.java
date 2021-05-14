package es.uco.iw.utilidades;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class EnvioCorreo {

	private static Properties props = null;
	private static Session sesion = null;
	private static String email = "hokuroincorporated@gmail.com";
	private static String nombre = "Hokuro";
	private static String password = "adaylachupa";
	
	/**
	 * Inicializa las propiedades necesarias para poder enviar un correo
	 */
	private static void init() {
		props = new Properties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.host", "smtp.gmail.com");
    	props.put("mail.smtp.port", "587"); 
    	props.put("mail.smtp.starttls.enable", "true");
    	props.put("mail.smtp.auth", "true");
    	props.put("mail.smtp.user", email);
        props.put("mail.smtp.clave", password);
    	
    	sesion = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(nombre, password);
            }
        });
    	sesion.setDebug(true);
	}
	
	/**
	 * Envia un correo al correo destinatario
	 * 
	 * @param destinatario Email del destinatario
	 * @param asunto Asunto del mensaje
	 * @param mensaje Mensaje a enviar
	 */
	public static void EnviarCorreo(String destinatario, String asunto, String mensaje) {
		if (props == null) {
			init();
		}
		
		MimeMessage msg = new MimeMessage(sesion);
		
    	try {
    		msg.setFrom(new InternetAddress(email, nombre));
    		msg.setRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
    		msg.setSubject(asunto);
    		msg.setText(mensaje);
    		
    		Transport transporte = sesion.getTransport("smtp");
    		transporte.connect(email, password);
    		transporte.sendMessage(msg, msg.getAllRecipients());
    		transporte.close();
		}
    	catch (UnsupportedEncodingException | MessagingException e) {
			System.out.println(e);
		}
	}
	
}
