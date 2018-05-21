package uporabniskiVmesnik;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.StringTokenizer;

import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import logika.Igra;
import logika.Igralec;
import logika.Poteza;

public class Okno extends JFrame implements ActionListener {
	
	/**
	 * cas, ko se racunalnik pretvarja, da razmislja
	 */
	public int hitrostRacunalnika = 100;
	
	/**
	 * platno, kjer se izrisuje igra
	 */
	public Platno platno;
	
	/**
	 * Statusna vrstica v spodnjem delu okna
	 */
	private JLabel status;
	
	/**
	 * Logika igre, null ce se igra trenutno ne igra
	 */
	public Igra igra;
	
	/**
	 * Strateg, ki postavlja rdece ploscice.
	 */
	private Strateg strategPrvi;
	private String imePrvega = "PRVI";

	/**
	 * Strateg, ki postavlja modre ploscice
	 */
	private Strateg strategDrugi;
	private String imeDrugega = "DRUGI";
	
	private JMenuItem nova = new JMenuItem("Nova igra");
	private JMenuItem dim = new JMenuItem("Dimenzije plosce");
	private JMenuItem rezultat = new JMenuItem("Rezultat");
	/**
	 * omogoci spreminjanje imen igralcev,
	 * tipe igralcev (clovek/racunalnik) in
	 * inteligenco racunalnika
	 */
	private JMenuItem igralca = new JMenuItem("Nastavi imena in tipe igralcev");
	
	// nastavi barve
	private JMenuItem barvaPrvega = new JMenuItem("Spremeni barvo prvega igralca");
	private JMenuItem barvaDrugega = new JMenuItem("Spremeni barvo drugega igralca");
	private JMenuItem barvaOznacenega = new JMenuItem("Spremeni barvo oznacenega polja");
	private JMenuItem barvaPrazne = new JMenuItem("Spremeni barvo praznega polja");
	private JMenuItem barvaOkna = new JMenuItem("Spremeni barvo okna");
	
	// pomoc uporabniku
	private JMenuItem navodila = new JMenuItem("Navodila za uporabo");
	private JMenuItem pravila = new JMenuItem("Pravila igre");
	private JMenuItem izhod = new JMenuItem("Izhod");
	
	public Okno() {
		super();
		setTitle("Cram");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new GridBagLayout());
		
		platno = new Platno(this);
		GridBagConstraints platno_layout = new GridBagConstraints();
		platno_layout.gridx = 0;
		platno_layout.gridy = 0;
		platno_layout.fill = GridBagConstraints.BOTH;
		platno_layout.weightx = 1.0;
		platno_layout.weighty = 1.0;
		getContentPane().add(platno, platno_layout);
		
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
		
		JMenuBar mb = new JMenuBar();
		
		nova.addActionListener(this);
		mb.add(nova);
		
		JMenu moznostiIgre = new JMenu("Možnosti igre");
		
		// preden zacnes novo igro, nastavis velikost plosce (kot v wordu velikost tabele)
		dim.addActionListener(this);
		moznostiIgre.add(dim);
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
		
		JMenu pomoc = new JMenu("Pomoc");
		
		pravila.addActionListener(this);
		pomoc.add(pravila);
		navodila.addActionListener(this);
		pomoc.add(navodila);
		
		mb.add(pomoc);
		
		izhod.addActionListener(this);
		mb.add(izhod);
		
		setJMenuBar(mb);
		
		novaIgra();
	}

	/**
	 * zakljuci morebitno staro in zacni novo igro
	 */
	public void novaIgra() {
		if (strategPrvi != null) { strategPrvi.prekini(); }
		if (strategDrugi != null) { strategDrugi.prekini(); }
		igra = new Igra();
		strategPrvi = new Racunalnik(this, Igralec.prvi);
		strategDrugi = new Racunalnik(this, Igralec.drugi);
		// Tistemu, ki je na potezi, to povemo
		switch (igra.stanje()) {
			case NA_POTEZI_PRVI: strategPrvi.na_potezi(); break;
			case NA_POTEZI_DRUGI: strategDrugi.na_potezi(); break;
			default: break;
		}
		osveziGUI();
}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == nova) {
			novaIgra();
		} else if (e.getSource() == dim) {
			String n = JOptionPane.showInputDialog("Vnesi višino plošèe: ");
			String m = JOptionPane.showInputDialog("Vnesi širino plošèe: ");
			Igra.visinaPlosce = Integer.parseInt(n);
			Igra.sirinaPlosce = Integer.parseInt(m);
			novaIgra();
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
	
	/** postavi ploscico in nasprotniku rece, da je na vrsti
	 * @param p
	 * @return true, ce je bila poteza izvedena
	 */
	public boolean odigraj(Poteza p) {
		if (igra.postaviPloscico(p)) {
			osveziGUI();
			switch (igra.stanje()) {
				case NA_POTEZI_PRVI: strategPrvi.na_potezi(); break;
				case NA_POTEZI_DRUGI: strategDrugi.na_potezi(); break;
				case ZMAGA_PRVI: break;
				case ZMAGA_DRUGI: break;
			}
			return true;
		} else return false;
	}
	
	/**
	 * Osvezi statusno vrstico in plosco
	 */
	public void osveziGUI() {
		if (igra == null) {
			status.setText("Igra ni v teku.");
		}
		else {
			switch(igra.stanje()) {
			case NA_POTEZI_PRVI: status.setForeground (platno.barvaPrvega); status.setText("Na potezi je "+imePrvega); break;
			case NA_POTEZI_DRUGI: status.setForeground (platno.barvaDrugega); status.setText("Na potezi je "+imeDrugega); break;
			case ZMAGA_PRVI: status.setForeground (platno.barvaPrvega); status.setText("Zmagal je "+imePrvega); break;
			case ZMAGA_DRUGI: status.setForeground (platno.barvaDrugega); status.setText("Zmagal je "+imeDrugega); break;
			}
		}
		repaint();
	}
	
	/**
	 * Igralec, ki je na vrsti ustrezno reagira na klik miske na plosco.
	 * @param i
	 * @param j
	 */
	public void klikniPolje(int i, int j) {
		if (igra != null) {
			switch (igra.stanje()) {
			case NA_POTEZI_DRUGI:
				strategDrugi.klik(i, j);
				break;
			case NA_POTEZI_PRVI:
				strategPrvi.klik(i, j);
				break;
			default:
				break;
			}
		}		
	}
	
	/**
	 * @return kopija trenutne igre
	 */
	public Igra copyIgra() {
		return new Igra(igra);
	}
}
