package model.entity;

public class InquilinoSeletor extends BaseSeletor{
	private String nome;
	private String email;
	private String telefone;

	public InquilinoSeletor() {
	}

	public InquilinoSeletor(String nome, String email, String telefone) {
		super();
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
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

	public boolean temFiltro() {
		return(this.getNome() != null && this.getNome().trim().length() > 0)
				|| (this.getEmail() != null && this.getEmail().trim().length() > 0)
				|| (this.getTelefone() != null && this.getTelefone().trim().length() > 0);
	}

}
