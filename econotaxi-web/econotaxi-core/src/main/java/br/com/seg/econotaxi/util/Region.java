/**
 * 
 */
package br.com.seg.econotaxi.util;

import java.util.List;

/**
 * @author bruno
 *
 */
public class Region {

	List<Coordinate> boundary;

	/* Métodos Get/Set */
	public List<Coordinate> getBoundary() {
		return boundary;
	}
	public void setBoundary(List<Coordinate> boundary) {
		this.boundary = boundary;
	}
	
}