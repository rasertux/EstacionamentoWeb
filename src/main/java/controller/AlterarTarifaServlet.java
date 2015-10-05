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
		dao.alterar(tarifa);
		response.sendRedirect("/EstacionamentoWeb/listar");
	}

	private String showFormulario(TarifaBean tarifa) {
		return "<!DOCTYPE html>" + "<html>" + "<head>" + "<meta charset='utf-8'>"
				+ "<title>Cadastro de Tarifas</title>" + "</head>" + "<body bgcolor='#DCDCDC'>"
				+ "  <h1> Tarifa - Alteração</h1>" + "  <hr>"
				+ "  <form method='post' action='/EstacionamentoWeb/alterar'>"
				+ "  Código:<input type='text' size='10' maxlength='10'" + "  value='" + tarifa.getIdtarifa() + "'"
				+ "  name='edcodigo' readonly='true'/><br>" +

		"  Descrição:<input type='text' size='35' maxlength='30'" + "  value='" + tarifa.getDescricao() + "'"
				+ "  name='eddescricao'/><br>" +

		"  Valor:<input type='text' size='10' maxlength='10'" + "  value='" + tarifa.getValor() + "'"
				+ "  name='edvalor'/><br>" +

		"  <input type='submit' value='Cadastrar'/>" + "  <input type='reset'  value='Limpar'/>" + " </form>" + "  <hr>"
				+ "  <a href='/Estacionamento/listar'>Listar Tarifa</a>" + "</body>" + "</html>";
	}

}
