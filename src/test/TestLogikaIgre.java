package test;

import junit.framework.TestCase;
import logika.Igra;
import logika.Stanje;

public class TestLogikaIgre extends TestCase {
	public void testIgra() {
		Igra igra = new Igra();
		// Na zacetku je na potezi prvi
		assertEquals(Stanje.NA_POTEZI_PRVI, igra.stanje());
		// Na zacetku imamo (2*sirina*visina-sirina-visina) potez
		assertEquals(2*Igra.sirinaPlosce*Igra.visinaPlosce-Igra.sirinaPlosce-Igra.visinaPlosce,
				igra.preostalePoteze().size());
		// Naredimo eno potezo
		igra.postaviPloscico(igra.preostalePoteze().iterator().next());
		// Sedaj je na potezi drugi
		assertEquals(Stanje.NA_POTEZI_DRUGI, igra.stanje());
	}
}
