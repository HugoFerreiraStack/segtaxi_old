package br.com.seg.econotaxi.view;

import java.io.Serializable;
import java.util.Iterator;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.springframework.web.jsf.FacesContextUtils;

import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.view.comum.LoginView;
import br.com.seg.econotaxi.view.comum.MenuView;
import br.com.seg.econotaxi.view.util.Message;

/**
 * Classe abstrata que deve ser herdada por todas as classes de visão.
 *
 * Criado em 25 de jun de 2017
 * @author Bruno rocha
 */
public abstract class BaseView implements Serializable {
	
	// Constantes
	private static final long serialVersionUID = 1594520623150487666L;

	/**
	 * Método responsável por obter alguma managed bean.
	 * 
	 * @param beanName o nome do componente
	 * @param t a classe
	 * @return o objeto managed bean instanciado
	 */
	public static <T> T recuperarBean(String beanName, Class<T> t) {
	    FacesContext context = FacesContext.getCurrentInstance();
	    T bean = (T) FacesContextUtils.getWebApplicationContext(context).getBean(beanName, t);
	    return bean;
	}
	
	/**
	 * Método responsável por definir o menu que ficará selecionado.
	 * 
	 * @param identificadorMenu o identificador do menu
	 */
	protected void definirMenu(Integer identificadorMenu) {
		
		MenuView view = (MenuView) recuperarBean("menuView", MenuView.class);
		view.definirMenu(identificadorMenu);
	}
	
	/**
	 * Método responsável por verificar alertas de arquivos
	 */
	protected void carregarInformacoesAlerta() {
		
		MenuView view = (MenuView) recuperarBean("menuView", MenuView.class);
		view.carregarInformacoesAlerta();
	}
	
	/**
	 * Método responsável por recuperar o usuário da sessão.
	 * 
	 * @return o usuário da sessão
	 */
	protected Usuario recuperarUsuarioSessao() {
		
		Usuario usuario = null;
		LoginView view = (LoginView) recuperarBean("loginView", LoginView.class);
		usuario = view.getUsuario();
		return usuario;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isMensagensErro() {
		
		Boolean verifica = Boolean.FALSE;
		if (FacesContext.getCurrentInstance().getMessages() != null) {
			Iterator<FacesMessage> mensagens = FacesContext.getCurrentInstance().getMessages();
			while (mensagens.hasNext()) {
				FacesMessage mensagem = mensagens.next();
				if (FacesMessage.SEVERITY_ERROR.equals(mensagem.getSeverity())) {
					verifica = Boolean.TRUE;
					break;
				}
			}
		}
		return verifica;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isMessages() {
		
		Boolean verifica = Boolean.FALSE;
		if (FacesContext.getCurrentInstance().getMessages() != null) {
			Iterator<FacesMessage> mensagens = FacesContext.getCurrentInstance().getMessages();
			while (mensagens.hasNext()) {
				FacesMessage mensagem = mensagens.next();
				if (FacesMessage.SEVERITY_ERROR.equals(mensagem.getSeverity()) || FacesMessage.SEVERITY_INFO.equals(mensagem.getSeverity())) {
					verifica = Boolean.TRUE;
					break;
				}
			}
		}
		return verifica;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isMessagesAlert() {
		
		Boolean verifica = Boolean.FALSE;
		if (FacesContext.getCurrentInstance().getMessages() != null) {
			Iterator<FacesMessage> mensagens = FacesContext.getCurrentInstance().getMessages();
			while (mensagens.hasNext()) {
				FacesMessage mensagem = mensagens.next();
				if (FacesMessage.SEVERITY_WARN.equals(mensagem.getSeverity())) {
					verifica = Boolean.TRUE;
					break;
				}
			}
		}
		return verifica;
	}
	
	/**
	 * Adiciona mensagem de sucesso.
	 * <br /><br />
	 *
	 * @param key
	 * @param params
	 */
	protected void addMsgSuccess(String key, String ... params) {
		Message.add(Message.SUCCESS, key, params);
	}

	/**
	 * Adiciona mensagem de informação.
	 * <br /><br />
	 *
	 * @param key
	 * @param params
	 */
	protected void addMsgInfo(String key, String ... params) {
		Message.add(Message.INFO, key, params);
	}

	/**
	 * Adiciona mensagem de erro.
	 * <br /><br />
	 *
	 * @param key
	 * @param params
	 */
	protected void addMsgErro(String key, String ... params) {
		Message.add(Message.ERROR, key, params);
	}
	
	protected void execJavaScript(String function) {
		
		RequestContext requestContext = RequestContext.getCurrentInstance();  
		requestContext.execute(function);
	}
	
}