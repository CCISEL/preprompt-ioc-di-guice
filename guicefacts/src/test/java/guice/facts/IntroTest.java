package guice.facts;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

public class IntroTest {
	
	// Services
	public interface Service1{}
	public interface Service2{}
	
	// Service's implementations
	public static class Service1Impl1 implements Service1{}
	public static class Service1Impl2 implements Service1{}
	public static class Service2Impl1 implements Service2{}
	public static class Service2Impl2 implements Service2{}
	
	// The injected class
	static class Dependent{		
		@Inject
		public Dependent(
				Service1 svc1, 
				Service2 svc, 
				@Named("host") String host,
				@Named("port") Integer port){
			//...
		}		
		//...
	}
	
	// Configuration module
	static class MyModule extends AbstractModule{
		@Override
		protected void configure() {
			 // bindings
			 bind(Service1.class).to(Service1Impl1.class);
			 bind(Service2.class).to(Service2Impl2.class);
			 bind(String.class).annotatedWith(Names.named("host")).toInstance("www.cc.isel.ipl.pt");
			 bind(int.class).annotatedWith(Names.named("port")).toInstance(80);			
		}		
	}
	
	@Test
	public void guice_basic_concepts(){
		Injector injector = Guice.createInjector(new MyModule());
		Dependent instance = injector.getInstance(Dependent.class);
		assertNotNull(instance);
	}
}
