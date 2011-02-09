package javatwitter.app;

import winterwell.jtwitter.OAuthSignpostClient;
import winterwell.jtwitter.Twitter;

public class TestTwitter {
	public static void main(String args[]){
	// Make an oauth client (you'll want to change this bit)
		OAuthSignpostClient oauthClient = new OAuthSignpostClient(
				"FImbe0979Sowdv4cJgNJjg", //consumerKey
				"gx4xCzXSxAcIQQpcms5Gnu6gA3HDJj4hqAofsdkfTo", // //consumerSecret 
				"oob");
		// Open the authorisation page in the user's browser
		// On Android, you'd direct the user to URI url = client.authorizeUrl();
		// On a desktop, we can do that like this:
		oauthClient.authorizeDesktop();
		// get the pin
		String v = OAuthSignpostClient.askUser("Please enter the verification PIN from Twitter");
		oauthClient.setAuthorizationCode(v);

		// Make a Twitter object
		Twitter twitter = new Twitter("greatwacky", oauthClient);
		// Print Daniel Winterstein's status
		System.out.println(twitter.getStatus("winterstein"));
		// Set my status
		twitter.setStatus("Messing about in Java");
  }
}
