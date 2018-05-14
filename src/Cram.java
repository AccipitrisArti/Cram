import logika.Igra;
import uporabniskiVmesnik.Okno;

public class Cram {

	public static void main(String[] args) {
		Okno okno = new Okno();
		okno.pack();
		okno.setVisible(true);
		okno.igra = new Igra(8, 8);
	}

}
