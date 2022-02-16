package br.com.seg.econotaxi.enums;

import java.util.Arrays;
import java.util.Optional;

/**
 * Classe de Enum
 *
 * Criado em 24 de jun de 2017
 * @author Bruno rocha
 */
public enum StatusAlertaNotificacaoEnum {

	/** cnab. */
	PROBLEMA_NAO_RESOLVIDO (1),
	
	/** slc. */
	PROBLEMA_RESOLVIDO (2);
	
	/** tipo arquivo. */
	private Integer problema;
	
	/**
	 * Instancia um novo status alerta notificacao enum.
	 *
	 * @param problema o status do problema
	 */
	private StatusAlertaNotificacaoEnum(Integer problema) {
		this.problema = problema;
	}
	
	/**
	 * Valor do status do problema.
	 *
	 * @param problema o status do problema
	 * @return tipo arquivo enum
	 */
	public static StatusAlertaNotificacaoEnum valueOfProblema(Integer problema) {
		Optional<StatusAlertaNotificacaoEnum> optional = Arrays.asList(values()).stream()
				.filter(StatusAlertaNotificacaoEnum -> StatusAlertaNotificacaoEnum.getProblema().equals(problema)).findFirst();
		
		return (optional.isPresent()) ? optional.get() : null;
	}
	
	// Métodos get/set
	public Integer getProblema() {
		return problema;
	}
	
}