package br.com.seg.econotaxi.enums;

import java.util.Arrays;
import java.util.Optional;

/**
 * Classe de Enum
 *
 * Criado em 24 de jun de 2017
 * @author Bruno rocha
 */
public enum StatusCorridaEnum {

	/** cnab. */
	SOLICITADO (1, "Solicitado"),
	
	A_CAMINHO (2, "A Caminho"),
	
	CANCELADA (3, "Cancelada"),
	
	CORRENTE (4, "Em Andamento"),
	
	FINALIZADO (5, "Finalizada"),
	
	CANCELADA_TEMPO (6, "Cancelada por Tempo"),
	
	AGENDADA (7, "Agendada");
	
	/** tipo arquivo. */
	private Integer status;
	
	private String descricao;
	
	private StatusCorridaEnum(Integer status, String descricao) {
		this.status = status;
		this.descricao = descricao;
	}
	
	public static StatusCorridaEnum valueOfStatus(Integer status) {
		Optional<StatusCorridaEnum> optional = Arrays.asList(values()).stream()
				.filter(StatusCorridaEnum -> StatusCorridaEnum.getStatus().equals(status)).findFirst();
		
		return (optional.isPresent()) ? optional.get() : null;
	}
	
	// Métodos get/set
	public Integer getStatus() {
		return status;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
}