/**
 * 
 */
package br.com.seg.econotaxi.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author bruno
 *
 */
@Entity
@Table(name = "empresa_conveniada")
public class EmpresaConveniada implements Serializable {

	// Constantes
	private static final long serialVersionUID = -2309683406034755581L;
	
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	@Column(name = "nome")
	private String nome;
	@Column(name = "cnpj")
	private String cnpj;
	@Column(name = "razao_social")
	private String razaoSocial;
	@Column(name = "email")
	private String email;
	@Column(name = "data_cadastro")
	private Date dataCadastro;
	@Column(name = "numero_inicio_voucher")
	private String numeroInicioVoucher;
	@Column(name = "numero_final_voucher")
	private String numeroFinalVoucher;
	@Column(name = "ind_voucher_eletronico")
	private Integer indicadorVoucherEletronico;
	@Column(name = "vl_km_rodado")
	private BigDecimal valorKmRodado;
	@Column(name = "desconto_porcentagem")
	private BigDecimal descontoPorcentagem;
	@Column(name = "ind_carros_selecionados")
	private Integer indicadorCarrosSelecionados;
	
	/* Métodos get/set */
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getRazaoSocial() {
		return razaoSocial;
	}
	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	public String getNumeroInicioVoucher() {
		return numeroInicioVoucher;
	}
	public void setNumeroInicioVoucher(String numeroInicioVoucher) {
		this.numeroInicioVoucher = numeroInicioVoucher;
	}
	public String getNumeroFinalVoucher() {
		return numeroFinalVoucher;
	}
	public void setNumeroFinalVoucher(String numeroFinalVoucher) {
		this.numeroFinalVoucher = numeroFinalVoucher;
	}
	public Integer getIndicadorVoucherEletronico() {
		return indicadorVoucherEletronico;
	}
	public void setIndicadorVoucherEletronico(Integer indicadorVoucherEletronico) {
		this.indicadorVoucherEletronico = indicadorVoucherEletronico;
	}
	public BigDecimal getValorKmRodado() {
		return valorKmRodado;
	}
	public void setValorKmRodado(BigDecimal valorKmRodado) {
		this.valorKmRodado = valorKmRodado;
	}
	public BigDecimal getDescontoPorcentagem() {
		return descontoPorcentagem;
	}
	public void setDescontoPorcentagem(BigDecimal descontoPorcentagem) {
		this.descontoPorcentagem = descontoPorcentagem;
	}
	public Integer getIndicadorCarrosSelecionados() {
		return indicadorCarrosSelecionados;
	}
	public void setIndicadorCarrosSelecionados(Integer indicadorCarrosSelecionados) {
		this.indicadorCarrosSelecionados = indicadorCarrosSelecionados;
	}
	public String getDescricaoIndicadorCarrosSelecionados() {
		String descricao = "";
		if (getIndicadorCarrosSelecionados() != null) {
			if (getIndicadorCarrosSelecionados().equals(1)) {
				descricao = "Sim";
			} else {
				descricao = "Não";
			}
		} else {
			descricao = "Não";
		}
		return descricao;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmpresaConveniada other = (EmpresaConveniada) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}