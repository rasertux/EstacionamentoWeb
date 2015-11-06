package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import helper.DataHelper;
import model.MovimentacaoBean;
import model.MovimentacaoDao;
import model.VeiculoBean;
import model.VeiculoDao;

/**
 * Servlet implementation class MovimentacaoServlet
 */
@WebServlet("/movimentacao")
public class MovimentacaoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MovimentacaoServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idmov = request.getParameter("idmov");
		String saida = request.getParameter("saida");
		if (idmov != null && saida != null) {
			MovimentacaoBean mov = new MovimentacaoBean();
			MovimentacaoDao movdao = new MovimentacaoDao();

			mov = (MovimentacaoBean) movdao.consultar(idmov);
			mov.setSaida(DataHelper.StringToCalendar("dd/MM/yyyy HH:mm", saida));

			movdao.insereSaida(mov);

			response.sendRedirect(request.getContextPath() + "/movimentacao.jsp");
		} else {
			String placa = request.getParameter("placa");
			String entrada = request.getParameter("entrada");

			MovimentacaoBean mov = new MovimentacaoBean();
			VeiculoBean veiculo = new VeiculoBean();

			MovimentacaoDao movdao = new MovimentacaoDao();
			VeiculoDao vdao = new VeiculoDao();

			veiculo = (VeiculoBean) vdao.consultar(placa);
			mov.setPlaca(veiculo);
			mov.setEntrada(DataHelper.StringToCalendar("dd/MM/yyyy HH:mm", entrada));
			mov.setFatura(veiculo.getIdtarifa().getValor());

			movdao.inserir(mov);

			response.sendRedirect(request.getContextPath() + "/movimentacao.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
