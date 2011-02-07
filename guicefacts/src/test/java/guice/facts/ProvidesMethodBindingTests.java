package guice.facts;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

public class ProvidesMethodBindingTests {
	
	interface Service1{}
	
	static class Service1Impl1 implements Service1{}
	static class Service1Impl2 implements Service1{}
	
	public static class Dependent{
		private final Service1 _srv;
		public Service1 getService(){ return _srv;}
		
		@Inject
		public Dependent(Service1 srv){
			_srv = srv;
		}
	}
	
	@Test
	public void modules_can_have_provides_methods(){
		
		Injector injector = Guice.createInjector(new AbstractModule(){
			public void configure(){
				bind(int.class).annotatedWith(Names.named("Service1Impl")).toInstance(1);
			}
			
			@SuppressWarnings("unused")
			@Provides
			public Service1 getDependent(@Named("Service1Impl") int i){				
				return i == 1 ? new Service1Impl1() 
					: new Service1Impl2();
			}
		});
		
		Dependent dep = injector.getInstance(Dependent.class);
		assertEquals(Service1Impl1.class, dep.getService().getClass());
		
		injector = Guice.createInjector(new AbstractModule(){
			public void configure(){
				bind(int.class).annotatedWith(Names.named("Service1Impl")).toInstance(2);
			}
			
			@SuppressWarnings("unused")
			@Provides
			public Service1 getDependent(@Named("Service1Impl") int i){				
				return i == 1 ? new Service1Impl1() 
					: new Service1Impl2();
			}
		});
		
		dep = injector.getInstance(Dependent.class);
		assertEquals(Service1Impl2.class, dep.getService().getClass());
		
	}

}
