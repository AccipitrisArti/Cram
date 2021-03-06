package logika;

public class Poteza {
	
	private int x1;
	private int y1;
	private int x2;
	private int y2;
	
	/** objekt, definiran s koordinatami dveh polj na igralni plošči
	 * polji (x1, y1) in (x2, y2) sta leksikografsko urejeni
	 * @param y1
	 * @param x1
	 * @param y2
	 * @param x2
	 */
	public Poteza(int y1, int x1, int y2, int x2) {
		if (x1<x2) {
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
		} else if (x1>x2) {
			this.x1 = x2;
			this.y1 = y2;
			this.x2 = x1;
			this.y2 = y1;
			// od tu naprej pa še za primer x1==x2
		} else if (y1<y2) {
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
		} else {
			this.x1 = x2;
			this.y1 = y2;
			this.x2 = x1;
			this.y2 = y1;
		}
	}

	public int getX1() {
		return x1;
	}

	public int getY1() {
		return y1;
	}
	
	public int getX2() {
		return x2;
	}

	public int getY2() {
		return y2;
	}
}
