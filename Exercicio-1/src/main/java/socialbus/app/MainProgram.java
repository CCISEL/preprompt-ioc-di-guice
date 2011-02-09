package socialbus.app;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import socialbus.core.SocialParserNotifier;

public class MainProgram {

	public static void main(String [] args) throws InterruptedException{
		ExecutorService threadPool = Executors.newCachedThreadPool();
		SocialParserNotifier social = new SocialParserNotifier(
				5000, 
				"100002002677659",  //Facebook ID - UID
				"2227470867|2.WYg_na0elLgjgTWtFM4_Og__.3600.1297270800-100002002677659|k8AiI6BpwtP7JUyX-nlPq9bhmvI",  // Facebook Access Token
				"home", // Facebook end point
				"...",  // user login
				"...", 	// user password
				"smtp.gmail.com",		// Email SMTP host
				"prompt.guice@gmail.com"); // // Message To recipient);
		social.run(360000);
	}
}
