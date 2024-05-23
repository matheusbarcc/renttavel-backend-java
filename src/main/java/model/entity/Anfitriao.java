package model.entity;

public class Anfitriao {
    private int id;
    private String nome;
    private String email;
    private String senha;

    public Anfitriao() {
    }

    public Anfitriao(String senha, String email, String nome, int id) {
        this.senha = senha;
        this.email = email;
        this.nome = nome;
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
