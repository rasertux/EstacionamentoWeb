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
		for (int i = 0; i < itens.size(); i++) {
			TarifaBean tarifa = (TarifaBean) itens.get(i);
			html.print(gerarLinha(tarifa, (i == 0), (i == itens.size() - 1)));
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

		String SUCESSO = "/EstacionamentoWeb/aviso?msg=Cadastro Com Sucesso&cor=green";
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
			sb.append("<HEAD><TITLE>CADASTRO DE VEICULOS</TITLE></HEAD>");
			sb.append("<BODY>");
			sb.append("<H1>CADASTRO DE VEICULOS</H1>");
			sb.append("<HR>");
			sb.append("<a href='/EstacionamentoWeb/index.html'> Menu</a>");
			sb.append("<form method='post' action='/EstacionamentoWeb/cadastrarveiculo'>");
			sb.append("<label>Placa: </label>");
			sb.append("<input type='text' size='7' name='edplaca'><br>");
			sb.append("<label>Marca: </label>");
			sb.append("<input type='text' size='20' name='edmarca'><br>");
			sb.append("<label>Modelo: </label>");
			sb.append("<input type='text' size='20' name='edmodelo'><br>");
			sb.append("<label>Tarifa: </label>");
			sb.append("<select name='campo'>");
		}

		sb.append("<option value='").append(tarifa.getIdtarifa()).append("'>")
				.append(tarifa.getDescricao() + " R$" + tarifa.getValor()).append("</option>)");

		if (rodape) {
			sb.append("</select><br>");
			sb.append("<input type='submit' value='Cadastrar'/>");
			sb.append("<input type='reset' value='Limpar'/>");
			sb.append("</form>");
			sb.append("<HR>");
			sb.append("</BODY>");
			sb.append("<HTML>");
		}
		return sb.toString();
	}

}
