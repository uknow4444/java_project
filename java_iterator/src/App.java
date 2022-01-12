import java.util.ArrayList;
import java.util.Iterator;

public class App {
    public static void main(String[] args) throws Exception {
        ArrayList<String> list = new ArrayList<>();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		
		
		Iterator<String> itr = list.iterator();
		if(itr.hasNext()) {
			System.out.println("!!!");
			System.out.println(itr);
			System.out.println(itr.next());
		}
		//2
		System.out.println(itr);
		if(itr.hasNext()) {
			System.out.println(itr);
			System.out.println(itr.next());
		}
		if(itr.hasNext())
			System.out.println(itr.next());
		if(itr.hasNext())
			System.out.println(itr.next());
		//while(itr.hasNext())
			//System.out.println(itr.next());
    }
}
