package br.com.seg.econotaxi.service.impl;

import br.com.seg.econotaxi.model.Credito;
import br.com.seg.econotaxi.repository.CreditoRepository;
import br.com.seg.econotaxi.service.CreditoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service("creditoService")
public class CreditoServiceImpl implements CreditoService {

    @Autowired
    private CreditoRepository creditoRepository;

    @Override
    public Credito recuperarPorUsuario(Long idUsuario) {
        return creditoRepository.findByIdUsuario(idUsuario);
    }

    @Override
    public void salvar(Long idUsuario, BigDecimal valor) {
        Credito credito = recuperarPorUsuario(idUsuario);

        if (credito == null) {
            credito = new Credito();
            credito.setIdUsuario(idUsuario);
            credito.setValor(valor);

        } else {

            BigDecimal valorAtualizado = credito.getValor().add(valor);
            credito.setValor(valorAtualizado);
        }

        creditoRepository.save(credito);
    }
}
