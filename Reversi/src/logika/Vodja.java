
package logika;

import inteligenca.*;

import java.util.*;

import javax.swing.SwingWorker;

import gui.GlavnoOkno;


public class Vodja {
	
	private Random random;
	
	// Glavno okno
	private GlavnoOkno okno;
	
	// Igra, ki jo trenutno igramo.
	public Igra igra;
	
	private Igralec clovek;	
	
	public boolean clovekNaVrsti;
		
	public Vodja(GlavnoOkno okno) {
		random = new Random();
		this.okno = okno;
		clovekNaVrsti = true;
	}
	
	public void novaIgra(Igralec clovek) {
		// Ustvarimo novo igro
		this.igra = new Igra();
		this.clovek = clovek;
		igramo();
	}
	
	
	public void igramo () {
		okno.osveziGUI();
		switch (Igra.stanjeIgre) {
		case ZMAGA_BELI: 
		case ZMAGA_CRNI: 
		case NEODLOCENO: 
			break;
		case NA_POTEZI_B:		
		case NA_POTEZI_C: 
			if (igra.naPotezi == clovek) {
				clovekNaVrsti = true;
				} else {
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
					{igra.narediPotezo(poteza);
					igramo();
					}
				} catch (Exception e) {};		
			}		
		};
		worker.execute();
	}
	

	// racunalnikova poteza za minimax (zbrisi)
	public void racunalnikovaPotezaMinimax() {
		List<OcenjenaPoteza> ocenjenePoteze = Minimax.oceniPoteze (igra, 2, clovek.nasprotnik());
		Poteza poteza = Minimax.maxPoteza(ocenjenePoteze);
		igra.narediPotezo(poteza);
		igramo();
	}
	
	
	public void clovekovaPoteza(Poteza poteza) {
		igra.narediPotezo(poteza);
		System.out.println("Delam potezo" + poteza);
		clovekNaVrsti = false;
		igramo();
		
		
	}
	
	//preverimo èe je poteza veljavna, da ne klikamo na neveljavna polja (potrebno je še vkljuèiti)
	//public static boolean preveriPotezo (Poteza p) {
		//int x = p.getX();
		//int y = p.getY();
		//for (int[] smer : Igra.smeri) {
			//int dx = smer[0];
			//int dy = smer[1];
			//int k = x + dx;
			//int l = y + dy;
			//if (Igra.plosca[k][l] == Polje.PRAZNO) return true;			
		//}
		//return false;
		//}
	
	

}