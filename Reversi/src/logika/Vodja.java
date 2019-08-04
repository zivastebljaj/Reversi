
package logika;

import inteligenca.*;

import java.util.*;

import javax.swing.SwingWorker;

import gui.GlavnoOkno;


public class Vodja {
	
	
	// Glavno okno
	private GlavnoOkno okno;
	
	// Igra, ki jo trenutno igramo.
	public static Igra igra;
	
	private Igralec clovek;	
	
	public boolean clovekNaVrsti;
		
	public Vodja(GlavnoOkno okno) {
		this.okno = okno;
		clovekNaVrsti = true;
	}
	
	public void novaIgra(Igralec clovek) {
		// Ustvarimo novo igro
		Vodja.igra = new Igra();
		this.clovek = clovek;
		igramo();
	}
	
	
	
	public void igramo () {
		igra.osveziStanje();
		okno.osveziGUI();
		switch (Igra.stanjeIgre) {
		case ZMAGA_BELI: 
		case ZMAGA_CRNI: 
		case NEODLOCENO: 
			break;
		case NA_POTEZI_C:
		case NA_POTEZI_B:
			if (igra.naPotezi == clovek) {
				clovekNaVrsti = true;
				} 
			else {
				racunalnikovaPoteza();
			}			
		
		}
	}

	
	public void racunalnikovaPoteza() {
		SwingWorker<Poteza, Void> worker = new SwingWorker<Poteza, Void> () {
			private Igra zacetnaIgra = igra;
			@Override
			protected Poteza doInBackground() {
				return AlphaBeta.alphabetaV (igra, clovek.nasprotnik());
			}
			@Override
			protected void done () {
				Poteza poteza;
				try {
					poteza = get();
					if (poteza != null && zacetnaIgra == igra)
					{
						igra.narediPotezo(poteza);
						//igra.zamenjajIgralca();
						igra.zamenjajStanje();
						igramo();
					}
					else System.out.println("Poteza je null");
				} catch (Exception e) {};		
			}		
		};
		worker.execute();
	}
	

	
	public void clovekovaPoteza(Poteza poteza) {
		if (preveriPotezo(poteza)) {
			igra.narediPotezo(poteza);			
			//igra.zamenjajIgralca();
			igra.zamenjajStanje();
			clovekNaVrsti = false;
		}
		igramo();
		
		
	}
	
	public static int[] vsebuje(Set<int[]> polja, int[] polje) {
		if (polja.size() == 0) return null;
		else {
			for (int[] p : polja) {
				if (Arrays.equals(p, polje)) return p;
			}
		}
		return null;
	}
	//preverimo �e je poteza veljavna, da ne klikamo na neveljavna polja
	public static boolean preveriPotezo (Poteza p) {
		int x = p.getX();
		int y = p.getY();
		int [] poteza = {x, y};
		Map <int[], Set<int[]>> moznePoteze = igra.moznePoteze();
		if (vsebuje(moznePoteze.keySet(), poteza) != null) {
			return true;
		}
		else return false;
	
			
	}
	
	

}