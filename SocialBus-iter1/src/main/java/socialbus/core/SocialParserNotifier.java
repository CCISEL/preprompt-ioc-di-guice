package socialbus.core;

import java.util.concurrent.ExecutorService;

import socialbus.channel.fb.FbPostsReader;
import socialbus.channel.mail.MailSender;
import socialbus.filters.PostContentFilter;
import socialbus.projection.PostToMailMessage;

import com.restfb.types.Post;

public class SocialParserNotifier {
	

	private final long periodInMilis;
	private final IInputChannel<Post> in;
	private final IOutputChannel<IMailMessage> out;
	private final IFilter<Post> filter;
	private final IProjection<Post, IMailMessage> projection;
	private final ExecutorService threadPool;
	public SocialParserNotifier(
			long periodInMilis,
			ExecutorService threadPool,
			String uid, 		  // for IInputChannel
			String accessToken,   // for IInputChannel 
			String graphEndPoint, // for IInputChannel
			String fromMailUser,  // for IOuputChannel
			String pswd, 		  // for IOuputChannel
			String host,          // for IOuputChannel
			String recipientTo   // for IOuputChannel
	) {
		super();
		this.periodInMilis = periodInMilis;
		this.in = new FbPostsReader(uid, accessToken, graphEndPoint);
		this.out = new MailSender(fromMailUser, pswd, host, recipientTo);
		this.filter = new PostContentFilter("portugal");
		this.projection = new PostToMailMessage();
		this.threadPool = threadPool;
	}
	public void start(){
		threadPool.submit(new SocialTask());
	}

	class SocialTask implements Runnable{
		@Override
		public void run() {
			while(true){
				Iterable<Post> posts = in.read();
				if(filter != null) 
					posts = filter.where(posts);
				for (Post p : posts) {
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
