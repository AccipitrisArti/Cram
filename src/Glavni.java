import logika.Igra;
import uporabniskiVmesnik.Okno;

public class Glavni {

	public static void main(String[] args) {
		Okno okno = new Okno(800, 800);
		okno.pack();
		okno.setVisible(true);
		Igra g = new Igra(4,4);
		okno.platno.narisi(g);
	}

}
