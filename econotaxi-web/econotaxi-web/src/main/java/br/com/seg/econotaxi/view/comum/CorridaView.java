package br.com.seg.econotaxi.view.comum;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import br.com.seg.econotaxi.enums.MenuEnum;
import br.com.seg.econotaxi.enums.StatusCorridaEnum;
import br.com.seg.econotaxi.enums.TipoCorridaEnum;
import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.model.Corrida;
import br.com.seg.econotaxi.model.Motorista;
import br.com.seg.econotaxi.model.Percurso;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.model.VisualizacaoCorrida;
import br.com.seg.econotaxi.service.CidadeService;
import br.com.seg.econotaxi.service.CorridaService;
import br.com.seg.econotaxi.service.MotoristaService;
import br.com.seg.econotaxi.service.PercursoService;
import br.com.seg.econotaxi.service.UsuarioService;
import br.com.seg.econotaxi.view.BaseView;
import br.com.seg.econotaxi.view.paginator.CorridaDataModel;

@Named
@Scope("view")
@ManagedBean(name = "corridaView")
public class CorridaView extends BaseView {

    // Constantes
	private static final long serialVersionUID = 1584745532035822519L;
	
	// Atributos
	@Autowired
	private CorridaService corridaService;
	@Autowired
	private PercursoService percursoService;
	@Autowired
	private CidadeService cidadeService;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private MotoristaService motoristaService;
	private Corrida corrida;
	private Corrida filtro;
	private CorridaDataModel corridaDataModel;
	private List<Cidade> listaCidade;
	private List<Percurso> listaPercurso;
	private List<VisualizacaoCorrida> visualizacoes;
	
	/**
	 * Método responsável pela inicialização de informações na tela.
	 */
	@PostConstruct
	public void init() {
		setListaCidade(cidadeService.recuperarTodasCidades());
		getFiltro().setMotorista(new Motorista());
		getFiltro().setUsuario(new Usuario());
		Integer status = (Integer) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("status");
		if (status != null) {
			getFiltro().setStatus(status);
		}
		Long idMotorista = (Long) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("idMotorista");
		if (idMotorista != null) {
			getFiltro().getMotorista().setId(idMotorista);
		}
		Long idPassageiro = (Long) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("idPassageiro");
		if (idPassageiro != null) {
			getFiltro().getUsuario().setId(idPassageiro);
		}
		Integer indicadorPanico = (Integer) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("indicadorPanico");
		if (indicadorPanico != null) {
			getFiltro().setIndicadorPanico(indicadorPanico);
		}
		Long idCorrida = (Long) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("idCorrida");
		if (idCorrida != null) {
			getFiltro().setId(idCorrida);
			carregarCorrida(corridaService.recuperarCorridaPorChave(idCorrida));
		}
		getFiltro().setTipo(TipoCorridaEnum.CORRIDA.getCodigo());
		setCorridaDataModel(new CorridaDataModel(corridaService, getFiltro()));
	}
	
	public void chamarModal() {
		if (getCorrida().getId() != null) {
			execJavaScript("exibirDetalhesCorrida()");
		}
	}
	
	public String detalharPassageiro() {
		
		definirMenu(MenuEnum.PASSAGEIROS.getMenu());
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("idUsuario", 
				getCorrida().getUsuario().getId());
		return "passageiro";
	}
	
	public String detalharMotorista() {
		
		definirMenu(MenuEnum.MOTORISTA.getMenu());
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("idMotorista", 
				getCorrida().getMotorista().getId());
		return "motorista";
	}
	
	public String acompanharPercurso() {
		
		return "corrida";
	}
	
	public void carregarCorrida(Corrida corrida) {
		
		setCorrida(corrida);
		if (getCorrida().getMotorista() != null && getCorrida().getMotorista().getIdUsuario() != null &&
				(getCorrida().getUsuarioMotorista() == null || getCorrida().getUsuarioMotorista().getId() == null)) {
			getCorrida().setUsuarioMotorista(usuarioService.recuperarUsuarioPorID(getCorrida().getMotorista().getIdUsuario()));
		} else {
			if (getCorrida().getUsuarioMotorista() == null) {
				getCorrida().setUsuarioMotorista(new Usuario());
			}
		}
		setListaPercurso(percursoService.recuperarPercursosCorrida(corrida));
		for (Percurso percurso : listaPercurso) {
			if (getCorrida().getLatitudesPercurso() == null || getCorrida().getLatitudesPercurso().isEmpty()) {
				getCorrida().setLatitudesPercurso(percurso.getLatitude());
				getCorrida().setLongitudesPercurso(percurso.getLongitude());
			} else {
				getCorrida().setLatitudesPercurso(getCorrida().getLatitudesPercurso() + ";" + percurso.getLatitude());
				getCorrida().setLongitudesPercurso(getCorrida().getLongitudesPercurso() + ";" + percurso.getLongitude());
			}
		}
	}
	
    public void pesquisarCorridaPorFiltro() {
    	
    	getFiltro().setTipo(TipoCorridaEnum.CORRIDA.getCodigo());
    	setCorridaDataModel(new CorridaDataModel(corridaService, filtro));
    }
    
    /**
     * Método responsável por limpar formulário e resultados da pesquisa.
     */
    public void limparFormulario() {
    	
    	setFiltro(null);
    	getFiltro().setMotorista(new Motorista());
		getFiltro().setUsuario(new Usuario());
    	setCorridaDataModel(null);
    }
    
    public void carregarCorridaVisualizacoes(Corrida corrida) {
		
		setCorrida(corridaService.recuperarCorridaPorChave(corrida.getId()));
		if (getCorrida().getMotorista() != null && getCorrida().getMotorista().getId() != null) {
			getCorrida().setMotorista(motoristaService.recuperarMotoristaPorChave(getCorrida().getMotorista().getId()));
			getCorrida().setUsuarioMotorista(usuarioService.recuperarUsuarioPorID(getCorrida().getMotorista().getIdUsuario()));
		} else {
			getCorrida().setMotorista(new Motorista());
			getCorrida().setUsuarioMotorista(new Usuario());
		}
		setVisualizacoes(corridaService.recuperarVisualizacoesCorrida(corrida));
	}

    // Métodos get/set
	public Corrida getCorrida() {
		if (corrida == null) {
			corrida = new Corrida();
		}
		return corrida;
	}

	public void setCorrida(Corrida corrida) {
		this.corrida = corrida;
	}

	public Corrida getFiltro() {
		if (filtro == null) {
			filtro = new Corrida();
			filtro.setMotorista(new Motorista());
			filtro.setUsuario(new Usuario());
		}
		return filtro;
	}

	public void setFiltro(Corrida filtro) {
		this.filtro = filtro;
	}

	public CorridaDataModel getCorridaDataModel() {
		return corridaDataModel;
	}

	public void setCorridaDataModel(CorridaDataModel corridaDataModel) {
		this.corridaDataModel = corridaDataModel;
	}

	public List<Cidade> getListaCidade() {
		return listaCidade;
	}

	public void setListaCidade(List<Cidade> listaCidade) {
		this.listaCidade = listaCidade;
	}
	
	public List<StatusCorridaEnum> getListaStatusCorrida() {
		return Arrays.asList(StatusCorridaEnum.values());
	}

	public List<Percurso> getListaPercurso() {
		return listaPercurso;
	}

	public void setListaPercurso(List<Percurso> listaPercurso) {
		this.listaPercurso = listaPercurso;
	}

	public List<VisualizacaoCorrida> getVisualizacoes() {
		return visualizacoes;
	}

	public void setVisualizacoes(List<VisualizacaoCorrida> visualizacoes) {
		this.visualizacoes = visualizacoes;
	}
	

}