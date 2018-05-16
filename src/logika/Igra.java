package logika;

import java.util.HashSet;
import java.util.Set;

public class Igra {
	
	public static int visinaPlosce = 50;
	public static int sirinaPlosce = 80;
	public Polje[][] polje;
	protected Igralec naPotezi;
	
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
	 * @return true, èe obstaja še kakšna poteza
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
	 * @return trenutno stanje igre
	 */
	public Stanje stanje() {
		// Ali imamo zmagovalca?
		Stanje s;
		if (preostalePoteze().size() != 0) {
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
	 * za parametre dobi koordinate obeh polj (i1, j1) in (i2, j2)
	 * na ti dve polji postavi ploscico, ce sta polji prazni in sosedni
	 * na vrsti je nasprotnik
	 * @param p Poteza
	 * @return true, ce je bila poteza izvedena, sicer false
	 */
	public boolean postaviPloscico(Poteza p) {
		if (polje[p.getY1()][p.getX1()] == Polje.prazno &&
				polje[p.getY2()][p.getX2()] == Polje.prazno &&
				(Math.abs(p.getY1()-p.getY2()) + Math.abs(p.getX1()-p.getX2())) == 1) {
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
