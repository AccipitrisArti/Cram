package uporabniskiVmesnik;

import logika.Poteza;

public class Clovek extends Strateg {
	private Okno master;
	
	public Clovek(Okno master) {
		this.master = master;
	}
	
	@Override
	public void na_potezi() {
	}

	@Override
	public void prekini() {
	}

	@Override
	public void klik(int i1, int j1, int i2, int j2) {
		master.odigraj(new Poteza(i1, j1, i2, j2));
	}
}
