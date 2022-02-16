package br.com.seg.econotaxi.vo;

import br.com.seg.econotaxi.enums.TipoVeiculoEnum;
import br.com.seg.econotaxi.model.Corrida;
import br.com.seg.econotaxi.model.Motorista;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.util.DateUtil;

import java.math.BigDecimal;
import java.util.Date;

public class MinhaCorridaVo {

    private Long idCorrida;
    private String origem;
    private String destino;

    private Long idMotorista;
    private Long idMotoristaUsuario;
    private String motora;

    private Long idVeiculo;
    private String marca;
    private String modelo;
    private String cor;
    private String placa;
    private Integer tipo;
    private String tipoDescricao;

    private Long duracao;
    private String duracaoDescricao;
    private Date dataSolicitacao;
    private String dataSolicitacaoFormatada;
    private String dataInicioFormatada;
    private String dataFimFormatada;
    private BigDecimal valor;
    
    private Double qtdClassificacao;

    private Long idPagamento;
    private Integer bandeira;
    
    private Motorista motorista;
    
    private Integer status;
    
    private String origemEndereco;
    private String destinoEndereco;
    private String origemLatitude;
    private String origemLongitude;
    private String destinoLatitude;
    private String destinoLongitude;
    private Date dataFinalizacao;
    private Double classificacaoCorrida;
    private Double classificacaoVeiculo;
    private String classificacaoDescricao;
    
    private Integer tipoDenuncia;
    private String denuncia;
    private BigDecimal valorFinal;
    private BigDecimal descontoCorrida;
    private String voucher;
    private Long idEmpresaConveniada;
    private String observacoes;
    private String observacoesTele;
    private String motivoCancelamento;
    private String motivoRecuperacao;
    private Integer tipoVeiculo;
    
    private String nomeMotorista;
    private String sexoMotorista;
    
    private Integer formaPagamento;
    

    public MinhaCorridaVo(Long idCorrida, String origem, String destino, Long idMotorista, Long idMotoristaUsuario,
                          Long idVeiculo, String marca, String modelo, String cor, String placa, Integer tipo,
                          Date dataSolicitacao, Date dataInicio, Date dataFinal, Double qtdClassificacao, Motorista motorista, BigDecimal valor,
                          Integer status) {

        this.idCorrida = idCorrida;
        this.origem = origem;
        this.destino = destino;

        this.idMotorista = idMotorista;
        this.idMotoristaUsuario = idMotoristaUsuario;

        this.idVeiculo = idVeiculo;
        this.marca = marca;
        this.modelo = modelo;
        this.cor = cor;
        this.placa = placa;
        this.tipo = tipo;
        this.status = status;

        if (this.tipo != null) {
            TipoVeiculoEnum tipoVeiculoEnum = TipoVeiculoEnum.valueOfCodigo(this.tipo);
            if (tipoVeiculoEnum != null) {
                this.tipoDescricao = tipoVeiculoEnum.getDescricao();
            }
        }

        if (dataInicio != null && dataFinal != null) {
            this.duracaoDescricao = DateUtil.formatarDuracao(dataInicio, dataFinal);
        }

        this.dataSolicitacao = dataSolicitacao;
        this.dataFinalizacao = dataFinal;

        if (this.dataSolicitacao != null) {
            StringBuilder ds = new StringBuilder();
            ds.append(DateUtil.formatarData(this.dataSolicitacao, "dd/MM/yyyy"));
            ds.append(" às ");
            ds.append(DateUtil.formatarData(this.dataSolicitacao, "HH:mm:ss"));

            this.dataSolicitacaoFormatada = ds.toString();
        }
        
        if (dataInicio != null) {
            StringBuilder ds = new StringBuilder();
            ds.append(DateUtil.formatarData(dataInicio, "dd/MM/yyyy"));
            ds.append(" às ");
            ds.append(DateUtil.formatarData(dataInicio, "HH:mm:ss"));

            this.dataInicioFormatada = ds.toString();
        }
        
        if (dataFinal != null) {
            StringBuilder ds = new StringBuilder();
            ds.append(DateUtil.formatarData(dataFinal, "dd/MM/yyyy"));
            ds.append(" às ");
            ds.append(DateUtil.formatarData(dataFinal, "HH:mm:ss"));

            this.dataFimFormatada = ds.toString();
        }
        this.qtdClassificacao = qtdClassificacao;
        this.motorista = motorista;

        this.valor = valor;
//        this.idPagamento = idPagamento;
//        this.bandeira = bandeira;
    }
    
    
    public MinhaCorridaVo(Corrida c, Long idMotorista, Long idMotoristaUsuario, 
            Long idVeiculo, String marca, String modelo, String cor, String placa, Integer tipoVeiculo,
            Motorista motorista, Usuario usuarioMotorista) {

		this.idCorrida = c.getId();
		this.origem = c.getOrigem();
		this.origemEndereco = c.getOrigemEndereco();
		this.destinoEndereco = c.getDestinoEndereco();
		this.destino = c.getDestino();
		
		this.voucher = c.getVoucher();
		this.idEmpresaConveniada = c.getIdEmpresaConveniada();

		this.idMotorista = idMotorista;
		this.idMotoristaUsuario = idMotoristaUsuario;

		this.idVeiculo = idVeiculo;
		this.marca = marca;
		this.modelo = modelo;
		this.cor = cor;
		this.placa = placa;
		this.tipoVeiculo = tipoVeiculo;
		this.status = c.getStatus();
		
		this.tipo = c.getTipo();

		if (this.tipoVeiculo != null) {
			TipoVeiculoEnum tipoVeiculoEnum = TipoVeiculoEnum.valueOfCodigo(this.tipoVeiculo);
			if (tipoVeiculoEnum != null) {
				this.tipoDescricao = tipoVeiculoEnum.getDescricao();
			}
		}

		if (c.getDataInicio() != null && c.getDataFinalizacao() != null) {
			this.duracaoDescricao = DateUtil.formatarDuracao(c.getDataInicio(), c.getDataFinalizacao());
		}

		this.dataSolicitacao = c.getDataSolicitacao();

		if (this.dataSolicitacao != null) {
			StringBuilder ds = new StringBuilder();
			ds.append(DateUtil.formatarData(this.dataSolicitacao, "dd/MM/yyyy"));
			ds.append(" às ");
			ds.append(DateUtil.formatarData(this.dataSolicitacao, "HH:mm:ss"));

			this.dataSolicitacaoFormatada = ds.toString();
		}

		if (c.getDataInicio() != null) {
			StringBuilder ds = new StringBuilder();
			ds.append(DateUtil.formatarData(c.getDataInicio(), "dd/MM/yyyy"));
			ds.append(" às ");
			ds.append(DateUtil.formatarData(c.getDataInicio(), "HH:mm:ss"));

			this.dataInicioFormatada = ds.toString();
		}

		if (c.getDataFinalizacao() != null) {
			StringBuilder ds = new StringBuilder();
			ds.append(DateUtil.formatarData(c.getDataFinalizacao(), "dd/MM/yyyy"));
			ds.append(" às ");
			ds.append(DateUtil.formatarData(c.getDataFinalizacao(), "HH:mm:ss"));

			this.dataFimFormatada = ds.toString();
		}
		this.classificacaoCorrida = c.getClassificacaoCorrida();
		this.classificacaoDescricao = c.getClassificacaoDescricao();
		this.nomeMotorista = usuarioMotorista.getNome();
		this.sexoMotorista = usuarioMotorista.getSexo();

		this.valorFinal = c.getValorFinal();
		this.motivoCancelamento = c.getMotivoCancelamento();
		this.formaPagamento = c.getFormaPagamento();
	}
    
    public Long getIdCorrida() {
        return idCorrida;
    }

    public void setIdCorrida(Long idCorrida) {
        this.idCorrida = idCorrida;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public Long getIdMotorista() {
        return idMotorista;
    }

    public void setIdMotorista(Long idMotorista) {
        this.idMotorista = idMotorista;
    }

    public Long getIdMotoristaUsuario() {
        return idMotoristaUsuario;
    }

    public void setIdMotoristaUsuario(Long idMotoristaUsuario) {
        this.idMotoristaUsuario = idMotoristaUsuario;
    }

    public String getMotora() {
        return motora;
    }

    public void setMotora(String motora) {
        this.motora = motora;
    }

    public Long getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(Long idVeiculo) {
        this.idVeiculo = idVeiculo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public String getTipoDescricao() {
        return tipoDescricao;
    }

    public void setTipoDescricao(String tipoDescricao) {
        this.tipoDescricao = tipoDescricao;
    }

    public Long getDuracao() {
        return duracao;
    }

    public void setDuracao(Long duracao) {
        this.duracao = duracao;
    }

    public String getDuracaoDescricao() {
        return duracaoDescricao;
    }

    public void setDuracaoDescricao(String duracaoDescricao) {
        this.duracaoDescricao = duracaoDescricao;
    }

    public Date getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(Date dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public String getDataSolicitacaoFormatada() {
        return dataSolicitacaoFormatada;
    }

    public void setDataSolicitacaoFormatada(String dataSolicitacaoFormatada) {
        this.dataSolicitacaoFormatada = dataSolicitacaoFormatada;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Long getIdPagamento() {
        return idPagamento;
    }

    public void setIdPagamento(Long idPagamento) {
        this.idPagamento = idPagamento;
    }

    public Integer getBandeira() {
        return bandeira;
    }

    public void setBandeira(Integer bandeira) {
        this.bandeira = bandeira;
    }

	public String getDataInicioFormatada() {
		return dataInicioFormatada;
	}

	public void setDataInicioFormatada(String dataInicioFormatada) {
		this.dataInicioFormatada = dataInicioFormatada;
	}

	public String getDataFimFormatada() {
		return dataFimFormatada;
	}

	public void setDataFimFormatada(String dataFimFormatada) {
		this.dataFimFormatada = dataFimFormatada;
	}

	public Double getQtdClassificacao() {
		return qtdClassificacao;
	}

	public void setQtdClassificacao(Double qtdClassificacao) {
		this.qtdClassificacao = qtdClassificacao;
	}

	public Motorista getMotorista() {
		return motorista;
	}

	public void setMotorista(Motorista motorista) {
		this.motorista = motorista;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}


	public String getOrigemEndereco() {
		return origemEndereco;
	}


	public void setOrigemEndereco(String origemEndereco) {
		this.origemEndereco = origemEndereco;
	}


	public String getDestinoEndereco() {
		return destinoEndereco;
	}


	public void setDestinoEndereco(String destinoEndereco) {
		this.destinoEndereco = destinoEndereco;
	}


	public String getOrigemLatitude() {
		return origemLatitude;
	}


	public void setOrigemLatitude(String origemLatitude) {
		this.origemLatitude = origemLatitude;
	}


	public String getOrigemLongitude() {
		return origemLongitude;
	}


	public void setOrigemLongitude(String origemLongitude) {
		this.origemLongitude = origemLongitude;
	}


	public String getDestinoLatitude() {
		return destinoLatitude;
	}


	public void setDestinoLatitude(String destinoLatitude) {
		this.destinoLatitude = destinoLatitude;
	}


	public String getDestinoLongitude() {
		return destinoLongitude;
	}


	public void setDestinoLongitude(String destinoLongitude) {
		this.destinoLongitude = destinoLongitude;
	}


	public Date getDataFinalizacao() {
		return dataFinalizacao;
	}


	public void setDataFinalizacao(Date dataFinalizacao) {
		this.dataFinalizacao = dataFinalizacao;
	}


	public Double getClassificacaoCorrida() {
		return classificacaoCorrida;
	}


	public void setClassificacaoCorrida(Double classificacaoCorrida) {
		this.classificacaoCorrida = classificacaoCorrida;
	}


	public Double getClassificacaoVeiculo() {
		return classificacaoVeiculo;
	}


	public void setClassificacaoVeiculo(Double classificacaoVeiculo) {
		this.classificacaoVeiculo = classificacaoVeiculo;
	}


	public String getClassificacaoDescricao() {
		return classificacaoDescricao;
	}


	public void setClassificacaoDescricao(String classificacaoDescricao) {
		this.classificacaoDescricao = classificacaoDescricao;
	}


	public Integer getTipoDenuncia() {
		return tipoDenuncia;
	}


	public void setTipoDenuncia(Integer tipoDenuncia) {
		this.tipoDenuncia = tipoDenuncia;
	}


	public String getDenuncia() {
		return denuncia;
	}


	public void setDenuncia(String denuncia) {
		this.denuncia = denuncia;
	}


	public BigDecimal getValorFinal() {
		return valorFinal;
	}


	public void setValorFinal(BigDecimal valorFinal) {
		this.valorFinal = valorFinal;
	}


	public BigDecimal getDescontoCorrida() {
		return descontoCorrida;
	}


	public void setDescontoCorrida(BigDecimal descontoCorrida) {
		this.descontoCorrida = descontoCorrida;
	}


	public String getVoucher() {
		return voucher;
	}


	public void setVoucher(String voucher) {
		this.voucher = voucher;
	}


	public Long getIdEmpresaConveniada() {
		return idEmpresaConveniada;
	}


	public void setIdEmpresaConveniada(Long idEmpresaConveniada) {
		this.idEmpresaConveniada = idEmpresaConveniada;
	}


	public String getObservacoes() {
		return observacoes;
	}


	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}


	public String getObservacoesTele() {
		return observacoesTele;
	}


	public void setObservacoesTele(String observacoesTele) {
		this.observacoesTele = observacoesTele;
	}


	public String getMotivoCancelamento() {
		return motivoCancelamento;
	}


	public void setMotivoCancelamento(String motivoCancelamento) {
		this.motivoCancelamento = motivoCancelamento;
	}


	public String getMotivoRecuperacao() {
		return motivoRecuperacao;
	}

	public void setMotivoRecuperacao(String motivoRecuperacao) {
		this.motivoRecuperacao = motivoRecuperacao;
	}


	public Integer getTipoVeiculo() {
		return tipoVeiculo;
	}


	public void setTipoVeiculo(Integer tipoVeiculo) {
		this.tipoVeiculo = tipoVeiculo;
	}


	public String getNomeMotorista() {
		return nomeMotorista;
	}


	public void setNomeMotorista(String nomeMotorista) {
		this.nomeMotorista = nomeMotorista;
	}


	public String getSexoMotorista() {
		return sexoMotorista;
	}


	public void setSexoMotorista(String sexoMotorista) {
		this.sexoMotorista = sexoMotorista;
	}


	public Integer getFormaPagamento() {
		return formaPagamento;
	}


	public void setFormaPagamento(Integer formaPagamento) {
		this.formaPagamento = formaPagamento;
	}
	
}