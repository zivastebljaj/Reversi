package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.*;

import javax.swing.*;

import logika.Igra;
import logika.Igralec;
import logika.Stanje;
import logika.Vodja;

@SuppressWarnings("serial")
public class GlavnoOkno extends JFrame implements ActionListener {
	/**
	 * JPanel, v katerega ri�emo �rne in bele
	 */
	private IgralnoPolje polje;

	
	//Statusna vrstica v spodnjem delu okna
	private JLabel status;
	
	//Rezultat belih in �rnih
	private JLabel bel;
	private JLabel crn;

	
	// Vodja igre
	private Vodja vodja;
	
	// Izbire v menujih
	private JMenuItem igraClovekRacunalnik;
	private JMenuItem igraRacunalnikClovek;

	/**
	 * Ustvari novo glavno okno in pri�ni igrati igro.
	 */
	public GlavnoOkno() {
		
		this.setTitle("Reversi");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new GridBagLayout());
		
		// vodja igre
		this.vodja = new Vodja(this);
		
		// menu
		JMenuBar menu_bar = new JMenuBar();
		this.setJMenuBar(menu_bar);
		JMenu igra_menu = new JMenu("Igra");
		menu_bar.add(igra_menu);

		igraClovekRacunalnik = new JMenuItem("�lovek - ra�unalnik");
		igra_menu.add(igraClovekRacunalnik);
		igraClovekRacunalnik.addActionListener(this);
		
		igraRacunalnikClovek = new JMenuItem("ra�unalnik - �lovek");
		igra_menu.add(igraRacunalnikClovek);
		igraRacunalnikClovek.addActionListener(this);

		// igralno polje
		polje = new IgralnoPolje(vodja);

		GridBagConstraints polje_layout = new GridBagConstraints();
		polje_layout.gridx = 0;
		polje_layout.gridy = 0;
		polje_layout.fill = GridBagConstraints.BOTH;
		polje_layout.weightx = 1.0;
		polje_layout.weighty = 1.0;
		getContentPane().add(polje, polje_layout);
				
		
		// statusna vrstica za sporo�ila
		status = new JLabel();
		status.setFont(new Font(status.getFont().getName(),
							    status.getFont().getStyle(),
							    20));
		GridBagConstraints status_layout = new GridBagConstraints();
		status_layout.gridx = 0;
		status_layout.gridy = 1;
		status_layout.anchor = GridBagConstraints.CENTER;
		getContentPane().add(status, status_layout);
		
		
		// rezultat beli
		bel = new JLabel();
		bel.setFont(new Font(bel.getFont().getName(),
							    bel.getFont().getStyle(),
							    20));
		GridBagConstraints bel_layout = new GridBagConstraints();
		bel_layout.gridx = 0;
		bel_layout.gridy = 2;
		bel_layout.anchor = GridBagConstraints.CENTER;
		getContentPane().add(bel, bel_layout);
		
		// rezultat crni
		crn = new JLabel();
		crn.setFont(new Font(crn.getFont().getName(),
								crn.getFont().getStyle(),
							    20));
		GridBagConstraints crn_layout = new GridBagConstraints();
		crn_layout.gridx = 0;
		crn_layout.gridy = 3;
		crn_layout.anchor = GridBagConstraints.CENTER;
		getContentPane().add(crn, crn_layout);
				
		
		// za�nemo novo igro �loveka proti ra�unalniku
		
		vodja.novaIgra(Igralec.BELI);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == igraClovekRacunalnik) {
			vodja.novaIgra(Igralec.BELI);
		}
		else if (e.getSource() == igraRacunalnikClovek) {
			vodja.novaIgra(Igralec.CRNI);
		}
		
	}

	public void osveziGUI() {
		if (Igra.stanjeIgre == null) {
			status.setText("Igra ni v teku.");
			bel.setText("BELI: " + Integer.toString(Igra.stevecBelih));
			crn.setText("CRNI: " + Integer.toString(Igra.stevecCrnih));
		}
		else {
			if (Igra.stanjeIgre == Stanje.NA_POTEZI_B) status.setText("Na potezi je beli.");
			if (Igra.stanjeIgre == Stanje.NA_POTEZI_C) status.setText("Na potezi je �rni.");
			if (Igra.stanjeIgre == Stanje.ZMAGA_BELI) status.setText("Zmagal je beli.");
			if (Igra.stanjeIgre == Stanje.ZMAGA_CRNI) status.setText("Zmagal je �rni.");
			if (Igra.stanjeIgre == Stanje.NEODLOCENO) status.setText("Neodlo�eno!");
		}
		
		bel.setText("BELI: " + Integer.toString(Igra.stevecBelih));
		crn.setText("CRNI: " + Integer.toString(Igra.stevecCrnih));
		
		polje.repaint();
	}
	



}
