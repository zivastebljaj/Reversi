package logika;

public enum Igralec {
	CRNI, BELI;

	public Igralec nasprotnik() {
		return (this == CRNI ? BELI : CRNI);
	}

	public Polje getPolje() {
		return (this == CRNI ? Polje.CRNO : Polje.BELO);
	}
}