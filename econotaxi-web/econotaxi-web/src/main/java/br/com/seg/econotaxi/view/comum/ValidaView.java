package br.com.seg.econotaxi.view.comum;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import br.com.seg.econotaxi.enums.TipoUsuarioEnum;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.service.UsuarioService;
import br.com.seg.econotaxi.view.BaseView;

/**
 * Classe de visão responsável pelo controle da tela de Dashboard.
 *
 * Criado em 25 de jun de 2017
 * @author Bruno rocha
 */
@Named
@ManagedBean
@Scope("view")
public class ValidaView extends BaseView implements Serializable {

	// Constantes
	private static final long serialVersionUID = -4568868033038316993L;
	
	// Atributos
	private String celular;
	
	@Autowired
	private UsuarioService usuarioService;
	
	/**
	 * Método responsável pela inicialização de informações na tela.
	 */
	@PostConstruct
	public void init() {
		
	}
	
	/**
	 * Método responsável por carregar Informações de Dashboard.
	 */
	public void validarCelular() {
		
		Usuario usuario = usuarioService.consultarUsuarioPorCelular(
				getCelular().replace("(", "").replace(")", "").replace("-", "").replaceAll(" ", ""));
		if (usuario == null || usuario.getId() == null) {
			addMsgErro("Usuário não existente.");
		} else {
			if (usuario.getTipo().equals(TipoUsuarioEnum.MOTORISTA.getCodigo())) {
				addMsgInfo("Usuário existente. ");
				addMsgInfo("O usuário é um motorista do aplicativo.");
			} else if (usuario.getTipo().equals(TipoUsuarioEnum.CLIENTE.getCodigo())) {
				addMsgInfo("Usuário existente.");
				addMsgInfo("O usuário é um passageiro/cliente do aplicativo.");
			} else if (usuario.getTipo().equals(TipoUsuarioEnum.LOJISTA.getCodigo())) {
				addMsgInfo("Usuário existente. ");
				addMsgInfo("O usuário é um Lojista e passageiro/cliente do aplicativo.");
			} else {
				addMsgInfo("Usuário existente. ");
				addMsgInfo("O usuário é um administrador do aplicativo.");
			}
		}
	}

	/* Métodos get/set */
	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

}