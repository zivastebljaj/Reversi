package inteligenca;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import logika.Igra;
import logika.Igralec;
import logika.Poteza;
import logika.Stanje;

public class AlphaBeta {
	
	private static final int ZMAGA = (1 << 20);
	private static final int ZGUBA = -ZMAGA;  
	private static final int NEODLOC = 0;

	private static final int GLOBINA = 5; // globalna globina algoritma minimax
	
	public static Poteza alphabetaV (Igra igra, Igralec jaz) {
		// Na zaèetku alpha = ZGUBA in beta = ZMAGA
		return alphabetaPoteze(igra, GLOBINA, ZGUBA, ZMAGA, jaz).poteza;
	}
	
	public static OcenjenaPoteza alphabetaPoteze(Igra igra, int globina, int alpha, int beta, Igralec jaz) {
		int ocena;
		// raèunalnik: maksimiramo oceno z zaèetno oceno ZGUBA
		// èlovek: minimiziramo oceno z zaèetno oceno ZMAGA
		if (igra.naPotezi == jaz) {ocena = ZGUBA;} else {ocena = ZMAGA;}
		Map<int[], Set<int[]>> moznePoteze = igra.moznePoteze();
		Entry<int[], Set<int[]>> entry = moznePoteze.entrySet().iterator().next();
		int[] key= entry.getKey(); //dobimo prvi element
		Poteza kandidat = new Poteza(key[0], key[1]);
		for (int[] k : moznePoteze.keySet() ) {
			Poteza p = new Poteza(k[0], k[1]);
			Igra zacasna_igra = new Igra(igra);
			zacasna_igra.osveziStanje();
			zacasna_igra.narediPotezo(p);
			//zacasna_igra.zamenjajIgralca();
			int ocenap = alphabetaPozicijo (zacasna_igra, globina-1, alpha, beta, jaz);
			if (igra.naPotezi == jaz) { // Maksimiramo oceno
				if (ocenap > ocena) { // Za alphabeta mora biti > namesto >=
					ocena = ocenap;
					kandidat = p;
					alpha = Math.max(alpha,ocena);
				}
			} else { // igra.naPotezi != jaz, torej minimiziramo oceno
				if (ocenap < ocena) { // Za alphabeta mora biti < namesto <=
					ocena = ocenap;
					kandidat = p;
					beta = Math.min(beta, ocena);					
				}	
			}	
			if (alpha >= beta) {return new OcenjenaPoteza (kandidat, ocena);} // Izstopimo iz "for loop", ker ostale poteze ne pomagajo
		}
		return new OcenjenaPoteza (kandidat, ocena);
	}

	public static int alphabetaPozicijo(Igra igra, int globina, int alpha, int beta, Igralec jaz) {
		Stanje stanje = Igra.stanjeIgre;
		switch (stanje) {
		case ZMAGA_BELI: return (jaz == Igralec.BELI ? ZMAGA : ZGUBA);
		case ZMAGA_CRNI: return (jaz == Igralec.CRNI ? ZMAGA : ZGUBA);
		case NEODLOCENO: return (NEODLOC);
		default:
		// Nekdo je na potezi
		if (globina == 0) {return oceniPozicijo(igra, jaz);}
		// globina > 0
	    OcenjenaPoteza ocenjenaPoteza = alphabetaPoteze (igra, globina, alpha, beta, jaz);
		return ocenjenaPoteza.vrednost;
		}
	}

	
	public static int oceniPozicijo(Igra igra, Igralec jaz) {
		int stBeli = Igra.stevecBelih;
		int stCrni = Igra.stevecCrnih;
		return (jaz == Igralec.BELI ? stBeli - stCrni : stCrni - stBeli);	
	}

}
