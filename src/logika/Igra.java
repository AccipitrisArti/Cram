package logika;

import java.util.HashSet;
import java.util.Set;

public class Igra {
	
	public static int visinaPlosce = 8;
	public static int sirinaPlosce = 8;
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
	 * @return true, če obstaja še kakšna poteza
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
	 * @return true, če je poteza veljavna
	 */
	public boolean veljavnaPoteza(Poteza poteza) {
		// preveri, če sta polji na plošči, če sta sosedni in prazni
		// upoštevamo, da je poteza že leksikografsko urejena
		if (poteza.getY2()<visinaPlosce && poteza.getX2()<sirinaPlosce &&
				poteza.getY1()>=0 && poteza.getX1()>=0 &&
				polje[poteza.getY1()][poteza.getX1()] == Polje.prazno && polje[poteza.getY2()][poteza.getX2()] == Polje.prazno &&
				Math.abs(poteza.getY1()-poteza.getY2())+Math.abs(poteza.getX1()-poteza.getX2()) == 1
				) {
					return true;
		}
		return false;
	}
	
	/**
	 * @return trenutno stanje igre (kdo je na potezi/zmagal)
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
		if (veljavnaPoteza(p)) {
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
