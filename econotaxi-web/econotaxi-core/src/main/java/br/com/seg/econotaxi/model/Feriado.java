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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author bruno
 *
 */
@Entity
@Table(name = "feriado")
public class Feriado implements Serializable {

	// Constantes
	private static final long serialVersionUID = -5356387898608532979L;

	// Atributos
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	@Column(name = "nome")
	private String nome;
	@Column(name = "data_feriado")
	private Date dataFeriado;
	@Column(name = "ind_nacional")
	private Integer indicadorNacional;
	@Column(name = "id_cidade", insertable = false, updatable = false)
    private Long idCidade;
	
	@ManyToOne
	@JoinColumn(name = "id_cidade", referencedColumnName = "id")
	private Cidade cidade;

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
	public Date getDataFeriado() {
		return dataFeriado;
	}
	public void setDataFeriado(Date dataFeriado) {
		this.dataFeriado = dataFeriado;
	}
	public Cidade getCidade() {
		return cidade;
	}
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	public Integer getIndicadorNacional() {
		return indicadorNacional;
	}
	public void setIndicadorNacional(Integer indicadorNacional) {
		this.indicadorNacional = indicadorNacional;
	}
	public Long getIdCidade() {
		return idCidade;
	}
	public void setIdCidade(Long idCidade) {
		this.idCidade = idCidade;
	}
	
}