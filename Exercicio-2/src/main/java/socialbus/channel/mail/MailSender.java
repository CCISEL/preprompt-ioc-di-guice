package socialbus.channel.mail;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import socialbus.core.IMailMessage;
import socialbus.core.IOutputChannel;
import socialbus.core.OutputChannelException;



/**
 * Authenticator represents an object that knows how to obtain 
 * authentication for a network connection. Usually, it will 
 * do this by prompting the user for information.
 */
public class MailSender extends Authenticator implements IOutputChannel<IMailMessage>{
	//---------------------------------------------------------------------
	//~~~~~~~~~~~~~~~~~~~~~~~   Instance Fields   ~~~~~~~~~~~~~~~~~~~~~~~~~
	//---------------------------------------------------------------------
	protected String fromMailUser;
	protected String recipientTo;
	protected Session session; //Information about user, host and protocols
	protected PasswordAuthentication authentication;
	protected Properties props;

	//---------------------------------------------------------------------
	//~~~~~~~~~~~~~~~~~~~~~~~~~~   CONSTRUCTOR      ~~~~~~~~~~~~~~~~~~~~~~~   
	//---------------------------------------------------------------------
	public MailSender(Properties props){
		this.props = props;
		if(props.getProperty("mail.transport.protocol") == null)
			props.put("mail.transport.protocol", "smtp");
		if(props.getProperty("mail.smtp.port") == null)
			props.put("mail.smtp.port", "587");
		if(props.getProperty("mail.smtps.auth") == null)
			props.put("mail.smtps.auth", "true");
		if(props.getProperty("mail.smtp.starttls.enable") == null)
			props.put("mail.smtp.starttls.enable", "true");	

	}
	public MailSender(String fromMailUser, String pswd, String host, String recipientTo){
		this(fromMailUser, pswd, host, false, recipientTo);
	}  
	public MailSender(String fromMailUser, String pswd, String host, boolean debug, String recipientTo){
		this(new Properties());
		this.fromMailUser = fromMailUser;
		this.recipientTo = recipientTo; 
		props.put("mail.user", fromMailUser);
		props.put("mail.host", host);
		props.put("mail.debug", debug ? "true" : "false");
		authentication = new PasswordAuthentication(fromMailUser, pswd);
		session = Session.getInstance(props, this);
	}
	public MailSender(Properties props, String recipientTo){
		this(new Properties());
		this.fromMailUser = props.getProperty("mail.user", "noUserDefined");
		this.recipientTo = recipientTo; 
		String pswd = props.getProperty("mail.passwd", "noPassDefined");
		authentication = new PasswordAuthentication(fromMailUser, pswd);
		session = Session.getInstance(props, this);
	}

	//---------------------------------------------------------------------
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~  METHODS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~    
	//---------------------------------------------------------------------
	/**
	 * Called when password authentication is needed. 
	 * Overrides the default implementation, which returns null. 
	 * The system will automatically invoke this method 
	 * when authentication is required.
	 */
	@Override
	public PasswordAuthentication getPasswordAuthentication(){
		return authentication;
	}
	@Override
	public void sendMessage(IMailMessage msg) {
		sendMessage(msg.getSubject(), msg.getBody(), recipientTo);
	}
	public void sendMessage(String subject, String body, String to){
		try {
			//
			//Create new MimeMessage
			//
			Address [] from = {new InternetAddress(fromMailUser)};
			MimeMessage msg = new MimeMessage(this.session);  			
			msg.addFrom(from);
			msg.addRecipients(Message.RecipientType.TO, to);
			msg.setSubject(subject);
			msg.setText(body);
			//
			// Send message
			//
			Transport tr = this.session.getTransport("smtp");      
			tr.connect(
					props.getProperty("mail.host", ""), 
					fromMailUser, 
					props.getProperty("mail.passwd", ""));
	        tr.sendMessage(msg, msg.getAllRecipients());//SendFailedException is thrown if any of the recipient addresses is invalid
	        tr.close();
		} catch (AddressException e) {
			throw new OutputChannelException(e);
		} catch (MessagingException e) {
			throw new OutputChannelException(e);
		}		
	}
}
