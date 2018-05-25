package uporabniskiVmesnik;

import logika.Igralec;
import logika.Polje;
import logika.Poteza;

public class Clovek extends Strateg {
	private Okno master;
	private Igralec jaz;
	private int globinaRazmisljanja;
	
	public Clovek(Okno master, Igralec jaz, int globina) {
		this.master = master;
		this.jaz = jaz;
		globinaRazmisljanja = globina;
	}
	
	@Override
	public void na_potezi() {
	}

	@Override
	public void prekini() {
	}
	
	public static int round(double x) {
		return (int)(x+0.5);
	}
	
	@Override
	public void klik(int i, int j) {
		if (master.platno.izbraniI == -1 && master.platno.izbraniJ == -1) {
			// ce ni oznaceno se nobeno polje, oznaci izbrano polje, ce je to se prazno
			if (master.igra.polje[i][j] == Polje.prazno) {
				master.platno.izbraniJ = round(j);
				master.platno.izbraniI = round(i);
			}
		} else if (i==master.platno.izbraniI && j==master.platno.izbraniJ) {
			// odstrani oznako, ce ponovno pritisnjeno isto polje
			master.platno.izbraniI = -1;
			master.platno.izbraniJ = -1;
		} else if (master.igra.veljavnaPoteza(i, j, master.platno.izbraniI, master.platno.izbraniJ)) {
			if (master.odigraj(new Poteza(i, j, master.platno.izbraniI, master.platno.izbraniJ))) {
				// naredi potezo, ce je veljavna
				master.platno.izbraniI = -1;
				master.platno.izbraniJ = -1;
			}
		}
		
	}
	
}
