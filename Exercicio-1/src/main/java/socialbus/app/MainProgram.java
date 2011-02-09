package socialbus.app;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import socialbus.core.SocialParserNotifier;

public class MainProgram {

	public static void main(String [] args) throws InterruptedException{
		ExecutorService threadPool = Executors.newCachedThreadPool();
		SocialParserNotifier social = new SocialParserNotifier(
				5000, 
				"...",  //Facebook ID - UID
				"...",  // Facebook Access Token
				"home", // Facebook end point
				"...",  // user login
				"...", 	// user password
				"smtp.gmail.com",		// Email SMTP host
				"prompt.guice@gmail.com"); // // Message To recipient);
		social.run(360000);
	}
}
