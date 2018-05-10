package Inteligenca;

import java.awt.List;
import java.util.Random;

import javax.swing.SwingWorker;

import logika.Igra;
import logika.Poteza;
import uporabniskiVmesnik.Okno;

public class NakljucnaInteligenca extends SwingWorker<Poteza, Object> {
	private Okno master;
	
	public NakljucnaInteligenca(Okno master) {
		this.master = master;
	}
	
	@Override
	protected Poteza doInBackground() throws Exception {
		Igra igra = master.copyIgra();
		for (int i = 0; i < 5; i++) {
			System.out.println("mislim...");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) { }
			if (this.isCancelled()) {
				System.out.println("Prekinili so me!");
				return null;
			}
		}
		System.out.println("Igram");
		Random r = new Random();
		Poteza p = new Poteza(r.nextInt(igra.sirinaPlosce),r.nextInt(igra.visinaPlosce),
				r.nextInt(igra.sirinaPlosce),r.nextInt(igra.visinaPlosce));
		return p;
	}
	
	@Override
	public void done() {
		try {
			Poteza p = this.get();
			if (p != null) { master.odigraj(p); }
		} catch (Exception e) {
		}
	}
}
