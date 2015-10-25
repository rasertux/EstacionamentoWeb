<%@page import="model.UsuarioDao"%>
<%@page import="model.UsuarioBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="author" content="David Martins, Rafael Sérgio" />
<link rel="stylesheet" href="css/bootstrap.min.css">
<script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
</head>
<body class="container-fluid">
	<div class="row">
		<div class="col-sm-3"></div>
		<div class="col-sm-6">
			<div class="panel panel-default" style="margin-top: 50px;">
				<div class="panel-heading text-center">
					<h1>Login</h1>
				</div>
				<div class="panel-body">
					<%
						if (request.getMethod().equalsIgnoreCase("POST")) {
							String login, senha;
							login = request.getParameter("edlogin");
							senha = request.getParameter("edsenha");
							if ((login == null) || (login.isEmpty()) || (senha == null) || (senha.isEmpty())) {
								out.println("<div class='alert alert-danger'><strong>Ops!</strong> Informe o usuário e a senha para acessar o sistema.</div>");
							} else {
								UsuarioBean logado = UsuarioDao.getUsuarioLogin(login, senha);
								if (logado == null) {
									out.println("<div class='alert alert-danger'><strong>Ops!</strong> Usuário e/ou senha inválidos!</div>");
								} else {
									response.sendRedirect("/EstacionamentoWeb/index.html");
								}
							}
						}
					%>
					<form role='form' class='form-group'
						action="/EstacionamentoWeb/login.jsp" method="post">
						<label>Login:</label> <input class='form-control' type="text"
							name="edlogin" size="20"><br> <label>Senha:</label>
						<input class='form-control' type="password" name="edsenha"
							size="20"><br> <a href="">Esqueceu a Senha?</a><br>
						<input class='btn btn-default' type="submit" value="Login">
					</form>
				</div>
				<div class="panel-footer">
					<small>&copy <a href="https://github.com/DaveKun"
						target="_blank">David Martins</a>, <a target="_blank"
						href="https://github.com/rasertux">Rafael Sérgio</a></small>
				</div>
			</div>
		</div>
		<div class="col-sm-3"></div>
	</div>
</body>
</html>