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
	 * Ali ra�ualnik igra PRVI ali DRUGI?
	 */
	private Igralec jaz; // koga igramo
	
	/**
	 * @param master glavno okno, v katerem vle�emo poteze
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
	 * Z metodo alphabeta-rezanje poi��i najbolj�o potezo v dani igri.
	 * 
	 * @param k �tevec globine, do kje smo �e preiskali
	 * @param igra
	 * @return najbolj�a poteza (ali null, �e ji ni), skupaj z oceno najbolj�e poteze
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
		// Nekdo je na potezi, ugotovimo, kaj se spla�a igrati
		if (k >= globina) {
			// dosegli smo najve�jo dovoljeno globino, zato
			// ne vrnemo poteze, ampak samo oceno pozicije
			return new OcenjenaPoteza(
					null,
					Ocena.oceniPozicijo(jaz, igra));
		}
		// Hranimo najbolj�o do sedaj videno potezo in njeno oceno.
		// Tu bi bilo bolje imeti seznam do sedaj videnih najbolj�ih potez, ker je lahko
		// v neki poziciji ve� enakovrednih najbolj�ih potez. Te bi lahko zbrali
		// v seznam, potem pa vrnili naklju�no izbrano izmed najbolj�ih potez, kar bi
		// popestrilo igro ra�unalnika.
		Poteza najboljsa = null;
		
		// na zacetku igre igra nekaj potez nakljucno
		if ((naPotezi == Igralec.prvi && master.tip.globinaPrvega > 1) ||
				(naPotezi == Igralec.drugi && master.tip.globinaDrugega > 1))
			if (igra.preostalePoteze().size() > Igra.sirinaPlosce*Igra.visinaPlosce) {
			master.status.setText("Naključen met računalnika");
			return new OcenjenaPoteza(igra.preostalePoteze().iterator().next(), 0);
		}
		
		// tu bi cas racunanja lahko skrajsal s tem, da bi preostale poteze izracunal le
		// na zacetku, potem pa bi jih ven brisal iz seznama
		if (naPotezi == jaz) {
			int v = Ocena.PORAZ;
			for (Poteza p : igra.preostalePoteze()) {
				// V kopiji igre odigramo potezo p
				Igra kopijaIgre = new Igra(igra);
				kopijaIgre.postaviPloscico(p);
				// Izra�unamo vrednost pozicije po odigrani potezi p
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
				// Izra�unamo vrednost pozicije po odigrani potezi p
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
