package br.com.seg.econotaxi.view.comum;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import br.com.seg.econotaxi.enums.StatusCorridaEnum;
import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.model.Corrida;
import br.com.seg.econotaxi.model.Localidade;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.service.CidadeService;
import br.com.seg.econotaxi.view.BaseView;
import br.com.seg.econotaxi.vo.CarrosVO;

@Named
@Scope("view")
@ManagedBean(name = "mapaTeleTaxiView")
public class MapaTeleTaxiView extends BaseView {

    // Constantes
	private static final long serialVersionUID = 1584745532035822519L;
	
	// Atributos
	@Autowired
	private CidadeService cidadeService;
	private List<Cidade> listaCidade;
	private Usuario usuarioTele;
	private Cidade cidadeSelecionada;
	private Integer empresaSelecionada;
	private List<CarrosVO> carros;
	private List<Localidade> locais;
	private List<Localidade> regioes;
	private String localidades;
	private String areas;
	private String motoristas;
	
	/**
	 * Método responsável pela inicialização de informações na tela.
	 */
	@PostConstruct
	public void init() {
		
		setUsuarioTele(recuperarUsuarioSessao());
		setListaCidade(cidadeService.recuperarTodasCidades());
	}
	
	public void exibirMapa() {
		
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
			if (getLocais() != null && !getLocais().isEmpty()) {
				for (Localidade localidade : getLocais()) {
					if (getLocalidades() == null || getLocalidades().isEmpty()) {
						setLocalidades(
								localidade.getNome() 
									+ "$$$" + localidade.getEndereco() 
									+ "$$$" + localidade.getCoordenadas());
					} else {
						setLocalidades(getLocalidades() + "%%%" + 
								localidade.getNome() 
									+ "$$$" + localidade.getEndereco() 
									+ "$$$" + localidade.getCoordenadas());
					}
				}
			}
		}
		
		Localidade[] regioes = rest.postForObject(
				Corrida.URL + "econotaxi-rest/rest/corridaLocal/regioes", 
				getCidadeSelecionada(), Localidade[].class);
		
		if (regioes != null && regioes.length > 0) {
			setRegioes(Arrays.asList(regioes));
			if (getRegioes() != null && !getRegioes().isEmpty()) {
				for (Localidade localidade : getRegioes()) {
					if (getAreas() == null || getAreas().isEmpty()) {
						setAreas(
								localidade.getNome()
									+ "$$$" + localidade.getEndereco() 
									+ "$$$" + localidade.getCoordenadas());
					} else {
						setAreas(getAreas() + "%%%" + 
								localidade.getNome() 
									+ "$$$" + localidade.getEndereco() 
									+ "$$$" + localidade.getCoordenadas());
					}
				}
			}
		}
		
		CarrosVO[] carros = rest.postForObject(
				Corrida.URL + "econotaxi-rest/rest/corridaLocal/carrosOnline", 
				getCidadeSelecionada(), CarrosVO[].class);
		if (carros != null && carros.length > 0) {
			
			List<CarrosVO> cList = Arrays.asList(carros).stream()
					.filter(carro -> carro != null)
					.filter(carro -> carro.getTipoTeleTaxi() != null 
						&& (carro.getTipoTeleTaxi().equals(empresaSelecionada) || carro.getTipoTeleTaxi().equals(3)))
					.collect(Collectors.toList());
			setCarros(cList);
			
			if (getCarros() != null && !getCarros().isEmpty()) {
				for (CarrosVO car : getCarros()) {
					if (car.getNomeMotorista() != null && !car.getNomeMotorista().isEmpty()) {
						if (getMotoristas() == null || getMotoristas().isEmpty()) {
							setMotoristas(
									car.getNomeMotorista() + (car.getUnidade() != null ? " ("  + car.getUnidade() + ") " : "")
										+ (car.getPlaca() != null ? " ("  + car.getPlaca() + ") " : "")
									+ "$$$" + car.getLatitude() 
									+ "$$$" + car.getLongitude() 
									+ "$$$" + car.getSexo()
									+ "$$$" + new SimpleDateFormat("dd/MM/YYYY 'às' HH:mm:ss").format(car.getDataUltimaPosicao())
									+ "$$$" + (car.getDataPontoApoio() != null ? new SimpleDateFormat("dd/MM/YYYY 'às' HH:mm:ss").format(car.getDataPontoApoio()) : "")
									+ "$$$" + car.getCelular()
									+ "$$$" + (car.getNomePontoApoio() != null ? car.getNomePontoApoio() : ""));
						} else {
							setMotoristas(getMotoristas() + "%%%" +
									car.getNomeMotorista() + (car.getUnidade() != null ? " ("  + car.getUnidade() + ") " : "")
										+ (car.getPlaca() != null ? " ("  + car.getPlaca() + ") " : "")
							+ "$$$" + car.getLatitude() 
							+ "$$$" + car.getLongitude() 
							+ "$$$" + car.getSexo()
							+ "$$$" + new SimpleDateFormat("dd/MM/YYYY 'às' HH:mm:ss").format(car.getDataUltimaPosicao())
							+ "$$$" + (car.getDataPontoApoio() != null ? new SimpleDateFormat("dd/MM/YYYY 'às' HH:mm:ss").format(car.getDataPontoApoio()) : "")
							+ "$$$" + car.getCelular()
							+ "$$$" + (car.getNomePontoApoio() != null ? car.getNomePontoApoio() : ""));
						}
					}
				}
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

	public List<Localidade> getRegioes() {
		return regioes;
	}

	public void setRegioes(List<Localidade> regioes) {
		this.regioes = regioes;
	}

	public String getAreas() {
		return areas;
	}

	public void setAreas(String areas) {
		this.areas = areas;
	}
	
}