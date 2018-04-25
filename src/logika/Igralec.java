package logika;

public enum Igralec {
	prvi, drugi;
	
	public Igralec nasprotnik() {
		return (this == prvi ? drugi : prvi);
	}
	
	public Polje getPolje() {
		return (this == prvi ? Polje.prvi : Polje.drugi);
	}
}
