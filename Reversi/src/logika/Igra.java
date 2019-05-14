package logika;

import java.util.*;

public class Igra {

	
	// Velikost igralne plošče je N x N.
	public static final int N = 8;


	// Igralno polje
	// public za test
	public Polje[][] plosca;
	
		
	// Igralec, ki je trenutno na potezi.
	public Igralec naPotezi;
	
	//public za test
	public int stevecBelih;
	public int stevecCrnih;
	
	public Stanje stanjeIgre;
	
	
	int[][] smeri = {{1,0},{-1,0},{0,1},{0,-1},{-1,-1},{1,-1},{-1,1},{1,1}};

	//nastavi zacetno stanje
	public Igra() {
		plosca = new Polje[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				plosca[i][j] = Polje.PRAZNO;
			}
		}
		plosca[3][3] = Polje.BELO;
		plosca[4][4] = Polje.BELO;
		plosca[3][4] = Polje.CRNO;
		plosca[4][3] = Polje.CRNO;
		
		naPotezi = Igralec.CRNI;
		stevecBelih = 2;
		stevecCrnih = 2;
		stanjeIgre = Stanje.NEODLOCENO;
	}
	
	// Vrne slovar moznih potez skupaj s poljem, s katerega lahko naredimo potezo.
	public Map<int[], int[]> moznePoteze(){
		Map<int[], int[]> poteze = new HashMap<int[], int[]>();
		for (int i = 0; i<8; i++) {
			for (int j = 0; j<8; j++) {
				if (plosca[i][j] == naPotezi.getPolje()){
					int[] zacetnoPolje = new int[2];
					zacetnoPolje[0] = i;
					zacetnoPolje[1] = j;
					
					for (int[] smer : smeri) {
						int dx = smer[0];
						int dy = smer[1];
						int k = i + dx;
						int l = j + dy;
						while ((0 <= k) &&(k <= 7) && (0 <= l) && (l <= 7)) {
							if (plosca[k][l] == naPotezi.getPolje()) break;
							
							if (plosca[k][l] == Polje.PRAZNO && plosca[k-dx][l-dy] == naPotezi.nasprotnik().getPolje()) {
								int[] koncnoPolje = new int[2];
								koncnoPolje[0] = k;
								koncnoPolje[1] = l;
								poteze.put(koncnoPolje, zacetnoPolje);
								//System.out.println("#"+ zacetnoPolje);
								break;
							}
							k += dx;
							l += dy;							
						}
					}
				}
			}
		}
		System.out.println();
		return poteze;
	}

	// Ko naredimo potezo, spremeni barvo vseh polj med začetnim in končnim.
	public void pobarvajMed(int[] polje1, int[] polje2) {
		int x1 = polje1[0];
		int y1 = polje1[1];
		int x2 = polje2[0];
		int y2 = polje2[1];
		int dx = 0;
		int dy = 0;
		

		if ((x1 - x2) != 0) {
			if (x1 > x2) dx = (x2 - x1)/(x1 - x2);
			else dx = (x2 - x1)/(x2 - x1);
		}
		if ((y1 - y2) != 0) {
			if (x1 > x2) dy = (y2 - y1)/(y1 - y2);
			else dy = (y2 - y1)/(y2 - y1);
		}

		int i = x1 + dx;
		int j = y1 + dy;
		//System.out.println(i + ", " +j);
		//System.out.println("*" + dx + ", " +dy + "*");
		while ((i<=7) && (j<=7)) {
			if (plosca[i][j] == naPotezi.nasprotnik().getPolje()) {
				plosca[i][j] = naPotezi.getPolje();
				i = i + dx;
				j = j + dy;
				System.out.println(i + ", " +j);
			}

			else break;
		}
		
	}
	//Če možnih potez ni več, igro končamo. Sicer pobarvamo ustrezna polja in zamenjamo igralca.
	public void narediPotezo(int x, int y) {
		Map <int[], int[]> moznePoteze = moznePoteze();
		int[] poteza = new int[2];
		poteza[0] = x;
		poteza[1] = y;
		if (moznePoteze().size() == 0) {
			prestejPolja();
			if (stevecBelih < stevecCrnih) stanjeIgre = Stanje.ZMAGA_CRNI;
			else if (stevecBelih > stevecCrnih) stanjeIgre = Stanje.ZMAGA_BELI;
			else stanjeIgre = Stanje.NEODLOCENO;
		}
		else {
			for (int[] koncnoPolje : moznePoteze.keySet()) {
				if (Arrays.equals(koncnoPolje, poteza)) {
					int[] zacetnoPolje = moznePoteze.get(koncnoPolje);
					plosca[x][y] = naPotezi.getPolje();
					pobarvajMed(zacetnoPolje, koncnoPolje);
					naPotezi = naPotezi.nasprotnik();
					break;
					}
			}
		}
	}
	//Da vemo, kdo je zmagovalec, preštejemo število črnih in belih polj.
	public void prestejPolja() {
		stevecBelih = 0;
		for (int i = 0; i<8; i++) {
			for (int j = 0; j<8; j++) {
				if (plosca[i][j] == Polje.BELO) stevecBelih += 1;
				if (plosca[i][j] == Polje.CRNO) stevecCrnih += 1;
			}
		}
	}
	
}