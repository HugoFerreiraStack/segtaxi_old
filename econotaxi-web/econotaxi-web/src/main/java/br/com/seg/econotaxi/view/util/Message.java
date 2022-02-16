package br.com.seg.econotaxi.view.util;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import br.com.seg.econotaxi.util.MensagensPropertiesUtil;

public class Message {

	public final static Severity SUCCESS = FacesMessage.SEVERITY_INFO;
	public final static Severity INFO = FacesMessage.SEVERITY_WARN;
	public final static Severity ERROR = FacesMessage.SEVERITY_ERROR;
	
	/**
	 * Construtor da classe. Utilize os métodos <code>public static</code>.
	 * <br /><br />
	 */
	private Message() {}
	
	/**
	 * TODO comentário.
	 * <br /><br />
	 *
	 * @param severidade
	 * @param keyMensagem
	 * @param argumentos
	 */
	public static void add(Severity severidade, String keyMensagem, String ... argumentos) {
		
		FacesMessage facesMessage = new FacesMessage(
				severidade, keyMensagem, "OK");
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}
	
	/**
	 * TODO comentário.
	 * <br /><br />
	 *
	 * @param keyMensagem
	 * @param argumentos
	 * @return String
	 */
	private static String recuperarMensagem(String keyMensagem, String ... argumentos) {
		
		return MensagensPropertiesUtil.get(keyMensagem, argumentos);
	}
	
}
