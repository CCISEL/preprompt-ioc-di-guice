package socialbus.core;

public interface IProjection<S, R>{
	public R projects(S source);
}
