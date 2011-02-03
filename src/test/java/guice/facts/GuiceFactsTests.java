package guice.facts;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class GuiceFactsTests {

	interface Service1{}
	
	static class Service1Impl1 implements Service1{}
	
	static class DependantWithCtorInjection{		
		private final Service1 _srv;
		
		@Inject
		public DependantWithCtorInjection(Service1 srv){
			_srv = srv;
		}
		public Service1 getService1(){ return _srv;}
	}
	
	@Test
	public void guice_can_ctor_inject(){
		
		Injector inj = Guice.createInjector(new AbstractModule(){
			protected void configure(){
				bind(Service1.class).to(Service1Impl1.class);				
			}
		});		
		DependantWithCtorInjection dep = inj.getInstance(DependantWithCtorInjection.class);
		assertEquals(Service1Impl1.class, dep.getService1().getClass());	
	}
}
