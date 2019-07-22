package inteligenca;

import java.util.*;



import logika.*;

public class Minimax {
	
	private static final Random RANDOM = new Random();
	
	private static final int ZMAGA = (1 << 20); // vrednost zmage, vec kot vsaka druga ocena pozicije
	private static final int ZGUBA = -ZMAGA;
	private static final int NEODLOCEN = 0;  // vrednost neodlocene igre	
	
	public static List<OcenjenaPoteza> oceniPoteze(Igra igra, int globina, Igralec jaz) {
		List<OcenjenaPoteza> ocenjenePoteze = new LinkedList<OcenjenaPoteza> ();
		Map<int[], Set<int[]>> moznePoteze = igra.moznePoteze();
		for (int[] k : moznePoteze.keySet() ) {
			Poteza p = new Poteza(k[0], k[1]);
			Igra zacasna_Igra = new Igra();
			zacasna_Igra.narediPotezo(p);
			int ocena = minimaxPozicijo (zacasna_Igra, globina-1, jaz);
			ocenjenePoteze.add(new OcenjenaPoteza(p, ocena));
		}
		return ocenjenePoteze;
	}
	
	public static int minimaxPozicijo(Igra igra, int globina, Igralec jaz) {
		Stanje stanje = Igra.stanjeIgre;
		switch (stanje) {
		case ZMAGA_BELI: return (jaz == Igralec.BELI ? ZMAGA : ZGUBA);
		case ZMAGA_CRNI: return (jaz == Igralec.CRNI ? ZMAGA : ZGUBA);
		case NEODLOCENO: return (NEODLOCEN);
		default:
		// Nekdo je na potezi
		if (globina == 0) {return oceniPozicijoA(igra, jaz);}
		// globina > 0
	    List<OcenjenaPoteza> ocenjenePoteze = oceniPoteze(igra, globina, jaz);
		if (igra.naPotezi == jaz) {return maxOcena(ocenjenePoteze);}
		else {return minOcena(ocenjenePoteze);}		
		}
	}
	
	public static int maxOcena(List<OcenjenaPoteza> ocenjenePoteze) {
		int max = ZGUBA;
		for (OcenjenaPoteza ocenjenaPoteza : ocenjenePoteze) {
			if (ocenjenaPoteza.vrednost > max) {max = ocenjenaPoteza.vrednost;}
		}
		return max;
	}
	
	public static Poteza maxPoteza(List<OcenjenaPoteza> ocenjenePoteze) {
		int max = ZGUBA;
		Poteza poteza = null;
		for (OcenjenaPoteza ocenjenaPoteza : ocenjenePoteze) {
			if (ocenjenaPoteza.vrednost >= max) {
				max = ocenjenaPoteza.vrednost;
				poteza = ocenjenaPoteza.poteza;			
			}
		}
		return poteza;
	}
	
	public static int minOcena(List<OcenjenaPoteza> ocenjenePoteze) {
		int min = ZMAGA;
		for (OcenjenaPoteza ocenjenaPoteza : ocenjenePoteze) {
			if (ocenjenaPoteza.vrednost < min) {min = ocenjenaPoteza.vrednost;}
		}
		return min;
	}
	
	// Metoda oceniPozicijo je odvisna od igre !!!
	//public static int oceniPozicijo(Igra igra, Igralec jaz) {
	//	int ocena = 0;
	//	for (Vrsta v : Igra.VRSTE) {
	//		ocena = ocena + oceniVrsto(v, igra, jaz);
	//	}
	//	return ocena;	
	//}
	
//	public static int oceniVrsto (Vrsta v, Igra igra, Igralec jaz) {
//		Polje[][] plosca = igra.getPlosca();
//		int count_belo = 0;
//		int count_crno = 0;
//		for (int k = 0; k < Igra.N && (count_crno == 0 || count_belo == 0); k++) {
//			switch (plosca[v.x[k]][v.y[k]]) {
//			case BELO: count_belo += 1; break;
//			case CRNO: count_crno += 1; break;
//			case PRAZNO: break;
//			}
//		}
//		if (count_belo > 0 && count_crno > 0) { return 0; }
//		else if (jaz == Igralec.BELI) { return count_belo - count_crno; }
//		else { return count_crno - count_belo; }
//	}

	
	// Nakljucna ocena pozicije.
	public static int oceniPozicijoA(Igra igra, Igralec jaz) {
		return RANDOM.nextInt(201) - 100;	
	}


}
