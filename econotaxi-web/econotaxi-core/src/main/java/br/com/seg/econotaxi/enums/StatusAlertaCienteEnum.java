package br.com.seg.econotaxi.enums;

import java.util.Arrays;
import java.util.Optional;

/**
 * Classe de Enum
 *
 * Criado em 24 de jun de 2017
 * @author Bruno rocha
 */
public enum StatusAlertaCienteEnum {

	/** cnab. */
	PROBLEMA_NAO_CIENTE (1),
	
	/** slc. */
	PROBLEMA_CIENTE (2);
	
	/** tipo arquivo. */
	private Integer problema;
	
	/**
	 * Instancia um novo status alerta notificacao enum.
	 *
	 * @param problema o status do problema
	 */
	private StatusAlertaCienteEnum(Integer problema) {
		this.problema = problema;
	}
	
	/**
	 * Valor do status do problema.
	 *
	 * @param problema o status do problema
	 * @return tipo arquivo enum
	 */
	public static StatusAlertaCienteEnum valueOfProblema(Integer problema) {
		Optional<StatusAlertaCienteEnum> optional = Arrays.asList(values()).stream()
				.filter(StatusAlertaCienteEnum -> StatusAlertaCienteEnum.getProblema().equals(problema)).findFirst();
		
		return (optional.isPresent()) ? optional.get() : null;
	}
	
	// Métodos get/set
	public Integer getProblema() {
		return problema;
	}
	
}