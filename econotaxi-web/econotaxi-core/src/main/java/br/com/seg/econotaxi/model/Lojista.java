/**
 * 
 */
package br.com.seg.econotaxi.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.seg.econotaxi.enums.StatusMotoristaEnum;

/**
 * @author bruno
 *
 */
@Entity
@Table(name = "lojista")
public class Lojista implements Serializable {

	// Constantes
	private static final long serialVersionUID = 8987534014408040651L;

	public Lojista() { }
	
	public Lojista(Lojista lojista, Usuario usuario) {
		
		super();
		this.id = lojista.getId();
		this.nomeLoja = lojista.getNomeLoja();
		this.cnpj = lojista.getCnpj();
		this.endereco = lojista.getEndereco();
		this.telefone = lojista.getTelefone();
		this.cep = lojista.getCep();
		this.status = lojista.getStatus();
		this.idUsuario = lojista.getIdUsuario();
		this.dataCadastro = lojista.getDataCadastro();
		this.idCidade = lojista.getIdCidade();
		this.motivoBloqueio = lojista.getMotivoBloqueio();
		this.motivoDesautorizacao = lojista.getMotivoDesautorizacao();
		this.nome = usuario.getNome();
		this.selfie = usuario.getImagem();
		this.sexo = usuario.getSexo();
		this.celular = usuario.getCelular();
		this.email = usuario.getLogin();
	}

	// Atributos
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@Column(name = "nome_loja")
	private String nomeLoja;
	@Column(name = "cnpj")
	private String cnpj;
	@Column(name = "endereco")
	private String endereco;
	@Column(name = "telefone")
	private String telefone;
	@Column(name = "cep")
	private String cep;
	@Column(name = "status")
	private Integer status;
	@Column(name = "id_usuario")
	private Long idUsuario;
	@Column(name = "data_cadastro")
	private Date dataCadastro;
	@Column(name = "id_cidade")
	private Long idCidade;
	@Column(name = "motivo_desautorizacao")
	private String motivoDesautorizacao;
	@Column(name = "motivo_bloqueio")
	private String motivoBloqueio;
	
	@Transient
	private String nome;
	
	@Transient
	private String selfie;
	
	@Transient
	private String sexo;
	
	@Transient
	private String celular;
	
	@Transient
	private String email;
	
	/* Métodos get/set */
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNomeLoja() {
		return nomeLoja;
	}
	public void setNomeLoja(String nomeLoja) {
		this.nomeLoja = nomeLoja;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public Date getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	public Long getIdCidade() {
		return idCidade;
	}
	public void setIdCidade(Long idCidade) {
		this.idCidade = idCidade;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSelfie() {
		return selfie;
	}
	public void setSelfie(String selfie) {
		this.selfie = selfie;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getMotivoBloqueio() {
		return motivoBloqueio;
	}
	public void setMotivoBloqueio(String motivoBloqueio) {
		this.motivoBloqueio = motivoBloqueio;
	}
	public String getMotivoDesautorizacao() {
		return motivoDesautorizacao;
	}
	public void setMotivoDesautorizacao(String motivoDesautorizacao) {
		this.motivoDesautorizacao = motivoDesautorizacao;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescricaoStatus() {
		
		String descricaoStatus = "";
		if (status != null) {
			descricaoStatus = StatusMotoristaEnum.valueOfStatus(status).getDescricao();
		}
		return descricaoStatus;
	}
	
}