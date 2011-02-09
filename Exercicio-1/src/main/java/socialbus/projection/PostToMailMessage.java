package socialbus.projection;

import com.restfb.types.Post;

import socialbus.core.IMailMessage;
import socialbus.core.IProjection;

public class PostToMailMessage implements IProjection<Post, IMailMessage>{
	private static boolean isNullOrBlank(String param) {
        if ((param == null) || param.trim().length() == 0) {
            return true;
        }
        return false;
    }
	@Override
	public IMailMessage projects(final Post source) {
		return new IMailMessage() {
			public String getSubject() {
				if(!isNullOrBlank(source.getCaption())) return source.getCaption();
				else return source.getName();
			}
			public String getBody() {
				return String.format("Message: %s\n Desc: %s\n; link:%s", 
					source.getMessage(),
					source.getDescription(), 
					source.getLink());
			}
		};
	}

}
