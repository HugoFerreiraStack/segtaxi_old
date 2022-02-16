package br.com.seg.econotaxi.view.comum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import br.com.seg.econotaxi.enums.MenuEnum;
import br.com.seg.econotaxi.enums.TipoUsuarioEnum;
import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.model.Corrida;
import br.com.seg.econotaxi.model.Notificacao;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.model.UsuarioNotificacao;
import br.com.seg.econotaxi.service.CidadeService;
import br.com.seg.econotaxi.service.NotificacaoService;
import br.com.seg.econotaxi.service.UsuarioNotificacaoService;
import br.com.seg.econotaxi.service.UsuarioService;
import br.com.seg.econotaxi.view.BaseView;
import br.com.seg.econotaxi.view.paginator.NotificacaoDataModel;

@Named
@Scope("view")
@ManagedBean(name = "notificacaoView")
public class NotificacaoView extends BaseView {

    // Constantes
	private static final long serialVersionUID = -6918164650365605188L;
	
	// Atributos
	@Autowired
	private NotificacaoService notificacaoService;
	@Autowired
	private UsuarioNotificacaoService usuarioNotificacaoService;
	@Autowired
	private CidadeService cidadeService; 
	@Autowired
	private UsuarioService usuarioService; 
	private Notificacao notificacao;
	private Notificacao filtro;
	private NotificacaoDataModel notificacaoDataModel;
	private Integer publico;
	private List<Cidade> cidades;
	private Cidade cidade;
	private List<Usuario> usuarios;
	private Usuario usuario;
	private Usuario usuarioTele;
	
	/**
	 * Método responsável pela inicialização de informações na tela.
	 */
	@PostConstruct
	public void init() {
		setUsuarioTele(recuperarUsuarioSessao());
		setNotificacaoDataModel(new NotificacaoDataModel(notificacaoService, getFiltro()));
	}
	
    public void carregarNotificacao(Notificacao notificacao) {
    	setNotificacao(notificacaoService.recuperarNotificacaoPorChave(notificacao.getId()));
    }
    
    public void salvarNotificacao() {
    	
    	definirMenu(MenuEnum.NOTIFICACOES.getMenu());
    	if (getPublico() == null) {
    		addMsgErro("O Público da notificação é obrigatório.");
    	} else {
    		
    		if (getPublico().equals(5) || getPublico().equals(6) || getPublico().equals(7)
    				|| getPublico().equals(10) || getPublico().equals(11)) {
    			
    			if (getUsuario() == null) {
    				addMsgErro("O Usuário da notificação é obrigatório.");
    			} else {
    				
    				notificacao.setDataNotificacao(new Date());
        			notificacao.setPublico(getPublico());
        			notificacao.setDataEnvio(new Date());
        			notificacaoService.salvarNotificacao(notificacao);
    				
    				UsuarioNotificacao usuarioNotificacao = new UsuarioNotificacao();
    				usuarioNotificacao.setIdNotificacao(notificacao.getId());
    				usuarioNotificacao.setIdUsuario(usuario.getId());
    				usuarioNotificacao.setDataNotificacao(notificacao.getDataNotificacao());
    				usuarioNotificacao.setTexto(notificacao.getTexto());
    				usuarioNotificacaoService.salvar(usuarioNotificacao);
    				setNotificacao(null);
    				addMsgSuccess("Notificação salva com sucesso! Em breve ela será enviada ao usuário específico.");
    				
    				List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();        
    				MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
    				MediaType[] mdArray = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED,
    						MediaType.APPLICATION_XML, MediaType.TEXT_XML, MediaType.TEXT_HTML };
    				List<MediaType> medias = Arrays.asList(mdArray);
    				converter.setSupportedMediaTypes(medias);     
    				messageConverters.add(converter);
    				RestTemplate rest = new RestTemplate();
    				rest.setMessageConverters(messageConverters);
    				
					RestTemplate restNotificacao = new RestTemplate();
					restNotificacao.postForEntity(Corrida.URL + "econotaxi-rest/rest/corridaLocal/adicionarNotificacao", 
							usuarioNotificacao, Void.class);
    				
    			}
    			
    		} else {
    			
    			notificacao.setDataNotificacao(new Date());
    			notificacao.setPublico(getPublico());
    			notificacaoService.salvarNotificacao(notificacao);
    			addMsgSuccess("Notificação salva com sucesso! Em breve ela será enviada ao(s) usuário(s).");
    			setNotificacao(null);
    		}
    	}
    }
    
    public void carregarInformacoesTipoNotificacao() {
    	
    	setUsuarios(null);
    	setUsuario(null);
    	setCidade(null);
    	setCidades(null);
    	if (getPublico() != null) {
    		if (getPublico().equals(5) || getPublico().equals(6) || getPublico().equals(7) 
    				|| getPublico().equals(10) || getPublico().equals(11)) {
    			setCidades(cidadeService.recuperarTodasCidades());
    			execJavaScript("esconderLoading()");
    		}
    	}
    }
    
    public void carregarUsuariosPorCidade() {
    	
    	setUsuario(null);
    	if (getPublico() != null && getCidade() != null && getCidade().getId() != null) {
    		if (getPublico().equals(5)) {
    			setUsuarios(usuarioService.recuperarUsuariosPorTipoCidade(
    					TipoUsuarioEnum.MOTORISTA.getCodigo(), getCidade()));
    		} else if (getPublico().equals(6)) {
    			setUsuarios(usuarioService.recuperarUsuariosPorTipoCidade(
    					TipoUsuarioEnum.LOJISTA.getCodigo(), getCidade()));
    		} else if (getPublico().equals(7)) {
    			setUsuarios(usuarioService.recuperarUsuariosPorTipoCidade(
    					TipoUsuarioEnum.CLIENTE.getCodigo(), getCidade()));
    		} else if (getPublico().equals(10)) {
    			setUsuarios(usuarioService.recuperarUsuariosPorTipoCidadeRadio(
    					TipoUsuarioEnum.MOTORISTA.getCodigo(), getCidade(), 2));
    		} else if (getPublico().equals(11)) {
    			setUsuarios(usuarioService.recuperarUsuariosPorTipoCidadeRadio(
    					TipoUsuarioEnum.MOTORISTA.getCodigo(), getCidade(), 1));
    		}
    	}
    }
    
    public void pesquisarNotificacaoPorFiltro() {
    	
    	setNotificacaoDataModel(new NotificacaoDataModel(notificacaoService, filtro));
    }
    
    public void excluirNotificacao() {
    	notificacaoService.excluirNotificacao(this.notificacao);
    	addMsgSuccess("Notificação excluída com sucesso.");
    }
    
    /**
     * Método responsável por limpar formulário e resultados da pesquisa.
     */
    public void limparFormulario() {
    	
    	setFiltro(null);
    	setNotificacaoDataModel(null);
    }

    // Métodos get/set
	public Notificacao getNotificacao() {
		if (notificacao == null) {
			notificacao = new Notificacao();
		}
		return notificacao;
	}
	public void setNotificacao(Notificacao notificacao) {
		this.notificacao = notificacao;
	}
	public Notificacao getFiltro() {
		if (filtro == null) {
			filtro = new Notificacao();
		}
		return filtro;
	}
	public void setFiltro(Notificacao filtro) {
		this.filtro = filtro;
	}
	public NotificacaoDataModel getNotificacaoDataModel() {
		return notificacaoDataModel;
	}
	public void setNotificacaoDataModel(NotificacaoDataModel notificacaoDataModel) {
		this.notificacaoDataModel = notificacaoDataModel;
	}
	public Integer getPublico() {
		return publico;
	}
	public void setPublico(Integer publico) {
		this.publico = publico;
	}
	public List<Cidade> getCidades() {
		return cidades;
	}
	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}
	public Cidade getCidade() {
		return cidade;
	}
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	public List<Usuario> getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Usuario getUsuarioTele() {
		return usuarioTele;
	}
	public void setUsuarioTele(Usuario usuarioTele) {
		this.usuarioTele = usuarioTele;
	}

}