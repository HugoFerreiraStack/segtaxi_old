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
public enum TipoCorridaEnum {

    CORRIDA (1, "Corrida"),
    
    ENTREGA (2, "Entrega");

    private Integer codigo;
    private String descricao;

    TipoCorridaEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }
    
   	public static TipoCorridaEnum valueOfTipoCorrida(Integer codigo) {
   		Optional<TipoCorridaEnum> optional = Arrays.asList(values()).stream()
   				.filter(TipoCorridaEnum -> TipoCorridaEnum.getCodigo().equals(codigo)).findFirst();
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