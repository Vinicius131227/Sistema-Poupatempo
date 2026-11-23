package dto;

import java.util.List;

public class PainelDTO {
    private SenhaDTO senhaAtual;
    private List<SenhaDTO> ultimasChamadas;

    public PainelDTO(SenhaDTO senhaAtual, List<SenhaDTO> ultimasChamadas) {
        this.senhaAtual = senhaAtual;
        this.ultimasChamadas = ultimasChamadas;
    }

    public SenhaDTO getSenhaAtual() {
        return senhaAtual;
    }

    public void setSenhaAtual(SenhaDTO senhaAtual) {
        this.senhaAtual = senhaAtual;
    }

    public List<SenhaDTO> getUltimasChamadas() {
        return ultimasChamadas;
    }

    public void setUltimasChamadas(List<SenhaDTO> ultimasChamadas) {
        this.ultimasChamadas = ultimasChamadas;
    }
}