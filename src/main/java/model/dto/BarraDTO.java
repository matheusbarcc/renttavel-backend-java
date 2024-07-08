package model.dto;

import java.util.List;

public class BarraDTO {
	
	private String label;
	private List<Double> data;
	private List<String> backgroundColor;
	
	public BarraDTO() {
		super();
	}
	
	public BarraDTO(String label, List<Double> data, List<String> backgroundColor) {
		super();
		this.label = label;
		this.data = data;
		this.backgroundColor = backgroundColor;
	}

	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public List<Double> getData() {
		return data;
	}
	public void setData(List<Double> data) {
		this.data = data;
	}

	public List<String> getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(List<String> backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
}
