package controller;

import java.io.IOException;
import java.util.Random;

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

			String scheme = request.getScheme();
			String serverName = request.getServerName();
			int serverPort = request.getServerPort();
			String contextPath = request.getContextPath();
			String resultpath = scheme + "://" + serverName + ":" + serverPort + contextPath;

			Random gerador = new Random();

			String aleatorio = "";

			for (int i = 1; i < 65; i++) {
				aleatorio += String.valueOf(gerador.nextInt(10));
			}

			usuario.setHashrecuperasenha(UsuarioDao.geraHashCriptografada(aleatorio));
			UsuarioDao.insereHashRecuperaSenha(usuario);

			SimpleEmail enviaemail = new SimpleEmail();

			String status = null;

			if ((usuario.getEmail() != null && usuario.getLogin() != null)
					&& (usuario.getEmail().equals(email) && usuario.getLogin().equals(login))) {
				try {
					enviaemail.setDebug(true);
					enviaemail.setHostName("smtp.gmail.com");
					enviaemail.setSmtpPort(587);
					enviaemail.setAuthentication("rasertux.test@gmail.com", "abc987654321");
					enviaemail.setStartTLSEnabled(true);
					enviaemail.addTo(usuario.getEmail());
					enviaemail.setFrom("rasertux.test@gmail.com");
					enviaemail.setSubject("Recuperação de Senha - EstacionamentoWeb");
					enviaemail.setMsg("Para recuperar a sua senha clique no link a seguir: " + resultpath
							+ "/novasenha.jsp?hash=" + usuario.getHashrecuperasenha());
					enviaemail.send();
				} catch (Exception e) {
					System.out.println(e);
				}
				status = "Siga as orientações enviadas por email.";
				response.sendRedirect("/EstacionamentoWeb/login.jsp?alert=info&status=" + status);
			} else {
				status = "Login ou Email inválido!";
				response.sendRedirect("/EstacionamentoWeb/login.jsp?alert=danger&status=" + status);
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
