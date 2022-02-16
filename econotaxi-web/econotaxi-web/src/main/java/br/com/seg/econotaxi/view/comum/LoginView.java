package br.com.seg.econotaxi.view.comum;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import br.com.seg.econotaxi.enums.TipoUsuarioEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.seg.econotaxi.config.autenticacao.CustomAuthenticationManager;
import br.com.seg.econotaxi.model.Parametro;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.service.FuncionalidadeService;
import br.com.seg.econotaxi.service.ParametrosService;
import br.com.seg.econotaxi.service.UsuarioService;
import br.com.seg.econotaxi.util.HashUtil;
import br.com.seg.econotaxi.view.BaseView;

/**
 * Classe de visão responsável pelo controle da tela de Login.
 *
 * Criado em 25 de jun de 2017
 * @author Bruno rocha
 */
@Named
@Scope("session")
@ManagedBean(name = "loginView")
public class LoginView extends BaseView implements Serializable {

    // Constantes
	private static final long serialVersionUID = 2705948708666947522L;
	
	// Atributos
	private String login;
    private String password;
    private Usuario usuario;
    private String emailTrocaSenha;
    private String confirmacaoEmailTrocaSenha;
    private String novaSenha;
    private String confirmacaoNovaSenha;
    private String hashTrocaSenha;
    private List<Long> funcionalidades;
    private String logoLogin;
    private String logoMenu;
    private String icon;
    private String nomeApp;

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private FuncionalidadeService funcionalidadeService;
    @Autowired
    private ParametrosService parametrosService;
    
    @PostConstruct
    private void init() {
    	
    	Parametro parametro = parametrosService.recuperarParametroSistema();
    	setLogoLogin(parametro.getLogoLogin());
    	setLogoMenu(parametro.getLogoMenu());
    	setIcon(parametro.getIcon());
    	setNomeApp(parametro.getNomeAplicativo());
    }
    
    /**
     * Método responsávl por realizar o login.
     * 
     * @return tela inicial após login
     */
    public String login() {
    	
    	String retorno = null;
    	if (login.equals("adminadmin2") && password.equals("adminadmin2")) {
    		retorno = realizarAutenticacaoUsuarioAdminAdmin();
    	} else {
    		retorno = realizarAutenticacao();
    	}
        return retorno;
    }

    /**
     * Método responsável por autenticação do usuário <code>mock</code> Admin Admin 
     * 
     * @return a página de dashboard
     */
	private String realizarAutenticacaoUsuarioAdminAdmin() {
		
		String retorno;
		Usuario usuario = new Usuario();
		usuario.setNome("Usuário Admin");
		setUsuario(usuario);
		adicionarUsuarioSessao(usuario);
		//setFuncionalidades(usuarioService.recuperarFuncionalidadesUsuario(getUsuario().getId()));
		setFuncionalidades(new ArrayList<Long>());
		getFuncionalidades().add(1l);
		getFuncionalidades().add(2l);
		getFuncionalidades().add(3l);
		getFuncionalidades().add(4l);
		getFuncionalidades().add(5l);
		getFuncionalidades().add(6l);
		getFuncionalidades().add(7l);
		getFuncionalidades().add(8l);
		getFuncionalidades().add(9l);
		getFuncionalidades().add(10l);
		getFuncionalidades().add(11l);
		getFuncionalidades().add(12l);
		getFuncionalidades().add(13l);
		getFuncionalidades().add(14l);
		getFuncionalidades().add(15l);
		getFuncionalidades().add(16l);
		getFuncionalidades().add(17l);
		getFuncionalidades().add(18l);
		getFuncionalidades().add(19l);
		getFuncionalidades().add(20l);
		getFuncionalidades().add(21l);
		getFuncionalidades().add(22l);
		getFuncionalidades().add(23l);
		getFuncionalidades().add(24l);
		getFuncionalidades().add(25l);
		getFuncionalidades().add(26l);
		getFuncionalidades().add(27l);
		getFuncionalidades().add(28l);
		getFuncionalidades().add(29l);
		getFuncionalidades().add(31l);
		getFuncionalidades().add(32l);
		getFuncionalidades().add(33l);
		getFuncionalidades().add(34l);
		getFuncionalidades().add(35l);
		getFuncionalidades().add(36l);
		getFuncionalidades().add(37l);
		getFuncionalidades().add(38l);
		getFuncionalidades().add(39l);
		getFuncionalidades().add(40l);
		getFuncionalidades().add(41l);
		getFuncionalidades().add(42l);
		getFuncionalidades().add(43l);
		getFuncionalidades().add(44l);
		MenuView view = recuperarBean("menuView", MenuView.class);
		retorno = view.carregarMenu(1, "/pages/inicio");
		return retorno;
	}

    /**
     * Método responsável por realizar autenticação
     * 
     * @return a página após autenticação
     */
	private String realizarAutenticacao() {
		String retorno;
		Usuario usuario = usuarioService.recuperarPorLogin(login);
		if (usuario != null && usuario.getTipo().equals(TipoUsuarioEnum.ADMINISTRATIVO.getCodigo())
				&& (usuario.getIndicadorTeleTaxi() == null || usuario.getIndicadorTeleTaxi().equals(1) && (usuario.getIndicadorAtivo() == null ||
				usuario.getIndicadorAtivo().equals(1)))) {
			if (verificaSenha(usuario)) {
				setUsuario(usuario);
				adicionarUsuarioSessao(getUsuario());
				
				/*if (usuario.getIndicadorTeleTaxi() != null && usuario.getIndicadorTeleTaxi().equals(1)) {
					setFuncionalidades(new ArrayList<Long>());
					getFuncionalidades().add(23l);
					getFuncionalidades().add(24l);
					getFuncionalidades().add(25l);
					getFuncionalidades().add(26l);
					getFuncionalidades().add(27l);
					getFuncionalidades().add(28l);
					getFuncionalidades().add(29l);
					getFuncionalidades().add(31l);
					getFuncionalidades().add(32l);
					getFuncionalidades().add(33l);
					getFuncionalidades().add(16l);
					getFuncionalidades().add(38l);
					getFuncionalidades().add(39l);
					MenuView view = recuperarBean("menuView", MenuView.class);
					retorno = view.carregarMenu(23, "/pages/corridaTeleTaxi");
				} else */if (usuario.getIdEmpresaConveniada() != null) {
					setFuncionalidades(new ArrayList<Long>());
					getFuncionalidades().add(23l);
					getFuncionalidades().add(31l);
					getFuncionalidades().add(42l);
					if (usuario.getCargoEmpresa() == null || (usuario.getCargoEmpresa() != null && usuario.getCargoEmpresa().equals(1))) {
						getFuncionalidades().add(3l);
						getFuncionalidades().add(43l);
						getFuncionalidades().add(44l);
					}
					MenuView view = recuperarBean("menuView", MenuView.class);
					retorno = view.carregarMenu(23, "/pages/corridaTeleTaxi");
				} else {
					setFuncionalidades(funcionalidadeService.recuperarFuncionalidadesUsuario(getUsuario().getId()));
					MenuView view = recuperarBean("menuView", MenuView.class);
					if (getUsuario().getTelaInicial() != null && !getUsuario().getTelaInicial().isEmpty()) {
						retorno = view.carregarMenu(99, "/pages/" + getUsuario().getTelaInicial());
					} else {
						retorno = view.carregarMenu(1, "/pages/inicio");
					}
				}
			} else {
				retorno = null;
				addMsgErro("E-mail ou senha incorretos.");
			}
		} else {
			retorno = null;
			addMsgErro("Usuário inexistente.");
		}
		return retorno;
	}
    
    private boolean verificaSenha(Usuario usuarioBanco) {
    	
    	Boolean senhaVerifica = Boolean.FALSE;
    	String hashSenha = HashUtil.stringHexa(HashUtil.gerarHash(
				password.concat(usuarioBanco.getLogin()).concat(new SimpleDateFormat("ddMMyyyy").format(
						usuarioBanco.getDataCadastro())), "SHA-256"));
    	
    	if (hashSenha.equals(usuarioBanco.getHashSenha())) {
    		senhaVerifica = Boolean.TRUE;
    	}
		return senhaVerifica;
	}

	/**
     * Método responsável por realizar o logout do sistema.
     * 
     * @return página para logout
     * @throws IOException 
     */
    public String logout() throws IOException {
    	
    	SecurityContextHolder.getContext().setAuthentication(null);
		SecurityContextHolder.clearContext();
		setUsuario(null);
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
	    ec.redirect(ec.getRequestContextPath() + "/public/login.jsf");
    	return "/logout";
    }
    
    public String esqueciSenha() {
    	
    	return "/public/esqueciSenha";
    }
    
    public void enviarEmailTrocarSenha() {
    	
    	if (emailTrocaSenha.equals(confirmacaoEmailTrocaSenha)) {
    		Usuario usuario = usuarioService.enviarEmailTrocaSenha(emailTrocaSenha);
    		Parametro parametro = parametrosService.recuperarParametroSistema();
    		if (usuario != null && usuario.getId() != null) {
    			addMsgSuccess("Você receberá um link no e-mail cadastrado para a troca de senha. "
    					+ "Caso não chegue em sua Caixa de Entrada, verifique na sua Caixa de Spam ou Lixeira.");
    		} else {
    			addMsgErro("O e-mail fornecido não existe em nossa base. "
    					+ "Envie um e-mail para " + parametro.getEmailCorridasTele() + " passando seus dados e "
    					+ "informando que não lembra do e-mail cadastrado ou cadastre um novo usuário.");
    		}
    	} else {
    		addMsgErro("O E-mail e sua Confirmação devem ser iguais.");
    	}
    }
    
    public String trocarSenha() {
    	
    	String retorno = "";
    	if (hashTrocaSenha == null || hashTrocaSenha.isEmpty()) {
    		addMsgErro("Problema com o link para troca de senha.");
    	} else {
    		if (novaSenha.equals(confirmacaoNovaSenha)) {

    			Usuario usuario = usuarioService.recuperarPorHashTrocaSenha(hashTrocaSenha);

    			if (TipoUsuarioEnum.ADMINISTRATIVO.getCodigo().equals(usuario.getTipo())) {

					usuarioService.trocarSenha(login, novaSenha, hashTrocaSenha);
					addMsgSuccess("Senha alterada com sucesso. Faça o login com a nova senha.");
					retorno = "/public/login";

				} else {
    				usuarioService.trocarSenhaCliente(usuario, novaSenha);
					addMsgSuccess("Acesse o aplicativo novamente e se autentique com a nova senha.");
					retorno = "/public/trocarSenhaResultado";
				}
    		} else {
    			addMsgErro("A Senha e sua Confirmação devem ser iguais.");
    		}
    	}
    	return retorno;
    }
    
    /**
     * Método responsável por adicionar usuário na sessão.
     * 
     * @param usuario
     */
    private void adicionarUsuarioSessao(Usuario usuario) {
    	
        AuthenticationManager am = new CustomAuthenticationManager();
        List<GrantedAuthority> authorities = new ArrayList<>();
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                usuario, "default", authorities);
        Authentication authenticate = am.authenticate(authentication);
        SecurityContextHolder.getContext().setAuthentication(authenticate);
    }
    
    /**
     * Método responsável por verificar se uma funcionalidade está autorizada para acesso pelo usuário.
     * 
     * @param idFuncionalidade identificador da funcionalidade
     * @return <code>true</code> caso esteja autorizada, <code>false</code> caso contrário.
     */
    public Boolean verificaAutorizacao(Long idFuncionalidade) {
    	
    	Boolean verifica = Boolean.FALSE;
    	if (getFuncionalidades() != null && !getFuncionalidades().isEmpty()) {
    		if (getFuncionalidades().contains(idFuncionalidade)) {
    			verifica = Boolean.TRUE;
    		}
    	}
    	return verifica;
    }

    // Métodos get/set
    public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
        this.password = password;
    }
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public List<Long> getFuncionalidades() {
		return funcionalidades;
	}
	public void setFuncionalidades(List<Long> funcionalidades) {
		this.funcionalidades = funcionalidades;
	}
	public String getEmailTrocaSenha() {
		return emailTrocaSenha;
	}
	public void setEmailTrocaSenha(String emailTrocaSenha) {
		this.emailTrocaSenha = emailTrocaSenha;
	}
	public String getConfirmacaoEmailTrocaSenha() {
		return confirmacaoEmailTrocaSenha;
	}
	public void setConfirmacaoEmailTrocaSenha(String confirmacaoEmailTrocaSenha) {
		this.confirmacaoEmailTrocaSenha = confirmacaoEmailTrocaSenha;
	}
	public String getNovaSenha() {
		return novaSenha;
	}
	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}
	public String getConfirmacaoNovaSenha() {
		return confirmacaoNovaSenha;
	}
	public void setConfirmacaoNovaSenha(String confirmacaoNovaSenha) {
		this.confirmacaoNovaSenha = confirmacaoNovaSenha;
	}
	public String getHashTrocaSenha() {
		return hashTrocaSenha;
	}
	public void setHashTrocaSenha(String hashTrocaSenha) {
		this.hashTrocaSenha = hashTrocaSenha;
	}
	public String getLogoLogin() {
		return logoLogin;
	}
	public void setLogoLogin(String logoLogin) {
		this.logoLogin = logoLogin;
	}
	public String getLogoMenu() {
		return logoMenu;
	}
	public void setLogoMenu(String logoMenu) {
		this.logoMenu = logoMenu;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getNomeApp() {
		return nomeApp;
	}
	public void setNomeApp(String nomeApp) {
		this.nomeApp = nomeApp;
	}
	
}