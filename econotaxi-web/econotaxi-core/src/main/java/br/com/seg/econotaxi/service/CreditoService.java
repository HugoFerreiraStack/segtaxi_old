package br.com.seg.econotaxi.service;

import br.com.seg.econotaxi.model.Credito;

import java.math.BigDecimal;

public interface CreditoService {

    Credito recuperarPorUsuario(Long idUsuario);

    void salvar(Long idUsuario, BigDecimal valor);

}
