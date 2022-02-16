package br.com.seg.econotaxi.view.comum;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import br.com.seg.econotaxi.enums.MenuEnum;
import br.com.seg.econotaxi.enums.StatusCorridaEnum;
import br.com.seg.econotaxi.enums.StatusMotoristaEnum;
import br.com.seg.econotaxi.enums.StatusUsuarioEnum;
import br.com.seg.econotaxi.enums.StatusVeiculoEnum;
import br.com.seg.econotaxi.enums.TipoCorridaEnum;
import br.com.seg.econotaxi.model.Corrida;
import br.com.seg.econotaxi.model.Lojista;
import br.com.seg.econotaxi.model.Motorista;
import br.com.seg.econotaxi.model.Veiculo;
import br.com.seg.econotaxi.service.CorridaService;
import br.com.seg.econotaxi.service.LojistaService;
import br.com.seg.econotaxi.service.MotoristaService;
import br.com.seg.econotaxi.service.UsuarioService;
import br.com.seg.econotaxi.service.VeiculoService;
import br.com.seg.econotaxi.view.BaseView;

/**
 * Classe de visão responsável pelo controle da tela de Dashboard.
 *
 * Criado em 25 de jun de 2017
 * @author Bruno rocha
 */
@Named
@ManagedBean
@Scope("view")
public class DashboardView extends BaseView implements Serializable {

	// Constantes
	private static final long serialVersionUID = -5794693607592014431L;
	
	// Atributos
	private Integer qtdMotoristasAtivos;
	private Integer qtdMotoristasPendentes;
	private Integer qtdLojistasAtivos;
	private Integer qtdLojistasPendentes;
	private Integer qtdVeiculosAtivos;
	private Integer qtdVeiculosPendentes;
	private Integer qtdUsuariosAtivos;
	private Integer qtdUsuariosPagamentosAtivo;
	private Integer qtdCorridasRealizadas;
	private Integer qtdCorridasCorrentes;
	private Integer qtdCorridasBuscando;
	private Integer qtdCorridasCaminho;
	private Integer qtdBotaoPanico;
	private List<Motorista> motoristasPendentes;
	private List<Veiculo> veiculosPendentes;
	private List<Corrida> corridasAndamento;
	private List<Lojista> lojistasPendentes;
	
	@Autowired
	private MotoristaService motoristaService;
	@Autowired
	private LojistaService lojistaService;
	@Autowired
	private CorridaService corridaService;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private VeiculoService veiculoService;
	
	/**
	 * Método responsável pela inicialização de informações na tela.
	 */
	@PostConstruct
	public void init() {
		
		definirMenu(MenuEnum.DASHBOARD.getMenu());
		carregarInformacoesDashboard();
	}
	
	/**
	 * Método responsável por carregar Informações de Dashboard.
	 */
	public void carregarDashboard() {
		
		carregarInformacoesDashboard();
	}

	/**
	 * Método responsável por carregar Informações de Dashboard.
	 */
	private void carregarInformacoesDashboard() {
		
		setQtdCorridasCaminho(corridaService.recuperarQtdCorridaPorStatus(
				StatusCorridaEnum.A_CAMINHO.getStatus()));
		setQtdCorridasBuscando(corridaService.recuperarQtdCorridaPorStatus(
				StatusCorridaEnum.SOLICITADO.getStatus()));
		setQtdCorridasCorrentes(corridaService.recuperarQtdCorridaPorStatus(
				StatusCorridaEnum.CORRENTE.getStatus()));
		setQtdCorridasRealizadas(corridaService.recuperarQtdCorridaPorStatus(
				StatusCorridaEnum.FINALIZADO.getStatus()));
		setQtdMotoristasAtivos(motoristaService.recuperarQtdMotoristaPorStatus(
				StatusMotoristaEnum.ATIVO.getStatus()));
		setQtdMotoristasPendentes(motoristaService.recuperarQtdMotoristaPorStatus(
				StatusMotoristaEnum.PENDENTE.getStatus()));
		setQtdVeiculosAtivos(veiculoService.recuperarQtdVeiculoPorStatus(
				StatusMotoristaEnum.ATIVO.getStatus()));
		setQtdVeiculosPendentes(veiculoService.recuperarQtdVeiculoPorStatus(
				StatusMotoristaEnum.PENDENTE.getStatus()));
		setQtdUsuariosAtivos(usuarioService.recuperarQtdUsuarioAppPorStatus(
				StatusUsuarioEnum.ATIVO.getStatus()));
		setQtdUsuariosPagamentosAtivo(usuarioService.recuperarQtdUsuarioAppComPagamento());
		setMotoristasPendentes(motoristaService.recuperarMotoristaPorStatus(
				StatusMotoristaEnum.PENDENTE.getStatus()));
		setCorridasAndamento(corridaService.recuperarCorridaPorStatus(
				StatusCorridaEnum.CORRENTE.getStatus(), TipoCorridaEnum.CORRIDA.getCodigo()));
		setVeiculosPendentes(veiculoService.recuperarVeiculoPorStatus(
				StatusVeiculoEnum.PENDENTE.getStatus()));
		setQtdLojistasAtivos(lojistaService.recuperarQtdLojistaPorStatus(
				StatusMotoristaEnum.ATIVO.getStatus()));
		setQtdLojistasPendentes(lojistaService.recuperarQtdLojistaPorStatus(
				StatusMotoristaEnum.PENDENTE.getStatus()));
		setLojistasPendentes(lojistaService.recuperarLojistaPorStatus(
				StatusMotoristaEnum.PENDENTE.getStatus()));
		setQtdBotaoPanico(corridaService.recuperarCorridasBotaoPanico());
	}
	
	public String carregarTelaCorridasRealizadas() {
		
		definirMenu(MenuEnum.CORRIDA.getMenu());
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("status", 
				StatusCorridaEnum.FINALIZADO.getStatus());
		return "corrida";
	}
	
	public String carregarTelaCorridasCorrentes() {
		
		definirMenu(MenuEnum.CORRIDA.getMenu());
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("status", 
				StatusCorridaEnum.CORRENTE.getStatus());
		return "corrida";
	}
	
	public String carregarTelaCorridasCaminho() {
		
		definirMenu(MenuEnum.CORRIDA.getMenu());
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("status", 
				StatusCorridaEnum.A_CAMINHO.getStatus());
		return "corrida";
	}
	
	public String carregarTelaCorridasBuscando() {
		
		definirMenu(MenuEnum.CORRIDA.getMenu());
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("status", 
				StatusCorridaEnum.SOLICITADO.getStatus());
		return "corrida";
	}
	
	public String carregarTelaCorridasBotaoPanico() {
		
		definirMenu(MenuEnum.CORRIDA.getMenu());
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("indicadorPanico", 
				1);
		return "corrida";
	}
	
	public String carregarTelaMotoristasAtivos() {
		
		definirMenu(MenuEnum.MOTORISTA.getMenu());
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("status", 
				StatusMotoristaEnum.ATIVO.getStatus());
		return "motorista";
	}
	
	public String carregarTelaMotoristasPendentes() {
		
		definirMenu(MenuEnum.MOTORISTA.getMenu());
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("status", 
				StatusMotoristaEnum.PENDENTE.getStatus());
		return "motorista";
	}
	
	public String carregarTelaLojistasAtivos() {
		
		definirMenu(MenuEnum.LOJISTAS.getMenu());
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("status", 
				StatusMotoristaEnum.ATIVO.getStatus());
		return "lojista";
	}
	
	public String carregarTelaLojistasPendentes() {
		
		definirMenu(MenuEnum.LOJISTAS.getMenu());
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("status", 
				StatusMotoristaEnum.PENDENTE.getStatus());
		return "lojista";
	}
	
	public String carregarTelaVeiculosAtivos() {
		
		definirMenu(MenuEnum.VEICULO.getMenu());
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("status", 
				StatusMotoristaEnum.ATIVO.getStatus());
		return "veiculo";
	}
	
	public String carregarTelaVeiculosPendentes() {
		
		definirMenu(MenuEnum.VEICULO.getMenu());
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("status", 
				StatusMotoristaEnum.PENDENTE.getStatus());
		return "veiculo";
	}
	
	public String carregarTelaUsuariosAtivos() {
		
		definirMenu(MenuEnum.PASSAGEIROS.getMenu());
		return "passageiro";
	}
	
	public String carregarTelaUsuariosCartaoAtivos() {
		
		definirMenu(MenuEnum.PASSAGEIROS.getMenu());
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("possuiCartao", 
				new Integer(1));
		return "passageiro";
	}
	
	public String detalharMotorista(Long idMotorista) {
		
		definirMenu(MenuEnum.MOTORISTA.getMenu());
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("idMotorista", 
				idMotorista);
		return "motorista";
	}
	
	public String detalharVeiculo(Long idVeiculo) {
		
		definirMenu(MenuEnum.VEICULO.getMenu());
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("idVeiculo", 
				idVeiculo);
		return "veiculo";
	}
	
	public String detalharCorrida(Long idCorrida) {
		
		definirMenu(MenuEnum.CORRIDA.getMenu());
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("idCorrida", 
				idCorrida);
		return "corrida";
	}
	
	public String detalharLojista(Long idLojista) {
		
		definirMenu(MenuEnum.LOJISTAS.getMenu());
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("idLojista", 
				idLojista);
		return "lojista";
	}

	// Métodos get/set
	public Integer getQtdMotoristasAtivos() {
		return qtdMotoristasAtivos;
	}

	public void setQtdMotoristasAtivos(Integer qtdMotoristasAtivos) {
		this.qtdMotoristasAtivos = qtdMotoristasAtivos;
	}

	public Integer getQtdMotoristasPendentes() {
		return qtdMotoristasPendentes;
	}

	public void setQtdMotoristasPendentes(Integer qtdMotoristasPendentes) {
		this.qtdMotoristasPendentes = qtdMotoristasPendentes;
	}

	public Integer getQtdUsuariosAtivos() {
		return qtdUsuariosAtivos;
	}

	public void setQtdUsuariosAtivos(Integer qtdUsuariosAtivos) {
		this.qtdUsuariosAtivos = qtdUsuariosAtivos;
	}

	public Integer getQtdCorridasRealizadas() {
		return qtdCorridasRealizadas;
	}

	public void setQtdCorridasRealizadas(Integer qtdCorridasRealizadas) {
		this.qtdCorridasRealizadas = qtdCorridasRealizadas;
	}

	public Integer getQtdCorridasCorrentes() {
		return qtdCorridasCorrentes;
	}

	public void setQtdCorridasCorrentes(Integer qtdCorridasCorrentes) {
		this.qtdCorridasCorrentes = qtdCorridasCorrentes;
	}

	public List<Motorista> getMotoristasPendentes() {
		return motoristasPendentes;
	}

	public void setMotoristasPendentes(List<Motorista> motoristasPendentes) {
		this.motoristasPendentes = motoristasPendentes;
	}

	public List<Corrida> getCorridasAndamento() {
		return corridasAndamento;
	}

	public void setCorridasAndamento(List<Corrida> corridasAndamento) {
		this.corridasAndamento = corridasAndamento;
	}

	public Integer getQtdVeiculosAtivos() {
		return qtdVeiculosAtivos;
	}

	public void setQtdVeiculosAtivos(Integer qtdVeiculosAtivos) {
		this.qtdVeiculosAtivos = qtdVeiculosAtivos;
	}

	public Integer getQtdVeiculosPendentes() {
		return qtdVeiculosPendentes;
	}

	public void setQtdVeiculosPendentes(Integer qtdVeiculosPendentes) {
		this.qtdVeiculosPendentes = qtdVeiculosPendentes;
	}

	public List<Veiculo> getVeiculosPendentes() {
		return veiculosPendentes;
	}

	public void setVeiculosPendentes(List<Veiculo> veiculosPendentes) {
		this.veiculosPendentes = veiculosPendentes;
	}

	public Integer getQtdUsuariosPagamentosAtivo() {
		return qtdUsuariosPagamentosAtivo;
	}

	public void setQtdUsuariosPagamentosAtivo(Integer qtdUsuariosPagamentosAtivo) {
		this.qtdUsuariosPagamentosAtivo = qtdUsuariosPagamentosAtivo;
	}

	public Integer getQtdLojistasAtivos() {
		return qtdLojistasAtivos;
	}

	public void setQtdLojistasAtivos(Integer qtdLojistasAtivos) {
		this.qtdLojistasAtivos = qtdLojistasAtivos;
	}

	public Integer getQtdLojistasPendentes() {
		return qtdLojistasPendentes;
	}

	public void setQtdLojistasPendentes(Integer qtdLojistasPendentes) {
		this.qtdLojistasPendentes = qtdLojistasPendentes;
	}

	public List<Lojista> getLojistasPendentes() {
		return lojistasPendentes;
	}

	public void setLojistasPendentes(List<Lojista> lojistasPendentes) {
		this.lojistasPendentes = lojistasPendentes;
	}

	public Integer getQtdBotaoPanico() {
		return qtdBotaoPanico;
	}

	public void setQtdBotaoPanico(Integer qtdBotaoPanico) {
		this.qtdBotaoPanico = qtdBotaoPanico;
	}

	public Integer getQtdCorridasBuscando() {
		return qtdCorridasBuscando;
	}

	public void setQtdCorridasBuscando(Integer qtdCorridasBuscando) {
		this.qtdCorridasBuscando = qtdCorridasBuscando;
	}

	public Integer getQtdCorridasCaminho() {
		return qtdCorridasCaminho;
	}

	public void setQtdCorridasCaminho(Integer qtdCorridasCaminho) {
		this.qtdCorridasCaminho = qtdCorridasCaminho;
	}
	
}