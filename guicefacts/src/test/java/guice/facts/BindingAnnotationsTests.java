package guice.facts;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

public class BindingAnnotationsTests {

	public static class DependentWithAnnotations{
		
		private final String _arg1;
		private final String _arg2;
		
		public String getArg1(){ return _arg1;}
		public String getArg2(){ return _arg2;}

		@Inject
		public DependentWithAnnotations(@Named("arg1") String arg1, @Named("arg2") String arg2){
			_arg1 = arg1;
			_arg2 = arg2;
		}
	}
	
	@Test
	public void annotations_differentiate_injections(){
		Injector injector = Guice.createInjector(new AbstractModule(){
			public void configure(){
				bind(String.class).annotatedWith(Names.named("arg1")).toInstance("arg1");
				bind(String.class).annotatedWith(Names.named("arg2")).toInstance("arg2");
				
			}
		});
		
		DependentWithAnnotations dep = injector.getInstance(DependentWithAnnotations.class);
		assertEquals("arg1",dep.getArg1());
		assertEquals("arg2",dep.getArg2());
		
	}
	
}
