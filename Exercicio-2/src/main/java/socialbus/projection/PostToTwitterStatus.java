package socialbus.projection;

import socialbus.core.IProjection;

import com.restfb.types.Post;

public class PostToTwitterStatus implements IProjection<Post, String> {
	private static final int TWITTER_STATUS_LIMIT = 140;
	
	private static boolean isNullOrBlank(String param) {
		if ((param == null) || param.trim().length() == 0) {
			return true;
		}
		return false;
	}
	@Override
	public String projects(Post source) {
		String res = null;
		if(!isNullOrBlank(source.getCaption())) res = source.getCaption();
		else if(!isNullOrBlank(source.getName())) res = source.getName();
		else if(!isNullOrBlank(source.getMessage())) res = source.getMessage();
		else if(!isNullOrBlank(source.getDescription())) res = source.getDescription();
		if(isNullOrBlank(res))throw new IllegalArgumentException(source.toString());
		if(res.length() > TWITTER_STATUS_LIMIT)
			res = res.substring(0, TWITTER_STATUS_LIMIT - 1);
		return res;
	}
}
