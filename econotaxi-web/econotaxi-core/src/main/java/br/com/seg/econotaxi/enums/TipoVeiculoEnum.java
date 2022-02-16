package br.com.seg.econotaxi.enums;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public enum TipoVeiculoEnum {

    PADRAO (1, "Táxi Padrão"),
    EXECUTIVO (2, "Táxi Executivo"),
    MULHER_PADRAO (3, "Táxi Mulher Padrão"),
    MULHER_EXECUTIVO (4, "Táxi Mulher Executivo"),
    ADAPTADO_PADRAO (5, "Táxi Adaptado Padrão"),
    ADAPTADO_EXECUTIVO (6, "Táxi Adaptado Executivo"),
    PARTICULAR_PADRAO (7, "Particular Padrão"),
    PARTICULAR_EXECUTIVO (8, "Particular Executivo"),
    MOTO_TAXI (9, "Moto Táxi");

    private Integer codigo;
    private String descricao;

    TipoVeiculoEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public static TipoVeiculoEnum valueOfCodigo(Integer codigo) {
        Optional<TipoVeiculoEnum> optional = Arrays.stream(values()).filter(
                tipoVeiculoEnum -> tipoVeiculoEnum.getCodigo().equals(codigo)).findFirst();

        return (optional.isPresent()) ? optional.get() : null;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }
}
