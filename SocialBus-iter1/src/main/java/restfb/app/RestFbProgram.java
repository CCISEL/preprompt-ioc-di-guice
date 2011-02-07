package restfb.app;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.exception.FacebookOAuthException;
import com.restfb.types.Post;
import com.restfb.types.User;

public class RestFbProgram {
	private static void write(String msg, Object ... args){
		if(msg != null)
			System.out.println(String.format(msg, args));
		else
			System.out.println(msg);
	} 
	public static void main(String[] args) {
		final String u1Id = "smart4x4";
		final String u2Id = "1271741625"; // <=> ana.b.neto 
		
		
		FacebookClient fbClient = new DefaultFacebookClient("2227470867|2.HGKoYnQgV8o6DJjsPc6kvA__.3600.1297000800-100000237647164|y-4McuM9r-v_o9MhCMt6MItMUHM");
		User u1 = fbClient.fetchObject(u1Id, User.class, Parameter.with("fields", "id"));
		User u2 = fbClient.fetchObject(u2Id, User.class);
		
		write(u1.getName());
		write(u2.getName());
		write("----------------------------------------");
		Connection<User> u1Friends = fbClient.fetchConnection(u1Id + "/friends", User.class);
		write("1st friend of %s list: %s", u1.getName(), u1Friends.getData().get(0).getName());
		try{
			Connection<User> u2Friends = fbClient.fetchConnection(u2Id + "/friends", User.class);  
			write("1st friend of %s list: %s", u2.getName(), u2Friends.getData().get(0).getName());
		}catch(FacebookOAuthException e){
			write(e.getErrorMessage());
		}
		write("----------------------------------------");
		Connection<Post> u1Posts = fbClient.fetchConnection(u1Id + "/feed", Post.class);
		write("Last post of %s: %s", u1.getName(), u1Posts.getData().get(0).getDescription());
		Connection<Post> u2Posts = fbClient.fetchConnection(u2Id + "/feed", Post.class);
		write("Last post of %s: %s", u2.getName(), u2Posts.getData().get(0).getMessage());
		
		
	}

}
