package it.flatfinder.mapper.universal;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import it.flatfinder.building.Building;
import it.flatfinder.mapper.CasaMapper;
import it.flatfinder.mapper.ImmobiliareMapper;
import it.flatfinder.mapper.Mapper;

public class UniversalMapper extends Mapper{
	
	List<Building> buildings = new LinkedList<>();
	
	public UniversalMapper() throws IOException, InterruptedException {
		
		buildings.addAll(new ImmobiliareMapper().getBuildings());
		buildings.addAll(new CasaMapper().getBuildings());		
	}

	@Override
	public List<Building> getBuildings() {
		return buildings;
	}

}
