package naivecontainer;

import java.util.Collection;
import java.util.LinkedList;

/* A simple configuration with fluent interface support */
public class SimpleConfiguration implements InjectorConfiguration {

	private final Collection<Binding<?>> _bindings = new LinkedList<Binding<?>>();
	
	@Override
	public void configure(Injector injector) {
		for(Binding<?> b : _bindings){
			injector.addBinding(b);
		}	
	}
	
	public static SimpleConfiguration with(){
		return new SimpleConfiguration();
	}
	
	public class Dependency<T>{
		public final Class<T> _klass;
		public Dependency(Class<T> klass){
			_klass = klass;
		}
		public SimpleConfiguration bindedTo(Class<? extends T> t){
			_bindings.add(new TypeBinding<T>(_klass, t));
			return SimpleConfiguration.this;
		}
		public <K extends T> SimpleConfiguration bindedTo(K k){
			_bindings.add(new InstanceBinding<T>(_klass, k));
			return SimpleConfiguration.this;
		}
		public SimpleConfiguration bindedToSingleton(Class<? extends T> k){
			_bindings.add(new SingletonBinding<T>(_klass, k));
			return SimpleConfiguration.this;
		}
	}
	
	public <T> Dependency<T> dependency(Class<T> klass){
		return new Dependency<T>(klass);
	}
}
