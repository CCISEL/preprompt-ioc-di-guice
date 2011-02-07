package socialbus.filters;

import com.restfb.types.Post;

public class PostContentFilter extends AbstractPostFilter{
	private final String keyword;

	public PostContentFilter (String keyword) {
		super();
		this.keyword = keyword.toLowerCase();
	}
	@Override
	protected boolean match(Post msg) {
		if(msg.getName() != null && msg.getName().toLowerCase().indexOf(keyword) > -1) 
			return true;
		if(msg.getCaption() != null && msg.getCaption().toLowerCase().indexOf(keyword) > -1) 
			return true;
		if(msg.getDescription() != null && msg.getDescription().toLowerCase().indexOf(keyword) > -1) 
			return true;
		if(msg.getMessage() != null && msg.getMessage().toLowerCase().indexOf(keyword) > -1) 
			return true;
		return false;
	}	
}
