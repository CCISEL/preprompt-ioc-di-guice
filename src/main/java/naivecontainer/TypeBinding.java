package naivecontainer;

import java.lang.reflect.InvocationTargetException;

/** A binding from a dependency type into another type */
public class TypeBinding<T> implements Binding<T> {

	public final Class<T> _key;
	public final Class<? extends T> _concrete;
	
	public TypeBinding(Class<T> key, Class<? extends T> concrete){
		_key = key;
		_concrete = concrete;
	}

	@Override
	public Class<T> getType() {
		return _key;
	}

	@Override
	public T getInstance(Injector injector) throws UnbindedTypeException, NaiveContainerDesignException, InvocationTargetException {
		return injector.getInstanceOfExactType(_concrete);
	}	
}
