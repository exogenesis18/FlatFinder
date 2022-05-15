package it.flatfinder.filter.applier;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.flatfinder.building.Building;
import it.flatfinder.filter.UserFilter;
import it.flatfinder.filter.enums.AgencyFilterEnum;
import it.flatfinder.filter.enums.ContractFilterEnum;
import it.flatfinder.filter.enums.RoomsFilterEnum;

public class UserFilterApplier {
	
	private List<Building> buildings;
	private UserFilter userFilter;

	
	public UserFilterApplier(List<Building> buildings, UserFilter userFilter) {
		// TODO Auto-generated constructor stub
		this.setBuildings(buildings);
		this.setUserFilter(userFilter);
	}
	
	public List<Building> applyFilter(){
		
		List<Building> filteredBuildings = new LinkedList<>();
		
		for(Building elem: buildings) {
			if(equalContract(elem.getContract(), userFilter.getContractFilter()) &&
				equalAgency(elem.getAgency(), userFilter.getAgencyFilter()) &&
				priceInRange(elem.getPrice(), userFilter.getMinPrice(), userFilter.getMaxPrice()) &&
				surfaceInRange(elem.getSurface(), userFilter.getMinSurface(), userFilter.getMaxSurface()) &&
				roomsInRange(elem.getRooms(),elem.isMoreThanFiveRooms(), userFilter.getMinRooms(), userFilter.getMaxRooms()))
				filteredBuildings.add(elem);
			
		}
		
		return filteredBuildings;
	}
	
	private boolean equalContract(String contract, ContractFilterEnum contractFilterEnum) {
		
		if(contractFilterEnum.getValue() == 0)
			return true;
		
		return contractFilterEnum.getDescrizione().equals(contract);
	}
	
	private boolean equalAgency(String agency, AgencyFilterEnum agencyFilterEnum) {
		
		if(agencyFilterEnum.getValue() == 0)
			return true;
		
		return (agencyFilterEnum.getValue() == 1 && !agency.equals("privato")) || (agencyFilterEnum.getValue() == 2 && agency.equals("privato"));
	}
	
	private boolean priceInRange(int price, int minPrice, int maxPrice) {
		
		if(minPrice == 0 && maxPrice == 0)
			return true;
		
		else if(minPrice == 0 && maxPrice != 0) 
			if(price > maxPrice)
				return false;
			else
				return true;
		
		else if(minPrice != 0 && maxPrice == 0)
			if(price < minPrice)
				return false;
			else
				return true;
		else
			if(price < minPrice || price > maxPrice)
				return false;
			else
				return true;
		
	}
	
	private boolean surfaceInRange(int surface, int minSurface, int maxSurface) {
		
		if(minSurface == 0 && maxSurface == 0)
			return true;
		
		else if(minSurface == 0 && maxSurface != 0) 
			if(surface > maxSurface)
				return false;
			else
				return true;
		
		else if(minSurface != 0 && maxSurface == 0)
			if(surface < minSurface)
				return false;
			else
				return true;
		else
			if(surface < minSurface || surface > maxSurface)
				return false;
			else
				return true;
		
	}
	
	private boolean roomsInRange(int rooms, boolean moreThanFiveRooms, RoomsFilterEnum minRooms, RoomsFilterEnum maxRooms) {
		
		if(minRooms.getValue() == 0 && maxRooms.getValue() == 0)
			return true;
		
		else if(minRooms.getValue() == 0 && maxRooms.getValue() != 0) 
			if(rooms > maxRooms.getValue())
				return false;
			else
				return true;
		
		else if(minRooms.getValue() != 0 && maxRooms.getValue() == 0)
			if(rooms < minRooms.getValue())
				return false;
			else
				return true;
		else
			if(rooms < minRooms.getValue() || rooms > maxRooms.getValue())
				return false;
			else
				return true;
	}	


	public UserFilter getUserFilter() {
		return userFilter;
	}


	public void setUserFilter(UserFilter userFilter) {
		this.userFilter = userFilter;
	}


	public List<Building> getBuildings() {
		return buildings;
	}


	public void setBuildings(List<Building> buildings) {
		this.buildings = buildings;
	}

}
