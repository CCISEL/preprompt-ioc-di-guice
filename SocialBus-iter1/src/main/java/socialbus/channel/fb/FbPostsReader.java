package socialbus.channel.fb;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import socialbus.core.IInputChannel;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.Post;


/**
 * Reads posts from an IInputChannel<Post> object and registers a log
 * from the already read posts.
 * @author mcarvalho
 */
public class FbPostsReader implements IInputChannel<Post>{
	//---------------------------------------------------------------------
	//~~~~~~~~~~~~~~~~~~~~~~~   Instance Fields   ~~~~~~~~~~~~~~~~~~~~~~~~~
	//---------------------------------------------------------------------
	final private String uid;
	final private FacebookClient fbClient;
	final private String graphEndPoint;
	final private Set<String> log;
	
	//---------------------------------------------------------------------
	//~~~~~~~~~~~~~~~~~~~~~~~~~~   CONSTRUCTOR      ~~~~~~~~~~~~~~~~~~~~~~~   
	//---------------------------------------------------------------------

	public FbPostsReader(String uid, String accessToken, String graphEndPoint) {
		super();
		this.uid = uid;
		this.fbClient = new DefaultFacebookClient(accessToken);
		this.graphEndPoint = graphEndPoint;
		this.log = new HashSet<String>();
	}
	//---------------------------------------------------------------------
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~  METHODS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~    
	//---------------------------------------------------------------------
	/**
	 * Returns a list of new posts returned from a FacebookClient object.
	 * It maintains a log of the posts already returned.  
	 */
	@Override
	public Iterable<Post> read() {
		List<Post> posts = fbClient.fetchConnection(
				uid + "/" + graphEndPoint, 
				Post.class).getData();
		posts = new ArrayList<Post>(posts);
		for (int i = 0; i < posts.size(); i++) {
			String postId = posts.get(i).getId();
			if(log.contains(postId)){
				posts.remove(i);
				i--;
			}else{
				log.add(postId);
			}
		}
		return posts;
	}

}
