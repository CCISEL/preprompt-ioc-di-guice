package guice.facts;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;

public class ProviderBindingTests {
	
interface Service1{}
	
	static class Service1Impl1 implements Service1{}
		
	public static class Dependent{
		private final Service1 _srv;
		public Service1 getService(){ return _srv;}
		
		@Inject
		public Dependent(Service1 srv){
			_srv = srv;
		}
	}
	
	@Test
	public void bindings_can_refer_to_a_provider(){		
		
		Injector injector = Guice.createInjector(new AbstractModule(){
			public void configure(){
				bind(Service1.class).toProvider(new Provider<Service1>(){
					@Override
					public Service1 get() {
						return new Service1Impl1();
					}					
				});
			}
		});
		
		Dependent dep = injector.getInstance(Dependent.class);
		assertEquals(Service1Impl1.class, dep.getService().getClass());		
	}
}