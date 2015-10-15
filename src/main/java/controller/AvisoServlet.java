package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AvisoServlet
 */
@WebServlet("/aviso")
public class AvisoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AvisoServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String mensagem, cor, voltar, titulo, listar, cadastrar;
		mensagem = request.getParameter("msg");
		cor = request.getParameter("cor");
		voltar = request.getHeader("Referer");
		if (voltar.contains("veiculo")) {

			titulo = "Cadastro de Veículo";
			cadastrar = "/EstacionamentoWeb/cadastrarveiculo";
			listar = "/EstacionamentoWeb/listarveiculo";
		} else {
			titulo = "Cadastro de Tarifa";
			cadastrar = "/EstacionamentoWeb/cadastrotarifa.html";
			listar = "/EstacionamentoWeb/listar";
		}
		PrintWriter html = response.getWriter();
		html.print(geraHtml(mensagem, cor, titulo, listar, cadastrar));
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

	private String geraHtml(String mensagem, String cor, String titulo, String listar,
			String cadastrar) {
		StringBuilder sb = new StringBuilder();
		sb.append("<HTML> ");
		sb.append("<HEAD><TITLE>" + titulo + "</TITLE>");
		sb.append("<meta name='viewport' content='width=device-width, initial-scale=1'>");
		sb.append("<meta name='author' content='David Martins, Rafael Sérgio' />");
		sb.append("<link rel='stylesheet' href='css/bootstrap.min.css'>");
		sb.append("<script src='js/jquery-1.11.3.min.js'></script>");
		sb.append("<script src='js/bootstrap.min.js'></script>");
		sb.append("</HEAD>");
		sb.append("<body class='container-fluid'>");
		sb.append("<div class='row'>");
		sb.append("<div class='col-sm-4'></div>");
		sb.append("<div class='col-sm-4'>");
		sb.append("<div class='panel panel-default' style='margin-top:50px;'>");
		sb.append("<div class='panel-heading'><h1 class='text-center'>" + titulo + "</h1>");
		sb.append("<a href='/EstacionamentoWeb/index.html'> Menu</a></div>");
		sb.append("<div class='panel-body'>");

		if (cor.equals("green")) {
			sb.append("<div class='alert alert-success'>");
			sb.append("<strong>Info! </strong>");
			sb.append(mensagem);
			sb.append("</div>");
		} else if (cor.equals("red")) {
			sb.append("<div class='alert alert-danger'>");
			sb.append("<strong>Ops! </strong>");
			sb.append(mensagem);
			sb.append("</div>");
		}
		sb.append("<a href='" + cadastrar + "'>Cadastrar</a>");
		sb.append(" | ");
		sb.append("<a href='" + listar + "'>Listar</a>");

		sb.append("</div>");
		sb.append(
				"<div class='panel-footer'><small>&copy <a href='https://github.com/DaveKun' target='_blank'>David Martins</a>, <a target='_blank' href='https://github.com/rasertux'>Rafael Sérgio</a></small></div>");
		sb.append("</div>");
		sb.append("</div>");
		sb.append("</div>");
		sb.append("<div class='col-sm-4'></div>");
		sb.append("</BODY>");
		sb.append("</HTML> ");
		return sb.toString();
	}

}
