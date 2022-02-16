package br.com.seg.econotaxi.vo;

import br.com.seg.econotaxi.model.Credito;
import br.com.seg.econotaxi.model.Pagamento;

import java.util.List;

public class DadosPagamentoVo {

    private List<Pagamento> pagamentos;
    private Credito credito;

    public List<Pagamento> getPagamentos() {
        return pagamentos;
    }

    public void setPagamentos(List<Pagamento> pagamentos) {
        this.pagamentos = pagamentos;
    }

    public Credito getCredito() {
        return credito;
    }

    public void setCredito(Credito credito) {
        this.credito = credito;
    }
}
