package guice.facts;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.CreationException;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.util.Modules;

public class ModuleTests {
	
	interface Service1{}
		
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
	
	class Module1 extends AbstractModule{
		@Override
		protected void configure() {
			bind(Service1.class).to(Service1Impl1.class);				
		}
	}
	
	class Module2 extends AbstractModule{
		@Override
		protected void configure() {
			bind(Service1.class).to(Service1Impl2.class);				
		}		
	}
	
	@Test(expected=CreationException.class)
	public void different_modules_cannot_bind_the_same_key(){					
		Guice.createInjector(new Module1(), new Module2());
	}
	
	@Test
	public void modules_can_be_overrided(){					
		Module m3 = Modules.override(new Module1()).with(new Module2());
		
		Injector injector = Guice.createInjector(m3);
		Dependant dep = injector.getInstance(Dependant.class);
		assertEquals(Service1Impl2.class, dep.getService1().getClass());		
	}
}
