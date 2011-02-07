package socialbus.core;

public interface IInputChannel<T>{
	Iterable<T> read();
}
