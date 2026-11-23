package model;

import java.sql.Timestamp;

public class Senha {
    private int id;
    private String codigo;
    private int servicoId;
    private Integer guicheId;
    private boolean prioritaria;
    private String status;
    private int rechamadas;
    private Timestamp dtEmissao;
    private Timestamp dtChamada;
    private Timestamp dtInicio;
    private Timestamp dtTermino;

    public Senha() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getServicoId() {
        return servicoId;
    }

    public void setServicoId(int servicoId) {
        this.servicoId = servicoId;
    }

    public Integer getGuicheId() {
        return guicheId;
    }

    public void setGuicheId(Integer guicheId) {
        this.guicheId = guicheId;
    }

    public boolean isPrioritaria() {
        return prioritaria;
    }

    public void setPrioritaria(boolean prioritaria) {
        this.prioritaria = prioritaria;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRechamadas() {
        return rechamadas;
    }

    public void setRechamadas(int rechamadas) {
        this.rechamadas = rechamadas;
    }

    public Timestamp getDtEmissao() {
        return dtEmissao;
    }

    public void setDtEmissao(Timestamp dtEmissao) {
        this.dtEmissao = dtEmissao;
    }

    public Timestamp getDtChamada() {
        return dtChamada;
    }

    public void setDtChamada(Timestamp dtChamada) {
        this.dtChamada = dtChamada;
    }

    public Timestamp getDtInicio() {
        return dtInicio;
    }

    public void setDtInicio(Timestamp dtInicio) {
        this.dtInicio = dtInicio;
    }

    public Timestamp getDtTermino() {
        return dtTermino;
    }

    public void setDtTermino(Timestamp dtTermino) {
        this.dtTermino = dtTermino;
    }
}