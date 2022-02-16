package br.com.seg.econotaxi.util;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

import br.com.seg.econotaxi.enums.LocaleEnum;

/**
 * TODO comentário.
 * <br /><br />
 * 
 * Criado em 14/02/2011
 * @author Bruno M. Rocha
 */
public class MensagensPropertiesUtil {

	private ResourceBundle RB = ResourceBundle.getBundle("messages_core");
	
	private static MensagensPropertiesUtil instance = new MensagensPropertiesUtil();
	
	/**
	 * Construtor privado. Esta classe não pode ser instanciada. Use os métodos
	 * <code>static</code> disponíveis.
	 */
	private MensagensPropertiesUtil() {
		RB = ResourceBundle.getBundle("messages_core", LocaleEnum.valueOf(getLocale().toUpperCase()).getLocale());
	}
	
	
	/**
	 * Obtém o valor de uma mensagem (String) do arquivo de propriedades.
	 * 
	 * @param chave Identificador da mensagem
	 * @param args Argumentos para usar na mensagem
	 * @return Conteúdo da mensagem (com os argumentos formatados)
	 */
	@SuppressWarnings("all")
	public static String get(String chave, String ... args) {
		
		ResourceBundle RB = ResourceBundle.getBundle("messages_core", LocaleEnum.valueOf(
				FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage().toUpperCase()).getLocale());
		String msg = null;
		try {
			msg = MessageFormat.format(RB.getString(chave), args);
		} catch (MissingResourceException e) {
			msg = chave;
		}
		return msg;
	}
	
	private String getLocale() {
		return FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage();
	}
	
}