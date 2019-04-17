package logika;

public enum Igralec {
	CRNO, BELO;

	public Igralec nasprotnik() {
		return (this == CRNO ? BELO : CRNO);
	}

	public Polje getPolje() {
		return (this == CRNO ? Polje.CRNO : Polje.BELO);
	}
}