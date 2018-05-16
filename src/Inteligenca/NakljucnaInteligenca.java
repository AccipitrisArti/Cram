package Inteligenca;

import java.awt.List;
import java.util.Random;

import javax.swing.SwingWorker;

import logika.Igra;
import logika.Polje;
import logika.Poteza;
import uporabniskiVmesnik.Okno;

public class NakljucnaInteligenca extends SwingWorker<Poteza, Object> {
	private Okno master;
	int izbI;
	int izbJ;
	int smer;
	
	public NakljucnaInteligenca(Okno master) {
		this.master = master;
	}
	
	@Override
	protected Poteza doInBackground() throws Exception {
		Igra igra = master.copyIgra();
		for (int i = 0; i < 5; i++) {
			System.out.println("mislim...");
			try {
				Thread.sleep(master.hitrostRacunalnika);
			} catch (InterruptedException e) { }
			if (this.isCancelled()) {
				System.out.println("Prekinili so me!");
				return null;
			}
		}
		System.out.println("Igram");
		Poteza p = igra.preostalePoteze().iterator().next();
		System.out.println(p);
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
