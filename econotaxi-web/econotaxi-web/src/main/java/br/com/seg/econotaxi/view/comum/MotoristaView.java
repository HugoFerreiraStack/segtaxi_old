package br.com.seg.econotaxi.view.comum;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import br.com.seg.econotaxi.enums.MenuEnum;
import br.com.seg.econotaxi.enums.StatusMotoristaEnum;
import br.com.seg.econotaxi.model.Auxiliar;
import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.model.Motorista;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.service.AuxiliarService;
import br.com.seg.econotaxi.service.CidadeService;
import br.com.seg.econotaxi.service.MotoristaService;
import br.com.seg.econotaxi.service.UsuarioService;
import br.com.seg.econotaxi.view.BaseView;
import br.com.seg.econotaxi.view.paginator.MotoristaDataModel;

@Named
@Scope("view")
@ManagedBean(name = "motoristaView")
public class MotoristaView extends BaseView {

    // Constantes
	private static final long serialVersionUID = 1584745532035822519L;
	
	// Atributos
	@Autowired
	private MotoristaService motoristaService;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private AuxiliarService auxiliarService;
	@Autowired
	private CidadeService cidadeService;
	private Motorista motorista;
	private Motorista motoristaAlterar;
	private Motorista filtro;
	private MotoristaDataModel motoristaDataModel;
	private String motivoDesautorizacao;
	private String motivoBloqueio;
	private List<Auxiliar> auxiliares;
	private List<Usuario> permissionarios;
	private List<Cidade> listaCidade;
	private String novaSenha;
	private String confirmacaoSenha;
	
	/**
	 * Método responsável pela inicialização de informações na tela.
	 */
	@PostConstruct
	public void init() {
		Integer status = (Integer) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("status");
		if (status != null) {
			getFiltro().setStatus(status);
		}
		Long idMotorista = (Long) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("idMotorista");
		if (idMotorista != null) {
			getFiltro().setId(idMotorista);
			carregarMotorista(motoristaService.recuperarMotoristaPorChave(idMotorista));
		}
		setListaCidade(cidadeService.recuperarTodasCidades());
		setMotoristaDataModel(new MotoristaDataModel(motoristaService, getFiltro()));
	}
	
	public void chamarModal() {
		if (getMotorista().getId() != null) {
			execJavaScript("exibirDetalhesMotorista()");
		}
	}
	
	public void atualizarLocalizacaoMotorista() {
		
		if (getMotorista().getId() != null) {
			setMotorista(motoristaService.recuperarMotoristaPorChave(motorista.getId()));
		}
	}
	
    public void autorizarMotorista() {
    	
    	definirMenu(MenuEnum.MOTORISTA.getMenu());
    	motorista = motoristaService.recuperarMotoristaPorChave(motorista.getId());
    	motorista.setStatus(StatusMotoristaEnum.ATIVO.getStatus());
    	motorista.setMotivoDesautorizacao(null);
    	motorista.setMotivoDesautorizacao(null);
    	motoristaService.salvarMotorista(motorista);
    	addMsgSuccess("Motorista autorizado com sucesso!");
    	setMotorista(null);
    }
    
    public void desautorizarMotorista() {
    	
    	if (motivoDesautorizacao != null && !motivoDesautorizacao.isEmpty()) {
    		definirMenu(MenuEnum.MOTORISTA.getMenu());
    		motorista = motoristaService.recuperarMotoristaPorChave(motorista.getId());
    		motorista.setStatus(StatusMotoristaEnum.NAO_AUTORIZADO.getStatus());
    		motorista.setMotivoDesautorizacao(motivoDesautorizacao);
    		motoristaService.salvarMotorista(motorista);
    		addMsgSuccess("Motorista não autorizado com sucesso!");
    	} else {
    		addMsgErro("Para desautorizar um motorista é preciso preencher um motivo.");
    	}
    	setMotorista(null);
    	setMotivoDesautorizacao(null);
    }
    
    public void excluirMotorista() {
    	
    	definirMenu(MenuEnum.MOTORISTA.getMenu());
    	motorista = motoristaService.recuperarMotoristaPorChave(motorista.getId());
    	motoristaService.excluir(motorista);
    	addMsgSuccess("Motorista excluído com sucesso!");
    	setMotorista(null);
    	pesquisarMotoristaPorFiltro();
    }
    
    public void alterarMotorista() {
    	
    	definirMenu(MenuEnum.MOTORISTA.getMenu());
    	Motorista m = motoristaService.recuperarMotoristaPorChave(motoristaAlterar.getId());
    	Usuario usuarioMotorista = usuarioService.recuperarUsuarioPorID(m.getIdUsuario());
    	Boolean verificaAlteracao = Boolean.FALSE;
    	if (usuarioMotorista.getNome() != null && !usuarioMotorista.getNome().equals(motoristaAlterar.getNome())) {
    		verificaAlteracao = Boolean.TRUE;
    		usuarioMotorista.setNome(motoristaAlterar.getNome());
    	}
    	if (m.getCarteira() != null && !m.getCarteira().equals(motoristaAlterar.getCarteira())) {
    		verificaAlteracao = Boolean.TRUE;
    		m.setCarteira(motoristaAlterar.getCarteira());
    	}
    	if (m.getCidadeMotorista() != null && !m.getCidadeMotorista().equals(motoristaAlterar.getCidadeMotorista())) {
    		verificaAlteracao = Boolean.TRUE;
    		m.setCidadeMotorista(motoristaAlterar.getCidadeMotorista());
    	}
    	if (m.getSiglaUf() != null && !m.getSiglaUf().equals(motoristaAlterar.getSiglaUf())) {
    		verificaAlteracao = Boolean.TRUE;
    		m.setSiglaUf(motoristaAlterar.getSiglaUf());
    	}
    	if (m.getDataNascimento() != null && !m.getDataNascimento().equals(motoristaAlterar.getDataNascimento())) {
    		if (motoristaAlterar.getDataNascimento() != null) {
    			verificaAlteracao = Boolean.TRUE;
    			String data = motoristaAlterar.getDataNascimento().replaceAll("/", "").replaceAll("-", "");
    			//data = data.substring(6).concat(data.substring(4, 6)).concat(data.substring(0, 4));
    			m.setDataNascimento(data);
    		}
    	}
    	if (m.getDataVencimentoMotorista() != null && !m.getDataVencimentoMotorista().equals(motoristaAlterar.getDataVencimentoMotorista())) {
    		if (motoristaAlterar.getDataVencimentoMotorista() != null) {
    			verificaAlteracao = Boolean.TRUE;
    			String data = motoristaAlterar.getDataVencimentoMotorista().replaceAll("/", "").replaceAll("-", "");
    			//data = data.substring(6).concat(data.substring(4, 6)).concat(data.substring(0, 4));
    			m.setDataVencimentoMotorista(data);
    		}
    	}
    	if (((m.getIndicadorPermissionario() != null && m.getIndicadorPermissionario().equals(1)) 
    			|| (m.getIndicadorAuxiliar() != null && m.getIndicadorAuxiliar().equals(1)))
    			&& m.getDataVencimentoTaxi() != null && !m.getDataVencimentoTaxi().equals(motoristaAlterar.getDataVencimentoTaxi())) {
    		if (motoristaAlterar.getDataVencimentoTaxi() != null) {
    			verificaAlteracao = Boolean.TRUE;
    			String data = motoristaAlterar.getDataVencimentoTaxi().replaceAll("/", "").replaceAll("-", "");
    			//data = data.substring(6).concat(data.substring(4, 6)).concat(data.substring(0, 4));
    			m.setDataVencimentoTaxi(data);
    		}
    	}
    	if (((m.getIndicadorPermissionario() != null && m.getIndicadorPermissionario().equals(1)) 
    			|| (m.getIndicadorAuxiliar() != null && m.getIndicadorAuxiliar().equals(1)))
    			&& m.getCarteiraTaxista() != null && !m.getCarteiraTaxista().equals(motoristaAlterar.getCarteiraTaxista())) {
    		verificaAlteracao = Boolean.TRUE;
    		m.setCarteiraTaxista(motoristaAlterar.getCarteiraTaxista());
    	}
    	if (((m.getIndicadorPermissionario() != null && m.getIndicadorPermissionario().equals(1)) 
    			|| (m.getIndicadorAuxiliar() != null && m.getIndicadorAuxiliar().equals(1)))
    			&& (m.getTipoTeleTaxi() != null && !m.getTipoTeleTaxi().equals(motoristaAlterar.getTipoTeleTaxi())
    			|| (m.getTipoTeleTaxi() == null && motoristaAlterar.getTipoTeleTaxi() != null))) {
    		verificaAlteracao = Boolean.TRUE;
    		m.setTipoTeleTaxi(motoristaAlterar.getTipoTeleTaxi());
    	}
    	if (((m.getIndicadorPermissionario() != null && m.getIndicadorPermissionario().equals(1)) 
    			|| (m.getIndicadorAuxiliar() != null && m.getIndicadorAuxiliar().equals(1)))
    			&& (m.getIndicadorAceitaVoucher() != null && !m.getIndicadorAceitaVoucher().equals(motoristaAlterar.getIndicadorAceitaVoucher())
    			|| (m.getIndicadorAceitaVoucher() == null && motoristaAlterar.getIndicadorAceitaVoucher() != null))) {
    		verificaAlteracao = Boolean.TRUE;
    		m.setIndicadorAceitaVoucher(motoristaAlterar.getIndicadorAceitaVoucher());
    	}
    	if (m.getIndicadorMaquinaDebito() != null && !m.getIndicadorMaquinaDebito().equals(motoristaAlterar.getIndicadorMaquinaDebito())
    			|| (m.getIndicadorMaquinaDebito() == null && motoristaAlterar.getIndicadorMaquinaDebito() != null)) {
    		verificaAlteracao = Boolean.TRUE;
    		m.setIndicadorMaquinaDebito(motoristaAlterar.getIndicadorMaquinaDebito());
    	}
    	if (m.getCpf() != null && !m.getCpf().equals(motoristaAlterar.getCpf())) {
    		verificaAlteracao = Boolean.TRUE;
    		m.setCpf(motoristaAlterar.getCpf());
    	}
    	if (m.getStatus() != null && !m.getStatus().equals(motoristaAlterar.getStatus())) {
    		verificaAlteracao = Boolean.TRUE;
    		m.setStatus(motoristaAlterar.getStatus());
    	}
    	if (m.getNome() != null && !m.getNome().equals(motoristaAlterar.getNome())) {
    		verificaAlteracao = Boolean.TRUE;
    		m.setNome(motoristaAlterar.getNome());
    	}
    	if (m.getNomeMae() != null && !m.getNomeMae().equals(motoristaAlterar.getNomeMae())) {
    		verificaAlteracao = Boolean.TRUE;
    		m.setNomeMae(motoristaAlterar.getNomeMae());
    	}
    	if (m.getBanco() != null && !m.getBanco().equals(motoristaAlterar.getBanco())
    			|| (m.getBanco() == null && motoristaAlterar.getBanco() != null
    			&& !motoristaAlterar.getBanco().isEmpty())) {
    		verificaAlteracao = Boolean.TRUE;
    		m.setBanco(motoristaAlterar.getBanco());
    	}
    	if (m.getAgencia() != null && !m.getAgencia().equals(motoristaAlterar.getAgencia())
    			|| (m.getAgencia() == null && motoristaAlterar.getAgencia() != null 
    			&& !motoristaAlterar.getAgencia().isEmpty())) {
    		verificaAlteracao = Boolean.TRUE;
    		m.setAgencia(motoristaAlterar.getAgencia());
    	}
    	if (m.getContaCorrente() != null && !m.getContaCorrente().equals(motoristaAlterar.getContaCorrente())
    			|| (m.getContaCorrente() == null && motoristaAlterar.getContaCorrente() != null
    			&& !motoristaAlterar.getContaCorrente().isEmpty())) {
    		verificaAlteracao = Boolean.TRUE;
    		m.setContaCorrente(motoristaAlterar.getContaCorrente());
    	}
    	if (m.getOperacao() != null && !m.getOperacao().equals(motoristaAlterar.getOperacao())
    			|| (m.getOperacao() == null && motoristaAlterar.getOperacao() != null
    			&& ! motoristaAlterar.getOperacao().isEmpty())) {
    		verificaAlteracao = Boolean.TRUE;
    		m.setOperacao(motoristaAlterar.getOperacao());
    	}
    	if (m.getCep() != null && !m.getCep().isEmpty() && !m.getCep().equals(motoristaAlterar.getCep())
    			|| ((m.getCep() == null || m.getCep().isEmpty()) && motoristaAlterar.getCep() != null
    			&& !motoristaAlterar.getCep().isEmpty())) {
    		verificaAlteracao = Boolean.TRUE;
    		m.setCep(motoristaAlterar.getCep());
    	}
    	if (m.getEndereco() != null && !m.getEndereco().isEmpty() && !m.getEndereco().equals(motoristaAlterar.getEndereco())
    			|| ((m.getEndereco() == null || m.getEndereco().isEmpty()) && motoristaAlterar.getEndereco() != null
    			&& !motoristaAlterar.getEndereco().isEmpty())) {
    		verificaAlteracao = Boolean.TRUE;
    		m.setEndereco(motoristaAlterar.getEndereco());
    	}
    	if (usuarioMotorista.getLogin() != null && !usuarioMotorista.getLogin().isEmpty() 
    			&& !usuarioMotorista.getLogin().equals(motoristaAlterar.getEmail()) 
    			&& motoristaAlterar.getEmail() != null
    			&& !motoristaAlterar.getEmail().isEmpty()) {
    		verificaAlteracao = Boolean.TRUE;
    		usuarioMotorista.setLogin(motoristaAlterar.getEmail());
    	}
    	if (usuarioMotorista.getCelular() != null && !usuarioMotorista.getCelular().isEmpty() 
    			&& !usuarioMotorista.getCelular().equals(motoristaAlterar.getCelular()) 
    			&& motoristaAlterar.getCelular() != null
    			&& !motoristaAlterar.getCelular().isEmpty()) {
    		verificaAlteracao = Boolean.TRUE;
    		usuarioMotorista.setCelular(motoristaAlterar.getCelular());
    	}
    	if (verificaAlteracao) {
    		motoristaService.salvarMotorista(m);
    		usuarioService.salvarUsuario(usuarioMotorista);
    		addMsgSuccess("Motorista alterado com sucesso!");
    	} else {
    		addMsgSuccess("Nenhum dado do Motorista foi alterado de fato.");
    	}
    	setMotoristaAlterar(null);
    	pesquisarMotoristaPorFiltro();
    }
    
    public void bloquearMotorista() {
    	
    	if (motivoBloqueio != null && !motivoBloqueio.isEmpty()) {
    		definirMenu(MenuEnum.MOTORISTA.getMenu());
    		motorista = motoristaService.recuperarMotoristaPorChave(motorista.getId());
    		motorista.setStatus(StatusMotoristaEnum.BLOQUEADO_TEMPORARIAMENTE.getStatus());
    		motorista.setMotivoBloqueio(motivoBloqueio);
    		motoristaService.salvarMotorista(motorista);
    		addMsgSuccess("Motorista bloqueado com sucesso!");
    	} else {
    		addMsgErro("Para bloquear um motorista é preciso preencher um motivo.");
    	}
    	setMotorista(null);
    	setMotivoBloqueio(null);
    }
    
    public void desbloquearMotorista() {
    	
    	definirMenu(MenuEnum.MOTORISTA.getMenu());
    	motorista = motoristaService.recuperarMotoristaPorChave(motorista.getId());
    	motorista.setStatus(StatusMotoristaEnum.ATIVO.getStatus());
    	motorista.setMotivoDesautorizacao(null);
    	motorista.setMotivoBloqueio(null);
    	motoristaService.salvarMotorista(motorista);
    	addMsgSuccess("Motorista desbloqueado com sucesso!");
    	setMotorista(null);
    }
    
    public String carregarVeiculos(Motorista motorista) {
    	
    	definirMenu(MenuEnum.VEICULO.getMenu());
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("idMotorista", 
				motorista.getId());
    	return "veiculo";
    }
    
    public void carregarMotorista(Motorista motorista) {
    	setMotorista(motorista);
    	Usuario usuario = usuarioService.recuperarUsuarioPorID(motorista.getIdUsuario());
    	getMotorista().setNome(usuario.getNome());
    	getMotorista().setSexo(usuario.getSexo());
    	getMotorista().setSelfie(usuario.getImagem());
    	getMotorista().setCelular(usuario.getCelular());
    }
    
    public void carregarMotoristaAuxiliares(Motorista motorista) {
    	setMotorista(motorista);
    	Usuario usuario = usuarioService.recuperarUsuarioPorID(motorista.getIdUsuario());
    	getMotorista().setNome(usuario.getNome());
    	getMotorista().setSexo(usuario.getSexo());
    	getMotorista().setSelfie(usuario.getImagem());
    	getMotorista().setCelular(usuario.getCelular());
    	setAuxiliares(auxiliarService.recuperarAuxiliaresUsuario(usuario));
    }
    
    public void carregarMotoristaPermissionarios(Motorista motorista) {
    	
    	setMotorista(motorista);
    	Usuario usuario = usuarioService.recuperarUsuarioPorID(motorista.getIdUsuario());
    	getMotorista().setNome(usuario.getNome());
    	getMotorista().setSexo(usuario.getSexo());
    	getMotorista().setSelfie(usuario.getImagem());
    	getMotorista().setCelular(usuario.getCelular());
    	setPermissionarios(usuarioService.recuperarPermissionariosUsuario(getMotorista()));
    }
    
    public void carregarMotoristaAlterar(Motorista motorista) {
    	setMotoristaAlterar(motorista);
    	Usuario usuario = usuarioService.recuperarUsuarioPorID(motorista.getIdUsuario());
    	getMotoristaAlterar().setNome(usuario.getNome());
    	getMotoristaAlterar().setSexo(usuario.getSexo());
    	getMotoristaAlterar().setSelfie(usuario.getImagem());
    	getMotoristaAlterar().setCelular(usuario.getCelular());
    }
    
    public void pesquisarMotoristaPorFiltro() {
    	
    	setMotoristaDataModel(new MotoristaDataModel(motoristaService, filtro));
    }

    public void limparFormulario() {
    	
    	setFiltro(null);
    	setMotoristaDataModel(null);
    }
    
    public void alterarSenhaMotorista() {
    	
    	if (getNovaSenha() == null || getNovaSenha().isEmpty()
    			|| getConfirmacaoSenha() == null || getConfirmacaoSenha().isEmpty()) {
    		addMsgErro("A 'Nova Senha' e a sua confirmação são obrigatórias.");
    	} else if (!getNovaSenha().equals(getConfirmacaoSenha())) {
    		addMsgErro("A 'Nova Senha' e a sua confirmação devem ser iguais.");
    	} else {
    		
    		if (getMotorista() != null && getMotorista().getId() != null) {
    			Usuario usuario = usuarioService.recuperarUsuarioPorID(getMotorista().getIdUsuario());
    			usuarioService.trocarSenhaCliente(usuario, getNovaSenha());
    			addMsgSuccess("Senha de motorista alterada com sucesso.");
    			setNovaSenha(null);
    			setConfirmacaoSenha(null);
    		}
    		
    	}
    }

    // Métodos get/set
	public Motorista getMotorista() {
		if (motorista == null) {
			motorista = new Motorista();
		}
		return motorista;
	}
	public void setMotorista(Motorista motorista) {
		this.motorista = motorista;
	}
	public Motorista getMotoristaAlterar() {
		if (motoristaAlterar == null) {
			motoristaAlterar = new Motorista();
		}
		return motoristaAlterar;
	}

	public void setMotoristaAlterar(Motorista motoristaAlterar) {
		this.motoristaAlterar = motoristaAlterar;
	}

	public Motorista getFiltro() {
		if (filtro == null) {
			filtro = new Motorista();
		}
		return filtro;
	}
	public void setFiltro(Motorista filtro) {
		this.filtro = filtro;
	}
	public MotoristaDataModel getMotoristaDataModel() {
		return motoristaDataModel;
	}
	public void setMotoristaDataModel(MotoristaDataModel motoristaDataModel) {
		this.motoristaDataModel = motoristaDataModel;
	}
	public List<StatusMotoristaEnum> getListaStatusMotorista() {
		return Arrays.asList(StatusMotoristaEnum.values());
	}
	public String getMotivoDesautorizacao() {
		return motivoDesautorizacao;
	}
	public void setMotivoDesautorizacao(String motivoDesautorizacao) {
		this.motivoDesautorizacao = motivoDesautorizacao;
	}
	public String getMotivoBloqueio() {
		return motivoBloqueio;
	}
	public void setMotivoBloqueio(String motivoBloqueio) {
		this.motivoBloqueio = motivoBloqueio;
	}
	public List<Auxiliar> getAuxiliares() {
		return auxiliares;
	}
	public void setAuxiliares(List<Auxiliar> auxiliares) {
		this.auxiliares = auxiliares;
	}
	public List<Usuario> getPermissionarios() {
		return permissionarios;
	}
	public void setPermissionarios(List<Usuario> permissionarios) {
		this.permissionarios = permissionarios;
	}
	public List<Cidade> getListaCidade() {
		return listaCidade;
	}
	public void setListaCidade(List<Cidade> listaCidade) {
		this.listaCidade = listaCidade;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}
	public String getConfirmacaoSenha() {
		return confirmacaoSenha;
	}
	public void setConfirmacaoSenha(String confirmacaoSenha) {
		this.confirmacaoSenha = confirmacaoSenha;
	}

}