package uporabniskiVmesnik;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.StringTokenizer;

import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import logika.Igra;

public class Okno extends JFrame implements ActionListener {
	
	public Platno platno;
	
	private JMenuItem nova = new JMenuItem("Nova igra");
	private JMenuItem rezultat = new JMenuItem("Rezultat");
	private JMenuItem igralca = new JMenuItem("Nastavi imena in tipe igralcev");
	
	private JMenuItem barvaPrvega = new JMenuItem("Spremeni barvo prvega igralca");
	private JMenuItem barvaDrugega = new JMenuItem("Spremeni barvo drugega igralca");
	private JMenuItem barvaOznacenega = new JMenuItem("Spremeni barvo oznacenega polja");
	private JMenuItem barvaPrazne = new JMenuItem("Spremeni barvo praznega polja");
	private JMenuItem barvaOkna = new JMenuItem("Spremeni barvo okna");
	
	private JMenuItem navodila = new JMenuItem("Navodila za uporabo");
	private JMenuItem pravila = new JMenuItem("Pravila igre");
	private JMenuItem izhod = new JMenuItem("Izhod");
	
	public Okno(int sirina, int visina) {
		super();
		setTitle("Cram");
		platno = new Platno(sirina, visina);
		add(platno);
		platno.setFocusable(true);
		
		JMenuBar mb = new JMenuBar();
		JMenu moznostiIgre = new JMenu("Možnosti igre");
		
		// preden zacnes novo igro, nastavis velikost plosce (kot v wordu velikost tabele)
		nova.addActionListener(this);
		moznostiIgre.add(nova);
		// izpis rezultata obeh igralcev
		rezultat.addActionListener(this);
		moznostiIgre.add(rezultat);
		// vsak igralec ima ime in lahko je tipa clovek ali racunalnik
		igralca.addActionListener(this);
		moznostiIgre.add(igralca);
		
		mb.add(moznostiIgre);
		
		
		JMenu izgled = new JMenu("Izgled");
		
		barvaPrvega.addActionListener(this);
		izgled.add(barvaPrvega);
		barvaDrugega.addActionListener(this);
		izgled.add(barvaDrugega);
		izgled.addSeparator();
		barvaOznacenega.addActionListener(this);
		izgled.add(barvaOznacenega);
		barvaPrazne.addActionListener(this);
		izgled.add(barvaPrazne);
		barvaOkna.addActionListener(this);
		izgled.add(barvaOkna);
		
		mb.add(izgled);
		
		JMenu pomoc = new JMenu("Pomoè");
		
		pravila.addActionListener(this);
		pomoc.add(pravila);
		navodila.addActionListener(this);
		pomoc.add(navodila);
		
		mb.add(pomoc);
		
		izhod.addActionListener(this);
		mb.add(izhod);
		
		setJMenuBar(mb);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == nova) {
			String n = JOptionPane.showInputDialog("Vnesi višino plošèe: ");
			String m = JOptionPane.showInputDialog("Vnesi širino plošèe: ");
			platno.igra = new Igra(Integer.parseInt(n), Integer.parseInt(m));
			repaint();
		} else if (e.getSource() == rezultat) {
			// uporabniski vmesnik steje zmage prvega in drugega igralca
			// izpis rezultata v oknu
		} else if (e.getSource() == igralca) {
			// izbira imen in tipov igralcev (clovek ali racunalnik)
		} else if (e.getSource() == barvaPrvega) {
			
		} else if (e.getSource() == barvaDrugega) {
			
		} else if (e.getSource() == barvaOznacenega) {
			
		} else if (e.getSource() == barvaPrazne) {
			
		} else if (e.getSource() == barvaOkna) {
			
		} else if (e.getSource() == pravila) {
			// pravila in cilj igre
		} else if (e.getSource() == navodila) {
			// navodila za uporabo programa
		} else if (e.getSource() == izhod) {
			dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}
		repaint();
	}
}
