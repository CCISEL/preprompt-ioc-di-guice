package socialbus.channel.twitter;

import naivecontainer.Named;
import socialbus.core.IOutputChannel;
import socialbus.core.OutputChannelException;
import winterwell.jtwitter.OAuthSignpostClient;
import winterwell.jtwitter.Twitter;

public class StatusSetter implements IOutputChannel<String>{
	private String[] accessToken;
	private final String twitterAccount;
	private final String consumerKey;
	private final String consumerSecret;
	public StatusSetter(
			@Named("TwitterAccount") String twitterAccount, 
			@Named("ConsumerKey") String consumerKey, 
			@Named("ConsumerSecret") String consumerSecret) {
		this.twitterAccount = twitterAccount;
		this.consumerKey = consumerKey;
		this.consumerSecret = consumerSecret;
	}
	@Override
	public void sendMessage(String msg) throws OutputChannelException {
		OAuthSignpostClient oauthClient = null;
		if(accessToken == null){
			// Make an oauth client
			oauthClient = new OAuthSignpostClient(consumerKey, consumerSecret, "oob");
			// Open the authorization page in the user's browser
			oauthClient.authorizeDesktop();
			// get the pin
			String v = OAuthSignpostClient.askUser("Please enter the verification PIN from Twitter");
			oauthClient.setAuthorizationCode(v);
			// Store the authorization  token details for future use
			accessToken = oauthClient.getAccessToken();
			// Next time we can use new OAuthSignpostClient(OAUTH_KEY, OAUTH_SECRET, 
			// accessToken[0], accessToken[1]) to avoid authenticating again.
		}else{
			oauthClient = new OAuthSignpostClient(
					consumerKey, consumerSecret, accessToken[0], accessToken[1]);
		}
		
		//Make a Twitter object
		Twitter twitter = new Twitter(twitterAccount, oauthClient);
		// Set my status
		twitter.setStatus(msg);
	}

}
