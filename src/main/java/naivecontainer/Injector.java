package naivecontainer;

import java.lang.reflect.InvocationTargetException;

/** An injector creates instances and injects dependencies into them */
public interface Injector {
	public <T> T getInstanceOfExactType(Class<T> type) throws NaiveContainerDesignException, InvocationTargetException, UnbindedTypeException;
	public <T> T getInstance(Class<T> type) throws UnbindedTypeException, NaiveContainerDesignException, InvocationTargetException;
	
	public void addBinding(Binding<?> b);
}
