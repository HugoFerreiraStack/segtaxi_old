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

/**
 * @author bruno
 *
 */
@Entity
@Table(name = "auxiliar")
public class Auxiliar implements Serializable {

	// Constantes
	private static final long serialVersionUID = 1831803987065027016L;

	// Atributos
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "id_usuario")
    private Long idUsuario;
    @Column(name = "id_veiculo")
    private Long idVeiculo;
    @Column(name = "nome")
    private String nome;
    @Column(name = "status")
    private Integer status;
    @Column(name = "data_pedido")
    private Date dataPedido;
    @Column(name = "data_autorizado")
    private Date dataAutorizado;
    @Column(name = "email")
    private String email;
    @Column(name = "cpf")
    private String cpf;
	
    /* Métodos get/set */
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public Long getIdVeiculo() {
		return idVeiculo;
	}
	public void setIdVeiculo(Long idVeiculo) {
		this.idVeiculo = idVeiculo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getDataPedido() {
		return dataPedido;
	}
	public void setDataPedido(Date dataPedido) {
		this.dataPedido = dataPedido;
	}
	public Date getDataAutorizado() {
		return dataAutorizado;
	}
	public void setDataAutorizado(Date dataAutorizado) {
		this.dataAutorizado = dataAutorizado;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
    
}