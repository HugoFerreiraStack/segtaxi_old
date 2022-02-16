package br.com.seg.econotaxi.enums;

import java.util.Arrays;
import java.util.Optional;

public enum StatusVeiculoSegEnum {

	PLACA_NAO_EXISTENTE ("406", "O Veículo utilizado não possui rastreador da Seg Rastreadores. Procure a Seg Rastreadores para verificar sua situação."),
	
	ATIVO ("411", "Ativo"),
	
	PLACA_ENCONTRADA_POSICAO_DESATUALIZADA ("410", "Placa Encontrada / Posição Desatualizada. Procure a Seg Rastreadores para ajustar seu rastreador."),
	
	PLACA_ENCONTRADA_POSICAO_NAO ("409", "Placa Encontrada / Posição Não Encontrada. Procure a Seg Rastreadores para ajustar seu rastreador."),
	
	PLACA_NAO_INFORMADA ("405", "Placa não informada ou Chave Inválida."),
	
	PROBLEMA_FINANCEIRO ("300", "Existem pendências financeiras. Procure a Seg Rastreadores para verificar a seguinte situação: ");
	
	/** tipo arquivo. */
	private String status;
	private String descricao;
	
	/**
	 * Instancia um novo status alerta notificacao enum.
	 *
	 * @param problema o status do problema
	 */
	private StatusVeiculoSegEnum(String status, String descricao) {
		this.status = status;
		this.descricao = descricao;
	}
	
	public static StatusVeiculoSegEnum valueOfStatus(String status) {
		Optional<StatusVeiculoSegEnum> optional = Arrays.asList(values()).stream()
				.filter(StatusVeiculoSegEnum -> StatusVeiculoSegEnum.getStatus().equals(status)).findFirst();
		
		return (optional.isPresent()) ? optional.get() : null;
	}
	
	// Métodos get/set
	public String getStatus() {
		return status;
	}
	
	public String getDescricao(){
		return descricao;
	}
	
}