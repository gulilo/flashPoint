package mecanics;

import java.util.ArrayList;

public class ArraylistHelper
{
	//taken from the internet: http://stackoverflow.com/questions/16681786/why-doesnt-arraylist-containsobject-class-work-for-finding-instances-types
	public static <E> boolean containsInstance(ArrayList<E> list, Class<? extends E> clazz)
	{
		for(E e : list)
		{
			if(clazz.isInstance(e))
			{
				return true;
			}
		}
		return false;
	}
	
	//return a list size 2, index 0 is a list of everything that added after the change, index 1 is everything that removed after the change.
	public static <E> ArrayList<ArrayList<E>> changes(ArrayList<E> before, ArrayList<E> after)
	{
		ArrayList<E> added = new ArrayList<E>();
		ArrayList<E> removed = new ArrayList<E>();
		for(int i = 0; i < before.size(); i++)
		{
			if(!after.contains(before.get(i)))
			{
				removed.add(before.get(i));
			}
		}
		for(int i = 0; i < after.size(); i++)
		{
			if(!before.contains(after.get(i)))
			{
				added.add(after.get(i));
			}
		}
		
		ArrayList ans = new ArrayList<ArrayList<E>>();
		ans.add(added);
		ans.add(removed);
		
		return ans;
	}
}
