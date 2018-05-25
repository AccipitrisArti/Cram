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
	
	private TipIgre tip = TipIgre.cr;
	private int globinaCesarja = 1;
	private int globinaBrdausa = 3;
	private int globinaKrpana = 5;
	
	/**
	 * Strateg, ki postavlja rdece ploscice.
	 */
	private Strateg strategPrvi;
	private String imePrvega = "PRVI";
	private int globinaPrvega = 2;
	/**
	 * Strateg, ki postavlja modre ploscice
	 */
	private Strateg strategDrugi;
	private String imeDrugega = "DRUGI";
	private int globinaDrugega = 3;
	
	private JMenuItem nova = new JMenuItem("Nova igra");
	private JMenuItem dim = new JMenuItem("Dimenzije plosce");
	
	// omogoci spreminjanje imen igralcev, tipe igralcev (clovek/racunalnik) in
	// inteligenco racunalnika
	private JMenuItem cc = new JMenuItem("Clovek vs. Clovek");
	
	private JMenuItem cr1 = new JMenuItem("proti avstrijskemu cesarju");
	private JMenuItem cr2 = new JMenuItem("proti Brdausu");
	private JMenuItem cr3 = new JMenuItem("proti Martinu Krpanu");
	
	private JMenuItem rc1 = new JMenuItem("proti avstrijskemu cesarju");
	private JMenuItem rc2 = new JMenuItem("proti Brdausu");
	private JMenuItem rc3 = new JMenuItem("proti Martinu Krpanu");
	
	private JMenuItem r1r1 = new JMenuItem("cesar vs. cesar");
	private JMenuItem r2r1 = new JMenuItem("Brdaus vs. cesar");
	private JMenuItem r3r1 = new JMenuItem("Martin Krpan vs. cesar");
	private JMenuItem r1r2 = new JMenuItem("cesar vs. Brdaus");
	private JMenuItem r2r2 = new JMenuItem("Brdaus vs. Brdaus");
	private JMenuItem r3r2 = new JMenuItem("Martin Krpan vs. Brdaus");
	private JMenuItem r1r3 = new JMenuItem("cesar vs. Martin Krpan");
	private JMenuItem r2r3 = new JMenuItem("Brdaus vs. Martin Krpan");
	private JMenuItem r3r3 = new JMenuItem("Martin Krpan vs. Martin Krpan");
	
	// nastavi barve
	private JMenuItem barve1 = new JMenuItem("osnovna barvna paleta 1");
	private JMenuItem barve2 = new JMenuItem("osnovna barvna paleta 2");
	private JMenuItem barveLava = new JMenuItem("Lava");
	private JMenuItem barveGozd = new JMenuItem("Gozd");
	private JMenuItem barveJama = new JMenuItem("Jama");
	private JMenuItem barveMorje = new JMenuItem("Morje");
	
	// pomoc uporabniku
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
		
		JMenuBar mb = new JMenuBar();
		
		nova.addActionListener(this);
		mb.add(nova);
		
		JMenu moznostiIgre = new JMenu("Mo�nosti igre");
		
		cc.addActionListener(this);
		moznostiIgre.add(cc);
		
		JMenu cr = new JMenu("Clovek vs. Racunalnik");
		cr1.addActionListener(this);
		cr.add(cr1);
		cr2.addActionListener(this);
		cr.add(cr2);
		cr3.addActionListener(this);
		cr.add(cr3);
		moznostiIgre.add(cr);
		
		JMenu rc = new JMenu("Racunalnik vs. Clovek");
		rc1.addActionListener(this);
		rc.add(rc1);
		rc2.addActionListener(this);
		rc.add(rc2);
		rc3.addActionListener(this);
		rc.add(rc3);
		moznostiIgre.add(rc);
		
		JMenu rr = new JMenu("Racunalnik vs. Racunalnik");
		r1r1.addActionListener(this);
		rr.add(r1r1);
		r2r1.addActionListener(this);
		rr.add(r2r1);
		r3r1.addActionListener(this);
		rr.add(r3r1);
		r1r2.addActionListener(this);
		rr.add(r1r2);
		r2r2.addActionListener(this);
		rr.add(r2r2);
		r3r2.addActionListener(this);
		rr.add(r3r2);
		r1r3.addActionListener(this);
		rr.add(r1r3);
		r2r3.addActionListener(this);
		rr.add(r2r3);
		r3r3.addActionListener(this);
		rr.add(r3r3);
		moznostiIgre.add(rr);
		
		moznostiIgre.addSeparator();
		dim.addActionListener(this);
		moznostiIgre.add(dim);
		
		mb.add(moznostiIgre);
		
		JMenu izgled = new JMenu("Izgled");
		
		barve1.addActionListener(this);
		izgled.add(barve1);
		barve2.addActionListener(this);
		izgled.add(barve2);
		barveLava.addActionListener(this);
		izgled.add(barveLava);
		barveGozd.addActionListener(this);
		izgled.add(barveGozd);
		barveJama.addActionListener(this);
		izgled.add(barveJama);
		barveMorje.addActionListener(this);
		izgled.add(barveMorje);
		
		mb.add(izgled);
		
		pravila.addActionListener(this);
		mb.add(pravila);
		
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
		if (tip == TipIgre.cc) {
			strategPrvi = new Clovek(this, Igralec.prvi, globinaPrvega);
			strategDrugi = new Clovek(this, Igralec.drugi, globinaDrugega);
		} else if (tip == TipIgre.cr) {
			strategPrvi = new Clovek(this, Igralec.prvi, globinaPrvega);
			strategDrugi = new Racunalnik(this, Igralec.drugi, globinaDrugega);
		} else if (tip == TipIgre.rc) {
			strategPrvi = new Racunalnik(this, Igralec.prvi, globinaPrvega);
			strategDrugi = new Clovek(this, Igralec.drugi, globinaDrugega);
		} else if (tip == TipIgre.rr) {
			strategPrvi = new Racunalnik(this, Igralec.prvi, globinaPrvega);
			strategDrugi = new Racunalnik(this, Igralec.drugi, globinaDrugega);
		}
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
			String n = JOptionPane.showInputDialog("Vnesi vi�ino plo��e: ");
			String m = JOptionPane.showInputDialog("Vnesi �irino plo��e: ");
			Igra.visinaPlosce = Integer.parseInt(n);
			Igra.sirinaPlosce = Integer.parseInt(m);
			novaIgra();
		} else if (e.getSource() == cc) {
			tip = TipIgre.cc;
			novaIgra();
		} else if (e.getSource() == cr1) {
			tip = TipIgre.cr;
			globinaDrugega = globinaCesarja;
			novaIgra();
		} else if (e.getSource() == cr2) {
			tip = TipIgre.cr;
			globinaDrugega = globinaBrdausa;
			novaIgra();
		} else if (e.getSource() == cr3) {
			tip = TipIgre.cr;
			globinaDrugega = globinaKrpana;
			novaIgra();
		} else if (e.getSource() == rc1) {
			tip = TipIgre.rc;
			globinaPrvega = globinaCesarja;
			novaIgra();
		} else if (e.getSource() == rc2) {
			tip = TipIgre.rc;
			globinaPrvega = globinaBrdausa;
			novaIgra();
		} else if (e.getSource() == rc3) {
			tip = TipIgre.rc;
			globinaPrvega = globinaKrpana;
			novaIgra();
		} else if (e.getSource() == r1r1) {
			tip = TipIgre.rr;
			globinaPrvega = globinaCesarja;
			globinaDrugega = globinaCesarja;
			novaIgra();
		} else if (e.getSource() == r1r2) {
			tip = TipIgre.rr;
			globinaPrvega = globinaCesarja;
			globinaDrugega = globinaBrdausa;
			novaIgra();
		} else if (e.getSource() == r1r3) {
			tip = TipIgre.rr;
			globinaPrvega = globinaCesarja;
			globinaDrugega = globinaKrpana;
			novaIgra();
		} else if (e.getSource() == r2r1) {
			tip = TipIgre.rr;
			globinaPrvega = globinaBrdausa;
			globinaDrugega = globinaCesarja;
			novaIgra();
		} else if (e.getSource() == r2r2) {
			tip = TipIgre.rr;
			globinaPrvega = globinaBrdausa;
			globinaDrugega = globinaBrdausa;
			novaIgra();
		} else if (e.getSource() == r2r3) {
			tip = TipIgre.rr;
			globinaPrvega = globinaBrdausa;
			globinaDrugega = globinaKrpana;
			novaIgra();
		} else if (e.getSource() == r3r1) {
			tip = TipIgre.rr;
			globinaPrvega = globinaKrpana;
			globinaDrugega = globinaCesarja;
			novaIgra();
		} else if (e.getSource() == r3r2) {
			tip = TipIgre.rr;
			globinaPrvega = globinaKrpana;
			globinaDrugega = globinaBrdausa;
			novaIgra();
		} else if (e.getSource() == r3r3) {
			tip = TipIgre.rr;
			globinaPrvega = globinaKrpana;
			globinaDrugega = globinaKrpana;
			novaIgra();
		} else if (e.getSource() == barve1) {
			platno.barvaPrazne = Color.lightGray;
			platno.barvaPrvega = Color.darkGray;
			platno.barvaDrugega = Color.red;
			platno.barvaOznacenega = Color.orange;
			platno.setBackground(Color.WHITE);
			osveziGUI();
		} else if (e.getSource() == barve2) {
			platno.barvaPrazne = Color.WHITE;
			platno.barvaPrvega = Color.orange;
			platno.barvaDrugega = Color.blue;
			platno.barvaOznacenega = Color.magenta;
			platno.setBackground(Color.gray);
			osveziGUI();
		} else if (e.getSource() == barveLava) {
			platno.barvaPrazne = Color.red;
			platno.barvaPrvega = Color.gray;
			platno.barvaDrugega = Color.lightGray;
			platno.barvaOznacenega = Color.orange;
			platno.setBackground(Color.darkGray);
			osveziGUI();
		} else if (e.getSource() == barveGozd) {
			platno.barvaPrazne = Color.gray;
			platno.barvaPrvega = Color.blue;
			platno.barvaDrugega = Color.green;
			platno.barvaOznacenega = Color.red;
			platno.setBackground(Color.lightGray);
			osveziGUI();
		} else if (e.getSource() == barveJama) {
			platno.barvaPrazne = Color.darkGray;
			platno.barvaPrvega = Color.gray;
			platno.barvaDrugega = Color.lightGray;
			platno.barvaOznacenega = Color.yellow;
			platno.setBackground(Color.BLACK);
			osveziGUI();
		} else if (e.getSource() == barveMorje) {
			platno.barvaPrazne = Color.lightGray;
			platno.barvaPrvega = Color.white;
			platno.barvaDrugega = Color.blue;
			platno.barvaOznacenega = Color.green;
			platno.setBackground(Color.cyan);
			osveziGUI();
		} else if (e.getSource() == pravila) {
			
			JFrame oknoPravil = new JFrame();
			setTitle("Pravila igre Cram");
			JLabel besedilo = new JLabel();
			besedilo.setFont(new Font(status.getFont().getName(),
								    status.getFont().getStyle(),
								    12));
			besedilo.setText("Igralca izmenicno na igralno plosco \n"
					+ "postavljata ploscice velikosti 1x2. \n"
					+ "Ko nek igralec ne more vec postaviti \n"
					+ "ploscice, je njegov nasprotnik zmagal.");
			oknoPravil.getContentPane().add(besedilo);
			oknoPravil.pack();
			oknoPravil.setVisible(true);
			
		} else if (e.getSource() == izhod) {
			dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}
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
