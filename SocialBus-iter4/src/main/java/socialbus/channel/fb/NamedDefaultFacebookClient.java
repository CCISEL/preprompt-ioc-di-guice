package socialbus.channel.fb;

import naivecontainer.Named;

import com.restfb.DefaultFacebookClient;

public class NamedDefaultFacebookClient extends DefaultFacebookClient{
	public NamedDefaultFacebookClient(@Named("FbAccessToken") String accessToken){
		super(accessToken);
	} 
}
