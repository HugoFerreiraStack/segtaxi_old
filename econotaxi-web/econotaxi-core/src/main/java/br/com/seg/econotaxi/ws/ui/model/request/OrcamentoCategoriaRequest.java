package br.com.seg.econotaxi.ws.ui.model.request;

import java.util.ArrayList;
import java.util.List;

import br.com.seg.econotaxi.dto.CoordinatesDTO;

public class OrcamentoCategoriaRequest {
	private OrcamentoCategoriaCoordinatesRequest origin;
	private CoordinatesDTO destination;
	private List<CoordinatesDTO> intermediateCoordinates = new ArrayList<CoordinatesDTO>();

	public OrcamentoCategoriaCoordinatesRequest getOrigin() {
		return origin;
	}
	public void setOrigin(OrcamentoCategoriaCoordinatesRequest origin) {
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
