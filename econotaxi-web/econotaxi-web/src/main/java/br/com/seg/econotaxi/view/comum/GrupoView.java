package br.com.seg.econotaxi.view.comum;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import br.com.seg.econotaxi.enums.MenuEnum;
import br.com.seg.econotaxi.model.Funcionalidade;
import br.com.seg.econotaxi.model.Perfil;
import br.com.seg.econotaxi.model.FuncionalidadePerfil;
import br.com.seg.econotaxi.model.FuncionalidadePerfilPk;
import br.com.seg.econotaxi.service.FuncionalidadeService;
import br.com.seg.econotaxi.service.PerfilService;
import br.com.seg.econotaxi.view.BaseView;
import br.com.seg.econotaxi.view.paginator.GrupoDataModel;

@Named
@Scope("view")
@ManagedBean(name = "grupoView")
public class GrupoView extends BaseView {

    // Constantes
	private static final long serialVersionUID = -6918164650365605188L;
	
	// Atributos
	@Autowired
	private PerfilService perfilService;
	@Autowired
	private FuncionalidadeService funcionalidadeService;
	private Perfil grupo;
	private Perfil filtro;
	private GrupoDataModel grupoDataModel;
	private List<Funcionalidade> funcionalidades;
	
	/**
	 * Método responsável pela inicialização de informações na tela.
	 */
	@PostConstruct
	public void init() {
		setGrupoDataModel(new GrupoDataModel(perfilService, getFiltro()));
		setFuncionalidades(funcionalidadeService.recuperarTodasFuncionalidades());
	}
	
	/**
     * Método responsável por carregar dados de um determinado Grupo de Usuário.
     * 
     * @param idGrupo o id do grupo de usuário
     */
    public void carregarGrupo(Perfil grupo) {
    	setGrupo(perfilService.recuperarPerfilPorChave(grupo.getId()));
    	List<Funcionalidade> funcs = new ArrayList<Funcionalidade>();
    	for (FuncionalidadePerfil grupoFuncionalidade : getGrupo().getListaPerfilFuncionalidade()) {
			Funcionalidade func = grupoFuncionalidade.getFuncionalidade();
			funcs.add(func);
		}
    	getGrupo().setFuncionalidades(funcs);
    }
    
    /**
     * Método responsável por salvar um grupo de usuário.
     */
    public void salvarGrupo() {
    	
    	definirMenu(MenuEnum.PERFIL.getMenu());
    	if (perfilService.verificaExistenciaPerfil(grupo)) {
    		addMsgErro("Perfil já existente!");
    	} else {
    		perfilService.salvarPerfil(grupo);
    		List<FuncionalidadePerfil> listFp = new ArrayList<FuncionalidadePerfil>();
    		for (Funcionalidade funcionalidade : getGrupo().getFuncionalidades()) {
    			FuncionalidadePerfil fp = new FuncionalidadePerfil();
    			FuncionalidadePerfilPk pk = new FuncionalidadePerfilPk();
    			pk.setIdFuncionalidade(funcionalidade.getId());
    			pk.setIdPerfil(grupo.getId());
    			fp.setId(pk);
    			listFp.add(fp);
    		}
    		grupo.setListaPerfilFuncionalidade(listFp);
    		perfilService.salvarPerfil(grupo);
    		addMsgSuccess("Perfil salvo com sucesso!");
    		setGrupo(null);
    	}
    }
    
    /**
     * Método responsável por excluir um grupo de usuário.
     */
    public void excluirGrupo() {
    	
    	definirMenu(MenuEnum.PERFIL.getMenu());
    	perfilService.excluir(grupo);
		addMsgSuccess("Perfil excluído com sucesso!");
		setGrupo(null);
    }
    
    /**
     * Método responsável por recuperar grupos de usuários por filtro.
     */
    public void pesquisarGrupoPorFiltro() {
    	
    	setGrupoDataModel(new GrupoDataModel(perfilService, filtro));
    }
    
    /**
     * Método responsável por limpar formulário e resultados da pesquisa.
     */
    public void limparFormulario() {
    	
    	setFiltro(null);
    	setGrupoDataModel(null);
    }

    // Métodos get/set
    public Perfil getGrupo() {
    	if (grupo == null) {
			grupo = new Perfil();
		}
		return grupo;
	}
	public void setGrupo(Perfil grupo) {
		this.grupo = grupo;
	}
	public Perfil getFiltro() {
		if (filtro == null) {
			filtro = new Perfil();
		}
		return filtro;
	}
	public void setFiltro(Perfil filtro) {
		this.filtro = filtro;
	}
	public GrupoDataModel getGrupoDataModel() {
		return grupoDataModel;
	}
	public void setGrupoDataModel(GrupoDataModel grupoDataModel) {
		this.grupoDataModel = grupoDataModel;
	}
	public List<Funcionalidade> getFuncionalidades() {
		return funcionalidades;
	}
	public void setFuncionalidades(List<Funcionalidade> funcionalidades) {
		this.funcionalidades = funcionalidades;
	}
	
}