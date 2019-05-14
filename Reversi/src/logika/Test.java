package logika;

import java.util.*;

public class Test {

	public static void main(String[] args) {
		Igra i = new Igra();
		printPlosca(i.plosca);
		//System.out.print(i.moznePoteze());
		//int[] polje1 = {4,3};
		//int[] polje2 = {2,3};
		//i.pobarvajMed(polje1, polje2);
		//printPlosca(i.plosca);
		//int[] polje3 = {3,3};
		//int[] polje4 = {5,5};
		//i.pobarvajMed(polje3, polje4);
		//printPlosca(i.plosca);
		i.narediPotezo(2, 3);
		printPlosca(i.plosca);
		//printPoteze(i.moznePoteze());
		i.narediPotezo(2, 2);
		printPlosca(i.plosca);
		//printPoteze(i.moznePoteze());
		i.narediPotezo(4, 5);
		printPlosca(i.plosca);
		//i.narediPotezo(0, 0);
		//System.out.println(i.naPotezi.getPolje().toString());
		//System.out.println(i.stevecBelih);
		//printPoteze(i.moznePoteze());
		i.narediPotezo(1, 3);
		printPlosca(i.plosca);
		i.narediPotezo(0, 3);
		printPlosca(i.plosca);
		i.narediPotezo(5, 5);
		printPlosca(i.plosca);
		i.narediPotezo(5, 5);
		printPlosca(i.plosca);
		i.narediPotezo(6, 5);
		printPlosca(i.plosca);
		i.narediPotezo(2, 4);
		printPlosca(i.plosca);
	
	

	}
	public static String pretvoriPolje(Polje p) {
		if (p == Polje.PRAZNO) return " ";
		if (p == Polje.CRNO) return "*";
		else return "o";
	}
	public static void printPlosca(Polje[][] M) {
		if (M == null) System.out.println("null");
		else {
			for (int i = 0; i < M.length; i++) {
				for (int j = 0; j < M[0].length; j++) {
					if (j == 0) System.out.print("|");
					System.out.print(pretvoriPolje(M[i][j]) + " |");
				}
				System.out.println();
			}
		}
	}
	

}
