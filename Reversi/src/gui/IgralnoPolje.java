package gui;

import java.awt.BasicStroke;
import java.awt.event.*;
import java.awt.*;

import javax.swing.*;

import logika.Igra;
import logika.Polje;
import logika.Poteza;
import logika.Vodja;

@SuppressWarnings("serial")
public class IgralnoPolje extends JPanel implements MouseListener {
	private Vodja vodja;
	
	private final static double LINE_WIDTH = 0.1;
	
	private final static double PADDING = 0.1;
	
	public IgralnoPolje(Vodja vodja) {
		super();
		setBackground(Color.getHSBColor(101, 28, 82));
		this.addMouseListener(this);
		
		this.vodja = vodja;
		
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(400, 400);
	}

	/**
	 * @return širina enega kvadratka
	 */
	private double squareWidth() {
		return Math.min(getWidth(), getHeight()) / Igra.N;
	}
	
	// beli in crni krozci
	
	private void paintBeli(Graphics2D g2, int i, int j) {
		double w = squareWidth();
		double r = w * (1.0 - LINE_WIDTH - 2.0 * PADDING); // premer O
		double x = w * (i + 0.5 * LINE_WIDTH + PADDING);
		double y = w * (j + 0.5 * LINE_WIDTH + PADDING);
		g2.setColor(Color.white);
		g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH)));
		g2.fillOval((int)x, (int)y, (int)r , (int)r);
	}
	
	private void paintCrni(Graphics2D g2, int i, int j) {
		double w = squareWidth();
		double r = w * (1.0 - LINE_WIDTH - 2.0 * PADDING); // premer O
		double x = w * (i + 0.5 * LINE_WIDTH + PADDING);
		double y = w * (j + 0.5 * LINE_WIDTH + PADDING);
		g2.setColor(Color.black);
		g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH)));
		g2.fillOval((int)x, (int)y, (int)r , (int)r);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;

		double w = squareWidth();
		
		// Èrte
		g2.setColor(Color.black);
		g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH)));
		for (int i = 1; i < Igra.N; i++) {
			g2.drawLine((int)(i * w),
					    (int)(0),
					    (int)(i * w),
					    (int)(Igra.N * w));
			g2.drawLine((int)(0),
					    (int)(i * w),
					    (int)(Igra.N * w),
					    (int)(i * w));
		}
		
		System.out.println(vodja);
		
		Polje[][] plosca;;
		if (vodja.igra != null) {
			plosca = vodja.igra.plosca;
			for (int i = 0; i < Igra.N; i++) {
				for (int j = 0; j < Igra.N; j++) {
					switch(plosca[i][j]) {
					case CRNO: paintCrni(g2, i, j); break;
					case BELO: paintBeli(g2, i, j); break;
					default: break;
					}
				}
			}
		}	
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (vodja.clovekNaVrsti) {
			int x = e.getX();
			int y = e.getY();
			System.out.println(x + ", " + y);
			int w = (int)(squareWidth());
			int i = x / w ;
			double di = (x % w) / squareWidth() ;
			int j = y / w ;
			double dj = (y % w) / squareWidth() ;
			if (0 <= i && i < Igra.N &&
					0.5 * LINE_WIDTH < di && di < 1.0 - 0.5 * LINE_WIDTH &&
					0 <= j && j < Igra.N && 
					0.5 * LINE_WIDTH < dj && dj < 1.0 - 0.5 * LINE_WIDTH) {
				vodja.clovekovaPoteza(new Poteza(i, j));
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {		
	}

	@Override
	public void mouseReleased(MouseEvent e) {		
	}

	@Override
	public void mouseEntered(MouseEvent e) {		
	}

	@Override
	public void mouseExited(MouseEvent e) {		
	}
}
