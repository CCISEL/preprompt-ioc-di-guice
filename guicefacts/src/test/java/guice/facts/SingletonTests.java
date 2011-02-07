package guice.facts;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Scopes;
import com.google.inject.Singleton;

public class SingletonTests {

	interface Service1{}
	
	@Singleton
	static class Service1Impl1 implements Service1 {}
	static class Service1Impl2 implements Service1 {}
	
	static class Dependant {
		private final Service1 _srv;
		public Service1 getService1(){ return _srv;}

		@Inject
		public Dependant(Service1 srv){
			_srv = srv;
		}
	}
	
	@Test
	public void classes_annotated_with_singleton_have_only_one_instance(){
		Injector injector = Guice.createInjector(new AbstractModule(){
			@Override
			protected void configure() {
				bind(Service1.class).to(Service1Impl1.class);				
			}			
		});
		
		Dependant dep1 = injector.getInstance(Dependant.class);
		Dependant dep2 = injector.getInstance(Dependant.class);
		assertTrue(dep1.getService1() == dep2.getService1());
		assertEquals(Service1Impl1.class, dep1.getService1().getClass());
	}
	
	@Test
	public void bindings_in_singleton_scope_always_inject_the_same_instance(){
		Injector injector = Guice.createInjector(new AbstractModule(){
			@Override
			protected void configure() {
				bind(Service1.class).to(Service1Impl2.class).in(Scopes.SINGLETON);				
			}			
		});
		
		Dependant dep1 = injector.getInstance(Dependant.class);
		Dependant dep2 = injector.getInstance(Dependant.class);
		assertTrue(dep1.getService1() == dep2.getService1());
		assertEquals(Service1Impl2.class, dep1.getService1().getClass());
	}
}
