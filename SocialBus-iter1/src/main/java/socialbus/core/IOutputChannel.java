package socialbus.core;

public interface IOutputChannel<T>{
	void sendMessage(T msg) throws OutputChannelException;
}
