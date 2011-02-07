package naivecontainer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Hashtable;
import java.util.Map;

import naivecontainer.exceptions.NaiveContainerConfigurationException;
import naivecontainer.exceptions.UnbindedTypeException;

/* A simple injector */
public class SimpleInjector implements Injector {
	
	private final Map<Class<?>, Binding<?>> _bindings = new Hashtable<Class<?>,Binding<?>>();	
	
	public SimpleInjector(){}
	
	public SimpleInjector(InjectorConfiguration cfg){
		cfg.configure(this);
	}
	
	public SimpleInjector withBinding(Binding<?> b){
		_bindings.put(b.getType(), b);
		return this;
	}
	
	@Override
	public void addBinding(Binding<?> b) {
		withBinding(b);		
	}

	@Override
	public <T> T getInstanceOfExactType(Class<T> type) throws NaiveContainerConfigurationException, InvocationTargetException {
		ClassDecorator<T> dec = new ClassDecorator<T>(type);
		Class<?>[] deps = dec.getDependencies();
		Object[] objs = new Object[deps.length];
		for(int i = 0 ; i<objs.length ; ++i){
			objs[i] = getInstance(deps[i]);			
		}
		return dec.newInstance(objs);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getInstance(Class<T> type) throws NaiveContainerConfigurationException, InvocationTargetException {
		Binding<T> b = (Binding<T>) _bindings.get(type);
		if(b != null) 
			return b.getInstance(this);
		int mod = type.getModifiers();
		if(!Modifier.isAbstract(mod) && !Modifier.isInterface(mod)) 
			return this.getInstanceOfExactType(type);
		
		throw new UnbindedTypeException(type);		
	}
}
