package br.com.seg.econotaxi.enums;

import java.util.Arrays;
import java.util.Optional;

public enum StatusMotoristaEnum {

	/** cnab. */
	PENDENTE (1, "Pendente de Aprovação"),
	
	ATIVO (2, "Ativo"),
	
	SOLICITOU_EXCLUSAO (3, "Solicitou Exclusão"),
	
	EXCLUIDO (4, "Excluído"),
	
	BLOQUEADO_TEMPORARIAMENTE (5, "Bloqueado Temporariamente"),
	
	NAO_AUTORIZADO (6, "Não Autorizado");
	
	/** tipo arquivo. */
	private Integer status;
	private String descricao;
	
	/**
	 * Instancia um novo status alerta notificacao enum.
	 *
	 * @param problema o status do problema
	 */
	private StatusMotoristaEnum(Integer status, String descricao) {
		this.status = status;
		this.descricao = descricao;
	}
	
	public static StatusMotoristaEnum valueOfStatus(Integer status) {
		Optional<StatusMotoristaEnum> optional = Arrays.asList(values()).stream()
				.filter(StatusMotoristaEnum -> StatusMotoristaEnum.getStatus().equals(status)).findFirst();
		
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