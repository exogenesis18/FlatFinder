package it.flatfinder.filter.enums;

public enum RoomsFilterEnum {
	
	INDIFFERENTE(0, "Indifferente"),
	ONE(1, "1"),
	TWO(2, "2"),
	THREE(3, "3"),
	FOUR(4, "4"),
	FIVE(5, "5"),
	FIVEPLUS(9999, "5+");
	
	private int value;
	private String descrizione;

	RoomsFilterEnum(int value, String descrizione) {
		
		this.value = value;
		this.descrizione = descrizione;
		
	}

	public int getValue() {
		return value;
	}
	
	public String getDescrizione() {
		return descrizione;
	}

}
