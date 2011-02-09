package socialbus.core;

public interface IFilter<T>{
	Iterable<T> where(Iterable<T> source);
}
