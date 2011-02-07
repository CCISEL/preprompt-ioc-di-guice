package guice.facts;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.TypeLiteral;

public class TypeLiteralTests {
	
	interface MyProvider<T>{
		T provide();
	}
	
	static class MyProviderImpl1 implements MyProvider<String>{
		public String provide(){ return "abc";}
	}
	
	static class MyProviderImpl2 implements MyProvider<Integer>{
		public Integer provide(){ return 123;}
	}
	
	static class MyProviderImpl3 implements MyProvider<MyProvider<String>>{
		public MyProvider<String> provide(){
			return new MyProviderImpl1();
		}
	}
	
		
	static class Dependent{
		
		private final MyProvider<String> _prov1;
		private final MyProvider<Integer> _prov2;
		private final MyProvider<MyProvider<String>> _prov3;
			
		public MyProvider<String> getProv1(){ return _prov1;}
		public MyProvider<Integer> getProv2(){ return _prov2;}
		public MyProvider<MyProvider<String>> getProv3(){ return _prov3;}
	
		@Inject
		public Dependent(
				MyProvider<String> prov1, 
				MyProvider<Integer> prov2, 
				MyProvider<MyProvider<String>> prov3){
			_prov1 = prov1;
			_prov2 = prov2;
			_prov3 = prov3;			
		}
	}
	
	@Test
	public void can_bind_to_generic_types(){
		Injector injector = Guice.createInjector(new AbstractModule(){
			@Override
			protected void configure() {
				bind(new TypeLiteral<MyProvider<String>>(){}).to(MyProviderImpl1.class);
				bind(new TypeLiteral<MyProvider<Integer>>(){}).to(MyProviderImpl2.class);
				bind(new TypeLiteral<MyProvider<MyProvider<String>>>(){}).to(MyProviderImpl3.class);
				//bind(new TypeLiteral<MyProvider>(){}).to(MyProviderImpl4.class);
			}		
						 
		});		
		Dependent dep = injector.getInstance(Dependent.class);
		assertEquals(MyProviderImpl1.class, dep.getProv1().getClass());
		assertEquals(MyProviderImpl2.class, dep.getProv2().getClass());
		assertEquals(MyProviderImpl3.class, dep.getProv3().getClass());
	}	
	
}
