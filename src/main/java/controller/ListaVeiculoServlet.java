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
 * Servlet implementation class ListaVeiculoServlet
 */
@WebServlet("/listarveiculo")
public class ListaVeiculoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private List<Object> itens = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ListaVeiculoServlet() {
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
		if (request.getParameter("edvalor") == null) {
			VeiculoDao dao = new VeiculoDao();
			itens = dao.getItens();
		}
		for (int i = 0; i < itens.size(); i++) {
			VeiculoBean veiculo = (VeiculoBean) itens.get(i);
			html.print(gerarLinha(veiculo, i, (i == 0), (i == itens.size() - 1)));
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		VeiculoDao dao = new VeiculoDao();
		String op;
		String vlr;
		op = request.getParameter("campo");
		vlr = request.getParameter("edvalor");

		switch (op) {
		case "placa":
			itens = dao.getItensPor(" where placa =\"" + vlr + "\"");
			break;
		case "marca":
			itens = dao.getItensPor(" where marca like '%" + vlr + "%'");
			break;
		case "modelo":
			itens = dao.getItensPor(" where modelo like '%" + vlr + "%'");
			break;
		case "idtarifa":
			itens = dao.getItensPor(" where idtarifa =\"" + vlr + "\"");
			break;
		default:
			itens = dao.getItens();
			break;
		}

		doGet(request, response);
	}

	private String gerarLinha(VeiculoBean veiculo, int vcor, boolean cabecalho, boolean rodape) {
		String urlremover = "/EstacionamentoWeb/remover?placa=" + veiculo.getPlaca();
		String urlalterar = "/EstacionamentoWeb/alterar?placa=" + veiculo.getPlaca();

		String imgremover = "<img src='remover.png' width='8%'>";
		String imgalterar = "<img src='alterar.png' width='8%'>";

		String remover = "<a href='" + urlremover + "'>" + imgremover + "</a>";
		String alterar = "<a href='" + urlalterar + "'>" + imgalterar + "</a>";

		String cor = (vcor % 2 == 0) ? "#6495ED" : "#483D8B";
		StringBuilder sb = new StringBuilder();
		
		List<Object> listaTarifas = null;
		TarifaDao daoTarifas = new TarifaDao();
		listaTarifas = daoTarifas.getItens();

		if (cabecalho) {
			sb.append("<html>");
			sb.append("<head>");
			sb.append("<meta charset=\"UTF-8\">");
			sb.append("<title>LISTA DE VEICULOS</title>");
			// javascript básico
			sb.append("<script type='text/javascript' src='http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js'></script>")
			.append("<script type='text/javascript'>")
			.append("$(document).ready(function(){")
			.append("	$('#tipo-pesquisa').change(function(){")
			.append("			if($(this).val() == 'idtarifa') {")
			.append("           	$('#pesquisa-padrao').hide();")
			.append("           	$('#valor-select').show();")
			.append("           	$('#pesquisa-padrao').removeAttr('name');")
			.append("           	$('#valor-select').attr('name', 'edvalor');")
			.append("           } else {")
			.append("           	$('#pesquisa-padrao').show();")
			.append("           	$('#valor-select').hide();")
			.append("           	$('#pesquisa-padrao').attr('name', 'edvalor');")
			.append("           	$('#valor-select').removeAttr('name');")
			.append("           }")
			.append("		});")
			.append("	});")
			.append("</script>");
			// fim do js básico
			sb.append("</head>");
			sb.append("<body>");
			sb.append("<H1>LISTAGEM DE VEICULOS</H1>");
			sb.append("<HR>");
			sb.append("<a href='/EstacionamentoWeb/index.html'> Menu</a>");
			sb.append("<form method='post' action='/EstacionamentoWeb/listarveiculo'>");
			sb.append("Pesquisar por:");
			sb.append("<select name='campo' id='tipo-pesquisa'>");
			sb.append("<option value='todos'>Todos</option>");
			sb.append("<option value='placa'>Placa</option>");
			sb.append("<option value='marca'>Marca</option>");
			sb.append("<option value='modelo'>Modelo</option>");
			sb.append("<option value='idtarifa'>Tarifa</option>");
			sb.append("</select>");
			sb.append("Valor da pesquisa:<input type='text' id='pesquisa-padrao' name='edvalor'/>");
			sb.append("<select style='display:none;' id='valor-select'>");
			// foreach criando options para cada tarifa
			for(Object objeto : listaTarifas) {
				TarifaBean tb = (TarifaBean) objeto;
				sb.append("<option value='")
				.append(tb.getIdtarifa())
				.append("'>")
				.append(tb.getDescricao())
				.append(" R$ ")
				.append(tb.getValor())
				.append("</option>");
			}
			// fim do foreach
			sb.append("</select>");
			sb.append("<input type='submit' value='Pesquisar'/>");
			sb.append("<input type='reset' value='Limpar'/>");
			sb.append("</form>");
			sb.append("<TABLE WIDTH='50%'>");
			sb.append("<TR bgcolor='#DCDCDC'>");
			sb.append("<TH>").append("Placa").append("</TH>");
			sb.append("<TH>").append("Marca").append("</TH>");
			sb.append("<TH>").append("Modelo").append("</TH>");
			sb.append("<TH>").append("Tarifa").append("</TH>");
			sb.append("<TH>").append("Ação").append("</TH>");
			sb.append("</TR>");
		}

		sb.append("<TR bgcolor='").append(cor).append("'>");
		sb.append("<TD>").append(veiculo.getPlaca()).append("</TD>");
		sb.append("<TD>").append(veiculo.getMarca()).append("</TD>");
		sb.append("<TD>").append(veiculo.getModelo()).append("</TD>");
		sb.append("<TD>").append(veiculo.getIdtarifa().getDescricao() + " R$" + veiculo.getIdtarifa().getValor())
				.append("</TD>");
		sb.append("<TD width=\"30%\" align=\"center\">").append(remover).append(" | ").append(alterar).append("</TD>");
		sb.append("</TR>");

		if (rodape) {
			sb.append("</TABLE>");
			sb.append("</HR>");
			sb.append("</body>");
			sb.append("<html>");
		}
		return sb.toString();
	}

}
