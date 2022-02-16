/**
 * 
 */
package br.com.seg.econotaxi.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.seg.econotaxi.enums.StatusMotoristaEnum;

/**
 * @author bruno
 *
 */
@Entity
@Table(name = "motorista_ligue")
public class MotoristaLigue implements Serializable {

	// Constantes
	private static final long serialVersionUID = 1780159195528691189L;
	
	public MotoristaLigue() { super(); }
	
	public MotoristaLigue(MotoristaLigue m) {
		super();
		this.id = m.getId();
		this.dataNascimento = m.getDataNascimento();
		this.cpf = m.getCpf();
		this.nota = m.getNota();
		this.status = m.getStatus();
		this.nomeMae = m.getNomeMae();
		this.rg = m.getRg();
		this.descricao = m.getDescricao();
		this.cidadeMotorista = m.getCidadeMotorista();
		this.siglaUf = m.getSiglaUf();
		this.latitudeCorrente = m.getLatitudeCorrente();
		this.longitudeCorrente = m.getLongitudeCorrente();
		this.indicadorOnline = m.getIndicadorOnline();
		this.dataUltimaPosicao = m.getDataUltimaPosicao();
		this.dataCadastro = m.getDataCadastro();
		this.celular = m.getCelular();
		this.telefone = m.getTelefone();
		this.motivoDesautorizacao = m.getMotivoDesautorizacao();
		this.motivoBloqueio = m.getMotivoBloqueio();
		this.carteira = m.getCarteira();
		this.imagemCarteira = m.getImagemCarteira();
		this.cidade = m.getCidade();
		this.classificacao = m.getClassificacao();
		this.qtdClassificacao = m.getQtdClassificacao();
		this.nome = m.getNome();
		this.sexo = m.getSexo();
		this.imagemCarteiraTaxista = m.getImagemCarteiraTaxista();
		this.idVeiculo = m.getIdVeiculo();
		this.indicadorAuxiliar = m.getIndicadorAuxiliar();
		this.indicadorPermissionario = m.getIndicadorPermissionario();
		this.indicadorMaquinaDebito = m.getIndicadorMaquinaDebito();
		this.dataVencimentoMotorista = m.getDataVencimentoMotorista();
		this.dataVencimentoTaxi = m.getDataVencimentoTaxi();
		this.carteiraTaxista = m.getCarteiraTaxista();
		this.tipoTeleTaxi = m.getTipoTeleTaxi();
		this.indicadorAceitaVoucher = m.getIndicadorAceitaVoucher();
		this.banco = m.getBanco();
		this.agencia = m.getAgencia();
		this.contaCorrente = m.getContaCorrente();
		this.operacao = m.getOperacao();
		this.formaRecebimento = m.getFormaRecebimento();
	}
	
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@Column(name = "data_nascimento")
	private String dataNascimento;
	@Column(name = "cpf")
	private String cpf;
	@Column(name = "nota")
	private Double nota;
	@Column(name = "status")
	private Integer status;
	@Column(name = "nome")
	private String nome;
	@Column(name = "nome_mae")
	private String nomeMae;
	@Column(name = "rg")
	private String rg;
	@Column(name = "descricao")
	private String descricao;
	@Column(name = "cidade")
	private String cidadeMotorista;
	@Column(name = "sigla_uf")
	private String siglaUf;
	@Column(name = "latitude_corrente")
	private String latitudeCorrente;
	@Column(name = "longitude_corrente")
	private String longitudeCorrente;
	@Column(name = "ind_online")
	private Integer indicadorOnline;
	@Column(name = "data_ultima_posicao")
	private Date dataUltimaPosicao;
	@Column(name = "data_cadastro")
	private Date dataCadastro;
	@Column(name = "celular")
	private String celular;
	@Column(name = "telefone")
	private String telefone;
	@Column(name = "motivo_desautorizacao")
	private String motivoDesautorizacao;
	@Column(name = "motivo_bloqueio")
	private String motivoBloqueio;
	@Column(name = "carteira")
	private String carteira;
	@Column(name = "imagem_carteira")
	private String imagemCarteira;
	@Column(name = "classificacao")
	private Double classificacao;
	@Column(name = "qtd_classificacao")
	private Integer qtdClassificacao;
	@Column(name = "carteira_taxista")
	private String carteiraTaxista;
	@Column(name = "data_vencimento_motorista")
	private String dataVencimentoMotorista;
	@Column(name = "data_vencimento_taxi")
	private String dataVencimentoTaxi;
	@Column(name = "ind_maquina_debito")
	private Integer indicadorMaquinaDebito;
	@Column(name = "banco")
	private String banco;
	@Column(name = "agencia")
	private String agencia;
	@Column(name = "conta_corrente")
	private String contaCorrente;
	@Column(name = "operacao")
	private String operacao;
	@Column(name = "forma_recebimento")
	private Integer formaRecebimento;
	
	@ManyToOne
	@JoinColumn(name = "id_cidade", referencedColumnName = "id")
	private Cidade cidade;
	
	@Column(name = "imagem_carteira_taxista")
	private String imagemCarteiraTaxista; 
	
	@Column(name = "id_veiculo")
	private Long idVeiculo;
	
	@Column(name = "ind_permissionario")
	private Integer indicadorPermissionario;
	
	@Column(name = "ind_auxiliar")
	private Integer indicadorAuxiliar;
	
	@Column(name = "ind_particular")
	private Integer indicadorParticular;
	
	@Column(name = "tipo_tele_taxi")
	private Integer tipoTeleTaxi;
	
	@Column(name = "ind_aceita_voucher")
	private Integer indicadorAceitaVoucher;
	
	@Column(name = "sexo")
	private String sexo;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "endereco")
	private String endereco;
	
	@Column(name = "cep")
	private String cep;
	
	@Transient
	private Long idCidade;
	
	@Transient
	private String selfie;
	
	
	@Transient
	private Veiculo veiculoCorrente;
	
	@Transient
	private Integer tipoMotorista;
	
	@Transient
	private String versaoApp;
	
	@Transient
	private String mensagemEnviada;
	
	/* Métodos Get/Set */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Double getNota() {
		return nota;
	}

	public void setNota(Double nota) {
		this.nota = nota;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getNomeMae() {
		return nomeMae;
	}

	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCidadeMotorista() {
		return cidadeMotorista;
	}

	public void setCidadeMotorista(String cidadeMotorista) {
		this.cidadeMotorista = cidadeMotorista;
	}

	public String getSiglaUf() {
		return siglaUf;
	}

	public void setSiglaUf(String siglaUf) {
		this.siglaUf = siglaUf;
	}

	public String getLatitudeCorrente() {
		return latitudeCorrente;
	}

	public void setLatitudeCorrente(String latitudeCorrente) {
		this.latitudeCorrente = latitudeCorrente;
	}

	public String getLongitudeCorrente() {
		return longitudeCorrente;
	}

	public void setLongitudeCorrente(String longitudeCorrente) {
		this.longitudeCorrente = longitudeCorrente;
	}

	public Integer getIndicadorOnline() {
		return indicadorOnline;
	}

	public void setIndicadorOnline(Integer indicadorOnline) {
		this.indicadorOnline = indicadorOnline;
	}

	public Date getDataUltimaPosicao() {
		return dataUltimaPosicao;
	}

	public void setDataUltimaPosicao(Date dataUltimaPosicao) {
		this.dataUltimaPosicao = dataUltimaPosicao;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public String getDescricaoStatus() {
		
		String descricaoStatus = "";
		if (status != null) {
			descricaoStatus = StatusMotoristaEnum.valueOfStatus(status).getDescricao();
		}
		return descricaoStatus;
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

	public String getCarteira() {
		return carteira;
	}

	public void setCarteira(String carteira) {
		this.carteira = carteira;
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

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getImagemCarteira() {
		return imagemCarteira;
	}

	public void setImagemCarteira(String imagemCarteira) {
		this.imagemCarteira = imagemCarteira;
	}

	public String getSelfie() {
		return selfie;
	}

	public void setSelfie(String selfie) {
		this.selfie = selfie;
	}

	public Double getClassificacao() {
		return classificacao;
	}

	public void setClassificacao(Double classificacao) {
		this.classificacao = classificacao;
	}

	public Integer getQtdClassificacao() {
		return qtdClassificacao;
	}

	public void setQtdClassificacao(Integer qtdClassificacao) {
		this.qtdClassificacao = qtdClassificacao;
	}

	public String getImagemCarteiraTaxista() {
		return imagemCarteiraTaxista;
	}

	public void setImagemCarteiraTaxista(String imagemCarteiraTaxista) {
		this.imagemCarteiraTaxista = imagemCarteiraTaxista;
	}

	public Long getIdVeiculo() {
		return idVeiculo;
	}

	public void setIdVeiculo(Long idVeiculo) {
		this.idVeiculo = idVeiculo;
	}

	public Integer getIndicadorPermissionario() {
		return indicadorPermissionario;
	}

	public void setIndicadorPermissionario(Integer indicadorPermissionario) {
		this.indicadorPermissionario = indicadorPermissionario;
	}

	public Integer getIndicadorAuxiliar() {
		return indicadorAuxiliar;
	}

	public void setIndicadorAuxiliar(Integer indicadorAuxiliar) {
		this.indicadorAuxiliar = indicadorAuxiliar;
	}

	public Integer getIndicadorParticular() {
		return indicadorParticular;
	}

	public void setIndicadorParticular(Integer indicadorParticular) {
		this.indicadorParticular = indicadorParticular;
	}

	public Veiculo getVeiculoCorrente() {
		return veiculoCorrente;
	}

	public void setVeiculoCorrente(Veiculo veiculoCorrente) {
		this.veiculoCorrente = veiculoCorrente;
	}

	public String getCarteiraTaxista() {
		return carteiraTaxista;
	}

	public void setCarteiraTaxista(String carteiraTaxista) {
		this.carteiraTaxista = carteiraTaxista;
	}

	public String getDataVencimentoMotorista() {
		return dataVencimentoMotorista;
	}

	public void setDataVencimentoMotorista(String dataVencimentoMotorista) {
		this.dataVencimentoMotorista = dataVencimentoMotorista;
	}

	public String getDataVencimentoTaxi() {
		return dataVencimentoTaxi;
	}

	public void setDataVencimentoTaxi(String dataVencimentoTaxi) {
		this.dataVencimentoTaxi = dataVencimentoTaxi;
	}

	public Integer getIndicadorMaquinaDebito() {
		return indicadorMaquinaDebito;
	}

	public void setIndicadorMaquinaDebito(Integer indicadorMaquinaDebito) {
		this.indicadorMaquinaDebito = indicadorMaquinaDebito;
	}

	public Integer getTipoTeleTaxi() {
		return tipoTeleTaxi;
	}

	public void setTipoTeleTaxi(Integer tipoTeleTaxi) {
		this.tipoTeleTaxi = tipoTeleTaxi;
	}
	
	public Integer getIndicadorAceitaVoucher() {
		return indicadorAceitaVoucher;
	}

	public void setIndicadorAceitaVoucher(Integer indicadorAceitaVoucher) {
		this.indicadorAceitaVoucher = indicadorAceitaVoucher;
	}
	
	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getContaCorrente() {
		return contaCorrente;
	}

	public void setContaCorrente(String contaCorrente) {
		this.contaCorrente = contaCorrente;
	}

	public String getOperacao() {
		return operacao;
	}

	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}

	public Integer getTipoMotorista() {
		return tipoMotorista;
	}

	public void setTipoMotorista(Integer tipoMotorista) {
		this.tipoMotorista = tipoMotorista;
	}

	public Boolean getDataMotoristaVencida() {
		
		Boolean verifica = Boolean.FALSE;
		try {
			if (getDataVencimentoMotorista() != null && !getDataVencimentoMotorista().isEmpty()
					&& new Date().getTime() > new SimpleDateFormat("ddMMyyyy").parse(
							getDataVencimentoMotorista().replaceAll("/", "").replaceAll("-", "")).getTime()) {
				verifica = Boolean.TRUE;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return verifica;
	}
	
	public Boolean getDataTaxistaVencida() {
		
		Boolean verifica = Boolean.FALSE;
		if (getIndicadorPermissionario() != null && getIndicadorPermissionario().equals(1)
				|| (getIndicadorAuxiliar() != null && getIndicadorAuxiliar().equals(1))) {
			
			try {
				if (getDataVencimentoTaxi() != null && !getDataVencimentoTaxi().isEmpty()
						&& new Date().getTime() > new SimpleDateFormat("ddMMyyyy").parse(
								getDataVencimentoTaxi().replaceAll("/", "").replaceAll("-", "")).getTime()) {
					verifica = Boolean.TRUE;
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return verifica;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public Integer getFormaRecebimento() {
		return formaRecebimento;
	}

	public void setFormaRecebimento(Integer formaRecebimento) {
		this.formaRecebimento = formaRecebimento;
	}
	
	public String getVersaoApp() {
		return versaoApp;
	}

	public void setVersaoApp(String versaoApp) {
		this.versaoApp = versaoApp;
	}
	
	public String getMensagemEnviada() {
		return mensagemEnviada;
	}

	public void setMensagemEnviada(String mensagemEnviada) {
		this.mensagemEnviada = mensagemEnviada;
	}
	
	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
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
		MotoristaLigue other = (MotoristaLigue) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}