package br.com.seg.econotaxi.model;

import javax.persistence.*;

@Entity
@Table(name = "sistema")
public class Sistema {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "chave")
    private String chave;

    @Column(name = "credencial")
    private String credencial;

    @Column(name = "escopos")
    private String escopos;

    @Column(name = "tipos_acessos")
    private String tiposAcessos;

    @Column(name = "id_recurso")
    private String idRecurso;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public String getCredencial() {
        return credencial;
    }

    public void setCredencial(String credencial) {
        this.credencial = credencial;
    }

    public String getEscopos() {
        return escopos;
    }

    public void setEscopos(String escopos) {
        this.escopos = escopos;
    }

    public String getTiposAcessos() {
        return tiposAcessos;
    }

    public void setTiposAcessos(String tiposAcessos) {
        this.tiposAcessos = tiposAcessos;
    }

    public String getIdRecurso() {
        return idRecurso;
    }

    public void setIdRecurso(String idRecurso) {
        this.idRecurso = idRecurso;
    }
}
