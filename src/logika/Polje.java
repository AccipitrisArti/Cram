package logika;

public enum Polje {
	prvi, drugi, prazno;
	
	public String toString() {
		switch (this) {
			case prazno: return " ";
			case prvi: return "X";
			case drugi: return "O";
			default: return "?";
			}
	}
}
