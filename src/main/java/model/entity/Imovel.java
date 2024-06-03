package model.entity;

public class Imovel {
    private int id;
    private String nome;
    private int tipo;
    private int capacidadePessoas;
    private int qtdQuarto;
    private int qtdCama;
    private int qtdBanheiro;
    private String descricao;
    private boolean isOcupado;
    private Endereco endereco;
    private Anfitriao anfitriao;

    public Imovel() {
    }

    public Imovel(int id, String nome, int tipo, int capacidadePessoas, int qtdQuarto, int qtdCama, int qtdBanheiro, String descricao, boolean isOcupado, Endereco endereco, Anfitriao anfitriao) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.capacidadePessoas = capacidadePessoas;
        this.qtdQuarto = qtdQuarto;
        this.qtdCama = qtdCama;
        this.qtdBanheiro = qtdBanheiro;
        this.descricao = descricao;
        this.isOcupado = isOcupado;
        this.endereco = endereco;
        this.anfitriao = anfitriao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean getIsOcupado() { return isOcupado; }

    public void setIsOcupado(boolean isOcupado) { this.isOcupado = isOcupado; }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Anfitriao getAnfitriao() {
        return anfitriao;
    }

    public void setAnfitriao(Anfitriao anfitriao) {
        this.anfitriao = anfitriao;
    }
}
