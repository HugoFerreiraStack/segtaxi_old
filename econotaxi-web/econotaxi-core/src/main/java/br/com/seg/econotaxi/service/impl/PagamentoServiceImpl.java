/**
 * 
 */
package br.com.seg.econotaxi.service.impl;

import java.util.List;

import br.com.seg.econotaxi.util.MaxiPagoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.seg.econotaxi.model.Pagamento;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.repository.PagamentoRepository;
import br.com.seg.econotaxi.service.PagamentoService;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author bruno
 *
 */
@Service("pagamentoService")
public class PagamentoServiceImpl implements PagamentoService {

	private static final int TAMANHO_CARTAO_16_POSICOES = 19;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private MaxiPagoUtil maxiPagoUtil;
	
	@Override
	@Transactional
	public Pagamento salvarPagamento(Pagamento pagamento) {

		String numeroCartao = pagamento.getNumero().replaceAll(" ", "");

		tratarNumeroCartao(pagamento);
		pagamentoRepository.save(pagamento);

		String token = maxiPagoUtil.adicionarCartao(pagamento, numeroCartao);
		pagamento.setTokenMaxipago(token);
		return pagamentoRepository.save(pagamento);
	}

	private void tratarNumeroCartao(Pagamento pagamento) {
		if (pagamento.getNumero().length() == TAMANHO_CARTAO_16_POSICOES) {

			String[] partes = pagamento.getNumero().split(" ");
			String numeroMascara = "**** **** **** " + partes[3];

			pagamento.setNumero(numeroMascara);
		}
	}

	@Override
	public Pagamento recuperarPagamentoPorChave(Long chave) {
		return pagamentoRepository.findById(chave);
	}

	@Override
	public List<Pagamento> recuperarPagamentosPorFiltro(Pagamento filtro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public void removerPagamento(Pagamento pagamento) {
		String idMaxipago = pagamento.getUsuario().getIdMaxipago();
		String token = pagamentoRepository.findToken(pagamento.getId());

		pagamentoRepository.delete(pagamento);

		if (token != null) {
			maxiPagoUtil.removerCartao(idMaxipago, token);
		}
	}

	@Override
	public List<Pagamento> recuperarPagamentosPorUsuario(Usuario usuario) {
		return pagamentoRepository.recuperarPagamentosPorUsuario(usuario);
	}

	@Override
	public String recuperarTokenPorId(Long id) {
		return pagamentoRepository.findToken(id);
	}

	@Override
	@Transactional
	public void salvar(Pagamento pagamento) {
		pagamentoRepository.save(pagamento);
	}

	@Override
	public Pagamento recuperarPagamentoPadrao(Usuario usuario) {
		return pagamentoRepository.recuperarPagamentoPadrao(usuario);
	}
}