package socialbus.tests;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import socialbus.core.IMailMessage;
import socialbus.projection.PostToMailMessage;

import com.restfb.types.Post;

public class PostToMailMessageUT {
	private final Post[] posts = new Post[2];
	@Before
	public void setUp(){
		posts[0] = new Post(){public String getName(){return "Badu Bandoleiro";}};
		posts[1] = new Post(){
			public String getCaption(){return "Nadava que nem desalmado";}
			public String getMessage(){return "Atravessa canal do Alibabá sem parar e a nadar";}
			public String getDescription(){return "Voltou de Rolls Royce e nao adormeceu.";}
		};
	}
	@Test
	public void testPostToMailMessage(){
		PostToMailMessage projection = new PostToMailMessage();
		Assert.assertEquals(
				projection.projects(posts[0]).getSubject(),
				posts[0].getName());
		IMailMessage mailMsg = projection.projects(posts[1]);
		Assert.assertEquals(
				mailMsg.getSubject(),
				posts[1].getCaption());
		Assert.assertTrue(
				mailMsg.getBody().indexOf(posts[1].getMessage()) >= 0);
		Assert.assertTrue(
				mailMsg .getBody().indexOf(posts[1].getDescription()) >= 0);

	}
}
