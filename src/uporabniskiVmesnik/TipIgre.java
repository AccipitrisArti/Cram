package uporabniskiVmesnik;

import java.util.HashMap;
import java.util.Map;

/**
 * @author MarinkoA15, PristovnikJ15
 * ii - igralec vs. igralec
 * i = r -> igralec = racunalnik
 * i = c -> igralec = clovek
 */
public enum TipIgre {
	cc, rc, cr, rr;
	
	protected int globinaPrvega = 1;
	protected int globinaDrugega = 3;
	
	protected Map<Integer, String> nasprotniki = new HashMap<Integer, String>();
	{
		nasprotniki.put(1, "cesar FRANC JOŽEF");
		nasprotniki.put(3, "BRDAUS");
		nasprotniki.put(5, "MARTIN KRPAN");
	}
	
	protected String[] imena() {
		String[] imena = new String[2];
		imena[0] = "PRVI";
		imena[1] = "DRUGI";
		if (this == TipIgre.rc || this == TipIgre.rr) {
			if (nasprotniki.containsKey(globinaPrvega)) imena[0] = nasprotniki.get(globinaPrvega);
			else imena[0] = "Računalnik"+globinaPrvega;
		}
		if (this == TipIgre.cr || this == TipIgre.rr) {
			if (nasprotniki.containsKey(globinaDrugega)) imena[1] = nasprotniki.get(globinaDrugega);
			else imena[1] = "Računalnik"+globinaDrugega;
		}
		return imena;
	}
}
