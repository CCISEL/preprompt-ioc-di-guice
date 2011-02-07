package socialbus.filters;

import java.util.Iterator;

import socialbus.core.IFilter;

import com.restfb.types.Post;

public abstract class AbstractPostFilter implements IFilter<Post>{
	/**
	 * Hook method
	 */
	protected abstract boolean match(Post msg);
	/**
	 * Template method 
	 */
	@Override
	public final Iterable<Post> where(final Iterable<Post> source) {
		return new Iterable<Post>() {
			public Iterator<Post> iterator() {
				return new FilteredPosts(source);
			}
		};
	}
	/**
	 * Auxiliary Iterator class.
	 */
	class FilteredPosts implements Iterator<Post>{
		final Iterable<Post> source;
		Iterator<Post> iter;
		Post curr, next;
		public FilteredPosts(Iterable<Post> source) {
			this.source = source;
		}
		private void init(){
			iter = source.iterator();
			while(iter.hasNext()){
				next = iter.next();
				if(match(next)) break; 
			}
		}
		public boolean hasNext() {
			if(iter == null) init();
			return next != null;
		}
		public Post next() {
			if(iter == null) init();
			curr = next;
			next = null;
			while(iter.hasNext()){
				Post aux = iter.next();
				if(match(aux)){
					next = aux;
					break; 
				}
			}
			return curr;
		}
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

}
