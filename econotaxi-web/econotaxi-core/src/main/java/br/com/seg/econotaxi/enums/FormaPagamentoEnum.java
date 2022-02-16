package br.com.seg.econotaxi.enums;

import java.util.Arrays;
import java.util.Optional;

public enum FormaPagamentoEnum {

    CREDITO (1, "Cartão de Crédito"),
    
    DINHEIRO (2, "Dinheiro"),
    
	DEBITO (3, "Cartão de Débito");

    private Integer codigo;
    private String descricao;

    FormaPagamentoEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }
    
   	public static FormaPagamentoEnum valueOfTipoTransacao(Integer codigo) {
   		Optional<FormaPagamentoEnum> optional = Arrays.asList(values()).stream()
   				.filter(FormaPagamentoEnum -> FormaPagamentoEnum.getCodigo().equals(codigo)).findFirst();
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