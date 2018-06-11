package uporabniskiVmesnik;

import java.util.HashMap;
import java.util.Map;

/**
 * @author MarinkoA15, PristovnikJ15
 * ii - igralec vs. igralec
 * i == r -> igralec = računalnik
 * i == c -> igralec = človek
 */
public enum TipIgre {
	cc, rc, cr, rr;
	
	public int globinaPrvega = 3;
	public int globinaDrugega = 3;
	
	int globinaCesarja = 1;
	int globinaBrdausa = 3;
	int globinaKrpana = 5;
	
	// naredimo slovar nekaj globin z imeni
	protected Map<Integer, String> nasprotniki = new HashMap<Integer, String>();
	{
		nasprotniki.put(globinaCesarja, "cesar FRANC JOŽEF");
		nasprotniki.put(globinaBrdausa, "BRDAUS");
		nasprotniki.put(globinaKrpana, "MARTIN KRPAN");
	}
	
	protected String[] imena() {
		String[] imena = new String[2];
		// če izbranih globin ni v slovarju nasprotniki, bo izbral privzeta imena PRVI, DRUGI za človeka
		// računalniku pa bo dal ime iz slovarja (če ne obstaja ga bo sam zgeneriral)
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
