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
public enum TipoTransacaoEnum {

    INSERT (1, "Insert"),
    
    UPDATE (2, "Update"),
    
	DELETE (3, "Delete");

    private Integer codigo;
    private String descricao;

    /**
     * Instancia um novo tipo transacao enum.
     *
     * @param codigo codigo
     */
    TipoTransacaoEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }
    
    /**
   	 * Value of Tipo de Transação.
   	 *
   	 * @param codigo o código do Tipo de Transação
   	 * @return o Tipo de Integração enum
   	 */
   	public static TipoTransacaoEnum valueOfTipoTransacao(Integer codigo) {
   		Optional<TipoTransacaoEnum> optional = Arrays.asList(values()).stream()
   				.filter(TipoTransacaoEnum -> TipoTransacaoEnum.getCodigo().equals(codigo)).findFirst();
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