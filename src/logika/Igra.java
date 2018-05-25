package logika;

import java.util.HashSet;
import java.util.Set;

public class Igra {
	
	public static int visinaPlosce = 8;
	public static int sirinaPlosce = 6;
	public Polje[][] polje;
	protected Igralec naPotezi;
	
	/**
	 * nastavi igralno plosco in na potezo postavi prvega igralca
	 */
	public Igra() {
		polje = new Polje[visinaPlosce][sirinaPlosce];
		for(int i=0; i<visinaPlosce; i++) {
			for(int j=0; j<sirinaPlosce; j++) {
				polje[i][j] = Polje.prazno;
			}
		}
		naPotezi = Igralec.prvi;
	}
	
	/**
	 * @param igra, kopija dane igre
	 */
	public Igra(Igra igra) {
		polje = new Polje[visinaPlosce][sirinaPlosce];
		for(int i=0; i<visinaPlosce; i++) {
			for(int j=0; j<sirinaPlosce; j++) {
				polje[i][j] = igra.polje[i][j];
			}
		}
		naPotezi = igra.naPotezi;
	}
	
	
	/**
	 * @return mnozico preostalih potez
	 */
	public Set<Poteza> preostalePoteze() {
		Set<Poteza> preostale = new HashSet<Poteza>();
		for (int i = 0; i < visinaPlosce; i++) {
			for (int j = 0; j < sirinaPlosce-1; j++) {
				if (polje[i][j] == Polje.prazno && polje[i][j+1] == Polje.prazno) {
					preostale.add(new Poteza(i, j, i, j+1));
				}
			}
		}
		for (int i = 0; i < visinaPlosce-1; i++) {
			for (int j = 0; j < sirinaPlosce; j++) {
				if (polje[i][j] == Polje.prazno && polje[i+1][j] == Polje.prazno) {
					preostale.add(new Poteza(i, j, i+1, j));
				}
			}
		}
		return preostale;
	}
	
	/**
	 * @return true, ce obstaja še kakšna poteza
	 */
	public boolean obstajaPoteza() {
		for (int i = 0; i < visinaPlosce; i++) {
			for (int j = 0; j < sirinaPlosce-1; j++) {
				if (polje[i][j] == Polje.prazno && polje[i][j+1] == Polje.prazno) {
					return true;
				}
			}
		}
		for (int i = 0; i < visinaPlosce-1; i++) {
			for (int j = 0; j < sirinaPlosce; j++) {
				if (polje[i][j] == Polje.prazno && polje[i+1][j] == Polje.prazno) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * @return true, ce je poteza veljavna
	 */
	public boolean veljavnaPoteza(int i1, int j1, int i2, int j2) {
		if (polje[i1][j1] == Polje.prazno && polje[i2][j2] == Polje.prazno &&
				Math.abs(i1-i2)+Math.abs(j1-j2) == 1 &&
				i1<visinaPlosce && i2<visinaPlosce && j2<sirinaPlosce && j1<sirinaPlosce) {
					return true;
		}
		return false;
	}
	
	/**
	 * @return trenutno stanje igre
	 */
	public Stanje stanje() {
		// Ali imamo zmagovalca?
		Stanje s;
		if (obstajaPoteza()) {
			// igre še ni konec
			if (naPotezi == Igralec.prvi) {
				s = Stanje.NA_POTEZI_PRVI;
			} else {
				s = Stanje.NA_POTEZI_DRUGI;
			}
			return s;
		} else {
			if (naPotezi == Igralec.prvi) {
				s = Stanje.ZMAGA_DRUGI;
			} else {
				s = Stanje.ZMAGA_PRVI;
			}
			return s;
		}
	}

	
	/**
	 * Za parameter dobi potezo.
	 * Ce sta polji prazni in sosedni, izvede potezo.
	 * Na vrsti je nasprotnik.
	 * @param p Poteza
	 * @return true, ce je bila poteza izvedena, sicer false
	 */
	public boolean postaviPloscico(Poteza p) {
		if (veljavnaPoteza(p.getY1(), p.getX1(), p.getY2(), p.getX2())) {
			polje[p.getY1()][p.getX1()] = naPotezi.getPolje();
			polje[p.getY2()][p.getX2()] = naPotezi.getPolje();
			naPotezi = naPotezi.nasprotnik();
			return true;
		} else {
			System.out.println("Poteza ni veljavna!");
			return false;
		}
	}
}
