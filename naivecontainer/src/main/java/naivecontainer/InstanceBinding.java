package naivecontainer;

/** A binding from a dependency type into an instance */
public class InstanceBinding<T> implements Binding<T> {

	public final Class<T> _key;
	public final T  _instance;
	
	public InstanceBinding(Class<T> key, T instance){
		_key = key;
		_instance = instance;
	}

	@Override
	public Class<T> getType() {
		return _key;
	}

	@Override
	public T getInstance(Injector injector) {
		return _instance;
	}	
}
