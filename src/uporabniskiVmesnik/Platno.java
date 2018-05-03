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
import logika.Stanje;

public class Platno extends JPanel implements MouseListener, MouseMotionListener, KeyListener {
	
	protected int sirina;
	protected int visina;
	protected Igra igra;
	protected Color barvaPrazne = Color.white;
	protected Color barvaPrvega = Color.RED;
	protected Color barvaDrugega = Color.BLUE;
	protected Color barvaOznacenega = Color.YELLOW;
	protected Color barvaOkna = Color.BLACK;
	// shranimo polje, iz katerega postavljamo ploscico
	protected int izbraniI = -1;
	protected int izbraniJ = -1;
	
	public Platno(int sirina, int visina) {
		this.sirina = sirina;
		this.visina = visina;
		igra = null;
		this.setBackground(barvaOkna);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addKeyListener(this);
	}
	
	public void narisi(Igra g) {
		igra = g;
		repaint();
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(sirina, visina);
	}
	
	public static int round(double x) {
		return (int)(x+0.5);
	}
	
	public void paintComponent(Graphics g) {
		if (igra == null) return;
		super.paintComponent(g);
		// risanje plosce
		for (int i=0; i<igra.visinaPlosce; i++) {
			for (int j=0; j<igra.sirinaPlosce; j++) {
				if (igra.polje[i][j]==Polje.prvi) {
					g.setColor(barvaPrvega);
				} else if (igra.polje[i][j]==Polje.drugi) {
					g.setColor(barvaDrugega);
				} else if (i==izbraniI && j==izbraniJ) {
					// ce je oznacen, ga obarvamo drugace
					g.setColor(barvaOznacenega);
				} else {
					g.setColor(barvaPrazne);
				}
				g.fillRect(j*Math.min(sirina, visina)/igra.sirinaPlosce + 5, i*Math.min(sirina, visina)/igra.visinaPlosce + 5, 
						Math.min(sirina, visina)/igra.sirinaPlosce - 10,
						Math.min(sirina, visina)/igra.visinaPlosce - 10);
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int izbI = round(e.getY()*igra.visinaPlosce/Math.min(sirina, visina));
		int izbJ = round(e.getX()*igra.sirinaPlosce/Math.min(sirina, visina));
		if (izbI >= 0 && izbI < igra.visinaPlosce && izbJ >= 0 && izbJ < igra.sirinaPlosce) {
			if (izbraniI == -1 && izbraniJ == -1) {
				// ce ni oznaceno se nobeno polje, oznaci izbrano polje, ce je to se prazno
				if (igra.polje[izbI][izbJ] == Polje.prazno) {
					izbraniJ = round(izbJ);
					izbraniI = round(izbI);
				}
			} else if (izbI==izbraniI && izbJ==izbraniJ) {
				// odstrani oznako, ce ponovno pritisnjeno isto polje
				izbraniI = -1;
				izbraniJ = -1;
			} else if (igra.postaviPloscico(round(e.getY()*igra.visinaPlosce/Math.min(sirina, visina)),
					round(e.getX()*igra.sirinaPlosce/Math.min(sirina, visina)),
					izbraniI, izbraniJ)) {
				// naredi potezo, ce je veljavna
				izbraniI = -1;
				izbraniJ = -1;
			}
		}
		if (igra.stanje()==Stanje.ZMAGA_PRVI) System.out.println("Zmagal je prvi igralec!");
		else if (igra.stanje()==Stanje.ZMAGA_DRUGI) System.out.println("Zmagal je drugi igralec!");
		repaint();
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
