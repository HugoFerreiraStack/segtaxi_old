/*
* MenuView.java:
*
* Criação: 25 de jun de 2017
*/
package br.com.seg.econotaxi.view.comum;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.util.StringUtils;

import br.com.seg.econotaxi.enums.MenuEnum;
import br.com.seg.econotaxi.enums.StatusAlertaCienteEnum;
import br.com.seg.econotaxi.enums.StatusAlertaNotificacaoEnum;
import br.com.seg.econotaxi.model.Alerta;
import br.com.seg.econotaxi.service.AlertaService;
import br.com.seg.econotaxi.view.BaseView;

/**
 * Classe de Visão que contém métodos que auxiliam na manipulação de dados em tela do menu. 
 *
 * Criado em 25 de jun de 2017
 * @author Bruno rocha
 */
@Named
@ManagedBean
@Scope("session")
public class MenuView extends BaseView {

	// Constantes
	private static final long serialVersionUID = -2117568075279407860L;
	
	// Atributos
	private Integer identificadorMenu;
	@Autowired
	private AlertaService alertaService;
	private List<Alerta> alertas;
	private List<Alerta> alertasNotificacao;
	private Integer numeroAlertas;
	private String textoAlerta;
	private String textoTitulo;
	private Alerta alertaSelecionado;
	
	/**
	 * Método responsável por definir o menu que ficará selecionado.
	 * 
	 * @param menu o identificador do menu 
	 */
	public void definirMenu(Integer menu) {
		setIdentificadorMenu(menu);
		setTextoTitulo(MenuEnum.valueOfMenu(menu).getDescricaoMenu());
	}
	
	public void resolverAlerta() {
		
		if (getAlertaSelecionado().getMotivoResolucao() == null
				|| getAlertaSelecionado().getMotivoResolucao().isEmpty()) {
			addMsgErro("alerta.obrigatorio");
		} else {
			alertaSelecionado.setStatusNotificacao(StatusAlertaNotificacaoEnum.PROBLEMA_RESOLVIDO.getProblema());
			alertaService.alterarAlerta(alertaSelecionado);
			addMsgSuccess("alerta.solucionado");
			carregarInformacoesAlerta();
			setAlertaSelecionado(null);
		}
	}
	
	/**
	 * Método responsável por carregar página e definir identificador de menu.
	 * 
	 * @param menu o identificador de menu
	 * @param pagina a página a ser direcionada
	 * @return a página a ser direcionada
	 */
	public String carregarMenu(Integer menu, String pagina) {
		
		definirMenu(menu);
		//carregarInformacoesAlerta();
		return pagina;
	}
	
	/**
	 * Método responsável por verificar alertas de arquivos
	 */
	public void carregarInformacoesAlerta() {
		
		setAlertas(alertaService.recuperarAlertasNaoVistos());
		setAlertasNotificacao(alertaService.recuperarAlertasNaoSolucionados());
		setNumeroAlertas(getAlertasNotificacao() != null && !getAlertasNotificacao().isEmpty() ?
				getAlertasNotificacao() .size() : 0);
		setTextoAlerta(null);
		if (getAlertas() != null && !getAlertas().isEmpty()) {
			for (Alerta alerta : getAlertas()) {
				setTextoAlerta(!StringUtils.isEmpty(getTextoAlerta()) ? 
						getTextoAlerta() + montaTextoAlerta(alerta) : montaTextoAlerta(alerta));
			}
		}
	}

	/**
	 * Método responsável por montar texto que será exibido no alerta.
	 * 
	 * @param alerta o alerta a ser exibido
	 * @return texto do alerta a ser exibido
	 */
	private String montaTextoAlerta(Alerta alerta) {

		StringBuilder texto = new StringBuilder();
		return texto.toString();
	}
	
	/**
	 * 
	 * @param alerta
	 */
	public void definirProblemaConhecido(Alerta alerta) {
		
		alerta.setStatusAlerta(StatusAlertaCienteEnum.PROBLEMA_CIENTE.getProblema());
		alertaService.alterarAlerta(alerta);
	}
	
	/**
	 * 
	 * @param alerta
	 */
	public void definirProblemaNaoConhecido(Alerta alerta) {
		
		alerta.setStatusAlerta(StatusAlertaCienteEnum.PROBLEMA_NAO_CIENTE.getProblema());
		alertaService.alterarAlerta(alerta);
	}
	
	/**
	 * 
	 * @param alerta
	 */
	public void definirProblemaResolvido(Alerta alerta) {
		
		setAlertaSelecionado(alerta);
	}

	// Métodos get/set
	public Integer getIdentificadorMenu() {
		return identificadorMenu;
	}
	public void setIdentificadorMenu(Integer identificadorMenu) {
		this.identificadorMenu = identificadorMenu;
	}
	public List<Alerta> getAlertas() {
		return alertas;
	}
	public void setAlertas(List<Alerta> alertas) {
		this.alertas = alertas;
	}
	public List<Alerta> getAlertasNotificacao() {
		return alertasNotificacao;
	}
	public void setAlertasNotificacao(List<Alerta> alertasNotificacao) {
		this.alertasNotificacao = alertasNotificacao;
	}
	public Integer getNumeroAlertas() {
		return numeroAlertas;
	}
	public void setNumeroAlertas(Integer numeroAlertas) {
		this.numeroAlertas = numeroAlertas;
	}
	public String getTextoAlerta() {
		return textoAlerta;
	}
	public void setTextoAlerta(String textoAlerta) {
		this.textoAlerta = textoAlerta;
	}
	public String getTextoTitulo() {
		return textoTitulo;
	}
	public void setTextoTitulo(String textoTitulo) {
		this.textoTitulo = textoTitulo;
	}
	public Alerta getAlertaSelecionado() {
		return alertaSelecionado;
	}
	public void setAlertaSelecionado(Alerta alertaSelecionado) {
		this.alertaSelecionado = alertaSelecionado;
	}
	
}