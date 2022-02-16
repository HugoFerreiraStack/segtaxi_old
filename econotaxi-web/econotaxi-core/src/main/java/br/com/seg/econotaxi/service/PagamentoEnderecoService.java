package br.com.seg.econotaxi.service;

import br.com.seg.econotaxi.model.PagamentoEndereco;

import java.util.List;

public interface PagamentoEnderecoService {

    PagamentoEndereco salvar(PagamentoEndereco pagamentoEndereco);

    List<PagamentoEndereco> recuperarEnderecosDoUsuario(Long idUsuario);

}
