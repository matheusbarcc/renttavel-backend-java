package model.entity;

public class EnderecoSeletor extends BaseSeletor{
	 private int id;
	    private int numero;
	    private String cep;
	    private String rua;
	    private String bairro;
	    private String cidade;
	    private String estado;
	    private String pais;
	    
		public EnderecoSeletor(int id, int numero, String cep, String rua, String bairro, String cidade, String estado,
				String pais) {
			super();
			this.id = id;
			this.numero = numero;
			this.cep = cep;
			this.rua = rua;
			this.bairro = bairro;
			this.cidade = cidade;
			this.estado = estado;
			this.pais = pais;
		}
		
		public EnderecoSeletor() {
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
		public String getCep() {
			return cep;
		}
		public void setCep(String cep) {
			this.cep = cep;
		}
		public String getRua() {
			return rua;
		}
		public void setRua(String rua) {
			this.rua = rua;
		}
		public String getBairro() {
			return bairro;
		}
		public void setBairro(String bairro) {
			this.bairro = bairro;
		}
		public String getCidade() {
			return cidade;
		}
		public void setCidade(String cidade) {
			this.cidade = cidade;
		}
		public String getEstado() {
			return estado;
		}
		public void setEstado(String estado) {
			this.estado = estado;
		}
		public String getPais() {
			return pais;
		}
		public void setPais(String pais) {
			this.pais = pais;
		}
		public boolean temFiltro() {
			return(this.getNumero() > 0)
					|| (this.getCep() != null && this.getCep().trim().length() > 0)
					|| (this.getRua() != null && this.getRua().trim().length() > 0)
					|| (this.getBairro() != null && this.getBairro().trim().length() > 0)
					|| (this.getCidade() != null && this.getCidade().trim().length() > 0)
					|| (this.getEstado() != null && this.getEstado().trim().length() > 0)
					|| (this.getPais() != null && this.getPais().trim().length() > 0);
		}
}
