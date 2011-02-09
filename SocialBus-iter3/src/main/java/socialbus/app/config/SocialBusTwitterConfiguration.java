package socialbus.app.config;

import socialbus.channel.fb.FbPostsReader;
import socialbus.channel.fb.NamedDefaultFacebookClient;
import socialbus.channel.twitter.StatusSetter;
import socialbus.core.IFilter;
import socialbus.core.IInputChannel;
import socialbus.core.IOutputChannel;
import socialbus.core.IProjection;
import socialbus.filters.PostContentFilter;
import socialbus.projection.PostToTwitterStatus;

import com.restfb.FacebookClient;

import naivecontainer.SimpleConfiguration;

public class SocialBusTwitterConfiguration extends SimpleConfiguration{
	@Override
	protected void configure() {
		//
		// IInputChannel
		//
		bind(IInputChannel.class).to(FbPostsReader.class);
		bind(String.class).withName("FbUid").to("100002002677659");
		bind(String.class).withName("FbGraphEp").to("home");
		bind(FacebookClient.class).to(NamedDefaultFacebookClient.class);
		bind(String.class).withName("FbAccessToken").to("2227470867|2.QRgvmL4yLgP_yCwzqOq_nA__.3600.1297213200-100002002677659|PffIclsTOCCO0bVo3TVw-egGgMg");
		//
		// IOutputChannel
		//
		bind(IOutputChannel.class).to(StatusSetter.class);
		bind(String.class).withName("TwitterAccount").to("greatwacky"); 
		bind(String.class).withName("ConsumerKey").to("FImbe0979Sowdv4cJgNJjg"); 
		bind(String.class).withName("ConsumerSecret").to("gx4xCzXSxAcIQQpcms5Gnu6gA3HDJj4hqAofsdkfTo"); 
		//
		// IFilter
		//
		bind(IFilter.class).to(PostContentFilter.class);
		bind(String.class).withName("FilterKeyword").to("portugal");
		//
		// IProjection
		//
		bind(IProjection.class).to(PostToTwitterStatus.class);
		//
		// SocialParserNotifier parameters
		//
		bind(int.class).withName("PeriodInMilis").to(5000);
	}
}
