package uporabniskiVmesnik;

import javax.swing.SwingWorker;

import inteligenca.AlphaBeta;
import logika.Igralec;
import logika.Poteza;

public class Racunalnik extends Strateg  {
	private Okno master;
	private Igralec jaz;
	private SwingWorker<Poteza,Object> mislec;
	private int globinaRazmisljanja;

	public Racunalnik(Okno master, Igralec jaz, int globina) {
		this.master = master;
		this.jaz = jaz;
		globinaRazmisljanja = globina;
	}
	
	@Override
	public void na_potezi() {
		// Začnemo razmišljati
		mislec = new AlphaBeta(master, globinaRazmisljanja, jaz);
		// mislec = new Minimax(master, globinaRazmisljanja, jaz);
		// mislec = new NakljucnaInteligenca(master);
		mislec.execute();
	}

	@Override
	public void prekini() {
		if (mislec != null) {
			mislec.cancel(true);
		}
	}

	@Override
	public void klik(int i, int j) {
	}
	
}
