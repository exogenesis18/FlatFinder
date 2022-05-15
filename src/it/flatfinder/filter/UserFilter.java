package it.flatfinder.filter;

import it.flatfinder.filter.enums.AgencyFilterEnum;
import it.flatfinder.filter.enums.ContractFilterEnum;
import it.flatfinder.filter.enums.RoomsFilterEnum;

public class UserFilter {
	
	
	//0: indifferente, 1: affitto, 2: vendita
	private ContractFilterEnum contractFilter = ContractFilterEnum.INDIFFERENTE;
	
	private int minPrice = 0;
	
	private int maxPrice = 0;
	
	//0: indifferente, 1: una, 2: due, 3: tre, 4: quattro, 5: cinque, 5+: cinqueplus
	private RoomsFilterEnum minRooms = RoomsFilterEnum.INDIFFERENTE;
	//0: indifferente, 1: una, 2: due, 3: tre, 4: quattro, 5: cinque, 5+: cinqueplus	
	private RoomsFilterEnum maxRooms = RoomsFilterEnum.INDIFFERENTE;
	
	private int minSurface = 0;
	
	private int maxSurface = 0;
	
	//0: indifferente, 1: si, 2:no
	private AgencyFilterEnum agencyFilter = AgencyFilterEnum.INDIFFERENTE;
	

	public ContractFilterEnum getContractFilter() {
		return contractFilter;
	}

	public void setContractFilter(ContractFilterEnum contractFilter) {
		this.contractFilter = contractFilter;
	}

	public int getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(int minPrice) {
		this.minPrice = minPrice;
	}

	public int getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(int maxPrice) {
		this.maxPrice = maxPrice;
	}

	public RoomsFilterEnum getMinRooms() {
		return minRooms;
	}

	public void setMinRooms(RoomsFilterEnum minRooms) {
		this.minRooms = minRooms;
	}

	public RoomsFilterEnum getMaxRooms() {
		return maxRooms;
	}

	public void setMaxRooms(RoomsFilterEnum maxRooms) {
		this.maxRooms = maxRooms;
	}

	public int getMinSurface() {
		return minSurface;
	}

	public void setMinSurface(int minSurface) {
		this.minSurface = minSurface;
	}

	public int getMaxSurface() {
		return maxSurface;
	}

	public void setMaxSurface(int maxSurface) {
		this.maxSurface = maxSurface;
	}

	public AgencyFilterEnum getAgencyFilter() {
		return agencyFilter;
	}

	public void setAgencyFilter(AgencyFilterEnum agencyFilter) {
		this.agencyFilter = agencyFilter;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Contratto: " + contractFilter.getDescrizione() +
			   "\nPrezzo: " + (minPrice == 0 && maxPrice == 0 ? "indifferente" : minPrice != 0 && maxPrice == 0 ? ("da " + minPrice) : minPrice == 0 && maxPrice != 0 ? ("fino a " + maxPrice) : ("da " + minPrice + " a " + maxPrice)) +
			   "\nNumero camere: " + (minRooms.getValue() == 0 && maxRooms.getValue() == 0 ? "indifferente" : minRooms.getValue() != 0 && maxRooms.getValue() == 0 ? ("da " + minRooms.getDescrizione()) : minRooms.getValue() == 0 && maxRooms.getValue() != 0 ? ("fino a " + maxRooms.getDescrizione()) : ("da " + minRooms.getDescrizione() + " a " + maxRooms.getDescrizione())) +
			   "\nSuperficie in m^2: " + (minSurface == 0 && maxSurface == 0 ? "indifferente" : minSurface != 0 && maxSurface == 0 ? ("da " + minSurface) : minSurface == 0 && maxSurface != 0 ? ("fino a " + maxSurface) : ("da " + minSurface + " a " + maxSurface)) +
			   "\nAgenzia: " + agencyFilter.getDescrizione();
	}
	

}
