package model.entity;

public class ImovelSeletor extends BaseSeletor{
    private String nome;
    private int tipo;
    private int capacidadePessoas;
    private int qtdQuarto;
    private int qtdCama;
    private int qtdBanheiro;
    private Endereco endereco;
    private Anfitriao anfitriao;

    public ImovelSeletor() {
    }

    public ImovelSeletor(String nome, int tipo, int capacidadePessoas, int qtdQuarto, int qtdCama, int qtdBanheiro, Endereco endereco, Anfitriao anfitriao) {
        this.nome = nome;
        this.tipo = tipo;
        this.capacidadePessoas = capacidadePessoas;
        this.qtdQuarto = qtdQuarto;
        this.qtdCama = qtdCama;
        this.qtdBanheiro = qtdBanheiro;
        this.endereco = endereco;
        this.anfitriao = anfitriao;
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

    public boolean temFiltro(){
        return (this.nome != null && this.nome.trim().length() > 0)
                || (this.tipo > 0)
                || (this.capacidadePessoas > 0)
                || (this.qtdQuarto > 0)
                || (this.qtdCama > 0)
                || (this.qtdBanheiro > 0)
                || (this.endereco != null)
                || (this.anfitriao != null);
    }
}
