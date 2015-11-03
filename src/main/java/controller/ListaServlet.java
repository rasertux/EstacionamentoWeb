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

/**
 * Servlet implementation class ListaServlet
 */
@WebServlet("/listar")
public class ListaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private List<Object> itens = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ListaServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter html = response.getWriter();
		if (request.getParameter("edvalor") == null) {
			TarifaDao dao = new TarifaDao();
			itens = dao.getItens();
		}
		if (itens.isEmpty()) {
			// não existe cadastro
			html.print(gerarListaVazia());

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
		TarifaDao dao = new TarifaDao();
		String op = "todos";
		String vlr;
		op = request.getParameter("campo");
		vlr = request.getParameter("edvalor");

		if (op.equals("todos"))
			itens = dao.getItens();
		else if (op.equals("idtarifa"))
			itens = dao.getItensPor(" where idtarifa like '%" + vlr + "%'");
		else
			itens = dao.getItensPor(" where descricao like '%" + vlr + "%'");

		doGet(request, response);
	}

	private String gerarLinha(TarifaBean tarifa, boolean cabecalho, boolean rodape) {
		String urlremover = "/EstacionamentoWeb/remover?id=" + tarifa.getIdtarifa();
		String urlalterar = "/EstacionamentoWeb/alterar?id=" + tarifa.getIdtarifa();

		String imgremover = "<img src='/EstacionamentoWeb/img/remover.png' width='8%'>";
		String imgalterar = "<img src='/EstacionamentoWeb/img/alterar.png' width='8%'>";

		String remover = "<a href='" + urlremover + "'>" + imgremover + "</a>";
		String alterar = "<a href='" + urlalterar + "'>" + imgalterar + "</a>";

		String valorFormatado = String.format("R$%.2f", tarifa.getValor());
		StringBuilder sb = new StringBuilder();

		if (cabecalho) {
			sb.append("<html>");
			sb.append("<head><TITLE>Lista de tarifas</TITLE></HEAD>");
			sb.append("<meta name='viewport' content='width=device-width, initial-scale=1'>");
			sb.append("<meta name='author' content='David Martins, Rafael Sérgio' />");
			sb.append("<link rel='stylesheet' href='css/bootstrap.min.css'>");
			sb.append("<script src='js/jquery-1.11.3.min.js'></script>");
			sb.append("<script src='js/bootstrap.min.js'></script>");
			sb.append("<body class='container-fluid'>");
			sb.append("<div class='row'>");
			sb.append("<div class='col-sm-2'></div>");
			sb.append("<div class='col-sm-8'>");
			sb.append("<div class='panel panel-default' style='margin-top:50px;'>");
			sb.append("<div class='panel-heading'>");
			sb.append(
					"<div align='right'><form action='/EstacionamentoWeb/logout' method='post'><input type='submit' value='Logout' />");
			sb.append("</form></div>");
			sb.append("<h1 class='text-center'>Listagem de Tarifas</h1>");
			sb.append("<a href='/EstacionamentoWeb/index.html'> Menu</a></div>");
			sb.append("<div class='panel-body'>");
			sb.append("<form role='form' class='form-inline' method='post' action='/EstacionamentoWeb/listar'>");
			sb.append("<div class='form-group'>");
			sb.append("<label>Pesquisar por: </label>");
			sb.append("<select name='campo' class='form-control'>");
			sb.append("<option value='idtarifa'>Código</option>");
			sb.append("<option value='descricao'>Descrição</option>");
			sb.append("<option value='todos'>Todos</option>");
			sb.append("</select>");
			sb.append("</div>");
			sb.append("<div class='form-group'>");
			sb.append("<label>Valor da pesquisa: </label><input class='form-control' type='text' name='edvalor'/>");
			sb.append("<input class='btn btn-default' type='submit' value='Pesquisar'/>");
			sb.append("<input class='btn btn-default' type='reset' value='Limpar'/>");
			sb.append("</div>");
			sb.append("</form>");
			sb.append("<TABLE class='table table-striped' WIDTH='50%'>");
			sb.append("<TR>");
			sb.append("<TH>").append("Codigo").append("</TH>");
			sb.append("<TH>").append("Descricao").append("</TH>");
			sb.append("<TH>").append("Preço").append("</TH>");
			sb.append("<TH>").append("Ação").append("</TH>");
			sb.append("</TR>");
		}

		sb.append("<TR>");
		sb.append("<TD>").append(tarifa.getIdtarifa()).append("</TD>");
		sb.append("<TD>").append(tarifa.getDescricao()).append("</TD>");
		sb.append("<TD>").append(valorFormatado).append("</TD>");
		sb.append("<TD width=\"30%\" align=\"center\">").append(remover).append(" | ").append(alterar).append("</TD>");
		sb.append("</TR>");

		if (rodape) {
			sb.append("</TABLE>");
			sb.append("</div>");
			sb.append(
					"<div class='panel-footer'><small>&copy <a href='https://github.com/DaveKun' target='_blank'>David Martins</a>, <a target='_blank' href='https://github.com/rasertux'>Rafael Sérgio</a></small></div>");
			sb.append("</div>");
			sb.append("</div>");
			sb.append("</div>");
			sb.append("<div class='col-sm-2'></div>");
			sb.append("</BODY>");
			sb.append("<HTML>");
		}
		return sb.toString();
	}

	private String gerarListaVazia() {
		StringBuilder sb = new StringBuilder();
		sb.append("<html>");
		sb.append("<head><TITLE>Lista de tarifas</TITLE>");
		sb.append("<meta name='viewport' content='width=device-width, initial-scale=1'>");
		sb.append("<meta name='author' content='David Martins, Rafael Sérgio' />");
		sb.append(
				"<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css'>");
		sb.append("<script src='https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js'></script>");
		sb.append("<script src='http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js'></script>");
		sb.append("</HEAD>");
		sb.append("<body class='container-fluid'>");
		sb.append("<div class='row'>");
		sb.append("<div class='col-sm-2'></div>");
		sb.append("<div class='col-sm-8'>");
		sb.append("<div class='panel panel-default' style='margin-top:50px;'>");
		sb.append("<div class='panel-heading'>");
		sb.append(
				"<div align='right'><form action='/EstacionamentoWeb/logout' method='post'><input type='submit' value='Logout' />");
		sb.append("</form></div>");
		sb.append("<h1 class='text-center'>Listagem de Tarifas</h1>");
		sb.append("<a href='/EstacionamentoWeb/index.html'> Menu</a></div>");
		sb.append("<div class='panel-body'>");
		sb.append("<form role='form' class='form-inline' method='post' action='/EstacionamentoWeb/listar'>");
		sb.append("<div class='form-group'>");
		sb.append("<label>Pesquisar por: </label>");
		sb.append("<select name='campo' class='form-control'>");
		sb.append("<option value='idtarifa'>Código</option>");
		sb.append("<option value='descricao'>Descrição</option>");
		sb.append("<option value='todos'>Todos</option>");
		sb.append("</select>");
		sb.append("</div>");
		sb.append("<div class='form-group'>");
		sb.append("<label>Valor da pesquisa: </label><input class='form-control' type='text' name='edvalor'/>");
		sb.append("<input class='btn btn-default' type='submit' value='Pesquisar'/>");
		sb.append("<input class='btn btn-default' type='reset' value='Limpar'/>");
		sb.append("</div>");
		sb.append("</form>");
		sb.append("<TABLE class='table table-striped' WIDTH='50%'>");
		sb.append("<TR>");
		sb.append("<TH>").append("Codigo").append("</TH>");
		sb.append("<TH>").append("Descricao").append("</TH>");
		sb.append("<TH>").append("Preço").append("</TH>");
		sb.append("<TH>").append("Ação").append("</TH>");
		sb.append("</TR>");
		sb.append("</TABLE>");
		sb.append("</div>");
		sb.append(
				"<div class='panel-footer'><small>&copy <a href='https://github.com/DaveKun' target='_blank'>David Martins</a>, <a target='_blank' href='https://github.com/rasertux'>Rafael Sérgio</a></small></div>");
		sb.append("</div>");
		sb.append("<div class='alert alert-warning'><strong>Ops!</strong> Nenhum cadastro encontrado.</div>");
		sb.append("</div>");
		sb.append("</div>");
		sb.append("<div class='col-sm-2'></div>");
		sb.append("</BODY>");
		sb.append("<HTML>");
		// retorna página
		return sb.toString();
	}

}
