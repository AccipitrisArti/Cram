package uporabniskiVmesnik;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import logika.Igra;
import logika.Igralec;
import logika.Poteza;

@SuppressWarnings("serial")
public class Okno extends JFrame implements ActionListener {
	
	/**
	 * platno, kjer se izrisuje igra
	 */
	public Platno platno;
	
	/**
	 * Statusna vrstica v spodnjem delu okna
	 */
	public JLabel status;
	
	/**
	 * Logika igre, null ce se igra trenutno ne igra
	 */
	public Igra igra;
	
	// privyeto igramo proti racunalniku
	public TipIgre tip = TipIgre.cr;
	
	/**
	 * Strateg, ki postavlja sive ploscice (v osnovni barvni paleti 1).
	 */
	private Strateg strategPrvi;
	/**
	 * Strateg, ki postavlja rdece ploscice (v osnovni barvni paleti 1).
	 */
	private Strateg strategDrugi;
	
	private JMenuItem nova = new JMenuItem("Nova igra");
	private JMenuItem dim = new JMenuItem("Dimenzije plošče");
	
	// omogoci spreminjanje imen igralcev, tipe igralcev (clovek/racunalnik) in
	// inteligenco racunalnika
	private JMenuItem cc = new JMenuItem("Človek vs. Človek");
	
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
	
	
	Image icon = Toolkit.getDefaultToolkit().getImage("Icon/Cram-icon.png");
	
	public Okno() throws IOException {
		super();
		setTitle("Cram");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new GridBagLayout());
		
		// nastavi ikono programa
		this.setIconImage(icon);
	    
		platno = new Platno(this);
		GridBagConstraints platno_layout = new GridBagConstraints();
		platno_layout.gridx = 0;
		platno_layout.gridy = 0;
		platno_layout.fill = GridBagConstraints.BOTH;
		platno_layout.weightx = 1.0;
		platno_layout.weighty = 1.0;
		getContentPane().add(platno, platno_layout);
		
		// statusna vrstica za sporocila
		status = new JLabel();
		status.setFont(new Font(status.getFont().getName(),
							    status.getFont().getStyle(),
							    20));
		GridBagConstraints status_layout = new GridBagConstraints();
		status_layout.gridx = 0;
		status_layout.gridy = 1;
		status_layout.anchor = GridBagConstraints.CENTER;
		getContentPane().add(status, status_layout);
		
		this.setLocationRelativeTo(null);
		this.setLocation(this.getX()-this.platno.sirina/2, this.getY()-this.platno.visina/2);
		
		JMenuBar mb = new JMenuBar();
		
		nova.addActionListener(this);
		mb.add(nova);
		
		JMenu moznostiIgre = new JMenu("Možnosti igre");
		
		cc.addActionListener(this);
		moznostiIgre.add(cc);
		
		JMenu cr = new JMenu("Človek vs. Računalnik");
		cr1.addActionListener(this);
		cr.add(cr1);
		cr2.addActionListener(this);
		cr.add(cr2);
		cr3.addActionListener(this);
		cr.add(cr3);
		moznostiIgre.add(cr);
		
		JMenu rc = new JMenu("Računalnik vs. Človek");
		rc1.addActionListener(this);
		rc.add(rc1);
		rc2.addActionListener(this);
		rc.add(rc2);
		rc3.addActionListener(this);
		rc.add(rc3);
		moznostiIgre.add(rc);
		
		JMenu rr = new JMenu("Računalnik vs. Računalnik");
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
			strategPrvi = new Clovek(this, Igralec.prvi, tip.globinaPrvega);
			strategDrugi = new Clovek(this, Igralec.drugi, tip.globinaDrugega);
		} else if (tip == TipIgre.cr) {
			strategPrvi = new Clovek(this, Igralec.prvi, tip.globinaPrvega);
			strategDrugi = new Racunalnik(this, Igralec.drugi, tip.globinaDrugega);
		} else if (tip == TipIgre.rc) {
			strategPrvi = new Racunalnik(this, Igralec.prvi, tip.globinaPrvega);
			strategDrugi = new Clovek(this, Igralec.drugi, tip.globinaDrugega);
		} else if (tip == TipIgre.rr) {
			strategPrvi = new Racunalnik(this, Igralec.prvi, tip.globinaPrvega);
			strategDrugi = new Racunalnik(this, Igralec.drugi, tip.globinaDrugega);
		}
		// Tistemu, ki je na potezi, to povemo
		switch (igra.stanje()) {
			case NA_POTEZI_PRVI: strategPrvi.na_potezi(); break;
			case NA_POTEZI_DRUGI: strategDrugi.na_potezi(); break;
			default: break;
		}
		platno.izbraniI = -1;
		platno.izbraniJ = -1;
		osveziGUI();
}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == nova) {
			novaIgra();
		} else if (e.getSource() == dim) {
			String n = JOptionPane.showInputDialog("Vnesi višino plošče: ");
			String m = JOptionPane.showInputDialog("Vnesi širino plošče: ");
			Igra.visinaPlosce = Integer.parseInt(n);
			Igra.sirinaPlosce = Integer.parseInt(m);
			novaIgra();
		} else if (e.getSource() == cc) {
			tip = TipIgre.cc;
			novaIgra();
		} else if (e.getSource() == cr1) {
			tip = TipIgre.cr;
			tip.globinaDrugega = tip.globinaCesarja;
			novaIgra();
		} else if (e.getSource() == cr2) {
			tip = TipIgre.cr;
			tip.globinaDrugega = tip.globinaBrdausa;
			novaIgra();
		} else if (e.getSource() == cr3) {
			tip = TipIgre.cr;
			tip.globinaDrugega = tip.globinaKrpana;
			novaIgra();
		} else if (e.getSource() == rc1) {
			tip = TipIgre.rc;
			tip.globinaPrvega = tip.globinaCesarja;
			novaIgra();
		} else if (e.getSource() == rc2) {
			tip = TipIgre.rc;
			tip.globinaPrvega = tip.globinaBrdausa;
			novaIgra();
		} else if (e.getSource() == rc3) {
			tip = TipIgre.rc;
			tip.globinaPrvega = tip.globinaKrpana;
			novaIgra();
		} else if (e.getSource() == r1r1) {
			tip = TipIgre.rr;
			tip.globinaPrvega = tip.globinaCesarja;
			tip.globinaDrugega = tip.globinaCesarja;
			novaIgra();
		} else if (e.getSource() == r1r2) {
			tip = TipIgre.rr;
			tip.globinaPrvega = tip.globinaCesarja;
			tip.globinaDrugega = tip.globinaBrdausa;
			novaIgra();
		} else if (e.getSource() == r1r3) {
			tip = TipIgre.rr;
			tip.globinaPrvega = tip.globinaCesarja;
			tip.globinaDrugega = tip.globinaKrpana;
			novaIgra();
		} else if (e.getSource() == r2r1) {
			tip = TipIgre.rr;
			tip.globinaPrvega = tip.globinaBrdausa;
			tip.globinaDrugega = tip.globinaCesarja;
			novaIgra();
		} else if (e.getSource() == r2r2) {
			tip = TipIgre.rr;
			tip.globinaPrvega = tip.globinaBrdausa;
			tip.globinaDrugega = tip.globinaBrdausa;
			novaIgra();
		} else if (e.getSource() == r2r3) {
			tip = TipIgre.rr;
			tip.globinaPrvega = tip.globinaBrdausa;
			tip.globinaDrugega = tip.globinaKrpana;
			novaIgra();
		} else if (e.getSource() == r3r1) {
			tip = TipIgre.rr;
			tip.globinaPrvega = tip.globinaKrpana;
			tip.globinaDrugega = tip.globinaCesarja;
			novaIgra();
		} else if (e.getSource() == r3r2) {
			tip = TipIgre.rr;
			tip.globinaPrvega = tip.globinaKrpana;
			tip.globinaDrugega = tip.globinaBrdausa;
			novaIgra();
		} else if (e.getSource() == r3r3) {
			tip = TipIgre.rr;
			tip.globinaPrvega = tip.globinaKrpana;
			tip.globinaDrugega = tip.globinaKrpana;
			novaIgra();
		} else if (e.getSource() == barve1) {
			platno.barvaPrazne = Color.lightGray;
			platno.barvaPrvega = Color.darkGray;
			platno.barvaDrugega = Color.red;
			platno.barvaOznacenega = Color.orange;
			platno.setBackground(Color.WHITE);
			osveziGUI();
		} else if (e.getSource() == barve2) {
			platno.barvaPrazne = new Color(164,181,199);
			platno.barvaPrvega = new Color(224, 168, 109);
			platno.barvaDrugega = new Color(96, 49, 29);
			platno.barvaOznacenega = new Color(253, 241, 229);
			platno.setBackground(new Color(9,5,6));
			osveziGUI();
		} else if (e.getSource() == barveLava) {
			platno.barvaPrazne = Color.gray;
			platno.barvaPrvega = new Color(162, 17, 16);
			platno.barvaDrugega = new Color(225, 57, 2);
			platno.barvaOznacenega = Color.lightGray;
			platno.setBackground(Color.darkGray);
			osveziGUI();
		} else if (e.getSource() == barveGozd) {
			platno.barvaPrazne = new Color(127, 57, 2);
			platno.barvaPrvega = new Color(27, 57, 2);
			platno.barvaDrugega = new Color(9, 45, 7);
			platno.barvaOznacenega = new Color(117, 112, 168);
			platno.setBackground(new Color(89, 138, 2));
			osveziGUI();
		} else if (e.getSource() == barveJama) {
			platno.barvaPrazne = Color.darkGray;
			platno.barvaPrvega = Color.gray;
			platno.barvaDrugega = Color.lightGray;
			platno.barvaOznacenega = Color.yellow;
			platno.setBackground(Color.BLACK);
			osveziGUI();
		} else if (e.getSource() == barveMorje) {
			platno.barvaPrazne = Color.white;
			platno.barvaPrvega = new Color(97, 101, 143);
			platno.barvaDrugega = new Color(41, 60, 114);
			platno.barvaOznacenega = new Color(153, 53, 115);
			platno.setBackground(new Color(181, 182, 253));
			osveziGUI();
		} else if (e.getSource() == pravila) {
			
			JFrame oknoPravil = new JFrame();
			oknoPravil.setTitle("Pravila igre Cram");
			oknoPravil.setFont(new Font(status.getFont().getName(),
				    status.getFont().getStyle(),
				    14));
			final int sirinaOkenca = 400;
			final int visinaOkenca = 300;
			oknoPravil.setPreferredSize(new Dimension(sirinaOkenca, visinaOkenca));
			JLabel besedilo = new JLabel("<html>"
					+ "<h2 style='padding: 8pt;'>Pravila igre Cram</h2>"
					+ "<p style='border:3px solid #ffa500; background-color: #eecc44; padding: 8pt; margin: 3pt 0 3pt 0; font-size:120%;'>"
					+ "Igralca izmenično na igralno ploščo </br>"
					+ "postavljata ploščice velikosti 1x2. </br>"
					+ "Ko nek igralec ne more več postaviti </br>"
					+ "ploščice, je njegov nasprotnik zmagal.</p>"
					+ "<p style='margin: 10pt; font-size:110%'>"
					+ "Za postavljanje ploščice zaporedoma pritisni </br>"
					+ "sosednji polji. Izbiro odstrani s ponovnim klikom.</html>");
			oknoPravil.getContentPane().add(besedilo);
			oknoPravil.setIconImage(icon);
			
			oknoPravil.setLocationRelativeTo(this);
			oknoPravil.setLocation(oknoPravil.getX()-sirinaOkenca/2, oknoPravil.getY()-visinaOkenca/2);
			
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
			case NA_POTEZI_PRVI: {
				status.setForeground (platno.barvaPrvega);
				if (tip == TipIgre.rc || tip == TipIgre.rr) status.setText(tip.imena()[0]+" razmišlja ...");
				else status.setText("Na potezi je "+tip.imena()[0]);
				break;}
			case NA_POTEZI_DRUGI: {
				status.setForeground (platno.barvaDrugega);
				if (tip == TipIgre.cr || tip == TipIgre.rr) status.setText(tip.imena()[1]+" razmišlja ...");
				else status.setText("Na potezi je "+tip.imena()[1]);
				break;}
			case ZMAGA_PRVI: status.setForeground (platno.barvaPrvega); status.setText("Zmagal je "+tip.imena()[0]); break;
			case ZMAGA_DRUGI: status.setForeground (platno.barvaDrugega); status.setText("Zmagal je "+tip.imena()[1]); break;
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
