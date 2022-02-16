package br.com.seg.econotaxi.enums;

import java.util.Arrays;
import java.util.Optional;

public enum TipoUsuarioEnum {

    ADMINISTRATIVO (1, "ADMINISTRATIVO", "ROLE_ADMINISTRATIVO"),
    MOTORISTA (2, "MOTORISTA", "ROLE_MOTORISTA"),
    CLIENTE (3, "CLIENTE APP", "ROLE_CLIENTE"),
    LOJISTA (4, "LOJISTA", "ROLE_LOJISTA");

    private Integer codigo;
    private String descricao;
    private String role;

    TipoUsuarioEnum(Integer codigo, String descricao, String role) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.role = role;
    }

    public static TipoUsuarioEnum valueOfCodigo(Integer codigo) {
        Optional<TipoUsuarioEnum> optional = Arrays.stream(values()).filter(
                tipoUsuarioEnum -> tipoUsuarioEnum.codigo.equals(codigo)).findFirst();

        return (optional.isPresent()) ? optional.get() : null;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getRole() {
        return role;
    }
}
