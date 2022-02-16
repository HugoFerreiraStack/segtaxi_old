package br.com.seg.econotaxi.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "usuario")
@DynamicUpdate
public class Usuario implements Serializable {

    // Constantes
	private static final long serialVersionUID = -6351124631343889905L;
	
	public Usuario() {
		super();
	}

	public Usuario(
			String login, String nome, 
			Date dataCadastro, String celular, 
			String dtNascimento, String sexo,
			Long corridas, Date dataPrimeiraCorrida,
			Date dataUltimaCorrida) {
		super();
		this.login = login;
		this.nome = nome;
		this.dataCadastro = dataCadastro;
		this.celular = celular;
		this.dtNascimento = dtNascimento;
		this.sexo = sexo;
		this.corridas = corridas;
		this.dataPrimeiraCorrida = dataPrimeiraCorrida;
		this.dataUltimaCorrida = dataUltimaCorrida;
	}
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "email")
    private String login;

    @Column(name = "senha")
    private String senha;

    @Column(name = "tipo_usuario")
    private Integer tipo;
    
    @Column(name = "nome")
    private String nome;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_cadastro")
    private Date dataCadastro;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_alteracao")
    private Date dataAlteracao;
    @Column(name = "ind_facebook")
    private Integer indicadorFacebook;
    @Column(name = "hash_facebook")
    private String hashFacebook;
    @Column(name = "ind_google")
    private Integer indicadorGoogle;
    @Column(name = "hash_google")
    private String hashGoogle;
    @Column(name = "data_nascimento")
    private Date dataNascimento;
    @Column(name = "cod_sms")
    private String codigoSms;
    @Column(name = "cod_email")
    private String codigoEmail;
    @Column(name = "hash_senha")
    private String hashSenha;
    @Column(name = "cod_senha")
    private String codigoSenha;
    @Column(name = "celular")
    private String celular;
    @Column(name = "ind_cadeirante")
    private Integer indicadorCadeirante;
    @Column(name = "hash_troca_senha")
    private String hashTrocaSenha;
    @Column(name = "ind_bloqueio")
    private Integer indicadorBloqueio;
    @Column(name = "motivo_bloqueio")
    private String motivoBloqueio;
    @Column(name = "latitude_corrente")
	private String latitudeCorrente;
	@Column(name = "longitude_corrente")
	private String longitudeCorrente;
	@Column(name = "data_ultima_posicao")
	private Date dataUltimaPosicao;
	@Column(name = "qtd_corridas_realizadas")
	private Long qtdCorridasRealizadas;
	@Column(name = "ind_tele_taxi")
	private Integer indicadorTeleTaxi;
	@Column(name = "tipo_tele_taxi")
	private Integer tipoTeleTaxi;
	@Column(name = "ind_empresa_conveniada")
	private Integer indicadorEmpresaConveniada;
	@Column(name = "id_empresa_conveniada")
	private Long idEmpresaConveniada;
	@Column(name = "id_centro_custo")
	private Long idCentroCusto;
	@Column(name = "dt_nascimento")
	private String dtNascimento;
	@Column(name = "ind_ativo")
	private Integer indicadorAtivo;
	@Column(name = "cargo_empresa")
	private Integer cargoEmpresa;

    @Column(name = "sexo")
    private String sexo;

    @Column(name = "imagem")
    private String imagem;
    
    @ManyToOne
    @JoinColumn(name = "id_radio_1", referencedColumnName = "id")
    private Radio radio1;
    @ManyToOne
    @JoinColumn(name = "id_radio_2", referencedColumnName = "id")
    private Radio radio2;
    @ManyToOne
    @JoinColumn(name = "id_empresa_conveniada", referencedColumnName = "id", updatable = false, insertable = false)
    private EmpresaConveniada empresaConveniada;
    @ManyToOne
    @JoinColumn(name = "id_centro_custo", referencedColumnName = "id", updatable = false, insertable = false)
    private CentroCusto centroCusto;

    @Column(name = "id_maxipago")
    private String idMaxipago;
    
    @Column(name = "versao_app")
    private String versaoApp;
    
    @Column(name = "cnpj_usuario")
    private String cnpjUsuario;
    
    @Column(name = "tela_inicial")
    private String telaInicial;
    
    @Column(name = "cnpj")
    private String cnpj;
    
    @JsonIgnore
    @OneToMany(mappedBy = "id.idUsuario", 
    		targetEntity = UsuarioPerfil.class, 
    		fetch = FetchType.EAGER, 
    		cascade = {CascadeType.ALL}, 
    		orphanRemoval = true)
    private List<UsuarioPerfil> listaGrupoUsuario;
    
    @Transient
    private List<Perfil> grupos;
    
    @Transient
    private List<Long> funcionalidades;
    
    @Transient
    private Long codigoPerfil;
    
    @Transient
    private Motorista motorista;
    
    @Transient
    private Veiculo veiculo;
    
    @Transient
    private Lojista lojista;
    
    @Transient
    private Long idCidade;
    
    @Transient
    private List<Veiculo> veiculosAuxiliar;
    
    @Transient
    private Integer qtdDescontoLojista;
    
    @Transient
    private Integer possuiCartao;
    
    @Transient
    private String nomeMotorista;
    
    @Transient
    private String unidadeMotorista;
    
    @Transient
    private String placaMotorista;
    
    @Transient
    private String mensagemEnviada;
    
    @Transient
    private Long corridas;
    @Transient
	private Date dataUltimaCorrida;
    @Transient
	private Date dataPrimeiraCorrida;
    
    @Transient
    private String mesAno;
    
    @Transient
    private Integer paginacao;
    
    @Transient
    private String hashImagem;
    @Transient
    private String dataNascimentoText;
    
    /* Métodos Get/Set */
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public Integer getTipo() {
        return tipo;
    }
    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Date getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	public Date getDataAlteracao() {
		return dataAlteracao;
	}
	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}
	public Integer getIndicadorFacebook() {
		return indicadorFacebook;
	}
	public void setIndicadorFacebook(Integer indicadorFacebook) {
		this.indicadorFacebook = indicadorFacebook;
	}
	public String getHashFacebook() {
		return hashFacebook;
	}
	public void setHashFacebook(String hashFacebook) {
		this.hashFacebook = hashFacebook;
	}
	public Integer getIndicadorGoogle() {
		return indicadorGoogle;
	}
	public void setIndicadorGoogle(Integer indicadorGoogle) {
		this.indicadorGoogle = indicadorGoogle;
	}
	public String getHashGoogle() {
		return hashGoogle;
	}
	public void setHashGoogle(String hashGoogle) {
		this.hashGoogle = hashGoogle;
	}
	public Date getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public String getCodigoSms() {
		return codigoSms;
	}
	public void setCodigoSms(String codigoSms) {
		this.codigoSms = codigoSms;
	}
	public String getCodigoEmail() {
		return codigoEmail;
	}
	public void setCodigoEmail(String codigoEmail) {
		this.codigoEmail = codigoEmail;
	}
	public String getHashSenha() {
		return hashSenha;
	}
	public void setHashSenha(String hashSenha) {
		this.hashSenha = hashSenha;
	}
	public String getCodigoSenha() {
		return codigoSenha;
	}
	public void setCodigoSenha(String codigoSenha) {
		this.codigoSenha = codigoSenha;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	public Integer getIndicadorCadeirante() {
		return indicadorCadeirante;
	}
	public void setIndicadorCadeirante(Integer indicadorCadeirante) {
		this.indicadorCadeirante = indicadorCadeirante;
	}
	public Radio getRadio1() {
		return radio1;
	}
	public void setRadio1(Radio radio1) {
		this.radio1 = radio1;
	}
	public Radio getRadio2() {
		return radio2;
	}
	public void setRadio2(Radio radio2) {
		this.radio2 = radio2;
	}
	public List<UsuarioPerfil> getListaGrupoUsuario() {
		return listaGrupoUsuario;
	}
	public void setListaGrupoUsuario(List<UsuarioPerfil> listaGrupoUsuario) {
		this.listaGrupoUsuario = listaGrupoUsuario;
	}
	public List<Perfil> getGrupos() {
		return grupos;
	}
	public void setGrupos(List<Perfil> grupos) {
		this.grupos = grupos;
	}
	public List<Long> getFuncionalidades() {
		return funcionalidades;
	}
	public void setFuncionalidades(List<Long> funcionalidades) {
		this.funcionalidades = funcionalidades;
	}
	public Long getCodigoPerfil() {
		return codigoPerfil;
	}
	public void setCodigoPerfil(Long codigoPerfil) {
		this.codigoPerfil = codigoPerfil;
	}
	@JsonIgnore
	public String getDescricaoPerfil() {
		
		String str = "";
		String descricaoPerfil = "";
		if (getListaGrupoUsuario() != null && !getListaGrupoUsuario().isEmpty()) {
			for (UsuarioPerfil grupo : listaGrupoUsuario) {
				descricaoPerfil += grupo.getPerfil().getNome() + " / ";
			}
			int ind = descricaoPerfil.lastIndexOf(" / ");
			str = new StringBuilder(descricaoPerfil).replace(ind, ind+3, "").toString();
		}
		return str;
	}
	public String getHashTrocaSenha() {
		return hashTrocaSenha;
	}
	public void setHashTrocaSenha(String hashTrocaSenha) {
		this.hashTrocaSenha = hashTrocaSenha;
	}
	public Integer getIndicadorBloqueio() {
		return indicadorBloqueio;
	}
	public void setIndicadorBloqueio(Integer indicadorBloqueio) {
		this.indicadorBloqueio = indicadorBloqueio;
	}
	public String getMotivoBloqueio() {
		return motivoBloqueio;
	}
	public void setMotivoBloqueio(String motivoBloqueio) {
		this.motivoBloqueio = motivoBloqueio;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
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
	public Date getDataUltimaPosicao() {
		return dataUltimaPosicao;
	}
	public void setDataUltimaPosicao(Date dataUltimaPosicao) {
		this.dataUltimaPosicao = dataUltimaPosicao;
	}
	public Motorista getMotorista() {
		return motorista;
	}
	public void setMotorista(Motorista motorista) {
		this.motorista = motorista;
	}
	public Veiculo getVeiculo() {
		return veiculo;
	}
	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}
	public Lojista getLojista() {
		return lojista;
	}
	public void setLojista(Lojista lojista) {
		this.lojista = lojista;
	}
	public Long getQtdCorridasRealizadas() {
		return qtdCorridasRealizadas;
	}
	public void setQtdCorridasRealizadas(Long qtdCorridasRealizadas) {
		this.qtdCorridasRealizadas = qtdCorridasRealizadas;
	}
	public String getIdMaxipago() {
		return idMaxipago;
	}
	public void setIdMaxipago(String idMaxipago) {
		this.idMaxipago = idMaxipago;
	}
	public Long getIdCidade() {
		return idCidade;
	}
	public void setIdCidade(Long idCidade) {
		this.idCidade = idCidade;
	}
	public List<Veiculo> getVeiculosAuxiliar() {
		return veiculosAuxiliar;
	}
	public void setVeiculosAuxiliar(List<Veiculo> veiculosAuxiliar) {
		this.veiculosAuxiliar = veiculosAuxiliar;
	}
	public Integer getIndicadorTeleTaxi() {
		return indicadorTeleTaxi;
	}
	public void setIndicadorTeleTaxi(Integer indicadorTeleTaxi) {
		this.indicadorTeleTaxi = indicadorTeleTaxi;
	}
	public Integer getTipoTeleTaxi() {
		return tipoTeleTaxi;
	}
	public void setTipoTeleTaxi(Integer tipoTeleTaxi) {
		this.tipoTeleTaxi = tipoTeleTaxi;
	}
	public Integer getQtdDescontoLojista() {
		return qtdDescontoLojista;
	}
	public void setQtdDescontoLojista(Integer qtdDescontoLojista) {
		this.qtdDescontoLojista = qtdDescontoLojista;
	}
	public Integer getPossuiCartao() {
		return possuiCartao;
	}
	public void setPossuiCartao(Integer possuiCartao) {
		this.possuiCartao = possuiCartao;
	}
	public Integer getIndicadorEmpresaConveniada() {
		return indicadorEmpresaConveniada;
	}
	public void setIndicadorEmpresaConveniada(Integer indicadorEmpresaConveniada) {
		this.indicadorEmpresaConveniada = indicadorEmpresaConveniada;
	}
	public Long getIdEmpresaConveniada() {
		return idEmpresaConveniada;
	}
	public void setIdEmpresaConveniada(Long idEmpresaConveniada) {
		this.idEmpresaConveniada = idEmpresaConveniada;
	}
	public String getDtNascimento() {
		return dtNascimento;
	}
	public void setDtNascimento(String dtNascimento) {
		this.dtNascimento = dtNascimento;
	}
	public String getNomeMotorista() {
		return nomeMotorista;
	}
	public void setNomeMotorista(String nomeMotorista) {
		this.nomeMotorista = nomeMotorista;
	}
	public String getUnidadeMotorista() {
		return unidadeMotorista;
	}
	public void setUnidadeMotorista(String unidadeMotorista) {
		this.unidadeMotorista = unidadeMotorista;
	}
	public String getPlacaMotorista() {
		return placaMotorista;
	}
	public void setPlacaMotorista(String placaMotorista) {
		this.placaMotorista = placaMotorista;
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
	public Long getCorridas() {
		return corridas;
	}
	public void setCorridas(Long corridas) {
		this.corridas = corridas;
	}
	public Date getDataUltimaCorrida() {
		return dataUltimaCorrida;
	}
	public void setDataUltimaCorrida(Date dataUltimaCorrida) {
		this.dataUltimaCorrida = dataUltimaCorrida;
	}
	public Date getDataPrimeiraCorrida() {
		return dataPrimeiraCorrida;
	}
	public void setDataPrimeiraCorrida(Date dataPrimeiraCorrida) {
		this.dataPrimeiraCorrida = dataPrimeiraCorrida;
	}
	public String getMesAno() {
		return mesAno;
	}
	public void setMesAno(String mesAno) {
		this.mesAno = mesAno;
	}
	public Integer getIndicadorAtivo() {
		return indicadorAtivo;
	}
	public void setIndicadorAtivo(Integer indicadorAtivo) {
		this.indicadorAtivo = indicadorAtivo;
	}
	public String getCnpjUsuario() {
		return cnpjUsuario;
	}
	public void setCnpjUsuario(String cnpjUsuario) {
		this.cnpjUsuario = cnpjUsuario;
	}
	public String getTelaInicial() {
		return telaInicial;
	}
	public void setTelaInicial(String telaInicial) {
		this.telaInicial = telaInicial;
	}
	public EmpresaConveniada getEmpresaConveniada() {
		return empresaConveniada;
	}
	public void setEmpresaConveniada(EmpresaConveniada empresaConveniada) {
		this.empresaConveniada = empresaConveniada;
	}
	public Integer getPaginacao() {
		return paginacao;
	}
	public void setPaginacao(Integer paginacao) {
		this.paginacao = paginacao;
	}
	public String getHashImagem() {
		return hashImagem;
	}
	public void setHashImagem(String hashImagem) {
		this.hashImagem = hashImagem;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getDataNascimentoText() {
		return dataNascimentoText;
	}
	public void setDataNascimentoText(String dataNascimentoText) {
		this.dataNascimentoText = dataNascimentoText;
	}
	public Integer getCargoEmpresa() {
		return cargoEmpresa;
	}
	public void setCargoEmpresa(Integer cargoEmpresa) {
		this.cargoEmpresa = cargoEmpresa;
	}
	public Long getIdCentroCusto() {
		return idCentroCusto;
	}
	public void setIdCentroCusto(Long idCentroCusto) {
		this.idCentroCusto = idCentroCusto;
	}
	public CentroCusto getCentroCusto() {
		return centroCusto;
	}
	public void setCentroCusto(CentroCusto centroCusto) {
		this.centroCusto = centroCusto;
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
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}