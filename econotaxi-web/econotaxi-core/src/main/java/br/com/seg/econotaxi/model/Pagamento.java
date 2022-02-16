/**
 * 
 */
package br.com.seg.econotaxi.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import br.com.seg.econotaxi.enums.StatusPagamentoEnum;

/**
 * @author bruno
 *
 */
@Entity
@Table(name = "pagamento")
public class Pagamento implements Serializable {

	// Constantes
	private static final long serialVersionUID = 3272581314786024924L;

	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	@Column(name = "tipo")
	private Integer tipo;
	@Column(name = "bandeira")
	private Integer bandeira;
	@Column(name = "ind_verificado")
	private Integer indicadorVerificado;
	@Column(name = "ind_falha")
	private Integer indicadorFalha;
	@Column(name = "numero")
	private String numero;
	@Column(name = "cvv")
	private String cvv;
	@Column(name = "numero_quatro_digitos")
	private String numeroQuatroDigitos;
	@Column(name = "validade")
	private String validade;
	@Column(name = "nome_impresso")
	private String nomeImpresso;
	@Column(name = "data_cadastro")
	private Date dataCadastro;
	@Column(name = "status")
	private Integer status;
	@Column(name = "hash_paypal")
	private String hashPaypal;
	@Column(name = "id_paypal")
	private String idPaypal;

	@Column(name = "token_maxipago")
	private String tokenMaxipago;
	
	@Column(name = "ind_padrao")
	private Integer indicadorPadrao;
	
	@ManyToOne
	@JoinColumn(name = "id_usuario", referencedColumnName = "id")
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "id_endereco_pag", referencedColumnName = "id")
	private PagamentoEndereco pagamentoEndereco;

	/* Métodos Get/Set */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public Integer getBandeira() {
		return bandeira;
	}

	public void setBandeira(Integer bandeira) {
		this.bandeira = bandeira;
	}

	public Integer getIndicadorVerificado() {
		return indicadorVerificado;
	}

	public void setIndicadorVerificado(Integer indicadorVerificado) {
		this.indicadorVerificado = indicadorVerificado;
	}

	public Integer getIndicadorFalha() {
		return indicadorFalha;
	}

	public void setIndicadorFalha(Integer indicadorFalha) {
		this.indicadorFalha = indicadorFalha;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public String getNumeroQuatroDigitos() {
		return numeroQuatroDigitos;
	}

	public void setNumeroQuatroDigitos(String numeroQuatroDigitos) {
		this.numeroQuatroDigitos = numeroQuatroDigitos;
	}

	public String getValidade() {
		return validade;
	}

	public void setValidade(String validade) {
		this.validade = validade;
	}

	public String getNomeImpresso() {
		return nomeImpresso;
	}

	public void setNomeImpresso(String nomeImpresso) {
		this.nomeImpresso = nomeImpresso;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getHashPaypal() {
		return hashPaypal;
	}

	public void setHashPaypal(String hashPaypal) {
		this.hashPaypal = hashPaypal;
	}

	public String getIdPaypal() {
		return idPaypal;
	}

	public void setIdPaypal(String idPaypal) {
		this.idPaypal = idPaypal;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public String getDescricaoStatus() {
		
		String descricaoStatus = "";
		if (status != null) {
			descricaoStatus = StatusPagamentoEnum.valueOfStatus(status).getDescricao();
		}
		return descricaoStatus;
	}

	public PagamentoEndereco getPagamentoEndereco() {
		return pagamentoEndereco;
	}

	public void setPagamentoEndereco(PagamentoEndereco pagamentoEndereco) {
		this.pagamentoEndereco = pagamentoEndereco;
	}

	public String getTokenMaxipago() {
		return tokenMaxipago;
	}

	public void setTokenMaxipago(String tokenMaxipago) {
		this.tokenMaxipago = tokenMaxipago;
	}

	public Integer getIndicadorPadrao() {
		return indicadorPadrao;
	}

	public void setIndicadorPadrao(Integer indicadorPadrao) {
		this.indicadorPadrao = indicadorPadrao;
	}
	
}