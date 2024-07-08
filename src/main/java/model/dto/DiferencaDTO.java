package model.dto;

public class DiferencaDTO {
	
	private String mes1;
	private double porcentagem1;
	private String mes2;
	private double porcentagem2;
	
	public DiferencaDTO() {
		super();
	}
	
	public DiferencaDTO(String mes1, double porcentagem1, String mes2, double porcentagem2) {
		super();
		this.mes1 = mes1;
		this.porcentagem1 = porcentagem1;
		this.mes2 = mes2;
		this.porcentagem2 = porcentagem2;
	}

	public String getMes1() {
		return mes1;
	}

	public void setMes1(String mes1) {
		this.mes1 = mes1;
	}

	public double getPorcentagem1() {
		return porcentagem1;
	}

	public void setPorcentagem1(double porcentagem1) {
		this.porcentagem1 = porcentagem1;
	}

	public String getMes2() {
		return mes2;
	}

	public void setMes2(String mes2) {
		this.mes2 = mes2;
	}

	public double getPorcentagem2() {
		return porcentagem2;
	}

	public void setPorcentagem2(double porcentagem2) {
		this.porcentagem2 = porcentagem2;
	}
}
