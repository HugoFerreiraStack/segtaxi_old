package br.com.seg.econotaxi.view.comum;

import java.net.URL;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import br.com.seg.econotaxi.enums.MenuEnum;
import br.com.seg.econotaxi.model.Parametro;
import br.com.seg.econotaxi.service.ParametrosService;
import br.com.seg.econotaxi.view.BaseView;
import br.com.seg.econotaxi.view.util.LogFileTailer;

@Named
@Scope("view")
@ManagedBean(name = "parametrosView")
public class ParametrosView extends BaseView {

    // Constantes
	private static final long serialVersionUID = -6918164650365605188L;
	
	// Atributos
	@Autowired
	private ParametrosService parametrosService;
	private Parametro parametro;
	private String log;
	
	/**
	 * Método responsável pela inicialização de informações na tela.
	 */
	@PostConstruct
	public void init() {
		setParametro(parametrosService.recuperarParametroSistema());
		if (getParametro() == null) {
			setParametro(new Parametro());
			getParametro().setId(1);
		}
	}
	
    public void salvarParametros() {
    	
    	definirMenu(MenuEnum.PARAMETROS.getMenu());
    	Parametro p = parametrosService.recuperarParametroSistema();
    	if (p != null && p.getId() != null) {
    		if (getParametro().getSenhaEmail() != null 
    				&& !getParametro().getSenhaEmail().isEmpty()) {
    			p.setSenhaEmail(getParametro().getSenhaEmail());
    		}
    		if (getParametro().getUsuarioEmail() != null 
    				&& !getParametro().getUsuarioEmail().isEmpty()) {
    			p.setUsuarioEmail(getParametro().getUsuarioEmail());
    		}
    		if (getParametro().getVersaoApp() != null 
    				&& !getParametro().getVersaoApp().isEmpty()) {
    			p.setVersaoApp(getParametro().getVersaoApp());
    		}
    		if (getParametro().getVersaoAppPassageiro() != null 
    				&& !getParametro().getVersaoAppPassageiro().isEmpty()) {
    			p.setVersaoAppPassageiro(getParametro().getVersaoAppPassageiro());
    		}  
    		if (getParametro().getVersaoAppIos() != null 
    				&& !getParametro().getVersaoAppIos().isEmpty()) {
    			p.setVersaoAppIos(getParametro().getVersaoAppIos());
    		}
    		if (getParametro().getUrlSmtp() != null 
    				&& !getParametro().getUrlSmtp().isEmpty()) {
    			p.setUrlSmtp(getParametro().getUrlSmtp());
    		}
    		if (getParametro().getPortaSmtp() != null 
    				&& !getParametro().getPortaSmtp().isEmpty()) {
    			p.setPortaSmtp(getParametro().getPortaSmtp());
    		}
    		if (getParametro().getIndicadorTipoCorridaTele() != null) {
    			p.setIndicadorTipoCorridaTele(getParametro().getIndicadorTipoCorridaTele());
    		}
    		
    		if (getParametro().getUsuarioSms() != null 
    				&& !getParametro().getUsuarioSms().isEmpty()) {
    			p.setUsuarioSms(getParametro().getUsuarioSms());
    		}
    		if (getParametro().getSenhaSms() != null 
    				&& !getParametro().getSenhaSms().isEmpty()) {
    			p.setSenhaSms(getParametro().getSenhaSms());
    		}
    		
    		if (getParametro().getNomeAplicativo() != null 
    				&& !getParametro().getNomeAplicativo().isEmpty()) {
    			p.setNomeAplicativo(getParametro().getNomeAplicativo());
    		}
    		if (getParametro().getEmailCorridasTele() != null 
    				&& !getParametro().getEmailCorridasTele().isEmpty()) {
    			p.setEmailCorridasTele(getParametro().getEmailCorridasTele());
    		}
    		if (getParametro().getHost() != null 
    				&& !getParametro().getHost().isEmpty()) {
    			p.setHost(getParametro().getHost());
    		}
    		parametrosService.salvar(p);
    	} else {
    		if (getParametro().getId() == null) {
    			getParametro().setId(1);
    		}
    		parametrosService.salvar(parametro);
    	}
    	addMsgSuccess("Parâmetros gerais salvos com sucesso!");
    }
    
    public void visualizarLog() {
    	
    	URL url = Thread.currentThread().getContextClassLoader().getResource("/");
    	System.out.println(url.getPath());
    	String path = url.getPath().substring(0, url.getPath().indexOf("/modules")) + "/standalone/log/server.log";
    	System.out.println(path);
    	LogFileTailer logFile = new LogFileTailer(path, 0);
    	if (logFile.getLog() != null) {
    		setLog(logFile.getLog().toString());
    	} else {
    		setLog("Não foi possível recuperar o log da aplicação.");
    	}
    }

    // Métodos get/set
	public Parametro getParametro() {
		return parametro;
	}
	public void setParametro(Parametro parametro) {
		this.parametro = parametro;
	}
	public String getLog() {
		return log;
	}
	public void setLog(String log) {
		this.log = log;
	}
    
}