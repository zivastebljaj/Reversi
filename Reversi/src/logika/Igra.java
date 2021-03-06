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
	
	//stevec belih in crnih polj
	public static int stevecBelih;
	public static int stevecCrnih;
	
	//ali je igra se v teku (nekdo je na potezi) ali ne
	public static Stanje stanjeIgre;
	
	
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
		
		naPotezi = Igralec.BELI;
		stevecBelih = 2;
		stevecCrnih = 2;
		stanjeIgre = Stanje.NA_POTEZI_B;
	}
	
	//za minmax
	public Polje[][] getPlosca() {
		return plosca;
	}
	
	// Vrne slovar moznih potez skupaj s poljem, s katerega lahko naredimo potezo.
	public Map<int[], Set<int[]>> moznePoteze(){
		Map<int[], Set<int[]>> poteze = new HashMap<int[], Set<int[]>>();
		for (int i = 0; i<8; i++) {
			for (int j = 0; j<8; j++) {
				if (plosca[i][j] == naPotezi.getPolje()){															
					for (int[] smer : smeri) {
						int dx = smer[0];
						int dy = smer[1];
						int k = i + dx;
						int l = j + dy;
						while ((0 <= k) &&(k <= 7) && (0 <= l) && (l <= 7)) {
							if (plosca[k][l] == naPotezi.getPolje()) break;							
							else if (plosca[k][l] == Polje.PRAZNO && plosca[k-dx][l-dy] == naPotezi.nasprotnik().getPolje()) {
								int[] zacetnoPolje = new int[2];
								zacetnoPolje[0] = i;
								zacetnoPolje[1] = j;
								int[] koncnoPolje = new int[2];
								koncnoPolje[0] = k;
								koncnoPolje[1] = l;
								Set<int[]> moznePoteze = poteze.keySet();
								int[] polje = vsebuje(moznePoteze, koncnoPolje);
								if(polje != null) {
									poteze.get(polje).add(zacetnoPolje);
									break;
									}
								else if (polje == null)  {
									Set<int[]> zacetnaPolja = new HashSet<int[]>();
									zacetnaPolja.add(zacetnoPolje);
									poteze.put(koncnoPolje, zacetnaPolja);
									break;
									}
								}
							else {
								k += dx;
								l += dy;
								}
							}
						}
					}
				}
			}
		return poteze;
	}
	// Pomozna funkcija, da vemo, ce mnozica ze vsebuje neko polje.
	public int[] vsebuje(Set<int[]> polja, int[] polje) {
		if (polja.size() == 0) return null;
		else {
			for (int[] p : polja) {
				if (Arrays.equals(p, polje)) return p;
			}
		}
		return null;
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
			if (y1 > y2) dy = (y2 - y1)/(y1 - y2);
			else dy = (y2 - y1)/(y2 - y1);
		}

		int i = x1 + dx;
		int j = y1 + dy;
		while ((i<=7) && (j<=7)) {
			if (plosca[i][j] == naPotezi.nasprotnik().getPolje()) {
				plosca[i][j] = naPotezi.getPolje();
				i += dx;
				j += dy;
			}
			else break;
		}
		
	}
	//Naredimo potezo. Tu predvidevamo, da je poteza veljavna.
	public void narediPotezo(Poteza poteza) {
		Map <int[], Set<int[]>> moznePoteze = moznePoteze();
		int x = poteza.getX();
		int y = poteza.getY();
		int[] polje = {x, y};
		boolean stikalo = false;
		Set<int[]> zacetnaPolja = new HashSet<int[]>();
		for (int[] koncnoPolje : moznePoteze.keySet()) {
			if (Arrays.equals(koncnoPolje, polje)) {
				zacetnaPolja = moznePoteze.get(koncnoPolje);
				stikalo = true;
				}
		}
		if (stikalo) {
			plosca[x][y] = naPotezi.getPolje();
			for(int[] zacetnoPolje : zacetnaPolja){
				pobarvajMed(zacetnoPolje, polje);
				}
			prestejPolja();
			naPotezi = naPotezi.nasprotnik();
			}
		}
	//Preštejemo število črnih in belih polj.
	public void prestejPolja() {
		stevecBelih = 0;
		stevecCrnih = 0;
		for (int i = 0; i<8; i++) {
			for (int j = 0; j<8; j++) {
				if (plosca[i][j] == Polje.BELO) stevecBelih += 1;
				if (plosca[i][j] == Polje.CRNO) stevecCrnih += 1;
			}
		}
	}
	//Preverimo, ali je se kaksna mozna poteza, tj. ce je igre konec ali ne.
	public void osveziStanje() {
		if (moznePoteze().size() == 0) {
			prestejPolja();
			if (stevecBelih < stevecCrnih) stanjeIgre = Stanje.ZMAGA_CRNI;
			else if (stevecBelih > stevecCrnih) stanjeIgre = Stanje.ZMAGA_BELI;
			else stanjeIgre = Stanje.NEODLOCENO;
			}
		}
		
	// Zamenjamo igralca na potezi in spremenimo stanje igre.
	/*public void zamenjajIgralca(){
		prestejPolja();
		naPotezi = naPotezi.nasprotnik();
		
	}*/
	public void zamenjajStanje() {
		if (stanjeIgre == Stanje.NA_POTEZI_B) stanjeIgre = Stanje.NA_POTEZI_C;
		else if (stanjeIgre == Stanje.NA_POTEZI_C) stanjeIgre = Stanje.NA_POTEZI_B;
	}
	
	public Igra(Igra igra) {
		plosca = new Polje[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				plosca[i][j] = igra.plosca[i][j];
			}
		}
		this.naPotezi = igra.naPotezi;
	}



	
}