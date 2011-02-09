package socialbus.tests;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import socialbus.channel.fb.FbPostsReader;
import socialbus.core.IInputChannel;
import static org.mockito.Mockito.*;

import com.restfb.Connection;
import com.restfb.FacebookClient;
import com.restfb.types.Post; 

public class FbPostsReaderUT {

	private FacebookClient fbClient;
	private final Post [] posts = new Post[5];
	private final String UID = "xxxxUid";
	private final String GRAPH_EP= "xxxxUid";
	
	/**
	 * The Arrays.asList returns an immutable array. 
	 * So this function has the same intention of Arrays.asList
	 * but returning a mutable array.    
	 */
	private static <T> List<T> asMutableList(T ... args){
		List<T> res = new ArrayList<T>(args.length);
		for (int i = 0; i < args.length; i++) {
			res.add(args[i]);
		}
		return res;
	} 
	/**
	 * Set up the responses for each fbClient.fetchConnection
	 * invocation.
	 */
	@Before
	public void setUp(){
		List<Connection<Post>> expectedRes = new ArrayList<Connection<Post>>();
		posts[0] = new Post(){public String getId(){return "111111";}};
		posts[1] = new Post(){public String getId(){return "111112";}};
		posts[2] = new Post(){public String getId(){return "111113";}};
		posts[3] = new Post(){public String getId(){return "111114";}};
		posts[4] = new Post(){public String getId(){return "111115";}};
		expectedRes.add(mock(Connection.class));
		expectedRes.add(mock(Connection.class));
		expectedRes.add(mock(Connection.class));
		when(expectedRes.get(0).getData()).thenReturn(asMutableList(new Post[]{posts[0], posts[1], posts[2]}));
		when(expectedRes.get(1).getData()).thenReturn(asMutableList(new Post[]{posts[0], posts[1], posts[2]}));
		when(expectedRes.get(2).getData()).thenReturn(asMutableList(new Post[]{posts[0], posts[1], posts[2], posts[3], posts[4]}));
		fbClient = mock(FacebookClient.class);
		when(fbClient.fetchConnection(UID + "/" + GRAPH_EP, Post.class))
			.thenReturn(expectedRes.get(0))  // 1st response
			.thenReturn(expectedRes.get(1))  // 2nd response - the same result as previous
			.thenReturn(expectedRes.get(2)); // 3rd response - two more posts on a total of 5 posts.
	}
	
	@Test
	public void testPostsLog(){
		IInputChannel<Post> reader = new FbPostsReader(UID, fbClient, GRAPH_EP);
		//
		// 1st invocation
		//
		Iterable<Post> res = reader.read();
		int count = 0;
		for (Post p : res) {
			Assert.assertEquals(posts[count++].getId(), p.getId());
		}
		Assert.assertEquals(count, 3);
		//
		// 2nd invocation - the response from fbClient is the same as in the previous call, 
		// then the FbPostsReader should not return more posts.
		//
		res = reader.read();
		count = 0;
		for (Post p : res) {
			Assert.assertEquals(posts[count++].getId(), p.getId());
		}
		Assert.assertEquals(count, 0);
		//
		// 3rd invocation - fbClient returns 5 posts and two more then in the previous calls,
		// then the FbPostsReader should return just more 2 posts.
		//
		res = reader.read();
		count = 0;
		for (Post p : res) {
			Assert.assertEquals(posts[3 + count++].getId(), p.getId());
		}
		Assert.assertEquals(count, 2);

	}
}
