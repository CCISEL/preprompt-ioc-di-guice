package socialbus.app;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import socialbus.core.SocialParserNotifier;

public class MainProgram {

	public static void main(String [] args) throws InterruptedException{
		ExecutorService threadPool = Executors.newCachedThreadPool();
		SocialParserNotifier social = new SocialParserNotifier(
				5000, 
				threadPool,
				"100002002677659", //Facebook ID - UID
				// Facebook Access Token 
				"2227470867|2.1kDKGi69NbGVs0_9EpVq0g__.3600.1297116000-100002002677659|SypT3k6fhVL9shDWZhGJzPsnMFs",
				"home", // Facebook end point
				"greatwacky@gmail.com", // user login
				"preprompt123", 		// user password
				"smtp.gmail.com",		// Email SMTP host
				"mcarvalho@cc.isel.ipl.pt"); // // Message To recipient);
		social.start();
		// block until the threadPool has finished shutting down,
        // which indicates that all tasks have finished executing
        threadPool.awaitTermination(360000, TimeUnit.MILLISECONDS);
	}
}
