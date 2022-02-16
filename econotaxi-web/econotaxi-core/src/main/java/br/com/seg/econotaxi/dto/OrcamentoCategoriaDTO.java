package br.com.seg.econotaxi.dto;

import java.util.List;
import java.util.ArrayList;


public class OrcamentoCategoriaDTO {
	private CoordinatesDTO origin;
	private CoordinatesDTO destination;
	private List<CoordinatesDTO> intermediateCoordinates = new ArrayList<CoordinatesDTO>();
	
	public CoordinatesDTO getOrigin() {
		return origin;
	}
	public void setOrigin(CoordinatesDTO origin) {
		this.origin = origin;
	}
	public CoordinatesDTO getDestination() {
		return destination;
	}
	public void setDestination(CoordinatesDTO destination) {
		this.destination = destination;
	}
	public List<CoordinatesDTO> getIntermediateCoordinates() {
		return intermediateCoordinates;
	}
	public void setIntermediateCoordinates(List<CoordinatesDTO> intermediateCoordinates) {
		this.intermediateCoordinates = intermediateCoordinates;
	}
	
}
