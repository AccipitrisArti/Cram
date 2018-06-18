package inteligenca;

import javax.swing.SwingWorker;

import logika.Igra;
import logika.Igralec;
import logika.Poteza;
import uporabniskiVmesnik.Okno;

public class AlphaBeta  extends SwingWorker<Poteza, Object> {

	private Okno master;
	private int alpha = Ocena.PORAZ-1;
	private int beta = Ocena.ZMAGA+1;

	/**
	 * Globina, do katere pregleduje minimax
	 */
	private int globina;

	/**
	 * Ali racualnik igra PRVI ali DRUGI?
	 */
	private Igralec jaz; // koga igramo
	
	/**
	 * @param master glavno okno, v katerem vlecemo poteze
	 * @param globina koliko potez naprej gledamo
	 * @param jaz koga igramo
	 */
	public AlphaBeta(Okno master, int globina, Igralec jaz) {
		this.master = master;
		this.globina = globina;
		this.jaz = jaz;
	}
	
	@Override
	protected Poteza doInBackground() throws Exception {
		Igra igra = master.copyIgra();
		OcenjenaPoteza p = alphaBeta(0, alpha, beta, igra);
		assert (p.poteza != null);
		return p.poteza;
	}
	
	@Override
	public void done() {
		try {
			Poteza p = this.get();
			if (p != null) { master.odigraj(p); }
		} catch (Exception e) {
		}
	}

	/**
	 * Z metodo alphabeta-rezanje poisci najboljso potezo v dani igri.
	 * 
	 * @param k stevec globine, do kje smo ze preiskali
	 * @param igra
	 * @return najboljsa poteza (ali null, ce ji ni), skupaj z oceno najboljse poteze
	 */
	private OcenjenaPoteza alphaBeta(int k, int alpha, int beta, Igra igra) {
		Igralec naPotezi = null;
		// Ugotovimo, ali je konec, ali je kdo na potezi?
		switch (igra.stanje()) {
		case NA_POTEZI_PRVI: naPotezi = Igralec.prvi; break;
		case NA_POTEZI_DRUGI: naPotezi = Igralec.drugi; break;
		// Igre je konec, ne moremo vrniti poteze, vrnemo le vrednost pozicije
		case ZMAGA_DRUGI:
			return new OcenjenaPoteza(
					null,
					(jaz == Igralec.drugi ? Ocena.ZMAGA : Ocena.PORAZ));
		case ZMAGA_PRVI:
			return new OcenjenaPoteza(
					null,
					(jaz == Igralec.prvi ? Ocena.ZMAGA : Ocena.PORAZ));
		}
		assert (naPotezi != null);
		// Nekdo je na potezi, ugotovimo, kaj se splaca igrati
		if (k >= globina) {
			// dosegli smo najvecjo dovoljeno globino, zato
			// ne vrnemo poteze, ampak samo oceno pozicije
			return new OcenjenaPoteza(
					null,
					Ocena.oceniPozicijo(jaz, igra));
		}
		// Hranimo najboljšo do sedaj videno potezo in njeno oceno.
		Poteza najboljsa = null;
		
		if (naPotezi == jaz) {
			int v = Ocena.PORAZ;
			for (Poteza p : igra.preostalePoteze()) {
				// V kopiji igre odigramo potezo p (mnozica poskrbi za nakljucnost izbire p)
				Igra kopijaIgre = new Igra(igra);
				kopijaIgre.postaviPloscico(p);
				// Izracunamo vrednost pozicije po odigrani potezi p
				int ocenaP = alphaBeta(k+1, alpha, beta, kopijaIgre).vrednost;
				if (ocenaP >= v) {
					najboljsa = p;
					v = ocenaP;
				}
				alpha = Math.max(v, alpha);
				if (beta <= alpha) {
					break;
				}
			}
			return new OcenjenaPoteza(najboljsa, v);
		} else {
			int v = Ocena.ZMAGA;
			for (Poteza p : igra.preostalePoteze()) {
				// V kopiji igre odigramo potezo p
				Igra kopijaIgre = new Igra(igra);
				kopijaIgre.postaviPloscico(p);
				// Izracunamo vrednost pozicije po odigrani potezi p
				int ocenaP = alphaBeta(k+1, alpha, beta, kopijaIgre).vrednost;
				if (ocenaP <= v) {
					najboljsa = p;
					v = ocenaP;
				}
				beta = Math.min(v, beta);
				if (beta <= alpha) {
					break;
				}
			}
			return new OcenjenaPoteza(najboljsa, v);
		}
	}
	
}
