package br.com.seg.econotaxi.view.comum;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import br.com.seg.econotaxi.enums.MenuEnum;
import br.com.seg.econotaxi.enums.StatusVeiculoEnum;
import br.com.seg.econotaxi.enums.StatusVeiculoSegEnum;
import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.model.Motorista;
import br.com.seg.econotaxi.model.Veiculo;
import br.com.seg.econotaxi.service.CidadeService;
import br.com.seg.econotaxi.service.VeiculoService;
import br.com.seg.econotaxi.view.BaseView;
import br.com.seg.econotaxi.view.paginator.VeiculoDataModel;
import br.com.seg.econotaxi.vo.VeiculoSegVO;

@Named
@Scope("view")
@ManagedBean(name = "veiculoView")
public class VeiculoView extends BaseView {

    // Constantes
	private static final long serialVersionUID = 1584745532035822519L;
	
	// Atributos
	@Autowired
	private VeiculoService veiculoService;
	@Autowired
	private CidadeService cidadeService;
	private Veiculo veiculo;
	private Veiculo veiculoAlterar;
	private Veiculo filtro;
	private VeiculoDataModel veiculoDataModel;
	private List<Cidade> listaCidade;
	private List<Motorista> listaMotorista;
	private String motivoBloqueio;
	private String motivoDesautorizacao;
	
	/**
	 * Método responsável pela inicialização de informações na tela.
	 */
	@PostConstruct
	public void init() {
		setListaCidade(cidadeService.recuperarTodasCidades());
		getFiltro().setMotorista(new Motorista());
		Integer status = (Integer) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("status");
		if (status != null) {
			getFiltro().setStatus(status);
		}
		Long idVeiculo = (Long) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("idVeiculo");
		if (idVeiculo != null) {
			getFiltro().setId(idVeiculo);
			carregarVeiculo(veiculoService.recuperarVeiculoPorChave(idVeiculo));
		}
		Long idMotorista = (Long) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("idMotorista");
		if (idMotorista != null) {
			getFiltro().getMotorista().setId(idMotorista);
		}
		setVeiculoDataModel(new VeiculoDataModel(veiculoService, getFiltro()));
	}
	
	public void verificarRastreadorSEG() {
		
		if (getVeiculo() != null && getVeiculo().getId() != null) {
			RestTemplate rest = new RestTemplate();
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			
			MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
			map.add("chave", "9179b41f6ded470b82e33912b71c3b3c");
			map.add("placa", getVeiculo().getPlaca().toUpperCase());
			
			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
			
			ResponseEntity<String> response = rest.postForEntity("http://sistema.segrastreadores.com.br/webhook/status_taxi", request , String.class );
			JsonParser parser = new JsonParser();
			JsonObject o = parser.parse(response.getBody()).getAsJsonObject();
			
			VeiculoSegVO veic = null;
			if (o.get("status") != null && o.get("codigo") != null 
					&& o.get("retorno") != null) {
				veic = new VeiculoSegVO();
				veic.setCodigo(o.get("codigo").getAsString());
				veic.setStatus(o.get("status").getAsString());
				veic.setRetorno(o.get("retorno").getAsString());
			}
			if (veic != null && veic.getCodigo() != null
					&& !veic.getCodigo().isEmpty()) {
				
				if (veic.getCodigo().equals(StatusVeiculoSegEnum.PLACA_NAO_EXISTENTE.getStatus())
						|| veic.getCodigo().equals(StatusVeiculoSegEnum.PLACA_ENCONTRADA_POSICAO_DESATUALIZADA.getStatus())
						|| veic.getCodigo().equals(StatusVeiculoSegEnum.PLACA_ENCONTRADA_POSICAO_NAO.getStatus())
						|| veic.getCodigo().equals(StatusVeiculoSegEnum.PROBLEMA_FINANCEIRO.getStatus())) {
					
					StatusVeiculoSegEnum enumVeic = StatusVeiculoSegEnum.valueOfStatus(veic.getCodigo());
					if (veic.getCodigo().equals(StatusVeiculoSegEnum.PROBLEMA_FINANCEIRO.getStatus())) {
						veiculo.setMsgErroRastreador(enumVeic.getDescricao() + veic.getRetorno());
					} else {
						veiculo.setMsgErroRastreador(enumVeic.getDescricao());
					}
					veiculo.setIndicadorRastreadorSeg(2);
					veiculoService.salvarVeiculo(veiculo);
				} else {
					veiculo.setMsgErroRastreador(null);
					veiculo.setIndicadorRastreadorSeg(1);
					veiculoService.salvarVeiculo(veiculo);
				}
			}
		}
	}
	
	public void chamarModal() {
		if (getVeiculo().getId() != null) {
			execJavaScript("exibirDetalhesVeiculo()");
		}
	}
	
	public void carregarVeiculo(Veiculo veiculo) {
		setVeiculo(veiculo);
	}
	
	public void carregarVeiculoAlterar(Veiculo veiculo) {
		setVeiculoAlterar(veiculo);
	}
	
	public void atualizarLocalizacaoVeiculo() {
		
		if (getVeiculo().getId() != null) {
			setVeiculo(veiculoService.recuperarVeiculoPorChave(veiculo.getId()));
		}
	}
	
	public void desautorizarVeiculo() {
		
		if (motivoDesautorizacao == null || motivoDesautorizacao.isEmpty()) {
			addMsgErro("O motivo da desautorização deve ser preenchido.");
		} else {
			definirMenu(MenuEnum.VEICULO.getMenu());
			veiculo = veiculoService.recuperarVeiculoPorChave(veiculo.getId());
			veiculo.setStatus(StatusVeiculoEnum.NAO_AUTORIZADO.getStatus());
			veiculo.setMotivoDesautorizacao(motivoDesautorizacao);
			veiculoService.salvarVeiculo(veiculo);
			addMsgSuccess("Veículo desautorizado com sucesso!");
		}
		setVeiculo(null);
	}
	
	public void autorizarVeiculo() {
    	
    	definirMenu(MenuEnum.VEICULO.getMenu());
    	veiculo = veiculoService.recuperarVeiculoPorChave(veiculo.getId());
    	veiculo.setStatus(StatusVeiculoEnum.ATIVO.getStatus());
    	veiculoService.salvarVeiculo(veiculo);
    	addMsgSuccess("Veículo autorizado com sucesso!");
    	setVeiculo(null);
    }
    
    public void bloquearVeiculo() {
    	
    	if (motivoBloqueio == null || motivoBloqueio.isEmpty()) {
    		addMsgErro("O motivo do bloqueio deve ser preenchido.");
    	} else {
    		definirMenu(MenuEnum.VEICULO.getMenu());
    		veiculo = veiculoService.recuperarVeiculoPorChave(veiculo.getId());
    		veiculo.setStatus(StatusVeiculoEnum.BLOQUEADO_TEMPORARIAMENTE.getStatus());
    		veiculo.setMotivoBloqueio(motivoBloqueio);
    		veiculoService.salvarVeiculo(veiculo);
    		addMsgSuccess("Veículo bloqueado com sucesso!");
    	}
    	setVeiculo(null);
    }
    
    public void desbloquearVeiculo() {
    	
    	definirMenu(MenuEnum.VEICULO.getMenu());
    	veiculo = veiculoService.recuperarVeiculoPorChave(veiculo.getId());
    	veiculo.setStatus(StatusVeiculoEnum.ATIVO.getStatus());
    	veiculoService.salvarVeiculo(veiculo);
    	addMsgSuccess("Veículo bloqueado com sucesso!");
    	setVeiculo(null);
    }
    
    public String detalharMotoristas() {
    	
    	definirMenu(MenuEnum.MOTORISTA.getMenu());
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("idVeiculo", 
				veiculo.getId());
    	return "motorista";
    }
    
    public String acompanharCorridas(Veiculo veiculo) {
    	
    	definirMenu(MenuEnum.CORRIDA.getMenu());
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("idVeiculo", 
				veiculo.getId());
    	return "corrida";
    }
    
    public void alterarVeiculo() {
    	
    	definirMenu(MenuEnum.VEICULO.getMenu());
    	Veiculo v = veiculoService.recuperarVeiculoPorChave(veiculoAlterar.getId());
    	Boolean verificaAlteracao = Boolean.FALSE;
    	Boolean verificaObrigatorio = Boolean.FALSE;
    	
    	if (veiculoAlterar.getPlaca() == null || veiculoAlterar.getPlaca().isEmpty()) {
    		verificaObrigatorio = Boolean.TRUE;
    	}
    	if (veiculoAlterar.getModelo() == null || veiculoAlterar.getModelo().isEmpty()) {
    		verificaObrigatorio = Boolean.TRUE;
    	}
    	if (veiculoAlterar.getIndicadorAdaptado() == null) {
    		verificaObrigatorio = Boolean.TRUE;
    	}
    	if (veiculoAlterar.getIndicadorBicicleta() == null) {
    		verificaObrigatorio = Boolean.TRUE;
    	}
    	
    	if (v.getPlaca() != null && !v.getPlaca().equals(veiculoAlterar.getPlaca())) {
    		verificaAlteracao = Boolean.TRUE;
    		v.setPlaca(veiculoAlterar.getPlaca());
    	}
    	if (v.getModelo() != null && !v.getModelo().equals(veiculoAlterar.getModelo())) {
    		verificaAlteracao = Boolean.TRUE;
    		v.setModelo(veiculoAlterar.getModelo());
    	}
    	if (v.getMarca() != null && !v.getMarca().equals(veiculoAlterar.getMarca())) {
    		verificaAlteracao = Boolean.TRUE;
    		v.setMarca(veiculoAlterar.getMarca());
    	}
    	if (v.getIndicadorAdaptado() != null && !v.getIndicadorAdaptado().equals(veiculoAlterar.getIndicadorAdaptado())
    			|| (v.getIndicadorAdaptado() == null && veiculoAlterar.getIndicadorAdaptado() != null)) {
    		verificaAlteracao = Boolean.TRUE;
    		v.setIndicadorAdaptado(veiculoAlterar.getIndicadorAdaptado());
    	}
    	if (v.getIndicadorBicicleta() != null && !v.getIndicadorBicicleta().equals(veiculoAlterar.getIndicadorBicicleta())
    			|| (v.getIndicadorBicicleta() == null && veiculoAlterar.getIndicadorBicicleta() != null)) {
    		verificaAlteracao = Boolean.TRUE;
    		v.setIndicadorBicicleta(veiculoAlterar.getIndicadorBicicleta());
    	}
    	if (v.getIndicadorCadeirinha() != null && !v.getIndicadorCadeirinha().equals(veiculoAlterar.getIndicadorCadeirinha())
    			|| (v.getIndicadorCadeirinha() == null && veiculoAlterar.getIndicadorCadeirinha() != null)) {
    		verificaAlteracao = Boolean.TRUE;
    		v.setIndicadorCadeirinha(veiculoAlterar.getIndicadorCadeirinha());
    	}
    	if (v.getUnidade() != null && !v.getUnidade().equals(veiculoAlterar.getUnidade())
    			|| (v.getUnidade() == null && veiculoAlterar.getUnidade() != null)) {
    		verificaAlteracao = Boolean.TRUE;
    		v.setUnidade(veiculoAlterar.getUnidade());
    	}
    	if (verificaAlteracao) {
    		if (!verificaObrigatorio) {
    			veiculoService.salvarVeiculo(v);
    			addMsgSuccess("Veículo alterado com sucesso!");
    		} else {
    			addMsgErro("Os campos 'Placa', 'Modelo', 'Adaptado p/ Cadeirantes' e 'Suporte p/ Bicicleta' são obrigatórios.");
    		}
    	} else {
    		addMsgSuccess("Nenhum dado do Veículo foi alterado de fato.");
    	}
    	setVeiculoAlterar(null);
    	pesquisarVeiculoPorFiltro();
    }
	
    public void pesquisarVeiculoPorFiltro() {
    	
    	setVeiculoDataModel(new VeiculoDataModel(veiculoService, filtro));
    }
    
    /**
     * Método responsável por limpar formulário e resultados da pesquisa.
     */
    public void limparFormulario() {
    	
    	setFiltro(null);
    	getFiltro().setMotorista(new Motorista());
    	setVeiculoDataModel(null);
    }

    // Métodos get/set
	public VeiculoDataModel getVeiculoDataModel() {
		return veiculoDataModel;
	}

	public void setVeiculoDataModel(VeiculoDataModel veiculoDataModel) {
		this.veiculoDataModel = veiculoDataModel;
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
	
	public Veiculo getVeiculoAlterar() {
		if (veiculoAlterar == null) {
			veiculoAlterar = new Veiculo();
		}
		return veiculoAlterar;
	}

	public void setVeiculoAlterar(Veiculo veiculoAlterar) {
		this.veiculoAlterar = veiculoAlterar;
	}

	public Veiculo getFiltro() {
		if (filtro == null) {
			filtro = new Veiculo();
		}
		return filtro;
	}

	public void setFiltro(Veiculo filtro) {
		this.filtro = filtro;
	}

	public List<Cidade> getListaCidade() {
		return listaCidade;
	}

	public void setListaCidade(List<Cidade> listaCidade) {
		this.listaCidade = listaCidade;
	}

	public List<Motorista> getListaMotorista() {
		return listaMotorista;
	}

	public void setListaMotorista(List<Motorista> listaMotorista) {
		this.listaMotorista = listaMotorista;
	}
	
	public List<StatusVeiculoEnum> getListaStatusVeiculo() {
		return Arrays.asList(StatusVeiculoEnum.values());
	}

	public String getMotivoBloqueio() {
		return motivoBloqueio;
	}

	public void setMotivoBloqueio(String motivoBloqueio) {
		this.motivoBloqueio = motivoBloqueio;
	}

	public String getMotivoDesautorizacao() {
		return motivoDesautorizacao;
	}

	public void setMotivoDesautorizacao(String motivoDesautorizacao) {
		this.motivoDesautorizacao = motivoDesautorizacao;
	}
	
}