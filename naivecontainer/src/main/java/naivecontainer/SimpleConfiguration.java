package naivecontainer;

import java.util.Collection;
import java.util.LinkedList;

/* A simple configuration with fluent interface support */
public class SimpleConfiguration implements InjectorConfiguration {

	private final Collection<Binding<?>> _bindings = new LinkedList<Binding<?>>();
	
	@Override
	public final void configure(Injector injector) {
		for(Binding<?> b : _bindings){
			injector.addBinding(b);
		}
		configure();
	}
	/**
	 * Hook method. It should be abstract, but we keep it
	 * with an empty body to avoid turning those class abstract
	 * and break retro-compatibility.   
	 */
	protected void configure() {
		
	}
	public static SimpleConfiguration with(){
		return new SimpleConfiguration();
	}
	
	public class Dependency<T>{
		public final Class<T> _klass;
		public Dependency(Class<T> klass){
			_klass = klass;
		}
		public SimpleConfiguration to(Class<? extends T> t){
			_bindings.add(new TypeBinding<T>(_klass, t));
			return SimpleConfiguration.this;
		}
		public <K extends T> SimpleConfiguration to(K k){
			_bindings.add(new InstanceBinding<T>(_klass, k));
			return SimpleConfiguration.this;
		}
		public SimpleConfiguration toSingleton(Class<? extends T> k){
			_bindings.add(new SingletonBinding<T>(_klass, k));
			return SimpleConfiguration.this;
		}
	}
	
	public <T> Dependency<T> bind(Class<T> klass){
		return new Dependency<T>(klass);
	}
}
