package br.com.seg.econotaxi.util;

import br.com.seg.econotaxi.exception.NegocioException;
import br.com.seg.econotaxi.model.Pagamento;
import br.com.seg.econotaxi.model.Parametro;
import br.com.seg.econotaxi.model.Transacao;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.service.ParametrosService;
import br.com.seg.econotaxi.vo.DadosMaxipagoVo;
import br.com.seg.maxipago.datacontract.ResponseBase;
import br.com.seg.maxipago.datacontract.nontransactional.ApiResponse;
import br.com.seg.maxipago.datacontract.transactional.ErrorResponse;
import br.com.seg.maxipago.datacontract.transactional.TransactionResponse;
import br.com.seg.maxipago.gateway.Transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import br.com.seg.maxipago.gateway.Api;

@Component
public class MaxiPagoUtil {

	private static final Logger LOG = LoggerFactory.getLogger(MaxiPagoUtil.class);
	
    private DadosMaxipagoVo dadosMaxipagoVo;

    /**
     * SIMULADOR DE TESTES = 1
        Rede = 2
        Cielo = 4
        TEF = 5
        Elavon = 6
        ChasePaymentech = 8
        GetNet = 3
     */
    private static final String PROCESSOR_ID = "1";

    public synchronized String adicionarUsuario(Usuario usuario) {

        try {
            ApiResponse response = getApi().AddConsumer(getId(), getChave(), String.valueOf(usuario.getId()), getFirstName(usuario.getNome()), getLastName(usuario.getNome()),
                    null, null, null, null, null, null, null, null, null, null);

            if (response.getErrorCode().equals("0")) {
                return response.getResult().getCustomerId();
            } else {
                throw new NegocioException(response.getErrorMessage());
            }
        } catch (Exception e) {
        	LOG.error(e.getMessage(), e);
            throw new NegocioException(tratarMensagem(e.getMessage()));
        }
    }

    public synchronized void removerUsuario(String idMaxipago) {
        try {
            ApiResponse response = getApi().DeleteConsumer(getId(), getChave(), idMaxipago);

            if (!response.getErrorCode().equals("0")) {
                throw new NegocioException(response.getErrorMessage());
            }
        } catch (Exception e) {
        	LOG.error(e.getMessage(), e);
            throw new NegocioException(tratarMensagem(e.getMessage()));
        }
    }

    public synchronized String adicionarCartao(Pagamento pagamento, String numeroCartao) {
        try {
            ApiResponse response = getApi().AddCardOnFile(getId(), getChave(), pagamento.getUsuario().getIdMaxipago(), numeroCartao, getMounthExpiration(pagamento.getValidade()),
                    getYearExpiration(pagamento.getValidade()), pagamento.getNomeImpresso(), pagamento.getPagamentoEndereco().getEndereco(), pagamento.getPagamentoEndereco().getComplemento(),
                    pagamento.getPagamentoEndereco().getCidade(), pagamento.getPagamentoEndereco().getUf(), String.valueOf(pagamento.getPagamentoEndereco().getCep()),
                    pagamento.getPagamentoEndereco().getPais(), getPhone(pagamento.getUsuario().getCelular()), pagamento.getUsuario().getLogin(), null, null, null, null);

            if (response.getErrorCode().equals("0")) {
                return response.getResult().getToken();
            } else {
                throw new NegocioException(response.getErrorMessage());
            }
        } catch (Exception e) {
        	LOG.error(e.getMessage(), e);
            throw new NegocioException(tratarMensagem(e.getMessage()));
        }
    }

    public synchronized void removerCartao(String idMaxipago, String tokenMaxipago) {
        try {
            ApiResponse response = getApi().DeleteCardOnFile(getId(), getChave(), idMaxipago, tokenMaxipago);

            if (!response.getErrorCode().equals("0")) {
                throw new NegocioException(response.getErrorMessage());
            }
        } catch (Exception e) {
        	LOG.error(e.getMessage(), e);
            throw new NegocioException(tratarMensagem(e.getMessage()));
        }
    }

    public synchronized void autorizacao(Transacao transacao) {

        try {
            ResponseBase response = getTransaction().Auth(getId(), getChave(), String.valueOf(transacao.getId()),
                    NumeroUtil.doubleDuasDecimais(transacao.getValor()), PROCESSOR_ID, transacao.getPagamento().getTokenMaxipago(),
                    transacao.getPagamento().getUsuario().getIdMaxipago(), null, null, null, null, null, null, null,
                    transacao.getPagamento().getCvv());

            setDadosTransacaoMaxipago(transacao, response);
        } catch (Exception e) {
        	LOG.error(e.getMessage(), e);
            throw new NegocioException(tratarMensagem(e.getMessage()));
        }

    }

    public synchronized void captura(Transacao transacao) {

        try {
            ResponseBase response = getTransaction().Capture(getId(), getChave(), transacao.getOrderIdMaxipago(),
                    String.valueOf(transacao.getId()), NumeroUtil.doubleDuasDecimais(transacao.getValor()));

            if (response.IsErrorResponse()) {
                throw new NegocioException(((ErrorResponse) response).getErrorMsg());
            }
        } catch (Exception e) {
        	LOG.error(e.getMessage(), e);
            throw new NegocioException(tratarMensagem(e.getMessage()));
        }
    }

    public synchronized void vendaDireta(Transacao transacao) {

        try {
            ResponseBase response = getTransaction().Sale(getId(), getChave(), String.valueOf(transacao.getId()), NumeroUtil.doubleDuasDecimais(transacao.getValor()),
                    PROCESSOR_ID, transacao.getPagamento().getTokenMaxipago(), transacao.getPagamento().getUsuario().getIdMaxipago(), null,
                    null, null, null, null, null, null, transacao.getPagamento().getCvv());

            setDadosTransacaoMaxipago(transacao, response);
        } catch (Exception e) {
        	LOG.error(e.getMessage(), e);
            throw new NegocioException(tratarMensagem(e.getMessage()));
        }
    }

    public synchronized void cancelarCaptura(Transacao transacao) {

        try {
            ResponseBase response = getTransaction().Void(getId(), getChave(), transacao.getTransactionIdMaxipago(), null);

            if (response.IsErrorResponse()) {
                throw new NegocioException(((ErrorResponse) response).getErrorMsg());
            }
        } catch (Exception e) {
        	LOG.error(e.getMessage(), e);
            throw new NegocioException(tratarMensagem(e.getMessage()));
        }
    }

    public synchronized void estornar(Transacao transacao) {

        try {
            ResponseBase response = getTransaction().Return(getId(), getChave(), transacao.getOrderIdMaxipago(),
                    String.valueOf(transacao.getId()), NumeroUtil.doubleDuasDecimais(transacao.getValor()));
        } catch (Exception e) {
        	LOG.error(e.getMessage(), e);
            throw new NegocioException(tratarMensagem(e.getMessage()));
        }
    }

    private String tratarMensagem(String maxiPagoMsg) {
        if (maxiPagoMsg.contains("not a valid credit card number")) {
            return "Número de cartão de crédito não é válido.";

        } else if (maxiPagoMsg.contains("expired credit card")) {
            return "Data de vencimento do cartão não é válida.";

        } else if (maxiPagoMsg.contains("is not a valid number in the range")) {
            return "O valor da transação não é válido";

        } else if (maxiPagoMsg.contains("can not be processed")) {
            return "O campo processorID enviado não é válido";
        }
        return maxiPagoMsg;
    }

    private Api getApi() {
        Api api = new Api();
        api.setEnvironment(getAmbiente());

        return api;
    }

    private Transaction getTransaction() {
        Transaction transaction = new Transaction();
        transaction.setEnvironment(getAmbiente());

        return transaction;
    }

    private String getFirstName(String nome) {
        String nomes[] = nome.split(" ");
        return nomes[0];
    }

    private String getLastName(String nome) {
        String nomes[] = nome.split(" ");
        return nomes[nomes.length - 1];
    }

    private String getMounthExpiration(String validade) {
        return validade.split("/")[0];
    }

    private String getYearExpiration(String validade) {
        return validade.split("/")[1];
    }

    private String getPhone(String celular) {
        return celular.replace("(", "").replace(")", "").replace(" ", "").replace("-", "");
    }

    private void setDadosTransacaoMaxipago(Transacao transacao, ResponseBase response) {
        if (response.IsTransactionResponse()) {

            TransactionResponse transactionResponse = (TransactionResponse) response;

            transacao.setOrderIdMaxipago(transactionResponse.getOrderID());
            transacao.setTransactionIdMaxipago(transactionResponse.getTransactionID());

        } else if (response.IsErrorResponse()) {
            throw new NegocioException(((ErrorResponse) response).getErrorMsg());
        }
    }

    private String getId() {
        return getDadosMaxipagoVo().getId();
    }

    private String getChave() {
        return getDadosMaxipagoVo().getChave();
    }

    private String getAmbiente() {
        return getDadosMaxipagoVo().getAmbiente();
    }

    private DadosMaxipagoVo getDadosMaxipagoVo() {
        if (this.dadosMaxipagoVo == null) {

            ParametrosService parametrosService = SpringContextUtil.getBean(ParametrosService.class);
            Parametro parametro = parametrosService.recuperarParametroSistema();

            if (parametro != null) {
                validarDadosMaxipago(parametro);

                this.dadosMaxipagoVo = new DadosMaxipagoVo();
                this.dadosMaxipagoVo.setId(parametro.getIdMaxipago());
                this.dadosMaxipagoVo.setChave(parametro.getChaveMaxipago());
                this.dadosMaxipagoVo.setAmbiente(parametro.getAmbienteMaxipago());
            }
        }

        return this.dadosMaxipagoVo;
    }

    private void validarDadosMaxipago(Parametro parametro) {
        if (parametro.getIdMaxipago() == null || parametro.getChaveMaxipago() == null ||
                parametro.getAmbienteMaxipago() == null) {
            throw new NegocioException("Dados da Maxipago não preenchidos");
        }
    }

}
