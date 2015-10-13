package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.TarifaBean;
import model.TarifaDao;
import model.VeiculoBean;
import model.VeiculoDao;

/**
 * Servlet implementation class AlterarVeiculoServlet
 */
@WebServlet("/alterarveiculo")
public class AlterarVeiculoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private List<Object> itens = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AlterarVeiculoServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter html = response.getWriter();
		TarifaDao tarifadao = new TarifaDao();
		VeiculoDao veiculodao = new VeiculoDao();
		itens = tarifadao.getItens();
		String placa = request.getParameter("placa");
		if (placa != null) {
			VeiculoBean veiculo = (VeiculoBean) veiculodao.consultar(placa);
			for (int i = 0; i < itens.size(); i++) {
				TarifaBean tarifa = (TarifaBean) itens.get(i);
				html.print(gerarLinha(veiculo, tarifa, (i == 0), (i == itens.size() - 1)));
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		VeiculoBean veiculo = new VeiculoBean();
		TarifaBean tarifa = new TarifaBean();
		String placa = request.getParameter("edplaca");
		String marca = request.getParameter("edmarca");
		String modelo = request.getParameter("edmodelo");
		tarifa.setIdtarifa(new Integer(request.getParameter("idtarifa")));
		
		String SUCESSO = "/EstacionamentoWeb/aviso?msg=Operacao realizada com sucesso!&cor=green";
		String ERRO = "/EstacionamentoWeb/aviso?msg=Erro na operacao&cor=red";

		veiculo.setPlaca(placa);
		veiculo.setMarca(marca);
		veiculo.setModelo(modelo);
		veiculo.setIdtarifa(tarifa);
		VeiculoDao dao = new VeiculoDao();
		dao.alterar(veiculo);
		if(dao.alterar(veiculo)) {
			response.sendRedirect(SUCESSO);
		} else {
			response.sendRedirect(ERRO);
		}
		
	}

	private String gerarLinha(VeiculoBean veiculo, TarifaBean tarifa, boolean cabecalho, boolean rodape) {
		StringBuilder sb = new StringBuilder();
		if (cabecalho) {
			sb.append("<HTML>");
			sb.append("<HEAD><TITLE>Alteração de Veículo</TITLE>")
			.append("<link rel='stylesheet' href='css/bootstrap.min.css' />")
			.append("<script type='text/javascript' src='js/jquery-1.11.3.min.js'></script>")
			.append("<script type='text/javascript' src='js/bootstrap.min.js'></script>")
			.append("</HEAD>");
			sb.append("<body class='container-fluid'>");
			sb.append("<div class='row'>");
			sb.append("<div class='col-sm-4'></div>");
			sb.append("<div class='col-sm-4'>");
			sb.append("<div class='panel panel-default' style='margin-top:50px;'>");
			sb.append("<div class='panel-heading'><h1 class='text-center'>Alteração de Veículo</h1>");
			sb.append("<a href='/EstacionamentoWeb/index.html'> Menu</a></div>");
			sb.append("<div class='panel-body'>");
			sb.append("<form method='post' class='form-group' role='form' action='/EstacionamentoWeb/alterarveiculo'>");
			sb.append("<label>Placa: </label>");
			sb.append(
					"<input class='form-control' type='text' maxlength='7' name='edplaca' readonly value='" + veiculo.getPlaca() + "'><br>");
			sb.append("<label>Marca: </label>");
			sb.append("<input class='form-control' type='text' maxlength='20' name='edmarca' value='" + veiculo.getMarca() + "'><br>");
			sb.append("<label>Modelo: </label>");
			sb.append("<input class='form-control' type='text' maxlength='20' name='edmodelo' value='" + veiculo.getModelo() + "'><br>");
			sb.append("<label>Tarifa: </label>");
			sb.append("<select class='form-control' name='idtarifa'>");
			sb.append("<option value='").append(veiculo.getIdtarifa().getIdtarifa()).append("'>")
					.append(veiculo.getIdtarifa().getDescricao() + " R$" + veiculo.getIdtarifa().getValor())
					.append("</option>");
		}

		if (tarifa.getIdtarifa() != veiculo.getIdtarifa().getIdtarifa()) {
			sb.append("<option value='").append(tarifa.getIdtarifa()).append("'>")
					.append(tarifa.getDescricao() + " R$" + tarifa.getValor()).append("</option>)");
		}

		if (rodape) {
			sb.append("</select><br>");
			sb.append("<input class='btn btn-default' type='submit' value='Alterar'/>");
			sb.append("<input class='btn btn-default' type='reset' value='Limpar'/>");
			sb.append("</form>");
			sb.append("</div>");
			sb.append(
					"<div class='panel-footer'><small>&copy <a href='https://github.com/DaveKun' target='_blank'>David Martins</a>, <a target='_blank' href='https://github.com/rasertux'>Rafael Sérgio</a></small></div>");
			sb.append("</div>");
			sb.append("</div>");
			sb.append("<div class='col-sm-4'></div>");
			sb.append("</BODY>");
			sb.append("<HTML>");
		}
		return sb.toString();
	}

}
