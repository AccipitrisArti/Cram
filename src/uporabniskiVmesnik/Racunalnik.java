package uporabniskiVmesnik;

import javax.swing.SwingWorker;

import Inteligenca.NakljucnaInteligenca;
import logika.Poteza;

public class Racunalnik extends Strateg  {
	private Okno master;
	private SwingWorker<Poteza,Object> mislec;
	private boolean prekini;

	public Racunalnik(Okno master) {
		this.master = master;
	}
	
	@Override
	public void na_potezi() {
		// Zaènemo razmišljati
		mislec = new NakljucnaInteligenca(master);
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
