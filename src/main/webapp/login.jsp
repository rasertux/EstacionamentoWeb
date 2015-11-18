<%@page import="model.UsuarioDao"%>
<%@page import="model.UsuarioBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="customtag"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="author" content="David Martins, Rafael Sérgio" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script type="text/javascript"
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</head>
<body class="container-fluid">
	<div class="row">
		<div class="col-sm-3"></div>
		<div class="col-sm-6">
			<div class="panel panel-default" style="margin-top: 50px;">
				<div class="panel-heading text-center">
					<h1 id="titulo">Login</h1>
				</div>
				<div id="formbody" class="panel-body">
					<%
						if (request.getMethod().equalsIgnoreCase("POST")) {
							String login, senha;
							login = request.getParameter("edlogin");
							senha = request.getParameter("edsenha");
							if ((login == null) || (login.isEmpty()) || (senha == null) || (senha.isEmpty())) {
								out.println(
										"<div class='alert alert-danger'><strong>Ops!</strong> Informe o usuário e a senha para acessar o sistema.</div>");
							} else {
								UsuarioBean logado = UsuarioDao.getUsuarioLogin(login, senha);
								if (logado == null) {
									out.println(
											"<div class='alert alert-danger'><strong>Ops!</strong> Usuário e/ou senha inválidos!</div>");
								} else {
									request.getSession().setAttribute("usuario", logado);
									response.sendRedirect("/EstacionamentoWeb/index.html");
								}
							}
						} else {
							String status = (String) request.getAttribute("status");
							if (status != null) {
								out.println("<div class='alert alert-danger'><strong>Ops!</strong>" + status + "</div>");
							}
						}
					%>
					<form role='form' class='form-group'
						action="/EstacionamentoWeb/login.jsp" method="post">
						<label>Login:</label> <input class='form-control' type="text"
							name="edlogin" size="20"><br> <label>Senha:</label>
						<input class='form-control' type="password" name="edsenha"
							size="20"><br> <a href="#"
							onclick="showformrecupera()">Esqueceu a Senha?</a><br> <input
							class='btn btn-default' type="submit" value="Login">
					</form>
					<script type="text/javascript">
						function showformrecupera() {
							var trocaform = true;
							$.post("/EstacionamentoWeb/recuperar", {
								'trocaform' : trocaform
							}, function(resposta) {
								$("#formbody").html(resposta);
								$("#titulo").html("Recuperar Senha");
							});
						}
					</script>
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