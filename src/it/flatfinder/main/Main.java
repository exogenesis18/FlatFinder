package it.flatfinder.main;

import java.util.List;

import it.flatfinder.building.Building;
import it.flatfinder.caller.ImmobiliareCaller;


public class Main {

	public static void main(String[] args) {

		ImmobiliareCaller caller = new ImmobiliareCaller();
		List<Building> buildings = caller.getResults();
		
		for(Building building: buildings) {
			System.out.println(building);
		}


	}

}
