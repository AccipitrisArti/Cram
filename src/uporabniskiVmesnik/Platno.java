package uporabniskiVmesnik;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import logika.Igra;
import logika.Polje;
import logika.Poteza;
import logika.Stanje;

public class Platno extends JPanel implements MouseListener, MouseMotionListener, KeyListener {
	
	protected int sirina = 700;
	protected int visina = 700;
	protected Okno okno;
	protected Color barvaPrazne = Color.white;
	protected Color barvaPrvega = Color.RED;
	protected Color barvaDrugega = Color.BLUE;
	protected Color barvaOznacenega = Color.YELLOW;
	protected Color barvaOkna = Color.BLACK;
	// shranimo polje, iz katerega postavljamo ploscico
	protected int izbraniI = -1;
	protected int izbraniJ = -1;
	protected double sirinaRoba = 0.1;
	
	public Platno(Okno okno) {
		this.okno = okno;
		this.setBackground(barvaOkna);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addKeyListener(this);
	}
	
	protected int velikostPolja() {
		return round(Math.min(okno.platno.getWidth()/okno.igra.sirinaPlosce, okno.platno.getHeight()/okno.igra.visinaPlosce));
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(sirina, visina);
	}
	
	public static int round(double x) {
		return (int)(x+0.5);
	}
	
	public void paintComponent(Graphics g) {
		if (okno.igra == null) return;
		super.paintComponent(g);
		// risanje plosce
		for (int i=0; i<okno.igra.visinaPlosce; i++) {
			for (int j=0; j<okno.igra.sirinaPlosce; j++) {
				if (okno.igra.polje[i][j]==Polje.prvi) {
					g.setColor(barvaPrvega);
				} else if (okno.igra.polje[i][j]==Polje.drugi) {
					g.setColor(barvaDrugega);
				} else if (i==izbraniI && j==izbraniJ) {
					// ce je oznacen, ga obarvamo drugace
					g.setColor(barvaOznacenega);
				} else {
					g.setColor(barvaPrazne);
				}
				g.fillRect(round((j+sirinaRoba/2)*velikostPolja()), round((i+sirinaRoba/2)*velikostPolja()), 
						round((1-sirinaRoba)*velikostPolja()),
						round((1-sirinaRoba)*velikostPolja()));
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// izberemo polje (najprej preverimo, da smo pritisnili na polje in en na rob)
		int izbI = -1;
		if (round((1-sirinaRoba)*50)>Math.abs(e.getY()*100/velikostPolja()-round(e.getY()/velikostPolja())*100-50)) {
			izbI = round(e.getY()/velikostPolja());
		}
		int izbJ = -1;
		if (round((1-sirinaRoba)*50)>Math.abs(e.getX()*100/velikostPolja()-round(e.getX()/velikostPolja())*100-50)) {
			izbJ = round(e.getX()/velikostPolja());
		}
		if (izbI >= 0 && izbI < okno.igra.visinaPlosce && izbJ >= 0 && izbJ < okno.igra.sirinaPlosce) {
			if (okno.igra.polje[izbI][izbJ] == Polje.prazno)
				okno.klikniPolje(izbI, izbJ);
		}
		okno.osveziGUI();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}
