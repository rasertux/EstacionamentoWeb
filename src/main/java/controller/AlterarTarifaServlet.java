package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.TarifaBean;
import model.TarifaDao;

/**
 * Servlet implementation class AlterarTarifaServlet
 */
@WebServlet("/alterar")
public class AlterarTarifaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AlterarTarifaServlet() {
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
		TarifaDao dao = new TarifaDao();
		String idStr = request.getParameter("id");
		if (idStr != null) {
			TarifaBean tarifa = (TarifaBean) dao.consultar(new Integer(idStr));
			html.println(showFormulario(tarifa));
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String codigo = request.getParameter("edcodigo");
		String descricao = request.getParameter("eddescricao");
		String valor = request.getParameter("edvalor");

		TarifaBean tarifa = new TarifaBean();
		tarifa.setIdtarifa(new Integer(codigo));
		tarifa.setDescricao(descricao);
		tarifa.setValor(new Float(valor));
		TarifaDao dao = new TarifaDao();

		String SUCESSO = "/EstacionamentoWeb/aviso?msg=Operacao realizada com sucesso!&cor=green";
		String ERRO = "/EstacionamentoWeb/aviso?msg=Erro na operacao&cor=red";

		if (dao.alterar(tarifa)) {
			response.sendRedirect(SUCESSO);
		} else {
			response.sendRedirect(ERRO);
		}

	}

	private String showFormulario(TarifaBean tarifa) {
		return "<!DOCTYPE html>" + "<html>" + "<head>"
				+ "<title>Cadastro de Tarifas</title> <meta name='viewport' content='width=device-width, initial-scale=1'>"
				+ "<meta name='author' content='David Martins, Rafael Sérgio' />"
				+ "<link rel='stylesheet' href='css/bootstrap.min.css'>"
				+ "<script src='js/jquery-1.11.3.min.js'></script>" + "<script src='js/bootstrap.min.js'></script>"
				+ "</head>" + "<body class='container-fluid'>" + "<div class='row'></div>"
				+ "<div class='col-sm-4'></div>" + "<div class='col-sm-4'>"
				+ "<div class='panel panel-default' style='margin-top:50px;'>"
				+ "<div class='panel-heading'><div align='right'>"
				+ "<form action='/EstacionamentoWeb/logout' method='post'>"
				+ "<input type='submit' value='Logout' /></form>"
				+ "</div><h1 class='text-center'> Tarifa - Alteração</h1>"
				+ "<a href='/EstacionamentoWeb/index.html'> Menu</a></div>" + "<div class='panel-body'>"
				+ "  <form role='form' class='form-group' method='post' action='/EstacionamentoWeb/alterar'>"
				+ "  <label>Código:</label><input class='form-control' type='text' size='10' maxlength='10'"
				+ "  value='" + tarifa.getIdtarifa() + "'" + "  name='edcodigo' readonly='true'/>" +

		"  <label>Descrição:</label><input class='form-control' type='text' required='required' size='35' maxlength='30'"
				+ "  value='" + tarifa.getDescricao() + "'" + "  name='eddescricao'/>" +

		"  <label>Valor:</label><input class='form-control' type='tel' required='required' pattern='([0-9]{1,9}\\.)?([0-9]{1,9}\\.)?[0-9]{1,9}$' size='10' maxlength='10'"
				+ "  value='" + tarifa.getValor() + "'" + "  name='edvalor'/><br />" +

		"  <input type='submit' class='btn btn-default' value='Alterar'/>" + " </form>" + "  <hr>"
				+ "  <a href='/EstacionamentoWeb/listar'>Listar Tarifa</a>" + "</div>"
				+ "<div class='panel-footer'><small>&copy <a href='https://github.com/DaveKun' target='_blank'>David Martins</a>, <a target='_blank' href='https://github.com/rasertux'>Rafael Sérgio</a></small></div>"
				+ "</div>" + "</div>" + "<div class='col-sm-4'></div>" + "</div>" + "</body>" + "</html>";
	}

}
