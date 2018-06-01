package inteligenca;

import logika.Igra;
import logika.Igralec;
import logika.Polje;
import uporabniskiVmesnik.Platno;

/**
 * Ocena trenutne pozicije
 */
public class Ocena {
	public static final int ZMAGA = (1 << 20);
	public static final int PORAZ = -ZMAGA;
	
	/**
	 * @param igra
	 * @return max in min st potez do konca igre
	 */
	private static int[] minMaxPotez(Igra igra) {
		int[][][] mM = new int[Igra.visinaPlosce+3][Igra.sirinaPlosce+3][2];
		for (int i=0; i<Igra.visinaPlosce+3; i++) {
			for (int j=0; j<Igra.sirinaPlosce+3; j++) {
				mM[i][j] = new int[] {0, 0};
				if (i>=3 && j>=3) {
					// izracun maximuma
					// sestejemo vrednosti v levem in zgornjem sosedu, odstejemo vrednost v levem-zgornem sosedu
					// ce polje prazno in prazen zgornji sosed pristejemo 1,
					// ce imajo vrednosti v levem 2x2 polju enake vsote po diagonalah
					// simetricno za zgornje 2x2 polje, ko sta polje in levi sosed prazna
					mM[i][j][1] = mM[i-1][j][1] +
							mM[i][j-1][1] - mM[i-1][j-1][1];
					if (i==3 && j>3) {
						if (mM[i][j-1][1] - mM[i][j-2][1] == mM[i-1][j-1][1] - mM[i-1][j-2][1] &&
								igra.polje[i-3][j-3] == Polje.prazno && igra.polje[i-3][j-4] == Polje.prazno) {
							mM[i][j][1] = mM[i][j][1]+1;
						}
					} else if (i>3 && j==3) {
						if (mM[i-1][j][1] - mM[i-2][j][1] == mM[i-1][j-1][1] - mM[i-2][j-1][1] &&
								igra.polje[i-3][j-3] == Polje.prazno && igra.polje[i-4][j-3] == Polje.prazno) {
							mM[i][j][1] = mM[i][j][1]+1;
						}
					} else if (j>3 && i>3) {
						if ((mM[i][j-1][1] - mM[i][j-2][1] == mM[i-1][j-1][1] - mM[i-1][j-2][1] &&
								igra.polje[i-3][j-3] == Polje.prazno && igra.polje[i-3][j-4] == Polje.prazno)||
								(mM[i-1][j][1] - mM[i-2][j][1] == mM[i-1][j-1][1] - mM[i-2][j-1][1] &&
								igra.polje[i-3][j-3] == Polje.prazno && igra.polje[i-4][j-3] == Polje.prazno)) {
							mM[i][j][1] = mM[i][j][1]+1;
						}
					}
				}
			}
		}
		
		// samo en zelo slab priblizek za minimum
		for (int i=0; i<Igra.visinaPlosce; i++) {
			for (int j=0; j<Igra.sirinaPlosce; j++) {
				if (igra.polje[i][j] == Polje.prazno) {
					mM[Igra.visinaPlosce+2][Igra.sirinaPlosce+2][0]++;
				}
			}
		}
		mM[Igra.visinaPlosce+2][Igra.sirinaPlosce+2][0] = mM[Igra.visinaPlosce+2][Igra.sirinaPlosce+2][0]/3;
		
		
		
		
		
		
		// popravi se formulo za minimum
		return mM[Igra.visinaPlosce+2][Igra.sirinaPlosce+2];
	}
	
	/**
	 * @param jaz igralec, ki �eli oceno
	 * @param igra trentno stanje igre (ne spreminjaj tega objekta!)
	 * @return ocena vrednosti pozicije (�e je igre konec, je ocena zagotovo pravilna)
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
			int[] mM = minMaxPotez(igra);
			
			// ocena odvisna od razlike med max in min
			if (mM[1]-mM[0]==0) {
				// ce je razlika 0, je rezultat fiksno dolocen
				if (mM[1]*50/2-Platno.round(mM[1]/2)*50==0) {
					vrednostPrvi = PORAZ/2;
					vrednostDrugi = ZMAGA/2;
				} else {
					vrednostPrvi = ZMAGA/2;
					vrednostDrugi = PORAZ/2;
				}
			} else {
				vrednostPrvi = (mM[1]-mM[0])*mM[1];
				vrednostDrugi = (mM[1]-mM[0]-1)*mM[1];
			}
			
			
			
			
			
			// tu bo nastavil vrednost za prvega in za drugega
			// iz max in min bo izracunal vrednost za prvega in vrednost za drugega igralca
			// ekstremne vrednosti za pozicijo so ko je min enak max, ker je rezultat ze dolocen
			// (v tem primeru vrednost igre odvisna od tega kdo je navrsti in koliko je potez)
			// sicer pa je igra bolj vredna, ce je razlika med min in max vecja (vec moznosti za spreminjanje rezultata)
			// ce je razlika med max in min  v celotni igri 1 je to veliko vredno ce si na vrsti, manj ce nisi?
			
			
			
			
			// ko je narejeno do tu, sledi le se casovna optimizacija (cim manj izracunavanja
			// vseh preostalih potez pri razmisljanju racunalnika, lahko se izracuna enkrat ob konstrukciji igre in potem brise na objektu igra),
			// lepsanje kode, komentiranje, moznost (rac-rac/clovek-rac/...), izbiranje tezavnosi (beginer/...)
			return (jaz == Igralec.drugi ? (vrednostDrugi - vrednostPrvi/2) : (vrednostPrvi - vrednostDrugi/2));
		}
		assert false;
		return 53;
	}
}