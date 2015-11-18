package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.mail.SimpleEmail;

import model.UsuarioBean;
import model.UsuarioDao;

/**
 * Servlet implementation class RecuperaSenha
 */
@WebServlet("/recuperar")
public class RecuperaSenha extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RecuperaSenha() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String trocaform = request.getParameter("trocaform");
		if (trocaform != null) {
			StringBuilder sb = new StringBuilder();
			sb.append(
					"<form role='form' class='form-group' id='formrecupera' action='/EstacionamentoWeb/recuperar' method='post' >");
			sb.append("<label>Login: </label>");
			sb.append("<input class='form-control' type='text' name='login' size='20' required='required'><br>");
			sb.append("<label>Email: </label>");
			sb.append("<input class='form-control' type='email' size='30' name='email' required='required'><br>");
			sb.append("<input class='btn btn-default' type='submit' value='Recuperar'>");
			sb.append("</form>");

			response.getWriter().write(sb.toString());
			response.setStatus(200);
		} else {
			String login = request.getParameter("login");
			String email = request.getParameter("email");

			UsuarioBean usuario = new UsuarioBean();

			usuario = UsuarioDao.getUsuario(login);

			SimpleEmail enviaemail = new SimpleEmail();

			String status = null;

			String scheme = request.getScheme();
			String serverName = request.getServerName();
			int serverPort = request.getServerPort();
			String contextPath = request.getContextPath();
			String resultpath = scheme + "://" + serverName + ":" + serverPort + contextPath;

			if (usuario.getEmail().equalsIgnoreCase(email)) {
				try {
					enviaemail.setDebug(true);
					enviaemail.setHostName("smtp.gmail.com");
					enviaemail.setSmtpPort(587);
					enviaemail.setAuthentication("Seu Login Aqui, se for gmail tambem incluir @gmail.com", "Sua Senha Aqui");
					enviaemail.setStartTLSEnabled(true);
					enviaemail.addTo(usuario.getEmail());
					enviaemail.setFrom("Seu Email Aqui");
					enviaemail.setSubject("Recuperação de Senha - EstacionamentoWeb");
					enviaemail.setMsg("Para recuperar a sua senha clique no link a seguir: " + resultpath
							+ "/novasenha.jsp?hash=" + usuario.getSenha());
					enviaemail.send();
				} catch (Exception e) {
					System.out.println(e);
				}
				status = "Siga as orientações enviadas por email.";
				request.setAttribute("status", status);
				response.sendRedirect("/EstacionamentoWeb/login.jsp");
			} else {
				status = "Email invalido!";
				request.setAttribute("status", status);
				response.sendRedirect("/EstacionamentoWeb/login.jsp");
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
