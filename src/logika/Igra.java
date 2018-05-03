package logika;

import java.awt.List;
import java.util.LinkedList;

public class Igra {
	
	public int visinaPlosce;
	public int sirinaPlosce;
	public Polje[][] polje;
	protected Igralec naPotezi;
	
	public Igra(int visina, int sirina) {
		visinaPlosce = visina;
		sirinaPlosce = sirina;
		polje = new Polje[visinaPlosce][sirinaPlosce];
		for(int i=0; i<visinaPlosce; i++) {
			for(int j=0; j<sirinaPlosce; j++) {
				polje[i][j] = Polje.prazno;
			}
		}
		naPotezi = Igralec.prvi;
	}
	
	
	/**
	 * @return true, èe obstaja še kakšna poteza
	 */
	public boolean obstojPoteze() {
		// LinkedList<Poteza> preostale = new LinkedList<Poteza>();
		for (int i = 0; i < visinaPlosce; i++) {
			for (int j = 0; j < sirinaPlosce-1; j++) {
				if (polje[i][j] == Polje.prazno && polje[i][j+1] == Polje.prazno) {
					// preostale.add(new Poteza(i, j, i, j+1));
					return true;
				}
			}
		}
		for (int i = 0; i < visinaPlosce-1; i++) {
			for (int j = 0; j < sirinaPlosce; j++) {
				if (polje[i][j] == Polje.prazno && polje[i+1][j] == Polje.prazno) {
					// preostale.add(new Poteza(i, j, i+1, j));
					return true;
				}
			}
		}
		return false;
	}
	
	
	/**
	 * @return trenutno stanje igre
	 */
	public Stanje stanje() {
		// Ali imamo zmagovalca?
		Stanje s;
		if (obstojPoteze()) {
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
	 * @param i1 
	 * @param j1
	 * @param i2
	 * @param j2
	 * @return true, ce je bila poteza izvedena, sicer false
	 */
	public boolean postaviPloscico(int i1, int j1, int i2, int j2) {
		if (polje[i1][j1] == Polje.prazno && polje[i2][j2] == Polje.prazno && (Math.abs(i1-i2) + Math.abs(j1-j2)) == 1) {
			polje[i1][j1] = naPotezi.getPolje();
			polje[i2][j2] = naPotezi.getPolje();
			naPotezi = naPotezi.nasprotnik();
			return true;
		} else {
			System.out.println("Poteza ni veljavna!");
			return false;
		}
	}
}
