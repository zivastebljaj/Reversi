package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.*;

import javax.swing.*;

import logika.Igralec;

@SuppressWarnings("serial")
public class GlavnoOkno extends JFrame implements ActionListener {
	/**
	 * JPanel, v katerega rišemo èrne in bele
	 */
	private IgralnoPolje polje;

	
	//Statusna vrstica v spodnjem delu okna
	private JLabel status;

	
	// Vodja igre
	private Vodja vodja;
	
	// Izbire v menujih
	private JMenuItem igraClovekRacunalnik;
	private JMenuItem igraRacunalnikClovek;

	/**
	 * Ustvari novo glavno okno in prièni igrati igro.
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

		igraClovekRacunalnik = new JMenuItem("èlovek - raèunalnik");
		igra_menu.add(igraClovekRacunalnik);
		igraClovekRacunalnik.addActionListener(this);
		
		igraRacunalnikClovek = new JMenuItem("raèunalnik - èlovek");
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
		
		// statusna vrstica za sporoèila
		status = new JLabel();
		status.setFont(new Font(status.getFont().getName(),
							    status.getFont().getStyle(),
							    20));
		GridBagConstraints status_layout = new GridBagConstraints();
		status_layout.gridx = 0;
		status_layout.gridy = 1;
		status_layout.anchor = GridBagConstraints.CENTER;
		getContentPane().add(status, status_layout);
		
		// zaènemo novo igro èloveka proti raèunalniku
		
		vodja.novaIgra(Igralec.BELO);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == igraClovekRacunalnik) {
			vodja.novaIgra(Igralec.BELO);
		}
		else if (e.getSource() == igraRacunalnikClovek) {
			vodja.novaIgra(Igralec.CRNO);
		}
		
	}

	public void osveziGUI() {
		if (vodja.igra == null) {
			status.setText("Igra ni v teku.");
		}
		else {
			switch(vodja.igra.stanje()) {
			case NA_POTEZI_BELO: status.setText("Na potezi je beli."); break;
			case NA_POTEZI_CRNO: status.setText("Na potezi je èrni."); break;
			case ZMAGA_BELO: status.setText("Zmagal je beli."); break;
			case ZMAGA_CRNO: status.setText("Zmagal je èrni."); break;
			case NEODLOCENO: status.setText("Neodloèeno!"); break;
			}
		}
		polje.repaint();
	}
	



}
