package br.com.seg.econotaxi.enums;

import java.util.Arrays;
import java.util.Optional;

public enum StatusPagamentoEnum {

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
	private StatusPagamentoEnum(Integer status, String descricao) {
		this.status = status;
		this.descricao = descricao;
	}
	
	public static StatusPagamentoEnum valueOfStatus(Integer status) {
		Optional<StatusPagamentoEnum> optional = Arrays.asList(values()).stream()
				.filter(StatusPagamentoEnum -> StatusPagamentoEnum.getStatus().equals(status)).findFirst();
		
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