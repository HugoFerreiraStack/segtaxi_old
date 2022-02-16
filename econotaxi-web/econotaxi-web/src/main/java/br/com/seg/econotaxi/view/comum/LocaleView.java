package br.com.seg.econotaxi.view.comum;

import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import br.com.seg.econotaxi.enums.LocaleEnum;
import br.com.seg.econotaxi.view.BaseView;

/**
 * @author Bruno Rocha
 *
 */
/**
 * @author bruno
 *
 */
@Named
@ManagedBean
@Scope("session")
public class LocaleView extends BaseView {

	// Constantes
	private static final long serialVersionUID = -7654185294484296328L;
	
	private Locale locale;
	private String idioma;

    @PostConstruct
    public void init() {
        locale = LocaleEnum.PT.getLocale();
    }

    public Locale getLocale() {
        return locale;
    }

    public String getLanguage() {
        return locale.getLanguage();
    }

    public void setLanguage(String language) {
        locale = LocaleEnum.valueOf(language.toUpperCase()).getLocale();
        Locale.setDefault(locale);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
    }
	
    public void trocarLocale() {
    	setLanguage(idioma);
    }

    /* Métodos Get/Set */
	public String getIdioma() {
		return idioma;
	}
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}
    

}