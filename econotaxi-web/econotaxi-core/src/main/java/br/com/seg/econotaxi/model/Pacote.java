/**
 * 
 */
package br.com.seg.econotaxi.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author bruno
 *
 */
@Entity
@Table(name = "pacote")
public class Pacote implements Serializable {

	// Constantes
	private static final long serialVersionUID = -8634278876886015876L;
	
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	@Column(name = "latitude_origem")
	private String latitudeOrigem;
	@Column(name = "longitude_origem")
	private String longitudeOrigem;
	@Column(name = "latitude_destino")
	private String latitudeDestino;
	@Column(name = "longitude_destino")
	private String longitudeDestino;
	@Column(name = "descricao")
	private String descricao;
	@Column(name = "preco")
	private BigDecimal preco;
	@Column(name = "desconto_porcentagem")
	private BigDecimal descontoPorcentagem;
	@Column(name = "titulo")
	private String titulo;
	@Column(name = "duracao_horas")
	private String duracaoHoras;
	
	@ManyToOne
	@JoinColumn(name = "id_cidade", referencedColumnName = "id")
	private Cidade cidade;
	
	@Transient
	private List<LocalPacote> locais;
	
	@Transient
	private Usuario usuario;
	@Transient
	private String origem;
	@Transient
	private String origemEndereco;

	/* Métodos Get/Set */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLatitudeOrigem() {
		return latitudeOrigem;
	}

	public void setLatitudeOrigem(String latitudeOrigem) {
		this.latitudeOrigem = latitudeOrigem;
	}

	public String getLongitudeOrigem() {
		return longitudeOrigem;
	}

	public void setLongitudeOrigem(String longitudeOrigem) {
		this.longitudeOrigem = longitudeOrigem;
	}

	public String getLatitudeDestino() {
		return latitudeDestino;
	}

	public void setLatitudeDestino(String latitudeDestino) {
		this.latitudeDestino = latitudeDestino;
	}

	public String getLongitudeDestino() {
		return longitudeDestino;
	}

	public void setLongitudeDestino(String longitudeDestino) {
		this.longitudeDestino = longitudeDestino;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public BigDecimal getDescontoPorcentagem() {
		return descontoPorcentagem;
	}

	public void setDescontoPorcentagem(BigDecimal descontoPorcentagem) {
		this.descontoPorcentagem = descontoPorcentagem;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	
	public String getDuracaoHoras() {
		return duracaoHoras;
	}

	public void setDuracaoHoras(String duracaoHoras) {
		this.duracaoHoras = duracaoHoras;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}

	public String getOrigemEndereco() {
		return origemEndereco;
	}

	public void setOrigemEndereco(String origemEndereco) {
		this.origemEndereco = origemEndereco;
	}
	
	public List<LocalPacote> getLocais() {
		return locais;
	}

	public void setLocais(List<LocalPacote> locais) {
		this.locais = locais;
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
		Pacote other = (Pacote) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}