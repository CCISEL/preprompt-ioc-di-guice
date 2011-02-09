package socialbus.tests;

import java.util.Arrays;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import socialbus.filters.PostContentFilter;

import com.restfb.types.Post;

public class PostContentFilterUT {
	private final Post[] posts = new Post[4];
	@Before
	public void setUp(){
		posts[0] = new Post(){public String getName(){return "Badu Bandoleiro";}};
		posts[1] = new Post(){public String getCaption(){return "Nadava que nem desalmado";}};
		posts[2] = new Post(){public String getMessage(){return "Atravessa canal do Alibabá sem parar e a nadar";}};
		posts[3] = new Post(){public String getDescription(){return "Voltou de Rolls Royce e nao adormeceu.";}};
	}
	@Test
	public void testPostContentFilter1(){
		PostContentFilter filter = new PostContentFilter("Badu");
		int count = 0;
		for (Post p : filter.where(Arrays.asList(posts))){
			Assert.assertEquals(
					posts[count++].getId(), 
					p.getId());
		}
		Assert.assertEquals(count, 1);
	}
	@Test
	public void testPostContentFilter2(){
		PostContentFilter filter = new PostContentFilter("desalmado");
		int count = 0;
		for (Post p : filter.where(Arrays.asList(posts))){
			Assert.assertEquals(
					posts[1 + count++].getId(), 
					p.getId());
		}
		Assert.assertEquals(count, 1);
	}
	@Test
	public void testPostContentFilter3(){
		PostContentFilter filter = new PostContentFilter("nada");
		int count = 0;
		for (Post p : filter.where(Arrays.asList(posts))){
			Assert.assertEquals(
					posts[1 + count++].getId(), 
					p.getId());
		}
		Assert.assertEquals(count, 2);
	}
	@Test
	public void testPostContentFilter4(){
		PostContentFilter filter = new PostContentFilter("Rolls");
		int count = 0;
		for (Post p : filter.where(Arrays.asList(posts))){
			Assert.assertEquals(
					posts[3 + count++].getId(), 
					p.getId());
		}
		Assert.assertEquals(count, 1);
	}
}
