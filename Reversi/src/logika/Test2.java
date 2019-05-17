package logika;

import java.util.*;
import java.util.Map;
import java.util.Set;

public class Test2 {

	public static void main(String[] args) {
		int[] polje = {1,2};
		int[] poteza1 = {2,2};
		int[] poteza2 = {6,5};
		//Map<int[], int[]> poteze = new HashMap<int[],int[]>();
		//poteze.put(polje, poteza1);
		//poteze.put(polje, poteza2);
		//System.out.println(poteze.toString());
		Set<int[]> mn = new HashSet<int[]>();
		mn.add(poteza1);
		for (int[] poteza : mn) System.out.println(poteza[0] + "&" + poteza[1]);

	}
	

}
