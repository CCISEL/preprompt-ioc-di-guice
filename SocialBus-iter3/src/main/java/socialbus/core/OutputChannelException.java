package socialbus.core;

public class OutputChannelException extends RuntimeException{
	private static final long serialVersionUID = -538938986512231111L;

	public OutputChannelException(Exception e){
		super(e);
	}
}
