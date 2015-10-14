package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.TarifaBean;
import model.TarifaDao;

/**
 * Servlet implementation class CadastroTarifaServlet
 */
@WebServlet("/cadastrotarifa")
public class CadastroTarifaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CadastroTarifaServlet() {
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
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String SUCESSO = "/EstacionamentoWeb/aviso?msg=Operação realizada com sucesso!&cor=green";
		String ERRO = "/EstacionamentoWeb/aviso?msg=Erro na operacão&cor=red";
		String descricao, valor;
		descricao = request.getParameter("eddescricao");
		valor = request.getParameter("edvalor");
		if (descricao == null || descricao.isEmpty() || valor == null || valor.isEmpty()) {
			response.sendRedirect("cadastrotarifa.html");
		} else {
			TarifaBean tarifa = new TarifaBean();
			tarifa.setIdtarifa(0);
			tarifa.setDescricao(descricao);
			tarifa.setValor(new Float(valor));

			TarifaDao dao = new TarifaDao();
			boolean op = dao.inserir(tarifa);
			if (op) {
				response.sendRedirect(SUCESSO);
			} else {
				response.sendRedirect(ERRO);

			}
		}
	}

}
