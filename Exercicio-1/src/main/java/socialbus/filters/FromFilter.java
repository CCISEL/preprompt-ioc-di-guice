package socialbus.filters;

import com.restfb.types.Post;

public class FromFilter extends AbstractPostFilter{
	private final String uid;
	private final String name;
	public FromFilter(String uid, String name) {
		super();
		this.uid = uid;
		this.name = name;
	}
	@Override
	protected boolean match(Post msg) {
		if(msg.getFrom().getId() == uid)
			return true;
		if(msg.getFrom().getName().indexOf(name) >= 0)
			return true;
		return false;
	}
}
