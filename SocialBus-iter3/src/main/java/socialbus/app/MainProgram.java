package socialbus.app;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import naivecontainer.Injector;
import naivecontainer.SimpleInjector;
import naivecontainer.XmlConfiguration;
import naivecontainer.exceptions.NaiveContainerException;

import socialbus.core.SocialParserNotifier;

public class MainProgram {
	
	static final String CFG_FILE = "D:\\MyFolder\\ISEL\\Cursos\\Pamp\\PrePrompt\\guice\\preprompt-ioc-di-guice\\SocialBus-iter3\\cfg\\SocialBusCfg.xml";
	
	public static void main(String [] args) 
		throws InterruptedException, 
			NaiveContainerException, 
			InvocationTargetException, 
			ParserConfigurationException, SAXException, IOException
	{
		InputStream in = new FileInputStream(CFG_FILE);
		Injector injector = new SimpleInjector(new XmlConfiguration(in));

		SocialParserNotifier app = injector.getInstance(SocialParserNotifier.class);
		app.run(360000);
	}
}
