package model;

public class VeiculoBean {

	private String placa, marca, modelo;
	private TarifaBean idtarifa;

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public TarifaBean getIdtarifa() {
		return idtarifa;
	}

	public void setIdtarifa(TarifaBean idtarifa) {
		this.idtarifa = idtarifa;
	}

}
