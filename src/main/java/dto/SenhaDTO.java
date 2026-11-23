package dto;

public class SenhaDTO {
    private int id;
    private String codigo;
    private String servicoNome;
    private Integer guicheNumero;
    private boolean prioritaria;
    private String status;
    private int rechamadas;
    private int pessoasFrente;
    private String dtEmissao;
    private String dtChamada;

    public SenhaDTO() {
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

    public String getServicoNome() {
        return servicoNome;
    }

    public void setServicoNome(String servicoNome) {
        this.servicoNome = servicoNome;
    }

    public Integer getGuicheNumero() {
        return guicheNumero;
    }

    public void setGuicheNumero(Integer guicheNumero) {
        this.guicheNumero = guicheNumero;
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

    public int getPessoasFrente() {
        return pessoasFrente;
    }

    public void setPessoasFrente(int pessoasFrente) {
        this.pessoasFrente = pessoasFrente;
    }

    public String getDtEmissao() {
        return dtEmissao;
    }

    public void setDtEmissao(String dtEmissao) {
        this.dtEmissao = dtEmissao;
    }

    public String getDtChamada() {
        return dtChamada;
    }

    public void setDtChamada(String dtChamada) {
        this.dtChamada = dtChamada;
    }
}