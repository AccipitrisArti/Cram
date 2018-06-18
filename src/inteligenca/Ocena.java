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
	protected static int[] minMaxPotez(Igra igra) {
		int[][][] mM = new int[Igra.visinaPlosce+3][Igra.sirinaPlosce+3][2];
		for (int i=0; i<Igra.visinaPlosce+3; i++) {
			for (int j=0; j<Igra.sirinaPlosce+3; j++) {
				mM[i][j] = new int[] {0, 0};
				if (i>=3 && j>=3) {
					// izracun maximuma (dinamicno programiranje)
					// sestejemo vrednosti v levem in zgornjem sosedu, odstejemo vrednost v levo-zgornjem sosedu
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
					// ce polje ima vsaj dva prazna soseda, povecaj min za 2/3, sicer za 1
					int stPraznihSosedov = 0;
					if (i > 0 && igra.polje[i-1][j] == Polje.prazno) stPraznihSosedov ++;
					if (i < Igra.visinaPlosce-1 && igra.polje[i+1][j] == Polje.prazno) stPraznihSosedov ++;
					if (j > 0 && igra.polje[i][j-1] == Polje.prazno) stPraznihSosedov ++;
					if (j < Igra.sirinaPlosce-1 && igra.polje[i][j+1] == Polje.prazno) stPraznihSosedov ++;
					if (stPraznihSosedov == 1) mM[Igra.visinaPlosce+2][Igra.sirinaPlosce+2][0] += 3;
					else if (stPraznihSosedov > 1) mM[Igra.visinaPlosce+2][Igra.sirinaPlosce+2][0] += 2;
				}
			}
		}
		mM[Igra.visinaPlosce+2][Igra.sirinaPlosce+2][0] = mM[Igra.visinaPlosce+2][Igra.sirinaPlosce+2][0]/6;

		return mM[Igra.visinaPlosce+2][Igra.sirinaPlosce+2];
	}
	
	/**
	 * @param jaz igralec, ki zeli oceno
	 * @param igra trenutno stanje igre (ne spreminjaj tega objekta!)
	 * @return ocena vrednosti pozicije (ce je igre konec, je ocena zagotovo pravilna)
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
			
			return (jaz == Igralec.drugi ? (vrednostDrugi - vrednostPrvi/2) : (vrednostPrvi - vrednostDrugi/2));
		}
		assert false;
		return 53;
	}
}
