package guice.facts;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.ConfigurationException;
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
	
	static class DependantWithMethodInjection{		
		private Service1 _srv;
		
		public DependantWithMethodInjection(){}
		
		@Inject
		public void setService1(Service1 srv){
			_srv = srv;
		}
		
		public Service1 getService1(){ return _srv;}
	}
	
	@Test
	public void guice_can_method_inject(){
		
		Injector inj = Guice.createInjector(new AbstractModule(){
			protected void configure(){
				bind(Service1.class).to(Service1Impl1.class);				
			}
		});		
		DependantWithMethodInjection dep = inj.getInstance(DependantWithMethodInjection.class);
		assertEquals(Service1Impl1.class, dep.getService1().getClass());	
	}
	
	@Test(expected=ConfigurationException.class)
	public void method_injections_are_not_optional_by_default(){
		
		Injector inj = Guice.createInjector(new AbstractModule(){
			protected void configure(){
				// removed! bind(Service1.class).to(Service1Impl1.class);				
			}
		});		
		DependantWithMethodInjection dep = inj.getInstance(DependantWithMethodInjection.class);
		assertNull(dep.getService1());	
	}	
	
	static class DependantWithOptionalMethodInjection{		
		private Service1 _srv = null;
		
		public DependantWithOptionalMethodInjection(){}
		
		@Inject(optional=true)
		public void setService1(Service1 srv){
			_srv = srv;
		}
		
		public Service1 getService1(){ return _srv;}
	}	
	
	@Test
	public void methods_injections_can_be_made_optional(){
		
		Injector inj = Guice.createInjector(new AbstractModule(){
			protected void configure(){
				// no binding!				
			}
		});		
		DependantWithOptionalMethodInjection dep = 
			inj.getInstance(DependantWithOptionalMethodInjection.class);
		assertNull(dep.getService1());	
	}
	
	
}
