package br.com.seg.econotaxi.view.comum;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import br.com.seg.econotaxi.enums.MenuEnum;
import br.com.seg.econotaxi.enums.StatusAlertaNotificacaoEnum;
import br.com.seg.econotaxi.model.Alerta;
import br.com.seg.econotaxi.service.AlertaService;
import br.com.seg.econotaxi.view.BaseView;
import br.com.seg.econotaxi.view.paginator.AlertaDataModal;
import br.com.seg.econotaxi.vo.AlertaFiltroVO;

@Named
@ManagedBean
@Scope("view")
public class AlertaView extends BaseView implements Serializable{

	// Constantes
	private static final long serialVersionUID = -3944411344347483460L;
	
	// Atributos
	@Autowired
	private AlertaService alertaService;
	
	private AlertaDataModal alertaDataModal;
	private AlertaFiltroVO filtro;
	private String resolucaoTodosAlertas;
	private Map<Long, Boolean> alertasSelecionadosParaResolucao = new HashMap<Long, Boolean>();
	
	@PostConstruct
	public void init() {
		
		setFiltro(new AlertaFiltroVO());
		this.filtro.setStatusAlertaNotificacao(StatusAlertaNotificacaoEnum.PROBLEMA_NAO_RESOLVIDO.getProblema());
		setAlertaDataModal(new AlertaDataModal(alertaService, filtro));
	}
	
	/**
	 * Método responsável por consultar alertas
	 */
	public void consultarAlertas() {
		
		definirMenu(MenuEnum.ALERTAS.getMenu());
		setAlertaDataModal(new AlertaDataModal(alertaService, filtro));
	}
	
	/**
	 * Método responsável por limpar o formulário de pesquisa
	 */
	public void limparFormulario() {
		
		definirMenu(MenuEnum.ALERTAS.getMenu());
		setFiltro(new AlertaFiltroVO());
		setAlertaDataModal(null);
	}
	
	public void resolverTodosAlertasSelecionados() {
		
		if( resolucaoTodosAlertas == null || resolucaoTodosAlertas.trim().equalsIgnoreCase("")) {
			addMsgErro("alerta.todos.resolucao.obrigatorio");
		} else {
			Boolean isAlgumRegistroSelecionado = Boolean.FALSE;
			for(Long ids : alertasSelecionadosParaResolucao.keySet()) {
				if( alertasSelecionadosParaResolucao.get(ids) ) {
					Alerta alerta = alertaService.consultarAlertaPorId(ids);
					alerta.setMotivoResolucao(resolucaoTodosAlertas);
					alerta.setStatusNotificacao(StatusAlertaNotificacaoEnum.PROBLEMA_RESOLVIDO.getProblema());
					alerta.setStatusAlerta(StatusAlertaNotificacaoEnum.PROBLEMA_RESOLVIDO.getProblema());
					alertaService.alterarAlerta(alerta);
					isAlgumRegistroSelecionado = Boolean.TRUE;
				}
			}
			if(isAlgumRegistroSelecionado) {
				addMsgSuccess("alerta.todos.resolucao.solucionado");
			} else {
				addMsgErro("alerta.nenhum.registro.selecionado");
			}
			alertasSelecionadosParaResolucao = new HashMap<Long, Boolean>();
			this.consultarAlertas();
			MenuView view = recuperarBean("menuView", MenuView.class);
			view.carregarInformacoesAlerta();
			this.resolucaoTodosAlertas = "";
		}
	}
	
	// Métodos get/set
	public AlertaDataModal getAlertaDataModal() {
		return alertaDataModal;
	}
	public void setAlertaDataModal(AlertaDataModal alertaDataModal) {
		this.alertaDataModal = alertaDataModal;
	}
	public AlertaFiltroVO getFiltro() {
		return filtro;
	}
	public void setFiltro(AlertaFiltroVO filtro) {
		this.filtro = filtro;
	}
	public String getResolucaoTodosAlertas() {
		return resolucaoTodosAlertas;
	}
	public void setResolucaoTodosAlertas(String resolucaoTodosAlertas) {
		this.resolucaoTodosAlertas = resolucaoTodosAlertas;
	}
	public Map<Long, Boolean> getAlertasSelecionadosParaResolucao() {
		return alertasSelecionadosParaResolucao;
	}
	public void setAlertasSelecionadosParaResolucao(Map<Long, Boolean> alertasSelecionadosParaResolucao) {
		this.alertasSelecionadosParaResolucao = alertasSelecionadosParaResolucao;
	}

}