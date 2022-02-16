package br.com.seg.econotaxi.view.comum;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import br.com.seg.econotaxi.enums.StatusCorridaEnum;
import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.model.Corrida;
import br.com.seg.econotaxi.model.EmpresaConveniada;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.model.Veiculo;
import br.com.seg.econotaxi.service.CidadeService;
import br.com.seg.econotaxi.service.CorridaService;
import br.com.seg.econotaxi.service.VeiculoService;
import br.com.seg.econotaxi.view.BaseView;
import br.com.seg.econotaxi.view.paginator.CorridaTeleDataModel;

@Named
@Scope("view")
@ManagedBean(name = "relatorioMotoristaTeleView")
public class RelatorioVeiculoVoucherView extends BaseView {

    // Constantes
	private static final long serialVersionUID = 1584745532035822519L;
	
	// Atributos
	@Autowired
	private CidadeService cidadeService;
	@Autowired
	private CorridaService corridaService;
	@Autowired
	private VeiculoService veiculoService;
	private EmpresaConveniada empresaConveniada;
	private List<Cidade> listaCidade;
	private Usuario usuarioTele;
	private Cidade cidadeSelecionada;
	private Integer empresaSelecionada;
	private String mesReferencia;
	private BigDecimal valorReceber;
	private BigDecimal valorConsumido;
	private CorridaTeleDataModel corridaDataModel;
	private Integer tipoTeleTaxi;
	private Veiculo veiculo;
	private List<Veiculo> veiculos;
	
	/**
	 * Método responsável pela inicialização de informações na tela.
	 */
	@PostConstruct
	public void init() {
		
		setUsuarioTele(recuperarUsuarioSessao());
		setListaCidade(cidadeService.recuperarTodasCidades());
		/*Corrida filtro = new Corrida();
		filtro.setStatus(999);
		filtro.setIndicadorTeleTaxi(1);
		setCorridaDataModel(new CorridaTeleDataModel(corridaService, filtro));*/
	}
	
	public void selecionarCidade() {
		
		if (getCidadeSelecionada() != null && getCidadeSelecionada().getId() != null) {
			setVeiculos(veiculoService.recuperarVeiculosPorCidade(getCidadeSelecionada()));
		}
	}
	
	public void exibirRelatorio() {
		
		Boolean verificaErro = Boolean.FALSE;
		if (!verificaErro) {
			
			Corrida filtro = new Corrida();
			filtro.setMesReferencia(getMesReferencia());
			filtro.setStatus(StatusCorridaEnum.FINALIZADO.getStatus());
			filtro.setIndicadorTeleTaxi(1);
			filtro.setTipoTeleTaxi(getTipoTeleTaxi());
			filtro.setCorridaVoucher(Boolean.TRUE);
			filtro.setVeiculo(getVeiculo());
			setCorridaDataModel(new CorridaTeleDataModel(corridaService, filtro));
			setValorConsumido(corridaService.recuperarValorConsumidoVeiculo(getVeiculo(), 
					getMesReferencia(), getTipoTeleTaxi()));
			if (getValorConsumido() != null) {
				setValorConsumido(getValorConsumido().setScale(2, BigDecimal.ROUND_HALF_EVEN));
			}
		}
	}
	
    // Métodos get/set
	public List<Cidade> getListaCidade() {
		return listaCidade;
	}

	public void setListaCidade(List<Cidade> listaCidade) {
		this.listaCidade = listaCidade;
	}
	
	public List<StatusCorridaEnum> getListaStatusCorrida() {
		return Arrays.asList(StatusCorridaEnum.values());
	}

	public Usuario getUsuarioTele() {
		return usuarioTele;
	}

	public void setUsuarioTele(Usuario usuarioTele) {
		this.usuarioTele = usuarioTele;
	}

	public Cidade getCidadeSelecionada() {
		return cidadeSelecionada;
	}

	public void setCidadeSelecionada(Cidade cidadeSelecionada) {
		this.cidadeSelecionada = cidadeSelecionada;
	}

	public Integer getEmpresaSelecionada() {
		return empresaSelecionada;
	}

	public void setEmpresaSelecionada(Integer empresaSelecionada) {
		this.empresaSelecionada = empresaSelecionada;
	}

	public EmpresaConveniada getEmpresaConveniada() {
		return empresaConveniada;
	}

	public void setEmpresaConveniada(EmpresaConveniada empresaConveniada) {
		this.empresaConveniada = empresaConveniada;
	}
	
	public List<String> getUltimosMeses() {
		
		List<String> meses = new ArrayList<String>();
		
		Calendar hoje = Calendar.getInstance();
		meses.add(new SimpleDateFormat("MM/YYYY").format(hoje.getTime()));
		for (int i = 0; i < 11; i++) {
			hoje.add(Calendar.MONTH, -1);
			meses.add(new SimpleDateFormat("MM/YYYY").format(hoje.getTime()));
		}
		return meses;
	}

	public String getMesReferencia() {
		return mesReferencia;
	}

	public void setMesReferencia(String mesReferencia) {
		this.mesReferencia = mesReferencia;
	}

	public BigDecimal getValorReceber() {
		return valorReceber;
	}

	public void setValorReceber(BigDecimal valorReceber) {
		this.valorReceber = valorReceber;
	}

	public BigDecimal getValorConsumido() {
		return valorConsumido;
	}

	public void setValorConsumido(BigDecimal valorConsumido) {
		this.valorConsumido = valorConsumido;
	}

	public CorridaTeleDataModel getCorridaDataModel() {
		return corridaDataModel;
	}

	public void setCorridaDataModel(CorridaTeleDataModel corridaDataModel) {
		this.corridaDataModel = corridaDataModel;
	}

	public Integer getTipoTeleTaxi() {
		return tipoTeleTaxi;
	}

	public void setTipoTeleTaxi(Integer tipoTeleTaxi) {
		this.tipoTeleTaxi = tipoTeleTaxi;
	}

	public Veiculo getVeiculo() {
		if (veiculo == null) {
			veiculo = new Veiculo();
		}
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	public List<Veiculo> getVeiculos() {
		return veiculos;
	}

	public void setVeiculos(List<Veiculo> veiculos) {
		this.veiculos = veiculos;
	}

}