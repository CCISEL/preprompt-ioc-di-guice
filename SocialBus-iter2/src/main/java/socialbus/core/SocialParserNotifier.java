package socialbus.core;

import java.util.concurrent.ExecutorService;

public class SocialParserNotifier<S, R>{
	

	private final long periodInMilis;
	private final IInputChannel<S> in;
	private final IOutputChannel<R> out;
	private final IFilter<S> filter;
	private final IProjection<S, R> projection;
	private final ExecutorService threadPool;
	public SocialParserNotifier(
			long periodInMilis,
			IInputChannel<S> in,
			IOutputChannel<R> out, 
			IFilter<S> filter,
			IProjection<S, R> projection,
			ExecutorService threadPool) {
		super();
		this.periodInMilis = periodInMilis;
		this.in = in;
		this.out = out;
		this.filter = filter;
		this.projection = projection;
		this.threadPool = threadPool;
	}
	public void start(){
		threadPool.submit(new SocialTask());
	}

	class SocialTask implements Runnable{
		@Override
		public void run() {
			while(true){
				Iterable<S> msgs = in.read();
				if(filter != null) 
					msgs = filter.where(msgs);
				for (S p : msgs) {
					out.sendMessage(projection.projects(p));
				}
				try {
					Thread.sleep(periodInMilis);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		}

	}
}
