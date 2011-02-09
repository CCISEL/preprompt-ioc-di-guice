package socialbus.tests;

import java.util.Arrays;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import socialbus.filters.FromFilter;

import com.restfb.types.CategorizedFacebookType;
import com.restfb.types.Post;

public class FromFilterUT {
	private final Post[] posts = new Post[5];
	
	@Before
	public void setUp(){
		final CategorizedFacebookType[] from = new CategorizedFacebookType[5];
		from[0] = new CategorizedFacebookType(){
			public String getId(){return "11111";}
			public String getName(){return "Jacinto Coelho";}
		};
		from[1] = new CategorizedFacebookType(){
			public String getId(){return "11112";}
			public String getName(){return "Bartolomeu Gabriel Baptista";}
		};
		from[2] = new CategorizedFacebookType(){
			public String getId(){return "11113";}
			public String getName(){return "Joana Orelhida da Silva";}
		};
		from[3] = new CategorizedFacebookType(){
			public String getId(){return "11114";}
			public String getName(){return "Lopes Santos Silva";}
		};
		from[4] = new CategorizedFacebookType(){
			public String getId(){return "11115";}
			public String getName(){return "Porquinho Barnabé Olaré";}
		};
		for (int i = 0; i < posts.length; i++) {
			final int index = i;
			posts[i] = new Post(){
				public CategorizedFacebookType getFrom(){return from[index];}
			};	
		}
	}
	@Test
	public void testFromFilter1(){
		FromFilter filter = new FromFilter("11112", "Orelhida da");
		int count = 0;
		for (Post p : filter.where(Arrays.asList(posts))){
			Assert.assertEquals(
					posts[1 + count++].getFrom().getId(), 
					p.getFrom().getId());
		}
		Assert.assertEquals(count, 2);
	}
	@Test
	public void testFromFilter2(){
		FromFilter filter = new FromFilter("", "Barnabé");
		int count = 0;
		for (Post p : filter.where(Arrays.asList(posts))){
			Assert.assertEquals(
					posts[4 + count++].getFrom().getId(), 
					p.getFrom().getId());
		}
		Assert.assertEquals(count, 1);
	}
}
