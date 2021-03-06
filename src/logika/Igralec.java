package logika;

public enum Igralec {
	prvi, drugi;
	
	/**
	 * @return vrne nasprotnika
	 */
	public Igralec nasprotnik() {
		return (this == prvi ? drugi : prvi);
	}
	
	// pretvori iz igralca v polje z vrednostjo igralca
	public Polje getPolje() {
		return (this == prvi ? Polje.prvi : Polje.drugi);
	}
}
