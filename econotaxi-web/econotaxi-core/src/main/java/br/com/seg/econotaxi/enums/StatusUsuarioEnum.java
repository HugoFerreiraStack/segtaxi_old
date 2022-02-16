package br.com.seg.econotaxi.enums;

import java.util.Arrays;
import java.util.Optional;

/**
 * Classe de Enum
 *
 * Criado em 24 de jun de 2017
 * @author Bruno rocha
 */
public enum StatusUsuarioEnum {

	/** cnab. */
	PENDENTE_SMS (1),
	
	/** slc. */
	PENDENTE_EMAIL (2),
	
	ATIVO (3),
	
	SOLICITOU_EXCLUSAO (4);
	
	/** tipo arquivo. */
	private Integer status;
	
	/**
	 * Instancia um novo status alerta notificacao enum.
	 *
	 * @param problema o status do problema
	 */
	private StatusUsuarioEnum(Integer status) {
		this.status = status;
	}
	
	public static StatusUsuarioEnum valueOfProblema(Integer status) {
		Optional<StatusUsuarioEnum> optional = Arrays.asList(values()).stream()
				.filter(StatusUsuarioEnum -> StatusUsuarioEnum.getStatus().equals(status)).findFirst();
		
		return (optional.isPresent()) ? optional.get() : null;
	}
	
	// Métodos get/set
	public Integer getStatus() {
		return status;
	}
	
}