package uporabniskiVmesnik;

public abstract class Strateg {
	/**
	 * Glavno okno klice to metodo, ko je strateg na potezi.
	 * Ce je na potezi racunalnik, ta izvede potezo,
	 * ce pa je clovek, se ne zgodi nic.
	 */
	public abstract void na_potezi();
	
	
	/**
	 * Strateg naj neha igrati potezo.
	 * Ce je na potezi racunalnik, prekine razmisljanje,
	 * ce pa je clovek, ne stori nic.
	 */
	public abstract void prekini();
	
	
	/**
	 * Ce je na potezi clovek, izbere polje in nato naredi potezo,
	 * ki jo doloci z drugim klikom, ce je poteza veljavna.
	 * Glavno okno klice to metodo, ko uporabnik klikne na polje (i,j).
	 * 
	 * @param i
	 * @param j
	 * 
	 */
	public abstract void klik(int i, int j);
	
}
