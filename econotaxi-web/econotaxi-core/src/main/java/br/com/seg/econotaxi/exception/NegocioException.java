package br.com.seg.econotaxi.exception;

/**
 * Classe de exceção
 *
 * Criado em 24 de jun de 2017
 * @author Bruno Rocha
 */
public class NegocioException extends RuntimeException {

	// Constantes
	private static final long serialVersionUID = -1515287815875005154L;

	public NegocioException(String msg) {
		super(msg);
	}
	
}