package model.entity;

public class ImovelSeletor extends BaseSeletor{
    private String nome;
    private int tipo;
    private int capacidadePessoas;
    private int qtdQuarto;
    private int qtdCama;
    private int qtdBanheiro;
    private int idEndereco;

    public ImovelSeletor() {
    }

    public ImovelSeletor(String nome, int tipo, int capacidadePessoas, int qtdQuarto, int qtdCama, int qtdBanheiro, int idEndereco) {
        this.nome = nome;
        this.tipo = tipo;
        this.capacidadePessoas = capacidadePessoas;
        this.qtdQuarto = qtdQuarto;
        this.qtdCama = qtdCama;
        this.qtdBanheiro = qtdBanheiro;
        this.idEndereco = idEndereco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
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

    public int getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(int idEndereco) {
        this.idEndereco = idEndereco;
    }

    public boolean temFiltro(){
        return (this.getNome() != null && this.getNome().trim().length() > 0)
                || (this.getTipo() > 0)
                || (this.getCapacidadePessoas() > 0)
                || (this.getQtdQuarto() > 0)
                || (this.getQtdCama() > 0)
                || (this.getQtdBanheiro() > 0)
                || (this.getIdEndereco() > 0);
    }
}
