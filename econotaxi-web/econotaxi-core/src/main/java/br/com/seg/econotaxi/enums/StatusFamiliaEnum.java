package br.com.seg.econotaxi.enums;

import java.util.Arrays;
import java.util.Optional;

public enum StatusFamiliaEnum {

	/** cnab. */
	PENDENTE (1, "Pendente de Aprovação"),
	
	AUTORIZADO (2, "Autorizado"),
	
	REJEITADO (3, "Rejeitado");
	
	/** tipo arquivo. */
	private Integer status;
	private String descricao;
	
	/**
	 * Instancia um novo status alerta notificacao enum.
	 *
	 * @param problema o status do problema
	 */
	private StatusFamiliaEnum(Integer status, String descricao) {
		this.status = status;
		this.descricao = descricao;
	}
	
	public static StatusFamiliaEnum valueOfStatus(Integer status) {
		Optional<StatusFamiliaEnum> optional = Arrays.asList(values()).stream()
				.filter(StatusFamiliaEnum -> StatusFamiliaEnum.getStatus().equals(status)).findFirst();
		
		return (optional.isPresent()) ? optional.get() : null;
	}
	
	// Métodos get/set
	public Integer getStatus() {
		return status;
	}
	
	public String getDescricao(){
		return descricao;
	}
	
}