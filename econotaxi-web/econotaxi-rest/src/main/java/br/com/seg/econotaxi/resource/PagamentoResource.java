/**
 * 
 */
package br.com.seg.econotaxi.resource;

import br.com.seg.econotaxi.enums.StatusPagamentoEnum;
import br.com.seg.econotaxi.exception.NegocioException;
import br.com.seg.econotaxi.model.Credito;
import br.com.seg.econotaxi.model.Pagamento;
import br.com.seg.econotaxi.model.Transacao;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.service.*;
import br.com.seg.econotaxi.vo.DadosPagamentoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author bruno
 *
 */
@RestController
@RequestMapping("rest/pagamento")
public class PagamentoResource {

	@Autowired
	private PagamentoService pagamentoService;

	@Autowired
	private TransacaoService transacaoService;

	@Autowired
	private CreditoService creditoService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private PagamentoEnderecoService pagamentoEnderecoService;

	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
	@RequestMapping(value = "/dados/{idUsuario}", method = RequestMethod.GET)
	public DadosPagamentoVo consultarDadosPagamento(@PathVariable("idUsuario") Long idUsuario) {

		DadosPagamentoVo dadosPagamentoVo = new DadosPagamentoVo();
		dadosPagamentoVo.setPagamentos(listarPagamentos(idUsuario));
		dadosPagamentoVo.setCredito(consultarCredito(idUsuario));

		return dadosPagamentoVo;
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/listar/{idUsuario}", method = RequestMethod.GET)
	public List<Pagamento> listarPagamentos(@PathVariable("idUsuario") Long idUsuario) {

		Usuario usuario = new Usuario();
		usuario.setId(idUsuario);

		return pagamentoService.recuperarPagamentosPorUsuario(usuario);
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/buscar", method = RequestMethod.POST)
	public Pagamento buscarPagamento(@RequestBody Pagamento pagamento) {
		
		return pagamentoService.recuperarPagamentoPorChave(pagamento.getId());
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/salvar", method = RequestMethod.POST)
	public Pagamento salvarPagamento(@RequestBody Pagamento pagamento) {

		List<Pagamento> pagamentos = pagamentoService.recuperarPagamentosPorUsuario(pagamento.getUsuario());
		
		String idMaxipago = usuarioService.adicionarMaxipago(pagamento.getUsuario().getId());

		pagamento.getUsuario().setIdMaxipago(idMaxipago);

		pagamento.getPagamentoEndereco().setPais("BR");
		pagamento.setPagamentoEndereco(pagamentoEnderecoService.salvar(pagamento.getPagamentoEndereco()));
		pagamento.setDataCadastro(new Date());
		if (pagamentos == null || pagamentos.isEmpty()) {
			pagamento.setIndicadorPadrao(1);
		} else {
			pagamento.setIndicadorPadrao(null);
		}
		pagamentoService.salvarPagamento(pagamento);

		Transacao transacao = new Transacao();
		transacao.setValor(new BigDecimal("0.01"));
		transacao.setPagamento(pagamento);

		adicionarTransacaoCredito(transacao);

		return pagamento;
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/padrao", method = RequestMethod.POST)
	public Pagamento definirPagamentoPadrao(@RequestBody Pagamento pagamento) {
		
		Pagamento pagPadrao = pagamentoService.recuperarPagamentoPadrao(pagamento.getUsuario());
		if (pagPadrao != null) {
			pagPadrao.setIndicadorPadrao(null);
			pagamentoService.salvar(pagPadrao);
		}
		
		Pagamento pagPersistente = pagamentoService.recuperarPagamentoPorChave(pagamento.getId());
		pagPersistente.setIndicadorPadrao(1);
		pagamentoService.salvar(pagPersistente);
		return pagPersistente;
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/excluir", method = RequestMethod.POST)
	public void excluirPagamento(@RequestBody Pagamento pagamento) {

		String idMaxipago = usuarioService.recuperarIdMaxipago(pagamento.getUsuario().getId());
		pagamento.getUsuario().setIdMaxipago(idMaxipago);

		pagamentoService.removerPagamento(pagamento);
		
		try {
			List<Pagamento> pagamentos = pagamentoService.recuperarPagamentosPorUsuario(
					pagamento.getUsuario());
			if (pagamentos != null && !pagamentos.isEmpty()) {
				Pagamento pagPadrao = pagamentos.get(0);
				pagPadrao.setIndicadorPadrao(1);
				pagamentoService.salvar(pagPadrao);
			}
		} catch (Exception e) {}
	}

	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
	@RequestMapping(value = "/credito/{idUsuario}", method = RequestMethod.GET)
	public Credito consultarCredito(@PathVariable("idUsuario") Long idUsuario) {
		return creditoService.recuperarPorUsuario(idUsuario);
	}

	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
	@RequestMapping(value = "/credito/adicionar", method = RequestMethod.POST)
	public void adicionarCredito(@RequestBody Transacao transacao) {
		adicionarTransacaoCredito(transacao);
	}

	private void adicionarTransacaoCredito(Transacao transacao) {
		String idMaxipago = usuarioService.recuperarIdMaxipago(transacao.getPagamento().getUsuario().getId());
		String tokenMaxipago = pagamentoService.recuperarTokenPorId(transacao.getPagamento().getId());

		transacao.getPagamento().getUsuario().setIdMaxipago(idMaxipago);
		transacao.getPagamento().setTokenMaxipago(tokenMaxipago);

		transacao.setDataTransacao(new Date());
		transacao.setStatus(StatusPagamentoEnum.PENDENTE.getStatus());

		transacao = transacaoService.salvarTransacao(transacao);
		transacao = transacaoService.efetivarTransacao(transacao);

		if (transacao.getErrorMaxipago() != null) {
			throw new NegocioException(transacao.getErrorMaxipago());
		}

		creditoService.salvar(transacao.getPagamento().getUsuario().getId(), transacao.getValor());
	}
	
}