package inteligenca;

import java.awt.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import logika.Igra;
import logika.Igralec;
import logika.Polje;
import logika.Poteza;

/**
 * Ocena trenutne pozicije
 */
public class Ocena {
	public static final int ZMAGA = (1 << 20);
	public static final int PORAZ = -ZMAGA;
	
	/**
	 * @param jaz igralec, ki želi oceno
	 * @param igra trentno stanje igre (ne spreminjaj tega objekta!)
	 * @return ocena vrednosti pozicije (èe je igre konec, je ocena zagotovo pravilna)
	 */
	public static int oceniPozicijo(Igralec jaz, Igra igra) {
		switch (igra.stanje()) {
		case ZMAGA_PRVI:
			return (jaz == Igralec.prvi ? ZMAGA : PORAZ);
		case ZMAGA_DRUGI:
			return (jaz == Igralec.drugi ? ZMAGA : PORAZ);
		case NA_POTEZI_PRVI:
		case NA_POTEZI_DRUGI:
			int vrednostPrvi = 0;
			int vrednostDrugi = 0;
			
			
			return (jaz == Igralec.drugi ? (vrednostDrugi - vrednostPrvi/2) : (vrednostPrvi - vrednostDrugi/2));
		}
		assert false;
		return 53;
	}
	
	// funkcija, ki vrne max in min st potez kosa (mnozice moznih potez na kosu)
	private static int[] stMoznihPotez(Set<int[]> kos) {
		int[] minMaxVrednost = new int[2];
		// izracun minimalnega in maximalnega st ploscic na kosu
		// dualen problem je iskanje max in min stevila izoliranih polj
		return minMaxVrednost;
	}
	
	/** v mnozico kos, dodaj vse prazne sosede ploscice p (ce se da priti do nekega polja po praznih poljih je tudi to polje prazno)
	 * iz preostalih potez izbrise poteze, ki pokrivajo te sosede
	 * @param kos
	 * @param p
	 * @param igra
	 * @param preostalepoteze
	 */
	private static Set<Poteza> dodajPrazneSosede(Set<int[]> kos, int[] p, Igra igra, Set<Poteza> preostalePoteze) {
		boolean dodani = false;
		kos.add(p);
		int[][] smer = {{1,0}, {0,1}, {-1,0}, {0,-1}};
		for (int[] s : smer) {
			if (igra.veljavnaPoteza(p[0], p[1], p[0]+s[0], p[1]+s[1])) {
				if (!kos.contains(new int[] {p[0]+s[0], p[1]+s[1]})) {
					dodani = true;
					kos.add(new int[] {p[0]+s[0], p[1]+s[1]});
					for (int[] sm : smer) {
						preostalePoteze.remove(new Poteza(p[0]+s[0], p[1]+s[1], p[0]+s[0]+sm[0], p[1]+s[1]+sm[1]));
					}
					dodajPrazneSosede(kos, new int[] {p[0]+s[0], p[1]+s[1]}, igra, preostalePoteze);
				}
			}
		}
		return preostalePoteze;
	}
	
	
	/** 
	 * @param igra
	 * @return vrne seznam kosov (mnozice praznih polj, ki se drzijo skupaj)
	 */
	private static Set<Set<int[]>> kosi(Igra igra) {
		Set<Poteza> preostalePoteze = igra.preostalePoteze();
		Set<Set<int[]>> kosi = new TreeSet<Set<int[]>>();
		// sestavljaj nove kose, dokler imamo se preostale poteze
		while (preostalePoteze.size() > 0) {
			Set<int[]> kos = new TreeSet<int[]>();
			Poteza p = preostalePoteze.iterator().next();
			preostalePoteze = dodajPrazneSosede(kos, new int[] {p.getY1(), p.getX1()}, igra, preostalePoteze);
			kosi.add(kos);
		}
		return kosi;
	}
	
	
	// Set<Set<int[]>> kosi = kosi(igra);
	// for (Set<int[]> kos : kosi) { // dodaj stMoznihPotez(kos) v seznam ekstrtemov }
	// funkcija, ki iz max in min vseh kosov vrne vrednost za prvega in vrednost za drugega igralca
	// ce je polij, ki jih pokrivajo preostale poteze (ignoriramo izolirana polja), 4n ali 4n+1 za nek n,
	// sem v slabsi poziciji, kot sicer
	// dobro bi bilo upostevati moznost izolacije polj, kar spremeni stevilo vseh potez do konca igre
	// funkcija odvisna od skupnega max, min in razlike med max in min, sodosti max in min
	// ce je razlika med max in min  v celotni igri 1 je to veliko vredno ce si na vrsti, manj ce nisi
}