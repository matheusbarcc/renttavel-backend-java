package model.entity;

public class Inquilino {
    private int id;
    private String nome;
    private String email;
    private String telefone;
    private Anfitriao anfitriao;

    public Inquilino() {
    }

    public Inquilino(int id, String nome, String email, String telefone, Anfitriao anfitriao) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

	public Anfitriao getAnfitriao() {
		return anfitriao;
	}

	public void setAnfitriao(Anfitriao anfitriao) {
		this.anfitriao = anfitriao;
	}
}
