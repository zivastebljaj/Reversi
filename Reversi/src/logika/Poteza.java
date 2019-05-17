package logika;

public class Poteza {
	private int x;
	private int y;
	
	public Poteza(int x, int y) {
		this.x = x;
		this.y = y;
	}
//public za test
	public Poteza() {
		// TODO Auto-generated constructor stub
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public String toString() {
		return "Poteza [x=" + x + ", y=" + y + "]";
	}
}
