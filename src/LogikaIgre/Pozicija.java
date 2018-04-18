package LogikaIgre;

public class Pozicija {
	
	protected int sirinaPlosce = 4;
	protected int visinaPlosce = 4;
	protected int[][] polja;
	protected boolean prviNaPotezi; // na vrsti prvi igralec
	
	public Pozicija() {
		/* polje je nezasedeno, ce je 0,
		 * na njem je ploscica prvega igralca, ce je 1
		 * in ploscica drugega, ce je -1
		 */
		for(int i=0; i<sirinaPlosce; i++) {
			for(int j=0; j<visinaPlosce; j++) {
				polja[i][j] = 0;
			}
		}
		prviNaPotezi = true;
	}
	
	
	/**
	 * za parametre dobi koordinate obeh polj (i1, j1) in (i2, j2)
	 * na ti dve polji postavi ploscico, ce sta polji prazni in sosedni
	 * ce je na vrsti prvi igralec, postavi na polji 1, sicer -1
	 * na vrsti je nasprotnik
	 * @param i1 
	 * @param j1
	 * @param i2
	 * @param j2
	 * @return true, ce je bila poteza izvedena, sicer false
	 */
	public boolean postaviPloscico(int i1, int j1, int i2, int j2) {
		if (polja[i1][j1] == 0 && polja[i2][j2] == 0 && (Math.abs(i1-i2) + Math.abs(j1-j2)) == 1) {
			if (prviNaPotezi) {
				polja[i1][j1] = 1;
				polja[i2][j2] = 1;
			} else {
				polja[i1][j1] = -1;
				polja[i2][j2] = -1;
			}
			prviNaPotezi = !prviNaPotezi;
			return true;
		} else {
			return false;
		}
	}
}
