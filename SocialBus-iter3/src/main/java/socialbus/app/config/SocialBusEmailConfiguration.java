package socialbus.app.config;

import socialbus.channel.fb.FbPostsReader;
import socialbus.channel.fb.NamedDefaultFacebookClient;
import socialbus.channel.mail.MailSender;
import socialbus.core.IFilter;
import socialbus.core.IInputChannel;
import socialbus.core.IOutputChannel;
import socialbus.core.IProjection;
import socialbus.filters.PostContentFilter;
import socialbus.projection.PostToTwitterStatus;

import com.restfb.FacebookClient;

import naivecontainer.SimpleConfiguration;

public class SocialBusEmailConfiguration extends SimpleConfiguration{
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
		bind(IOutputChannel.class).to(MailSender.class);
		bind(String.class).withName("MailUser").to("greatwacky@gmail.com"); 
		bind(String.class).withName("MailPasswd").to("preprompt123"); 
		bind(String.class).withName("MailHost").to("smtp.gmail.com"); 
		bind(String.class).withName("MailRecipientTo").to("mcarvalho@cc.isel.ipl.pt");
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
