package model;

import java.util.ArrayList;
import java.util.List;

public class Guiche {
    private int id;
    private int numero;
    private boolean ocupado;
    private int contadorPrioridadeConsecutiva;
    private List<Servico> servicos = new ArrayList<>();

    public Guiche() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }

    public int getContadorPrioridadeConsecutiva() {
        return contadorPrioridadeConsecutiva;
    }

    public void setContadorPrioridadeConsecutiva(int contadorPrioridadeConsecutiva) {
        this.contadorPrioridadeConsecutiva = contadorPrioridadeConsecutiva;
    }

    public List<Servico> getServicos() {
        return servicos;
    }

    public void setServicos(List<Servico> servicos) {
        this.servicos = servicos;
    }
}