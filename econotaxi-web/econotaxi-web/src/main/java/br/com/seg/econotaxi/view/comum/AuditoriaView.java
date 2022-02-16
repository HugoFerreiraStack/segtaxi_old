package br.com.seg.econotaxi.view.comum;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import br.com.seg.econotaxi.enums.MenuEnum;
import br.com.seg.econotaxi.model.Auditoria;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.service.AuditoriaService;
import br.com.seg.econotaxi.service.UsuarioService;
import br.com.seg.econotaxi.view.BaseView;
import br.com.seg.econotaxi.view.paginator.AuditoriaDataModel;

@Named
@ManagedBean
@Scope("view")
public class AuditoriaView extends BaseView implements Serializable{

	// Constantes
	private static final long serialVersionUID = 7036854833022056730L;
	
	// Atributos
	@Autowired
	private AuditoriaService auditoriaService;
	@Autowired
	private UsuarioService usuarioService;
	private AuditoriaDataModel auditoriaDataModel;
	private Auditoria filtro;
	private List<Usuario> usuarios;
	
	@PostConstruct
	public void init() {
		
		setAuditoriaDataModel(new AuditoriaDataModel(auditoriaService, getFiltro()));
		setUsuarios(usuarioService.recuperarUsuariosAdministrativo());
	}
	
	/**
	 * Método responsável por consultar alertas
	 */
	public void pesquisarAuditoriaPorFiltro() {
		
		definirMenu(MenuEnum.AUDITORIA.getMenu());
		setAuditoriaDataModel(new AuditoriaDataModel(auditoriaService, getFiltro()));
	}
	
	/**
	 * Método responsável por limpar o formulário de pesquisa
	 */
	public void limparFormulario() {
		
		definirMenu(MenuEnum.AUDITORIA.getMenu());
		setFiltro(null);
		setAuditoriaDataModel(null);
	}
	
	/**
	 * 
	 * @param auditoria
	 * @throws IOException 
	 */
	public void downloadAuditoria(Auditoria auditoria) throws IOException {
		
		byte[] arquivo = auditoria.getObjeto().getBytes();
		FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        String nomeArquivo = auditoria.getClasseSimples() + "_" + auditoria.getDescricaoTipoTransacao() 
        	+ "_" + new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss").format(auditoria.getDataEvento()) + ".json";
        response.reset();
        response.setContentType("application/json; charset=ISO-8859-1");
        response.setContentLength(arquivo.length);
        response.setHeader("Content-disposition", "attachment; filename=" + nomeArquivo);
        OutputStream output = response.getOutputStream();
        output.write(arquivo);
        output.close();
        facesContext.responseComplete();
	}

	// Métodos get/set
	public AuditoriaDataModel getAuditoriaDataModel() {
		return auditoriaDataModel;
	}
	public void setAuditoriaDataModel(AuditoriaDataModel auditoriaDataModel) {
		this.auditoriaDataModel = auditoriaDataModel;
	}
	public Auditoria getFiltro() {
		if (filtro == null) {
			filtro = new Auditoria();
		}
		return filtro;
	}
	public void setFiltro(Auditoria filtro) {
		this.filtro = filtro;
	}
	public List<Usuario> getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

}