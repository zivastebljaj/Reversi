package logika;

public enum Polje {
	PRAZNO,
	CRNO,
	BELO;

	public String toString() {
		switch (this) {
		case PRAZNO: return " ";
		case CRNO: return "CRNO";
		case BELO: return "BELO";
		default: return "?";
		}
	}
}