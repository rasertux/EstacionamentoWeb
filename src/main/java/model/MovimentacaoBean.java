package model;

import java.util.Calendar;

public class MovimentacaoBean {

	private Integer idmov;
	private VeiculoBean placa;
	private Calendar entrada;
	private Calendar saida;
	private Float fatura;

	public Integer getIdmov() {
		return idmov;
	}

	public void setIdmov(Integer idmov) {
		this.idmov = idmov;
	}

	public VeiculoBean getPlaca() {
		return placa;
	}

	public void setPlaca(VeiculoBean placa) {
		this.placa = placa;
	}

	public Calendar getEntrada() {
		return entrada;
	}

	public void setEntrada(Calendar entrada) {
		this.entrada = entrada;
	}

	public Calendar getSaida() {
		return saida;
	}

	public void setSaida(Calendar saida) {
		if (getEntrada() == null || saida.before(getEntrada())) {
			throw new IllegalArgumentException();
		}
		this.saida = saida;
	}

	public Float getFatura() {
		return fatura;
	}

	public void setFatura(Float fatura) {
		this.fatura = fatura;
	}
}
