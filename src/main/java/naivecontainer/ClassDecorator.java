package naivecontainer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/** Decorates Class<T> with some context specific methods */

public class ClassDecorator<T> {
	private final Class<T> _klass;
	Constructor<T> _ctor;
	
	public Class<T> getDecoratedClass(){
		return _klass;
	}

	@SuppressWarnings("unchecked")
	public ClassDecorator(Class<T> klass) throws NaiveContainerDesignException{
		_klass = klass;
		Constructor<T>[] ctors = (Constructor<T>[]) klass.getConstructors();
		if(ctors.length != 1) throw new InvalidConstructorException(klass);
		_ctor = ctors[0];
	}
	
	public Class<?>[] getDependencies(){
		return _ctor.getParameterTypes();
	}
	
	public T newInstance(Object[] args) throws InvocationTargetException {
		try {
			return _ctor.newInstance(args);
		} catch (IllegalArgumentException e) {
			throw new UnexpectedException(e);
		} catch (InstantiationException e) {
			throw new UnexpectedException(e);
		} catch (IllegalAccessException e) {
			throw new UnexpectedException(e);
		} 
	}
}
