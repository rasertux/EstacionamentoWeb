<%@page import="model.UsuarioDao"%>
<%@page import="model.UsuarioBean"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Alterar Usuário</title>
<meta name='viewport' content='width=device-width, initial-scale=1'>
<meta name='author' content='David Martins, Rafael Sérgio' />
<link rel='stylesheet' href='css/bootstrap.min.css'>
<script src='js/jquery-1.11.3.min.js'></script>
<script src='js/bootstrap.min.js'></script>
</head>
<body class='container-fluid'>
	<div class='row'>
		<div class='col-sm-4'></div>
		<div class='col-sm-4'>
			<div class='panel panel-default' style='margin-top: 50px;'>
				<div class='panel-heading'>
					<div align="right">
						<form action="/EstacionamentoWeb/logout" method="post">
							<input type="submit" value="Logout" />
						</form>
					</div>
					<h1 class='text-center'>Alterar Usuário</h1>
					<a href='/EstacionamentoWeb/index.html'> Menu</a>
				</div>
				<div class='panel-body'>
					<%
						UsuarioBean usuariobanco = new UsuarioBean();
						if (request.getMethod().equalsIgnoreCase("GET")) {
							String login = request.getParameter("login");
							if (login != null && !login.isEmpty()) {
								usuariobanco = UsuarioDao.getUsuario(login);
							} else {
								response.sendRedirect(request.getContextPath() + "/listarusuarios.jsp");
							}
						} else {
							String vnome, vlogin, vsenha, vemail;
							vnome = request.getParameter("ednome");
							vlogin = request.getParameter("edlogin");
							vsenha = request.getParameter("edsenha");
							vemail = request.getParameter("edemail");

							UsuarioBean usuario = new UsuarioBean();
							usuario.setNome(vnome);
							usuario.setLogin(vlogin);
							usuario.setSenha(vsenha);
							usuario.setEmail(vemail);
							if (UsuarioDao.alterarUsuario(usuario)) {
								response.sendRedirect(request.getContextPath() + "/listarusuarios.jsp");
							} else {
								out.println("Erro na alteração!");
								out.println("<a href='/EstacionamentoWeb/listarusuarios.jsp'>Listar</a>");

							}
						}
					%>

					<form class='form-group' action="alterarusuario.jsp" method="post">
						<label>Nome: </label> <input class='form-control' type="text"
							size="30" name="ednome" required='required'
							value=<%out.println(usuariobanco.getNome());%>><br>
						<label>Login: </label> <input class='form-control' type="text"
							size="20" name="edlogin" readonly
							value=<%out.println(usuariobanco.getLogin());%>><br>
						<label>Senha: </label> <input class='form-control' type="password"
							size="20" name="edsenha" required='required'><br> <label>Email:
						</label> <input class='form-control' type="email" size="30" name="edemail"
							required='required'
							value=<%out.println(usuariobanco.getEmail());%>><br>
						<input class='btn btn-default' type="submit" value="Alterar">
						<hr>
						<a href='/EstacionamentoWeb/listarusuarios.jsp'>Listar
							Usuários</a>
					</form>
				</div>
				<div class='panel-footer'>
					<small>&copy <a href='https://github.com/DaveKun'
						target='_blank'>David Martins</a>, <a target='_blank'
						href='https://github.com/rasertux'>Rafael Sérgio</a></small>
				</div>
			</div>
		</div>
		<div class='col-sm-4'></div>
	</div>
</body>
</html>