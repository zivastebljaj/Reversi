
package logika;

import inteligenca.*;

import java.util.List;
import java.util.Random;

import gui.GlavnoOkno;


public class Vodja {
	
	// Glavno okno
	private GlavnoOkno okno;
	
	// Igra, ki jo trenutno igramo.
	public Igra igra;
	
	private Igralec clovek;	
	
	public boolean clovekNaVrsti;
		
	public Vodja(GlavnoOkno okno) {
		this.okno = okno;
		clovekNaVrsti = true;
	}
	
	public void novaIgra(Igralec clovek) {
		// Ustvarimo novo igro
		this.igra = new Igra();
		this.clovek = clovek;
		igramo();
	}
	
	@SuppressWarnings("static-access")
	public void igramo () {
		okno.osveziGUI();
		switch (igra.stanjeIgre) {
		case ZMAGA_BELI: 
		case ZMAGA_CRNI: 
		case NEODLOCENO: 
			break;
		case NA_POTEZI_B:		
		case NA_POTEZI_C: 
			if (igra.naPotezi == clovek) {
				clovekNaVrsti = true;
//			} else {
//				racunalnikovaPoteza();
			}			
		}
	}
	
	public void racunalnikovaPoteza() {
		List<OcenjenaPoteza> ocenjenePoteze = Minimax.oceniPoteze (igra, 2, clovek.nasprotnik());
		Poteza poteza = Minimax.maxPoteza(ocenjenePoteze);
		igra.narediPotezo(poteza);
		igramo();
	}
	
	
	public void clovekovaPoteza(Poteza poteza) {
		igra.narediPotezo(poteza);
		System.out.println("Delam potezo" + poteza);
			// clovekNaVrsti = false;	
			igramo();
		
		
	}
	
	

}