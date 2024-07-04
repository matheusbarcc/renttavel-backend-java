package model.entity;

import java.util.List;
import java.util.ArrayList;

public class ImovelSeletor extends BaseSeletor {
    private String nome;
    private List<Integer> tipos; // Lista de inteiros para os tipos de imóvel
    private int capacidadePessoas;
    private int qtdQuarto;
    private int qtdCama;
    private int qtdBanheiro;
    private boolean isOcupado;
    private int idEndereco;
    private int idAnfitriao;

    public ImovelSeletor() {
        this.tipos = new ArrayList<>(); // Inicializar a lista de tipos
    }

    public ImovelSeletor(String nome, List<Integer> tipos, int capacidadePessoas, int qtdQuarto, int qtdCama, int qtdBanheiro,
                         boolean isOcupado, int idEndereco, int idAnfitriao) {
        super();
        this.nome = nome;
        this.tipos = tipos;
        this.capacidadePessoas = capacidadePessoas;
        this.qtdQuarto = qtdQuarto;
        this.qtdCama = qtdCama;
        this.qtdBanheiro = qtdBanheiro;
        this.isOcupado = isOcupado;
        this.idEndereco = idEndereco;
        this.idAnfitriao = idAnfitriao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Integer> getTipos() {
        return tipos;
    }

    public void setTipos(List<Integer> tipos) {
        this.tipos = tipos;
    }

    public int getCapacidadePessoas() {
        return capacidadePessoas;
    }

    public void setCapacidadePessoas(int capacidadePessoas) {
        this.capacidadePessoas = capacidadePessoas;
    }

    public int getQtdQuarto() {
        return qtdQuarto;
    }

    public void setQtdQuarto(int qtdQuarto) {
        this.qtdQuarto = qtdQuarto;
    }

    public int getQtdCama() {
        return qtdCama;
    }

    public void setQtdCama(int qtdCama) {
        this.qtdCama = qtdCama;
    }

    public int getQtdBanheiro() {
        return qtdBanheiro;
    }

    public void setQtdBanheiro(int qtdBanheiro) {
        this.qtdBanheiro = qtdBanheiro;
    }

    public boolean getIsOcupado() {
        return isOcupado;
    }

    public void setIsOcupado(boolean isOcupado) {
        this.isOcupado = isOcupado;
    }

    public int getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(int idEndereco) {
        this.idEndereco = idEndereco;
    }

    public int getIdAnfitriao() {
        return idAnfitriao;
    }

    public void setIdAnfitriao(int idAnfitriao) {
        this.idAnfitriao = idAnfitriao;
    }

    public boolean temFiltro() {
        return (this.getNome() != null && this.getNome().trim().length() > 0)
                || (this.getTipos() != null && !this.getTipos().isEmpty())
                || (this.getCapacidadePessoas() > 0)
                || (this.getQtdQuarto() > 0)
                || (this.getQtdCama() > 0)
                || (this.getQtdBanheiro() > 0)
                || (this.getIsOcupado())
                || (this.getIdEndereco() > 0)
                || (this.getIdAnfitriao() > 0);
    }
}