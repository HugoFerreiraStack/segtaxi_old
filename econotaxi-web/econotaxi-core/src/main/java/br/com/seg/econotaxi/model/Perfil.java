/**
 * 
 */
package br.com.seg.econotaxi.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author bruno
 *
 */
@Entity
@Table(name = "perfil")
public class Perfil implements Serializable {

	// Constantes
	private static final long serialVersionUID = -1030975315116408332L;

	// Atributos
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "nome")
	private String nome;
	@Column(name = "descricao")
	private String descricao;
	
	@OneToMany(mappedBy = "id.idPerfil", 
			targetEntity = FuncionalidadePerfil.class, 
			fetch = FetchType.EAGER, 
			cascade = CascadeType.ALL, 
			orphanRemoval = true)
	@JsonIgnore
    private List<FuncionalidadePerfil> listaPerfilFuncionalidade;
    
    @Transient
    private List<Funcionalidade> funcionalidades;
    
    @Transient
    private Long codigoFuncionalidade;
	
	/* Métodos Get/Set */
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
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public List<FuncionalidadePerfil> getListaPerfilFuncionalidade() {
		return listaPerfilFuncionalidade;
	}
	public void setListaPerfilFuncionalidade(List<FuncionalidadePerfil> listaPerfilFuncionalidade) {
		this.listaPerfilFuncionalidade = listaPerfilFuncionalidade;
	}
	public List<Funcionalidade> getFuncionalidades() {
		return funcionalidades;
	}
	public void setFuncionalidades(List<Funcionalidade> funcionalidades) {
		this.funcionalidades = funcionalidades;
	}
	public Long getCodigoFuncionalidade() {
		return codigoFuncionalidade;
	}
	public void setCodigoFuncionalidade(Long codigoFuncionalidade) {
		this.codigoFuncionalidade = codigoFuncionalidade;
	}
	@JsonIgnore
	public String getDescricaoFuncionalidades() {
		
		String str = "";
		String descricaoFuncionalidades = "";
		if (getListaPerfilFuncionalidade() != null && !getListaPerfilFuncionalidade().isEmpty()) {
			for (FuncionalidadePerfil grupo : listaPerfilFuncionalidade) {
				descricaoFuncionalidades += grupo.getFuncionalidade().getNome() + " / ";
			}
			int ind = descricaoFuncionalidades.lastIndexOf(" / ");
			str = new StringBuilder(descricaoFuncionalidades).replace(ind, ind+3, "").toString();
		}
		return str;
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
		Perfil other = (Perfil) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}