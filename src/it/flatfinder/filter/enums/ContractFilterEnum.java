package it.flatfinder.filter.enums;

public enum ContractFilterEnum {
	
	INDIFFERENTE(0, "indifferente"),
	AFFITTO(1, "affitto"),
	VENDITA(2, "vendita");
	
	private int value;
	private String descrizione;

	ContractFilterEnum(int value, String descrizione) {
		
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
