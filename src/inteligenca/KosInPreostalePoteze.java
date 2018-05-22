package inteligenca;

import java.util.Set;

import logika.Igra;
import logika.Poteza;

public class KosInPreostalePoteze {
	Set<int[]> kos;
	Set<Poteza> preostalePoteze;
	
	public KosInPreostalePoteze(Set<int[]> kos, Set<Poteza> preostalePoteze) {
		this.kos = kos;
		this.preostalePoteze = preostalePoteze;
	}
	
	/** v mnozico kos, dodaj vse prazne sosede ploscice p (ce se da priti do nekega polja po praznih poljih je tudi to polje prazno)
	 * iz preostalih potez izbrise poteze, ki pokrivajo te sosede
	 * @param kos
	 * @param p
	 * @param igra
	 * @param preostalePoteze
	 */
	protected void dodajPrazneSosede(int[] p) {
		kos.add(p);
		for (int i=1; i<=2; i++) {
			for (Poteza po : preostalePoteze) {
				if (kos.contains(new int[] {po.getY1(), po.getX1()}) &&
						!kos.contains(new int[] {po.getY2(), po.getX2()})) {
					kos.add(new int[] {po.getY2(), po.getX2()});
					preostalePoteze.remove(po);
					dodajPrazneSosede(new int[] {po.getY2(), po.getX2()});
				} else if (!kos.contains(new int[] {po.getY1(), po.getX1()}) &&
						kos.contains(new int[] {po.getY2(), po.getX2()})) {
					kos.add(new int[] {po.getY1(), po.getX1()});
					preostalePoteze.remove(po);
					dodajPrazneSosede(new int[] {po.getY1(), po.getX1()});
				}
			}
		}
	}
}
