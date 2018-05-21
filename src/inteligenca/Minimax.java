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
	 * Ali raèualnik igra PRVI ali DRUGI?
	 */
	private Igralec jaz; // koga igramo
	
	/**
	 * @param master glavno okno, v katerem vleèemo poteze
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
	 * Z metodo minimax poišèi najboljšo potezo v dani igri.
	 * 
	 * @param k števec globine, do kje smo že preiskali
	 * @param igra
	 * @return najboljša poteza (ali null, èe ji ni), skupaj z oceno najboljše poteze
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
		// Nekdo je na potezi, ugotovimo, kaj se splaèa igrati
		if (k >= globina) {
			// dosegli smo najveèjo dovoljeno globino, zato
			// ne vrnemo poteze, ampak samo oceno pozicije
			return new OcenjenaPoteza(
					null,
					Ocena.oceniPozicijo(jaz, igra));
		}
		// Hranimo najboljšo do sedaj videno potezo in njeno oceno.
		// Tu bi bilo bolje imeti seznam do sedaj videnih najboljših potez, ker je lahko
		// v neki poziciji veè enakovrednih najboljših potez. Te bi lahko zbrali
		// v seznam, potem pa vrnili nakljuèno izbrano izmed najboljših potez, kar bi
		// popestrilo igro raèunalnika.
		Poteza najboljsa = null;
		int ocenaNajboljse = 0;
		
		// tu bi cas racunanja lahko skrajsal s tem, da bi preostale poteze izracunal le
		// na zacetku, potem pa bi jih ven brisal iz seznama
		for (Poteza p : igra.preostalePoteze()) {
			// V kopiji igre odigramo potezo p
			Igra kopijaIgre = new Igra(igra);
			kopijaIgre.postaviPloscico(p);
			// Izraèunamo vrednost pozicije po odigrani potezi p
			int ocenaP = minimax(k+1, kopijaIgre).vrednost;
			// Èe je p boljša poteza, si jo zabeležimo
			if (najboljsa == null // še nimamo kandidata za najboljšo potezo
				|| (naPotezi == jaz && ocenaP > ocenaNajboljse) // maksimiziramo
				|| (naPotezi != jaz && ocenaP < ocenaNajboljse) // minimiziramo
				) {
				najboljsa = p;
				ocenaNajboljse = ocenaP;
			}
		}
		// Vrnemo najboljšo najdeno potezo in njeno oceno
		assert (najboljsa != null);
		return new OcenjenaPoteza(najboljsa, ocenaNajboljse);
	}
	
}
