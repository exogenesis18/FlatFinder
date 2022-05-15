package it.flatfinder.filter.enums;

public enum AgencyFilterEnum {
	
	INDIFFERENTE(0, "indifferente"),
	AGENCY(1, "sì"),
	PRIVATE(2, "no");
	
	private int value;
	private String descrizione;

	AgencyFilterEnum(int value, String descrizione) {
		
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
