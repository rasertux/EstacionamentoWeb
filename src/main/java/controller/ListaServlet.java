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
		for (int i = 0; i < itens.size(); i++) {
			TarifaBean tarifa = (TarifaBean) itens.get(i);
			html.print(gerarLinha(tarifa, i, (i == 0), (i == itens.size() - 1)));
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
			itens = dao.getItensPor(" where idtarifa =" + vlr);
		else
			itens = dao.getItensPor(" where descricao like '%" + vlr + "%'");

		doGet(request, response);
	}

	private String gerarLinha(TarifaBean tarifa, int vcor, boolean cabecalho, boolean rodape) {
		String urlremover = "/EstacionamentoWeb/remover?id=" + tarifa.getIdtarifa();
		String urlalterar = "/EstacionamentoWeb/alterar?id=" + tarifa.getIdtarifa();

		String imgremover = "<img src='Remover.PNG'>";
		String imgalterar = "<img src='Editar.PNG'>";

		String remover = "<a href='" + urlremover + "'>" + imgremover + "</a>";
		String alterar = "<a href='" + urlalterar + "'>" + imgalterar + "</a>";

		String valorFormatado = String.format("R$%.2f", tarifa.getValor());
		String cor = (vcor % 2 == 0) ? "#6495ED" : "#483D8B";
		StringBuilder sb = new StringBuilder();

		if (cabecalho) {
			sb.append("<HTML>");
			sb.append("<HEAD><TITLE>LISTA DE TARIFAS</TITLE></HEAD>");
			sb.append("<BODY>");
			sb.append("<H1>LISTAGEM DE TARIFAS</H1>");
			sb.append("<HR>");
			sb.append("<a href='/EstacionamentoWeb/index.html'> Menu</a>");
			sb.append("<form method='post' action='/EstacionamentoWeb/listar'>");
			sb.append("Pesquisar por:");
			sb.append("<select name='campo'>");
			sb.append("<option value='idtarifa'>Código</option>");
			sb.append("<option value='descricao'>Descrição</option>");
			sb.append("<option value='todos'>Todos</option>");
			sb.append("</select>");
			sb.append("Valor da pesquisa:<input type='text' name='edvalor'/>");
			sb.append("<input type='submit' value='Pesquisar'/>");
			sb.append("<input type='reset' value='Limpar'/>");
			sb.append("</form>");
			sb.append("<TABLE WIDTH='70%'>");
			sb.append("<TR bgcolor='#DCDCDC'>");
			sb.append("<TH>").append("Codigo").append("</TH>");
			sb.append("<TH>").append("Descricao").append("</TH>");
			sb.append("<TH>").append("Preço").append("</TH>");
			sb.append("<TH>").append("Ação").append("</TH>");
			sb.append("</TR>");
		}

		sb.append("<TR bgcolor='").append(cor).append("'>");
		sb.append("<TD>").append(tarifa.getIdtarifa()).append("</TD>");
		sb.append("<TD>").append(tarifa.getDescricao()).append("</TD>");
		sb.append("<TD>").append(valorFormatado).append("</TD>");
		sb.append("<TD>").append(remover).append(alterar).append("<TD>");
		sb.append("</TR>");

		if (rodape) {
			sb.append("</TABLE>");
			sb.append("</HR>");
			sb.append("</BODY>");
			sb.append("<HTML>");
		}
		return sb.toString();
	}

}
