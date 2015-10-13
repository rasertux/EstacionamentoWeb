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
 * Servlet implementation class CadastroVeiculoServlet
 */
@WebServlet("/cadastrarveiculo")
public class CadastroVeiculoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private List<Object> itens = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CadastroVeiculoServlet() {
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
		TarifaDao dao = new TarifaDao();
		itens = dao.getItens();
		if (itens.isEmpty()) {
			html.print(anyTarifa());
		} else {
			for (int i = 0; i < itens.size(); i++) {
				TarifaBean tarifa = (TarifaBean) itens.get(i);
				html.print(gerarLinha(tarifa, (i == 0), (i == itens.size() - 1)));
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
		VeiculoDao dao = new VeiculoDao();

		String SUCESSO = "/EstacionamentoWeb/aviso?msg=Cadastrado Com Sucesso&cor=green";
		String ERRO = "/EstacionamentoWeb/aviso?msg=Erro No Cadastro&cor=red";

		veiculo.setPlaca(request.getParameter("edplaca"));
		veiculo.setMarca(request.getParameter("edmarca"));
		veiculo.setModelo(request.getParameter("edmodelo"));

		tarifa.setIdtarifa(new Integer(request.getParameter("campo")));
		veiculo.setIdtarifa(tarifa);

		boolean op = dao.inserir(veiculo);
		if (op) {
			response.sendRedirect(SUCESSO);
		} else {
			response.sendRedirect(ERRO);

		}

		doGet(request, response);
	}

	private String gerarLinha(TarifaBean tarifa, boolean cabecalho, boolean rodape) {
		StringBuilder sb = new StringBuilder();
		if (cabecalho) {
			sb.append("<HTML>");
			sb.append("<HEAD><TITLE>Cadastro de Veículos</TITLE>");
			sb.append("<meta name='viewport' content='width=device-width, initial-scale=1'>");
			sb.append("<meta name='author' content='David Martins, Rafael Sérgio' />");
			sb.append(
					"<link rel='stylesheet' href='css/bootstrap.min.css'>");
			sb.append("<script src='js/jquery-1.11.3.min.js'></script>");
			sb.append("<script src='js/bootstrap.min.js'></script>");
			sb.append("</HEAD>");
			sb.append("<body class='container-fluid'>");
			sb.append("<div class='row'>");
			sb.append("<div class='col-sm-4'></div>");
			sb.append("<div class='col-sm-4'>");
			sb.append("<div class='panel panel-default' style='margin-top:50px;'>");
			sb.append("<div class='panel-heading'><h1 class='text-center'>Cadastro de Veículo</h1>");
			sb.append("<a href='/EstacionamentoWeb/index.html'> Menu</a></div>");
			sb.append("<div class='panel-body'>");
			sb.append("<form role='form' class='form-group' method='post' action='/EstacionamentoWeb/cadastrarveiculo'>");
			sb.append("<label>Placa: </label>");
			sb.append("<input class='form-control' type='text' maxlength='7' name='edplaca'><br>");
			sb.append("<label>Marca: </label>");
			sb.append("<input class='form-control' type='text' maxlength='20' name='edmarca'><br>");
			sb.append("<label>Modelo: </label>");
			sb.append("<input class='form-control' type='text' maxlength='20' name='edmodelo'><br>");
			sb.append("<label>Tarifa: </label>");
			sb.append("<select class='form-control' name='campo'>");
		}

		sb.append("<option value='").append(tarifa.getIdtarifa()).append("'>")
				.append(tarifa.getDescricao() + " R$" + tarifa.getValor()).append("</option>)");

		if (rodape) {
			sb.append("</select><br>");
			sb.append("<input class='btn btn-default' type='submit' value='Cadastrar'/>");
			sb.append("<input class='btn btn-default' type='reset' value='Limpar'/>");
			sb.append("</form>");
			sb.append("</div>");
			sb.append(
					"<div class='panel-footer'><small>&copy <a href='https://github.com/DaveKun' target='_blank'>David Martins</a>, <a target='_blank' href='https://github.com/rasertux'>Rafael Sérgio</a></small></div>");
			sb.append("</div>");
			sb.append("</div>");
			sb.append("</div>");
			sb.append("<div class='col-sm-4'></div>");
			sb.append("</BODY>");
			sb.append("<HTML>");
		}
		return sb.toString();
	}

	private String anyTarifa() {
		StringBuilder sb = new StringBuilder();
		sb.append("<HTML>");
		sb.append("<HEAD><TITLE>Cadastro de veículos</TITLE>");
		sb.append("<meta name='viewport' content='width=device-width, initial-scale=1'>");
		sb.append("<meta name='author' content='David Martins, Rafael Sérgio' />");
		sb.append(
				"<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css'>");
		sb.append("<script src='https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js'></script>");
		sb.append("<script src='http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js'></script>");
		sb.append("</HEAD>");
		sb.append("<body class='container-fluid'>");
		sb.append("<div class='row'>");
		sb.append("<div class='col-sm-4'></div>");
		sb.append("<div class='col-sm-4'>");
		sb.append("<div class='panel panel-default' style='margin-top:50px;'>");
		sb.append("<div class='panel-heading'><h1 class='text-center'>Cadastro de Veículo</h1>");
		sb.append("<a href='/EstacionamentoWeb/index.html'> Menu</a></div>");
		sb.append("<div class='panel-body'>");
		sb.append("<div class='alert alert-warning'><strong>Ops!</strong> Nenhum cadastro encontrado.</div>");
		sb.append("</div>");
		sb.append(
				"<div class='panel-footer'><small>&copy <a href='https://github.com/DaveKun' target='_blank'>David Martins</a>, <a target='_blank' href='https://github.com/rasertux'>Rafael Sérgio</a></small></div>");
		sb.append("</div>");
		sb.append("</div>");
		sb.append("</div>");
		sb.append("<div class='col-sm-4'></div>");
		sb.append("</BODY>");
		sb.append("<HTML>");
		return sb.toString();
	}

}
