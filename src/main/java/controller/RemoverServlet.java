package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.MovimentacaoDao;
import model.TarifaDao;
import model.VeiculoDao;

/**
 * Servlet implementation class RemoverServlet
 */
@WebServlet("/remover")
public class RemoverServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RemoverServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String remover = request.getParameter("remover");

		String SUCESSO = "/EstacionamentoWeb/aviso?msg=Operacao realizada com sucesso!&cor=green";
		String ERRO = "/EstacionamentoWeb/aviso?msg=Erro na operacao&cor=red";

		if (remover.equalsIgnoreCase("mov")) {
			String idmov = request.getParameter("idmov");
			MovimentacaoDao movdao = new MovimentacaoDao();
			movdao.remover(idmov);
			response.sendRedirect(request.getContextPath() + "/movimentacao.jsp");
		} else if (remover.equalsIgnoreCase("tarifa")) {
			String idtarifa = request.getParameter("id");
			Integer id = new Integer(idtarifa);
			TarifaDao dao = new TarifaDao();
			if (dao.remover(id)) {
				response.sendRedirect(SUCESSO);
			} else {
				response.sendRedirect(ERRO);
			}
		} else {
			String placa = request.getParameter("placa");
			if (placa != null) {
				VeiculoDao dao = new VeiculoDao();
				if (dao.remover(placa)) {
					response.sendRedirect(SUCESSO);
				} else {
					response.sendRedirect(ERRO);
				}
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
		doGet(request, response);
	}

}
