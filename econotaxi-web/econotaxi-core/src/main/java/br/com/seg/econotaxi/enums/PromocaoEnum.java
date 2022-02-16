package br.com.seg.econotaxi.enums;

import java.util.Arrays;
import java.util.Optional;

/**
 * TipoTransacaoEnum.java
 * 
 * Criado em 06 de junho de 2017
 *
 * @author welson
 */
public enum PromocaoEnum {

    PRIMEIRA_CORRIDA (1, "Primeira Corrida"),
    
    SEGUNDA_CORRIDA (2, "Segunda Corrida"),
    
    TERCEIRA_CORRIDA (3, "Terceira Corrida"),
    
    QUARTA_CORRIDA (4, "Quarta Corrida"),
    
    QUINTA_CORRIDA (5, "Quinta Corrida"),
    
    SEXTA_CORRIDA (6, "Sexta Corrida"),
    
    SETIMA_CORRIDA (7, "Sétima Corrida"),
    
    OITAVA_CORRIDA (8, "Oitava Corrida"),
    
    NOVA_CORRIDA (9, "Nona Corrida"),
    
    DECIMA_CORRIDA (10, "Décima Corrida"),
    
    APOS_10_CORRIDAS (11, "Após 10 Corridas"),
    
    COM_15_CORRIDAS (12, "Com 15 Corridas"),
    
    CADA_5_CORRIDAS (13, "A Cada 5 Corridas"),
    
    CADA_10_CORRIDAS (14, "A Cada 10 Corridas"),
    
    CADA_15_CORRIDAS (15, "A Cada 15 Corridas");

    private Integer codigo;
    private String descricao;

    PromocaoEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }
    
   	public static PromocaoEnum valueOfPromocao(Integer codigo) {
   		Optional<PromocaoEnum> optional = Arrays.asList(values()).stream()
   				.filter(PromocaoEnum -> PromocaoEnum.getCodigo().equals(codigo)).findFirst();
   		return (optional.isPresent()) ? optional.get() : null;
   	}

    // Métodos get/set
    public Integer getCodigo() {
        return codigo;
    }
	public String getDescricao() {
		return descricao;
	}
    
}