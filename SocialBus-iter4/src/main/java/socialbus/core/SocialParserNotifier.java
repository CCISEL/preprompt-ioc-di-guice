package socialbus.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import naivecontainer.Named;

public class SocialParserNotifier<S, R>{
	

	private final long periodInMilis;
	private final IInputChannel<S> in;
	private final IOutputChannel<R> out;
	private final IFilter<S> filter;
	private final IProjection<S, R> projection;
	private final ExecutorService threadPool = Executors.newCachedThreadPool();
	public SocialParserNotifier(
			@Named("PeriodInMilis") int periodInMilis,
			IInputChannel<S> in,
			IOutputChannel<R> out, 
			IFilter<S> filter,
			IProjection<S, R> projection) {
		super();
		this.periodInMilis = periodInMilis;
		this.in = in;
		this.out = out;
		this.filter = filter;
		this.projection = projection;
	}
	public void start(){
		threadPool.submit(new SocialTask());
	}
	public void awaitTermination(int i, TimeUnit milliseconds) throws InterruptedException {
		threadPool.awaitTermination(i, milliseconds);
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
