package inteligenca;

import javax.swing.SwingWorker;

import logika.Igra;
import logika.Igralec;
import logika.Poteza;
import uporabniskiVmesnik.Okno;

public class Minimax  extends SwingWorker<Poteza, Object> {

	private Okno master;

	/**
	 * Globina, do katere pregleduje minimax
	 */
	private int globina;

	/**
	 * Ali ra�ualnik igra PRVI ali DRUGI?
	 */
	private Igralec jaz; // koga igramo
	
	/**
	 * @param master glavno okno, v katerem vle�emo poteze
	 * @param globina koliko potez naprej gledamo
	 * @param jaz koga igramo
	 */
	public Minimax(Okno master, int globina, Igralec jaz) {
		this.master = master;
		this.globina = globina;
		this.jaz = jaz;
	}
	
	@Override
	protected Poteza doInBackground() throws Exception {
		Igra igra = master.copyIgra();
		OcenjenaPoteza p = minimax(0, igra);
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
	 * Z metodo minimax poi��i najbolj�o potezo v dani igri.
	 * 
	 * @param k �tevec globine, do kje smo �e preiskali
	 * @param igra
	 * @return najbolj�a poteza (ali null, �e ji ni), skupaj z oceno najbolj�e poteze
	 */
	private OcenjenaPoteza minimax(int k, Igra igra) {
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
		// Nekdo je na potezi, ugotovimo, kaj se spla�a igrati
		if (k >= globina) {
			// dosegli smo najve�jo dovoljeno globino, zato
			// ne vrnemo poteze, ampak samo oceno pozicije
			return new OcenjenaPoteza(
					null,
					Ocena.oceniPozicijo(jaz, igra));
		}
		// Hranimo najbolj�o do sedaj videno potezo in njeno oceno.
		Poteza najboljsa = null;
		int ocenaNajboljse = 0;
		
		// mnozica preostalePoteze poskrbi za nakljucnost (nepredvidljivost)
		for (Poteza p : igra.preostalePoteze()) {
			// V kopiji igre odigramo potezo p
			Igra kopijaIgre = new Igra(igra);
			kopijaIgre.postaviPloscico(p);
			// Izra�unamo vrednost pozicije po odigrani potezi p
			int ocenaP = minimax(k+1, kopijaIgre).vrednost;
			// �e je p bolj�a poteza, si jo zabele�imo
			if (najboljsa == null // �e nimamo kandidata za najbolj�o potezo
				|| (naPotezi == jaz && ocenaP > ocenaNajboljse) // maksimiziram, ce sem na potezi
				|| (naPotezi != jaz && ocenaP < ocenaNajboljse) // minimiziramo, ce nisem
				) {
				najboljsa = p;
				ocenaNajboljse = ocenaP;
			}
		}
		// Vrnemo najbolj�o najdeno potezo in njeno oceno
		assert (najboljsa != null);
		return new OcenjenaPoteza(najboljsa, ocenaNajboljse);
	}
	
}
