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
		html.print("<HEAD><TITLE> Estacionamento Web </TITLE></HEAD> ");
		html.print("<BODY bgcolor='"+cor+"' align='center'> ");
		html.print("<HR>");
		html.print("<H1>"+ mensagem+ "</H1>");
		html.print("<a href='"+voltar+"'>Voltar</a>");
		html.print("<HR> ");
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
