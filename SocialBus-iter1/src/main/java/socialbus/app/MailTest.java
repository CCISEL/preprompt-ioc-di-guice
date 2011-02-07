package socialbus.app;

import socialbus.channel.mail.MailSender;
import socialbus.core.IMailMessage;
import socialbus.core.IOutputChannel;

public class MailTest {
	public static void main(String[] args) {
		IOutputChannel<IMailMessage> cl = new MailSender(
				"greatwacky@gmail.com", // user login
				"preprompt123", 		// user password
				"smtp.gmail.com",		// Email SMTP host
				"mcarvalho@cc.isel.ipl.pt"); // // Message To recipient
		cl.sendMessage(new IMailMessage() {
			public String getSubject() {
				return "Teste 123";
			}
			public String getBody() {
				return "Email enviado do Email Sender\nPara ser usado na sessão de huberGuice\nAdeus";
			}
		});
	}
}
