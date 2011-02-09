package socialbus.app;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import naivecontainer.Injector;
import naivecontainer.SimpleInjector;
import naivecontainer.exceptions.NaiveContainerException;

import com.restfb.DefaultFacebookClient;
import com.restfb.types.Post;

import socialbus.app.config.SocialBusEmailConfiguration;
import socialbus.app.config.SocialBusTwitterConfiguration;
import socialbus.channel.fb.FbPostsReader;
import socialbus.channel.twitter.StatusSetter;
import socialbus.core.IFilter;
import socialbus.core.IInputChannel;
import socialbus.core.IOutputChannel;
import socialbus.core.IProjection;
import socialbus.core.SocialParserNotifier;
import socialbus.filters.PostContentFilter;
import socialbus.projection.PostToTwitterStatus;

public class MainProgram {

	public static void main(String [] args) throws InterruptedException, NaiveContainerException, InvocationTargetException{
		Injector injector = new SimpleInjector(new SocialBusTwitterConfiguration());
		// Injector injector = new SimpleInjector(new SocialBusEmailConfiguration());
		SocialParserNotifier social = injector.getInstance(SocialParserNotifier.class);
		// run
		social.start();
		// block until the threadPool has finished shutting down,
		// which indicates that all tasks have finished executing
		social.awaitTermination(360000, TimeUnit.MILLISECONDS);
		
	}
}
