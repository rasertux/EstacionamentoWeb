package test;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;

import model.MovimentacaoBean;
import model.VeiculoBean;
import model.VeiculoDao;

public class MovimentacaoTest {

	@Test(expected=IllegalArgumentException.class)
	public void dataSaidaMaiorQueEntrada() {
		MovimentacaoBean mov = new MovimentacaoBean();
		VeiculoBean veiculo = new VeiculoBean();
		VeiculoDao vdao = new VeiculoDao();

		veiculo = (VeiculoBean) vdao.consultar("ZZZ9999");

		Calendar entrada = Calendar.getInstance();
		mov.setEntrada(entrada);
		Calendar saida = new GregorianCalendar(2015, 05, 10);
		mov.setSaida(saida);

		mov.setIdmov(20);
		mov.setPlaca(veiculo);

		assertTrue(mov.getSaida().after(mov.getEntrada()));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void setDataSaidaSemDataEntrada() {
		MovimentacaoBean mov = new MovimentacaoBean();
		VeiculoBean veiculo = new VeiculoBean();
		VeiculoDao vdao = new VeiculoDao();

		veiculo = (VeiculoBean) vdao.consultar("ZZZ9999");
		
		mov.setIdmov(20);
		mov.setPlaca(veiculo);
		
		Calendar saida = Calendar.getInstance();
		
		mov.setSaida(saida);
	}

}
