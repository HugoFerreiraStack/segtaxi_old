package br.com.seg.econotaxi.view.comum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.seg.econotaxi.enums.StatusCorridaEnum;
import br.com.seg.econotaxi.model.Auditoria;
import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.model.Corrida;
import br.com.seg.econotaxi.model.Localidade;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.service.AuditoriaService;
import br.com.seg.econotaxi.service.CidadeService;
import br.com.seg.econotaxi.view.BaseView;
import br.com.seg.econotaxi.vo.CarrosVO;

@Named
@Scope("view")
@ManagedBean(name = "quadroTeleTaxiView")
public class QuadroTeleTaxiView extends BaseView {

    // Constantes
	private static final long serialVersionUID = 1584745532035822519L;
	
	// Atributos
	@Autowired
	private CidadeService cidadeService;
	@Autowired
	private AuditoriaService auditoriaService;
	private List<Cidade> listaCidade;
	private Usuario usuarioTele;
	private Cidade cidadeSelecionada;
	private Integer empresaSelecionada;
	private List<CarrosVO> carros;
	private List<Localidade> locais;
	private String localidades;
	private String motoristas;
	private List<Localidade> pas;
	private CarrosVO motorista;
	
	/**
	 * Método responsável pela inicialização de informações na tela.
	 */
	@PostConstruct
	public void init() {
		
		setUsuarioTele(recuperarUsuarioSessao());
		setListaCidade(cidadeService.recuperarTodasCidades());
	}
	
	public void exibirQuadro() {
		
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();        
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		MediaType[] mdArray = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED,
				MediaType.APPLICATION_XML, MediaType.TEXT_XML, MediaType.TEXT_HTML };
		List<MediaType> medias = Arrays.asList(mdArray);
		converter.setSupportedMediaTypes(medias);     
		messageConverters.add(converter);
		RestTemplate rest = new RestTemplate();
		rest.setMessageConverters(messageConverters);  
		
		Localidade[] pas = rest.postForObject(
				Corrida.URL + "econotaxi-rest/rest/corridaLocal/localidades", 
				getCidadeSelecionada(), Localidade[].class);
		if (pas != null && pas.length > 0) {
			setLocais(Arrays.asList(pas));
			
			Collections.sort(getLocais(), new Comparator<Localidade>() {
				@Override
				public int compare(Localidade o1, Localidade o2) {
					return o1.getNome().compareTo(o2.getNome());
				}
			});
		}
		
		CarrosVO[] carros = rest.postForObject(
				Corrida.URL + "econotaxi-rest/rest/corridaLocal/carrosOnline", 
				getCidadeSelecionada(), CarrosVO[].class);
		if (carros != null && carros.length > 0) {
			
			List<CarrosVO> cList = Arrays.asList(carros).stream()
					.filter(carro -> carro != null)
					.filter(carro -> carro.getDataPontoApoio() != null)
					.filter(carro -> carro.getTipoTeleTaxi() != null 
						&& (carro.getTipoTeleTaxi().equals(empresaSelecionada) || carro.getTipoTeleTaxi().equals(3)))
					.collect(Collectors.toList());
			setCarros(cList);
			
		}
		
		setPas(new ArrayList<Localidade>());
		for (Localidade local : getLocais()) {
			if (getCarros() != null && !getCarros().isEmpty()) {
			
				List<CarrosVO> cList = getCarros().stream()
						.filter(carro -> carro != null)
						.filter(carro -> carro.getDataPontoApoio() != null)
						.filter(carro -> carro.getIdPontoApoio() != null && carro.getIdPontoApoio().equals(local.getId()))
						.collect(Collectors.toList());
				
				if (cList != null && !cList.isEmpty()) {
					Collections.sort(cList, new Comparator<CarrosVO>() {
						@Override
						public int compare(CarrosVO o1, CarrosVO o2) {
							return o1.getDataPontoApoio().compareTo(o2.getDataPontoApoio());
						}
					});
					local.setCarros(cList);
				}
			}
			getPas().add(local);
		}
		
	}
	
	public void carregarMotorista(CarrosVO motorista) {
		setMotorista(motorista);
	}
	
	public void retirarMotoristaPA() {
		
		if (getMotorista() != null) {
			
			getMotorista().setIdPontoApoio(null);
			getMotorista().setNomePontoApoio(null);
			getMotorista().setDataPontoApoio(null);
			getMotorista().setIdCidade(cidadeSelecionada.getId());
			RestTemplate rest = new RestTemplate();
			rest.postForEntity(Corrida.URL + "econotaxi-rest/rest/corridaLocal/mudarPosicaoMotoristaLocal", 
					getMotorista(), Void.class);
			exibirQuadro();
			
			Auditoria a = new Auditoria();
			a.setClasse("PaMotorista");
			a.setDataEvento(new Date());
			a.setUsuario(recuperarUsuarioSessao());
			a.setObjeto(converterObjectParaJson(getMotorista()));
			a.setIdUsuario(a.getUsuario().getId());
			a.setTipoTransacao(3);
			a.setTransacaoViaSistema(0);
			if (FacesContext.getCurrentInstance() != null 
					&& FacesContext.getCurrentInstance().getExternalContext() != null
					&& FacesContext.getCurrentInstance().getExternalContext().getRequest() != null) {
				HttpServletRequest request = (HttpServletRequest) 
						FacesContext.getCurrentInstance().getExternalContext().getRequest();
				if (request != null) {
					String ipAddress = request.getHeader("X-FORWARDED-FOR");
					if (ipAddress == null) {
						ipAddress = request.getRemoteAddr();
						a.setIp(ipAddress);
					}
				}
			}
			auditoriaService.salvar(a);
		}
	}
	
	public void mudarMotoristaPosicao() {
		
		if (getMotorista() != null && getMotorista().getDataPontoApoio() != null) {
			
			getMotorista().setIdCidade(cidadeSelecionada.getId());
			RestTemplate rest = new RestTemplate();
			rest.postForEntity(Corrida.URL + "econotaxi-rest/rest/corridaLocal/mudarPosicaoMotoristaLocal", 
					getMotorista(), Void.class);
			exibirQuadro();
			
			Auditoria a = new Auditoria();
			a.setClasse("PaMotorista");
			a.setDataEvento(new Date());
			a.setUsuario(recuperarUsuarioSessao());
			a.setObjeto(converterObjectParaJson(getMotorista()));
			a.setIdUsuario(a.getUsuario().getId());
			a.setTipoTransacao(2);
			a.setTransacaoViaSistema(0);
			if (FacesContext.getCurrentInstance() != null 
					&& FacesContext.getCurrentInstance().getExternalContext() != null
					&& FacesContext.getCurrentInstance().getExternalContext().getRequest() != null) {
				HttpServletRequest request = (HttpServletRequest) 
						FacesContext.getCurrentInstance().getExternalContext().getRequest();
				if (request != null) {
					String ipAddress = request.getHeader("X-FORWARDED-FOR");
					if (ipAddress == null) {
						ipAddress = request.getRemoteAddr();
						a.setIp(ipAddress);
					}
				}
			}
			auditoriaService.salvar(a);
		} else {
			addMsgErro("A data e horário que entrou no PA são obrigatórios.");
		}
	}
	
	/**
	 * TODO javadoc
	 * 
	 * @param entidade
	 * @return
	 */
	private String converterObjectParaJson(Object entidade) {
		
		String json = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			json = mapper.writeValueAsString(entidade);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
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

	public List<CarrosVO> getCarros() {
		return carros;
	}

	public void setCarros(List<CarrosVO> carros) {
		this.carros = carros;
	}

	public List<Localidade> getLocais() {
		return locais;
	}

	public void setLocais(List<Localidade> locais) {
		this.locais = locais;
	}

	public String getLocalidades() {
		return localidades;
	}

	public void setLocalidades(String localidades) {
		this.localidades = localidades;
	}

	public String getMotoristas() {
		return motoristas;
	}

	public void setMotoristas(String motoristas) {
		this.motoristas = motoristas;
	}

	public List<Localidade> getPas() {
		return pas;
	}

	public void setPas(List<Localidade> pas) {
		this.pas = pas;
	}

	public CarrosVO getMotorista() {
		if (motorista == null) {
			motorista = new CarrosVO();
		}
		return motorista;
	}

	public void setMotorista(CarrosVO motorista) {
		this.motorista = motorista;
	}
	
}