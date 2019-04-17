package logika;

import java.util.*;

public class Igra {

	
	// Velikost igralne plošče je N x N.
	public static final int N = 8;


	// Igralno polje
	private Polje[][] plosca;
	
		
	// Igralec, ki je trenutno na potezi.
	// Vrednost je poljubna, Äe je igre konec (se pravi, lahko je napana).
	public Igralec naPotezi;
	
	private int stevecBelih;

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
		
		naPotezi = Igralec.CRNO;
		stevecBelih = 2;
	}
	// gremo po vrsticah; če koordinate niso na plošči, dodamo prazno polje
	public List<Polje> sosednji(int i, int j, Polje[][] plosca){
		List<Polje> sosedi = new ArrayList<Polje>();
		for (int k = -1; k<2; k++) {			
			if ((i-1) < 0) sosedi.add(Polje.PRAZNO);
			else if ((j + k) < 0) sosedi.add(Polje.PRAZNO);
			else if ((j + k) > 7) sosedi.add(Polje.PRAZNO);
			else sosedi.add(plosca[i -1][j + k]);
		}
		if ((j -1) < 0) sosedi.add(Polje.PRAZNO);
		else sosedi.add(plosca[i][j - 1]);
		if ((j +1) > 8) sosedi.add(Polje.PRAZNO);
		sosedi.add(plosca[i] [j +1]);
		for (int k = -1; k<2; k++) {
			if ((i+1) > 7) sosedi.add(Polje.PRAZNO);
			else if ((j + k) < 0) sosedi.add(Polje.PRAZNO);
			else if ((j + k) > 7) sosedi.add(Polje.PRAZNO);
			sosedi.add(plosca[i + 1][j + k]);	
		}
		return sosedi;
	}
	public ArrayList<int[]> primernaPolja() {
		List<int[]> polja = new ArrayList<int []>();
		if (naPotezi == Igralec.BELO) {
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					int[] koordinate = new int[2];
					koordinate[0]= i;
					koordinate[1] = j;
					if (plosca[i][j] == Polje.PRAZNO && funkcija(poljre)) polja.add(koordinate);
				}
			}
		}
	}
	
	
}