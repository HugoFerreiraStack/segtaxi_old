/**
 * 
 */
package br.com.seg.econotaxi.service.impl;

import java.math.BigDecimal;
import java.util.List;

import br.com.seg.econotaxi.enums.StatusPagamentoEnum;
import br.com.seg.econotaxi.exception.NegocioException;
import br.com.seg.econotaxi.util.MaxiPagoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.seg.econotaxi.model.Transacao;
import br.com.seg.econotaxi.repository.TransacaoRepository;
import br.com.seg.econotaxi.service.CreditoService;
import br.com.seg.econotaxi.service.TransacaoService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author bruno
 *
 */
@Service("transacaoService")
public class TransacaoServiceImpl extends AbstractService implements TransacaoService {

	@Autowired
	private TransacaoRepository transacaoRepository;
	@Autowired
	private CreditoService creditoService;

	@Autowired
	private MaxiPagoUtil maxiPagoUtil;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Transacao salvarTransacao(Transacao transacao) {
		return transacaoRepository.save(transacao);
	}

	@Override
	public Transacao efetivarTransacao(Transacao transacao) {
		try {
			maxiPagoUtil.vendaDireta(transacao);

			if (transacao.getOrderIdMaxipago() != null &&
					transacao.getTransactionIdMaxipago() != null) {
				transacao.setStatus(StatusPagamentoEnum.PAGAMENTO_APROVADO.getStatus());
			}
		} catch (NegocioException e) {
			getLogger().error(e.getMessage());

			transacao.setStatus(StatusPagamentoEnum.PAGAMENTO_REJEITADO.getStatus());
			transacao.setErrorMaxipago(e.getMessage());
			creditoService.salvar(transacao.getCorrida().getUsuario().getId(), 
					new BigDecimal(transacao.getValor().doubleValue() * -1));
		}

		return transacaoRepository.save(transacao);
	}

	@Override
	public Transacao recuperarCidadePorChave(Long chave) {
		return transacaoRepository.findById(chave);
	}

	@Override
	public Long pesquisarCountTransacaoPorFiltro(Transacao filtro) {
		return transacaoRepository.pesquisarCountTransacaoPorFiltro(filtro);
	}

	@Override
	public List<Transacao> pesquisarTransacaoPorFiltro(Transacao filtro, int first, int pageSize) {
		return transacaoRepository.pesquisarTransacaoPorFiltro(filtro, first, pageSize);
	}

	@Override
	public List<Transacao> recuperarTransacoesPendentesEnvio() {
		return transacaoRepository.recuperarTransacoesPendentesEnvio();
	}

}