import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;

import logika.Igra;
import uporabniskiVmesnik.Okno;

/**
 * @author Pristovnik Jan, Marinko Anze
 *
 */
public class Cram {
	
	/** glavna funkcija za zagon programa
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		Okno okno = new Okno();
	    
		okno.pack();
		okno.setVisible(true);
	}

}
