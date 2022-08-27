package it.flatfinder.mapper;

import java.io.IOException;
import java.util.List;

import it.flatfinder.building.Building;

public abstract class Mapper {
	
	public abstract List<Building> getBuildings() throws IOException, InterruptedException;

}
