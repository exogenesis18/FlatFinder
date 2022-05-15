package it.flatfinder.building;

public class Building {
	
	private String site;
	
	private String id;
	
	private String category;
	
	private int price;
	
	private String title;
	
	private boolean trattativaRiservata;
	
	private String url;
	
	private String contract;
	
	private int rooms;
	
	private int surface;
	
	private boolean moreThanFiveRooms;
	
	private int bathrooms;
	
	private String agency;
	
	private String location;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public boolean isTrattativaRiservata() {
		return trattativaRiservata;
	}
	
	public void setTrattativaRiservata(boolean trattativaRiservata) {
		this.trattativaRiservata = trattativaRiservata;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getContract() {
		return contract;
	}

	public void setContract(String contract) {
		this.contract = contract.equals("sale") ? "vendita" : contract.equals("rent") ? "affitto" : contract;
	}
	
	public int getRooms() {
		return rooms;
	}

	public void setRooms(int rooms) {
		this.rooms = rooms;
	}
	
	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}
	
	public int getSurface() {
		return surface;
	}

	public void setSurface(int surface) {
		this.surface = surface;
	}
	
	public boolean isMoreThanFiveRooms() {
		return moreThanFiveRooms;
	}

	public void setMoreThanFiveRooms(boolean moreThanFiveRooms) {
		this.moreThanFiveRooms = moreThanFiveRooms;
	}
	
	public int getBathrooms() {
		return bathrooms;
	}

	public void setBathrooms(int bathrooms) {
		this.bathrooms = bathrooms;
	}
	
	public String getAgency() {
		return agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}
	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Immobile in " + contract + " a" + location + 
				"\nCategoria: " + category +
				"\nTitolo: " + title +
				"\nPrezzo: " + (trattativaRiservata ? "trattativa riservata" : "€ " + price) + 
				"\nStanze: " + rooms + (moreThanFiveRooms ? "+" : "") +
				"\nBagni: " + bathrooms +
				"\nSuperficie: " + surface + " m^2" +
				"\nAgenzia: " + agency + 
				"\n" + url;
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(obj instanceof Building && ((Building) obj).getSite().equals(site) && ((Building) obj).getId().equals(id)) {
			return true;	
		}
		else {
			return false;
		}
	}

	

}
