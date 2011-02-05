package naivecontainer;

import java.lang.reflect.InvocationTargetException;

/** Defines a mapping between a dependency type and a concrete type */
public interface Binding<T> {
	Class<T> getType();
	T getInstance(Injector injector) throws UnbindedTypeException, NaiveContainerDesignException, InvocationTargetException;
}
