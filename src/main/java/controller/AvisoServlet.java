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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mensagem, cor, voltar;
		mensagem = request.getParameter("msg");
		cor = request.getParameter("cor");
		voltar = "/EstacionamentoWeb/index.html";
		PrintWriter html = response.getWriter();
		html.print("<HTML> ");
		html.print("<HEAD><TITLE>Cadastro de Veículos</TITLE>");
		html.print("<meta name='viewport' content='width=device-width, initial-scale=1'>");
		html.print("<meta name='author' content='David Martins, Rafael Sérgio' />");
		html.print(
				"<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css'>");
		html.print("<script src='https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js'></script>");
		html.print("<script src='http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js'></script>");
		html.print("</HEAD>");
		html.print("<body class='container-fluid'>");
		html.print("<div class='row'>");
		html.print("<div class='col-sm-4'></div>");
		html.print("<div class='col-sm-4'>");
		html.print("<div class='panel panel-default' style='margin-top:50px;'>");
		html.print("<div class='panel-heading'><h1 class='text-center'>Cadastro de Veículo</h1>");
		html.print("<a href='/EstacionamentoWeb/index.html'> Menu</a></div>");
		html.print("<div class='panel-body'>");
		
		if(cor.equals("green")) {
			html.print("<div class='alert alert-success'>");
			html.print("<strong>Info! </strong>");
			html.print(mensagem);
			html.print("</div>");
		}
		else if(cor.equals("red")) {
			html.print("<div class='alert alert-danger'>");
			html.print("<strong>Ops! </strong>");
			html.print(mensagem);
			html.print("</div>");
		}
		html.print("<a href='"+voltar+"'>Voltar</a>");
		
		html.print("</div>");
		html.print(
				"<div class='panel-footer'><small>&copy <a href='https://github.com/DaveKun' target='_blank'>David Martins</a>, <a target='_blank' href='https://github.com/rasertux'>Rafael Sérgio</a></small></div>");
		html.print("</div>");
		html.print("</div>");
		html.print("</div>");
		html.print("<div class='col-sm-4'></div>");
		html.print("</BODY>");
		html.print("</HTML> ");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
