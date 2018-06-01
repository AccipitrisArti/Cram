package inteligenca;

import javax.swing.SwingWorker;

import logika.Igra;
import logika.Poteza;
import uporabniskiVmesnik.Okno;

/**
 * @author MarinkoA15, PristovnikJ15
 * razred, ki vsebuje funkcije za izracun nakljucnih potez igralca (racunalnika)
 */
public class NakljucnaInteligenca extends SwingWorker<Poteza, Object> {
	private Okno master;
		
	public NakljucnaInteligenca(Okno master) {
		this.master = master;
	}
	
	// po dolocenem casu vrne nakljucno potezo
	@Override
	protected Poteza doInBackground() throws Exception {
		Igra igra = master.copyIgra();
		if (this.isCancelled()) {
			System.out.println("Prekinili so me!");
			return null;
		}
		// iterator na preostalih potezah poskrbi za nakljucnost 
		Poteza p = igra.preostalePoteze().iterator().next();
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
