package br.com.seg.econotaxi.service.impl;

import br.com.seg.econotaxi.model.PagamentoEndereco;
import br.com.seg.econotaxi.repository.PagamentoEnderecoRepository;
import br.com.seg.econotaxi.service.PagamentoEnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("pagamentoEnderecoService")
public class PagamentoEnderecoServiceImpl extends AbstractService implements PagamentoEnderecoService {

    @Autowired
    private PagamentoEnderecoRepository pagamentoEnderecoRepository;

    @Override
    public PagamentoEndereco salvar(PagamentoEndereco pagamentoEndereco) {
        return pagamentoEnderecoRepository.save(pagamentoEndereco);
    }

    @Override
    public List<PagamentoEndereco> recuperarEnderecosDoUsuario(Long idUsuario) {
        return pagamentoEnderecoRepository.findByIdUsuario(idUsuario);
    }
}
