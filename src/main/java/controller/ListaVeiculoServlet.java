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
		if (itens.isEmpty()) {
			// não encontrou nenhum veiculo cadastrado
			html.print(gerarListaVazia());

		} else {
			for (int i = 0; i < itens.size(); i++) {
				VeiculoBean veiculo = (VeiculoBean) itens.get(i);
				html.print(gerarLinha(veiculo, (i == 0), (i == itens.size() - 1)));
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

	private String gerarLinha(VeiculoBean veiculo, boolean cabecalho, boolean rodape) {
		String urlremover = "/EstacionamentoWeb/remover?placa=" + veiculo.getPlaca();
		String urlalterar = "/EstacionamentoWeb/alterarveiculo?placa=" + veiculo.getPlaca();

		String imgremover = "<img src='/EstacionamentoWeb/img/remover.png' width='8%'>";
		String imgalterar = "<img src='/EstacionamentoWeb/img/alterar.png' width='8%'>";

		String remover = "<a href='" + urlremover + "'>" + imgremover + "</a>";
		String alterar = "<a href='" + urlalterar + "'>" + imgalterar + "</a>";

		StringBuilder sb = new StringBuilder();

		List<Object> listaTarifas = null;
		TarifaDao daoTarifas = new TarifaDao();
		listaTarifas = daoTarifas.getItens();

		if (cabecalho) {
			sb.append("<html>");
			sb.append("<head>");
			sb.append("<title>LISTA DE VEICULOS</title>");
			sb.append("<meta name='viewport' content='width=device-width, initial-scale=1'>");
			sb.append("<meta name='author' content='David Martins, Rafael Sérgio' />");
			sb.append("<link rel='stylesheet' href='css/bootstrap.min.css'>");
			sb.append("<script src='js/jquery-1.11.3.min.js'></script>");
			sb.append("<script src='js/bootstrap.min.js'></script>");
			// javascript básico
			sb.append("<script type='text/javascript'>").append("$(document).ready(function(){")
					.append("	$('#tipo-pesquisa').change(function(){")
					.append("			if($(this).val() == 'idtarifa') {")
					.append("           	$('#pesquisa-padrao').hide();")
					.append("           	$('#valor-select').show();")
					.append("           	$('#pesquisa-padrao').removeAttr('name');")
					.append("           	$('#valor-select').attr('name', 'edvalor');").append("           } else {")
					.append("           	$('#pesquisa-padrao').show();")
					.append("           	$('#valor-select').hide();")
					.append("           	$('#pesquisa-padrao').attr('name', 'edvalor');")
					.append("           	$('#valor-select').removeAttr('name');").append("           }")
					.append("		});").append("	});").append("</script>");
			// fim do js básico
			sb.append("</head>");
			sb.append("<body class='container-fluid'>");
			sb.append("<div class='row'>");
			sb.append("<div class='col-sm-2'></div>");
			sb.append("<div class='col-sm-8'>");
			sb.append("<div class='panel panel-default' style='margin-top:50px;'>");
			sb.append("<div class='panel-heading'>");
			sb.append(
					"<div align='right'><form action='/EstacionamentoWeb/logout' method='post'><input type='submit' value='Logout' />");
			sb.append("</form></div>");
			sb.append("<h1 class='text-center'>Listagem de Veículos</h1>");
			sb.append("<a href='/EstacionamentoWeb/index.html'> Menu</a></div>");
			sb.append("<div class='panel-body'>");
			sb.append("<form role='form' class='form-inline' method='post' action='/EstacionamentoWeb/listarveiculo'>");
			sb.append("<div class='form-group'><label>Pesquisar por:</label>");
			sb.append("<select class='form-control' name='campo' id='tipo-pesquisa'>");
			sb.append("<option value='todos'>Todos</option>");
			sb.append("<option value='placa'>Placa</option>");
			sb.append("<option value='marca'>Marca</option>");
			sb.append("<option value='modelo'>Modelo</option>");
			sb.append("<option value='idtarifa'>Tarifa</option>");
			sb.append("</select></div>");
			sb.append(
					"<div class='form-group'><label>Valor da pesquisa:</label><input class='form-control' type='text' id='pesquisa-padrao' name='edvalor'/>");
			sb.append("<select class='form-control' style='display:none;' id='valor-select'>");
			// foreach criando options para cada tarifa
			for (Object objeto : listaTarifas) {
				TarifaBean tb = (TarifaBean) objeto;
				sb.append("<option value='").append(tb.getIdtarifa()).append("'>").append(tb.getDescricao())
						.append(" R$ ").append(tb.getValor()).append("</option>");
			}
			// fim do foreach
			sb.append("</select></div>");
			sb.append("<input class='btn btn-default' type='submit' value='Pesquisar'/>");
			sb.append("<input class='btn btn-default' type='reset' value='Limpar'/>");
			sb.append("</form>");
			sb.append("<TABLE class='table table-striped'>");
			sb.append("<TR>");
			sb.append("<TH>").append("Placa").append("</TH>");
			sb.append("<TH>").append("Marca").append("</TH>");
			sb.append("<TH>").append("Modelo").append("</TH>");
			sb.append("<TH>").append("Tarifa").append("</TH>");
			sb.append("<TH>").append("Ação").append("</TH>");
			sb.append("</TR>");
		}

		sb.append("<TR>");
		sb.append("<TD>").append(veiculo.getPlaca()).append("</TD>");
		sb.append("<TD>").append(veiculo.getMarca()).append("</TD>");
		sb.append("<TD>").append(veiculo.getModelo()).append("</TD>");
		sb.append("<TD>").append(veiculo.getIdtarifa().getDescricao() + " R$" + veiculo.getIdtarifa().getValor())
				.append("</TD>");
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
			sb.append("</body>");
			sb.append("<html>");
		}
		return sb.toString();
	}

	private String gerarListaVazia() {
		List<Object> listaTarifas = null;
		TarifaDao daoTarifas = new TarifaDao();
		listaTarifas = daoTarifas.getItens();

		StringBuilder sb = new StringBuilder();

		sb.append("<html>");
		sb.append("<head>");
		sb.append("<title>Lista de veículos</title>");
		sb.append("<meta name='viewport' content='width=device-width, initial-scale=1'>");
		sb.append("<meta name='author' content='David Martins, Rafael Sérgio' />");
		sb.append(
				"<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css'>");
		sb.append("<script src='https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js'></script>");
		sb.append("<script src='http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js'></script>");
		// javascript básico
		sb.append("<script type='text/javascript'>").append("$(document).ready(function(){")
				.append("	$('#tipo-pesquisa').change(function(){")
				.append("			if($(this).val() == 'idtarifa') {")
				.append("           	$('#pesquisa-padrao').hide();").append("           	$('#valor-select').show();")
				.append("           	$('#pesquisa-padrao').removeAttr('name');")
				.append("           	$('#valor-select').attr('name', 'edvalor');").append("           } else {")
				.append("           	$('#pesquisa-padrao').show();").append("           	$('#valor-select').hide();")
				.append("           	$('#pesquisa-padrao').attr('name', 'edvalor');")
				.append("           	$('#valor-select').removeAttr('name');").append("           }")
				.append("		});").append("	});").append("</script>");
		// fim do js básico
		sb.append("</head>");
		sb.append("<body class='container-fluid'>");
		sb.append("<div class='row'>");
		sb.append("<div class='col-sm-2'></div>");
		sb.append("<div class='col-sm-8'>");
		sb.append("<div class='panel panel-default' style='margin-top:50px;'>");
		sb.append("<div class='panel-heading'>");
		sb.append(
				"<div align='right'><form action='/EstacionamentoWeb/logout' method='post'><input type='submit' value='Logout' />");
		sb.append("</form></div>");
		sb.append("<h1 class='text-center'>Listagem de Veículos</h1>");
		sb.append("<a href='/EstacionamentoWeb/index.html'> Menu</a></div>");
		sb.append("<div class='panel-body'>");
		sb.append("<form role='form' class='form-inline' method='post' action='/EstacionamentoWeb/listarveiculo'>");
		sb.append("<div class='form-group'><label>Pesquisar por:</label>");
		sb.append("<select class='form-control' name='campo' id='tipo-pesquisa'>");
		sb.append("<option value='todos'>Todos</option>");
		sb.append("<option value='placa'>Placa</option>");
		sb.append("<option value='marca'>Marca</option>");
		sb.append("<option value='modelo'>Modelo</option>");
		sb.append("<option value='idtarifa'>Tarifa</option>");
		sb.append("</select></div>");
		sb.append(
				"<div class='form-group'><label>Valor da pesquisa:</label><input class='form-control' type='text' id='pesquisa-padrao' name='edvalor'/>");
		sb.append("<select class='form-control' style='display:none;' id='valor-select'>");
		// foreach criando options para cada tarifa
		for (Object objeto : listaTarifas) {
			TarifaBean tb = (TarifaBean) objeto;
			sb.append("<option value='").append(tb.getIdtarifa()).append("'>").append(tb.getDescricao()).append(" R$ ")
					.append(tb.getValor()).append("</option>");
		}
		// fim do foreach
		sb.append("</select></div>");
		sb.append("<input class='btn btn-default' type='submit' value='Pesquisar'/>");
		sb.append("<input class='btn btn-default' type='reset' value='Limpar'/>");
		sb.append("</form>");
		sb.append("<TABLE class='table table-striped'>");
		sb.append("<TR>");
		sb.append("<TH>").append("Placa").append("</TH>");
		sb.append("<TH>").append("Marca").append("</TH>");
		sb.append("<TH>").append("Modelo").append("</TH>");
		sb.append("<TH>").append("Tarifa").append("</TH>");
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
		sb.append("</body>");
		sb.append("<html>");
		return sb.toString();
	}

}
