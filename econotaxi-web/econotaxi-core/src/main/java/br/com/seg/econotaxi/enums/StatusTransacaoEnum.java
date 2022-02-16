package br.com.seg.econotaxi.enums;

import java.util.Arrays;
import java.util.Optional;

public enum StatusTransacaoEnum {

	/** cnab. */
	PENDENTE (1, "Pendente de Aprovação"),
	
	PAGAMENTO_APROVADO (2, "Pagamento Aprovado"),
	
	PAGAMENTO_REJEITADO (3, "Pagamento Rejeitado");
	
	/** tipo arquivo. */
	private Integer status;
	private String descricao;
	
	/**
	 * Instancia um novo status alerta notificacao enum.
	 *
	 * @param problema o status do problema
	 */
	private StatusTransacaoEnum(Integer status, String descricao) {
		this.status = status;
		this.descricao = descricao;
	}
	
	public static StatusTransacaoEnum valueOfStatus(Integer status) {
		Optional<StatusTransacaoEnum> optional = Arrays.asList(values()).stream()
				.filter(StatusTransacaoEnum -> StatusTransacaoEnum.getStatus().equals(status)).findFirst();
		
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