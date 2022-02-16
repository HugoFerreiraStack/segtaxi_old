package br.com.seg.econotaxi.view.comum;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;

import br.com.seg.econotaxi.enums.MenuEnum;
import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.model.Corrida;
import br.com.seg.econotaxi.service.CidadeService;
import br.com.seg.econotaxi.view.BaseView;
import br.com.seg.econotaxi.view.paginator.CidadeDataModel;

@Named
@Scope("view")
@ManagedBean(name = "cidadeView")
public class CidadeView extends BaseView {

    // Constantes
	private static final long serialVersionUID = 1584745532035822519L;
	
	// Atributos
	@Autowired
	private CidadeService cidadeService;
	private Cidade cidade;
	private Cidade filtro;
	private CidadeDataModel cidadeDataModel;
	private String cidadeMapa;
	
	/**
	 * Método responsável pela inicialização de informações na tela.
	 */
	@PostConstruct
	public void init() {
		setCidadeDataModel(new CidadeDataModel(cidadeService, getFiltro()));
	}
    
    public void salvarCidade() {
    	
    	definirMenu(MenuEnum.CIDADE.getMenu());
    	if (cidadeService.verificaExistenciaCidade(cidade)) {
    		addMsgErro("Cidade já existente!");
    	} else {
    		if (cidade.getId() == null) {
    			cidade.setDataCadastro(new Date());
    			cidade.setDataAlteracao(cidade.getDataCadastro());
    			cidadeService.salvarCidade(cidade);
    		} else {
    			Cidade c = cidadeService.recuperarCidadePorChave(cidade.getId());
    			c.setDataAlteracao(new Date());
    			c.setNome(cidade.getNome());
    			c.setSiglaPais(cidade.getSiglaPais());
    			c.setSiglaUf(cidade.getSiglaUf());
    			c.setDescricao(cidade.getDescricao());
    			c.setValorMinimoCorrida(cidade.getValorMinimoCorrida());
    			c.setValorMinimoCorridaBandeira2(cidade.getValorMinimoCorridaBandeira2());
    			c.setValorBandeirada(cidade.getValorBandeirada());
    			c.setValorKmBandeira1(cidade.getValorKmBandeira1());
    			c.setValorKmBandeira2(cidade.getValorKmBandeira2());
    			c.setValorHoraParada(cidade.getValorHoraParada());
    			c.setValorBandeirada(cidade.getValorBandeirada());
    			c.setValorKmBandeira1(cidade.getValorKmBandeira1());
    			c.setValorKmBandeira2(cidade.getValorKmBandeira2());
    			c.setValorAdicionalExecutivo(cidade.getValorAdicionalExecutivo());
    			c.setValorPorcentagemAdicionalExecutivo(cidade.getValorPorcentagemAdicionalExecutivo());
    			c.setValorAdicionalSuporteBike(cidade.getValorAdicionalSuporteBike());
    			c.setValorPorcentagemAdicionalSuporteBike(cidade.getValorPorcentagemAdicionalSuporteBike());
    			c.setValorMinimoEntrega(cidade.getValorMinimoEntrega());
    			c.setHorarioInicioBandeira2(cidade.getHorarioInicioBandeira2());
    			c.setHorarioFimBandeira2(cidade.getHorarioFimBandeira2());
    			c.setValorKmEntrega(cidade.getValorKmEntrega());
    			c.setKgMaximoEntrega(cidade.getKgMaximoEntrega());
    			c.setIndicadorAceitaPagamentoDebito(cidade.getIndicadorAceitaPagamentoDebito());
    			c.setIndicadorPossuiRastreador(cidade.getIndicadorPossuiRastreador());
    			c.setIndicadorAceitaPagamentoDinheiro(cidade.getIndicadorAceitaPagamentoDinheiro());
    			c.setPorcentagemDescontoLojista(cidade.getPorcentagemDescontoLojista());
    			c.setPorcentagemDescontoDinheiro(cidade.getPorcentagemDescontoDinheiro());
    			c.setPorcentagemDescontoAniversariante(cidade.getPorcentagemDescontoAniversariante());
    			c.setPorcentagemDesconto(cidade.getPorcentagemDesconto());
    			c.setDataInicio(cidade.getDataInicio());
    			c.setHoraInicio(cidade.getHoraInicio());
    			c.setDataFim(cidade.getDataFim());
    			c.setHoraFim(cidade.getHoraFim());
    			c.setValorKmForaCidade(cidade.getValorKmForaCidade());
    			c.setKmForaCidade(cidade.getKmForaCidade());
    			
    			c.setValorHoraParadaRadio(cidade.getValorHoraParadaRadio());
    			c.setValorBandeiradaRadio(cidade.getValorBandeiradaRadio());
    			c.setValorKmBandeira1Radio(cidade.getValorKmBandeira1Radio());
    			c.setValorKmBandeira2Radio(cidade.getValorKmBandeira2Radio());
    			c.setIndicadorAceitaPagamentoCredito(cidade.getIndicadorAceitaPagamentoCredito());
    			
    			c.setIndicadorPossuiTaximetro(cidade.getIndicadorPossuiTaximetro());
    			
    			c.setIndicadorPossuiCarroParticular(cidade.getIndicadorPossuiCarroParticular());
    			c.setIndicadorPossuiTaxi(cidade.getIndicadorPossuiTaxi());
    			c.setIndicadorPossuiMotoTaxi(cidade.getIndicadorPossuiMotoTaxi());
    			c.setIndicadorPossuiEntregaMoto(cidade.getIndicadorPossuiEntregaMoto());
    			c.setIndicadorPossuiEntregaCarro(cidade.getIndicadorPossuiEntregaCarro());
    			c.setIndicadorPossuiEntregaCaminhao(cidade.getIndicadorPossuiEntregaCaminhao());
    			c.setIndicadorPossuiGuincho(cidade.getIndicadorPossuiGuincho());
    			c.setIndicadorPossuiTeleTaxi(cidade.getIndicadorPossuiTeleTaxi());
    			
    			c.setValorHoraParadaParticular(cidade.getValorHoraParadaParticular());
    			c.setValorBandeiradaParticular(cidade.getValorBandeiradaParticular());
    			c.setValorKmBandeira1Particular(cidade.getValorKmBandeira1Particular());
    			c.setValorKmBandeira2Particular(cidade.getValorKmBandeira2Particular());
    			
    			c.setValorHoraParadaMotoTaxi(cidade.getValorHoraParadaMotoTaxi());
    			c.setValorBandeiradaMotoTaxi(cidade.getValorBandeiradaMotoTaxi());
    			c.setValorKmBandeira1MotoTaxi(cidade.getValorKmBandeira1MotoTaxi());
    			c.setValorKmBandeira2MotoTaxi(cidade.getValorKmBandeira2MotoTaxi());
    			
    			c.setValorAdicionalExecutivoParticular(cidade.getValorAdicionalExecutivoParticular());
    			c.setValorPorcentagemAdicionalExecutivoParticular(cidade.getValorPorcentagemAdicionalExecutivoParticular());
    			c.setValorKmForaCidadeParticular(cidade.getValorKmForaCidadeParticular());
    			c.setValorKmForaCidadeMotoTaxi(cidade.getValorKmForaCidadeMotoTaxi());
    			
    			c.setPorcentagemDescontoDinheiroParticular(cidade.getPorcentagemDescontoDinheiroParticular());
    			c.setPorcentagemDescontoAniversarianteParticular(cidade.getPorcentagemDescontoAniversarianteParticular());
    			c.setPorcentagemDescontoParticular(cidade.getPorcentagemDescontoParticular());
    			c.setDataInicioParticular(cidade.getDataInicioParticular());
    			c.setHoraInicioParticular(cidade.getHoraInicioParticular());
    			c.setDataFimParticular(cidade.getDataFimParticular());
    			c.setHoraFimParticular(cidade.getHoraFimParticular());
    			
    			c.setPorcentagemDescontoDinheiroMoto(cidade.getPorcentagemDescontoDinheiroMoto());
    			c.setPorcentagemDescontoAniversarianteMoto(cidade.getPorcentagemDescontoAniversarianteMoto());
    			c.setPorcentagemDescontoMoto(cidade.getPorcentagemDescontoMoto());
    			c.setDataInicioMoto(cidade.getDataInicioMoto());
    			c.setHoraInicioMoto(cidade.getHoraInicioMoto());
    			c.setDataFimMoto(cidade.getDataFimMoto());
    			c.setHoraFimMoto(cidade.getHoraFimMoto());
    			
    			cidadeService.salvarCidade(c);
    			
    			RestTemplate rest = new RestTemplate();
    			rest.postForEntity(Corrida.URL + "econotaxi-rest/rest/corridaLocal/adicionarCidadeLocal", 
    					c, Void.class);
    		}
    		addMsgSuccess("Cidade salva com sucesso!");
    		setCidade(null);
    	}
    }
    
    public void salvarLocalizacao() {
    	
    	Cidade c = cidadeService.recuperarCidadePorChave(cidade.getId());
    	c.setDataAlteracao(new Date());
    	c.setLatitude(cidade.getLatitude());
    	c.setLongitude(cidade.getLongitude());
    	c.setRaioKm(cidade.getRaioKm());
		cidadeService.salvarCidade(c);
		
		RestTemplate rest = new RestTemplate();
		rest.postForEntity(Corrida.URL + "econotaxi-rest/rest/corridaLocal/adicionarCidadeLocal", 
				c, Void.class);
		addMsgSuccess("Localização salva com sucesso!");
    }
    
    public void carregarCidade(Cidade cidade) {
    	setCidade(cidadeService.recuperarCidadePorChave(cidade.getId()));
    	setCidadeMapa(getCidade().getNome());
    }
    
    public void excluirCidade() {
    	
    	definirMenu(MenuEnum.CIDADE.getMenu());
    	cidadeService.excluir(cidade);
		addMsgSuccess("Cidade excluída com sucesso!");
		setCidade(null);
    }
    
    public void pesquisarCidadePorFiltro() {
    	
    	setCidadeDataModel(new CidadeDataModel(cidadeService, filtro));
    }
    
    /**
     * Método responsável por limpar formulário e resultados da pesquisa.
     */
    public void limparFormulario() {
    	
    	setFiltro(null);
    	setCidadeDataModel(null);
    }

    // Métodos get/set
	public Cidade getCidade() {
		if (cidade == null) {
			cidade = new Cidade();
		}
		return cidade;
	}
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	public Cidade getFiltro() {
		if (filtro == null) {
			filtro = new Cidade();
		}
		return filtro;
	}
	public void setFiltro(Cidade filtro) {
		this.filtro = filtro;
	}
	public CidadeDataModel getCidadeDataModel() {
		return cidadeDataModel;
	}
	public void setCidadeDataModel(CidadeDataModel cidadeDataModel) {
		this.cidadeDataModel = cidadeDataModel;
	}
	public String getCidadeMapa() {
		return cidadeMapa;
	}
	public void setCidadeMapa(String cidadeMapa) {
		this.cidadeMapa = cidadeMapa;
	}
	
}