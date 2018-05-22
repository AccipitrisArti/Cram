package inteligenca;

import java.awt.List;
import java.util.HashMap;
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
	 * @param kos
	 * @return max in min st potez na kosu (mnozici indeksov polj)
	 */
	private static int[] stMoznihPotez(Set<int[]> kos) {
		int[] minMaxVrednost = new int[2];
		
		
		
		
		
		
		// izracun minimalnega in maximalnega st ploscic na kosu
		// dualen problem je iskanje max in min stevila izoliranih polj
		return minMaxVrednost;
	}
	
	/** 
	 * @param igra
	 * @return vrne seznam kosov (mnozice praznih polj, ki se drzijo skupaj)
	 */
	private static Set<Set<int[]>> kosi(Igra igra) {
		KosInPreostalePoteze kInPP = new KosInPreostalePoteze(new TreeSet<int[]>(),
				igra.preostalePoteze());
		Set<Set<int[]>> kosi = new TreeSet<Set<int[]>>();
		// sestavljaj nove kose, dokler imamo se preostale poteze, katerih polja se niso v nobenem kosu
		while (kInPP.preostalePoteze.size() > 0) {
			Poteza p = kInPP.preostalePoteze.iterator().next();
			// definiraj razred Kos, na katerem bo funkcija dodajPrazneSosede metoda
			kInPP.dodajPrazneSosede(new int[] {p.getY1(), p.getX1()});
			kosi.add(kInPP.kos);
		}
		return kosi;
	}
	
	
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
			
			
			
			
			
			
			
			
			
			// tu bo nastavil vrednost za prvega in za drugega
			// Set<Set<int[]>> kosi = kosi(igra);
			// for (Set<int[]> kos : kosi) { // dodaj stMoznihPotez(kos) v seznam ekstrtemov }
			// iz seznama ekstremov na vseh kosih (max in min) vecjih od 1 izracuna
			// in vrne vrednost za prvega in vrednost za drugega igralca
			// ce je polij, ki jih pokrivajo preostale poteze (ignoriramo izolirana polja), 4n ali 4n+1 za nek n,
			// sem v slabsi poziciji, kot sicer
			// dobro bi bilo upostevati moznost izolacije polj, kar spremeni stevilo vseh potez do konca igre
			// funkcija odvisna od skupnega max, min in razlike med max in min, sodosti max in min
			// ce je razlika med max in min  v celotni igri 1 je to veliko vredno ce si na vrsti, manj ce nisi
			
			
			
			
			// ko je narejeno do tu, sledi le se casovna optimizacija (cim manj izracunavanja
			// vseh preostalih potez pri razmisljanju racunalnika, lahko se izracuna enkrat ob konstrukciji igre in potem brise na objektu igra),
			// lepsanje kode, komentiranje
			return (jaz == Igralec.drugi ? (vrednostDrugi - vrednostPrvi/2) : (vrednostPrvi - vrednostDrugi/2));
		}
		assert false;
		return 53;
	}
}