package socialbus.app;

import java.util.concurrent.ExecutorService;
import com.restfb.DefaultFacebookClient;
import com.restfb.types.Post;

import socialbus.channel.fb.FbPostsReader;
import socialbus.channel.twitter.StatusSetter;
import socialbus.core.IInputChannel;
import socialbus.core.IOutputChannel;
import socialbus.core.SocialParserNotifier;
import socialbus.filters.PostContentFilter;
import socialbus.projection.PostToTwitterStatus;

public class MainProgram {

	public static void main(String [] args) throws InterruptedException{
		ExecutorService threadPool;
		IInputChannel<Post> reader = new FbPostsReader(
				"100002002677659", 
				new DefaultFacebookClient(
				"2227470867|2.SF_JilpSpuiPYeczsYMmMA__.3600.1297123200-100002002677659|BQdyN--ejChnE05Us7YJFzCtW7g"), 
				"home");
		IOutputChannel<String> outCh = new StatusSetter(
				"greatwacky", // twitterAccount 
				"FImbe0979Sowdv4cJgNJjg", // consumerKey 
				"gx4xCzXSxAcIQQpcms5Gnu6gA3HDJj4hqAofsdkfTo"); // consumerSecret 
		/*
		IOutputChannel<IMailMessage> outCh = new MailSender(
				"greatwacky@gmail.com", // user login
				"preprompt123", 		// user password
				"smtp.gmail.com",		// Email SMTP host
				"prompt.guice@gmail.com"); // // Message To recipient
				*/
		SocialParserNotifier<Post, String> social = new SocialParserNotifier<Post, String>(
				5000, 
				reader, 
				outCh, 
				new PostContentFilter("portugal"), 
				new PostToTwitterStatus());
		social.run(360000);
	}
}
