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
						String hash = request.getParameter("hash");
						if (hash == null || UsuarioDao.getUsuarioPorHash(hash).getSenha() == null) {
							response.sendRedirect("/EstacionamentoWeb/login.jsp");
						} else {
							if (request.getMethod().equalsIgnoreCase("POST")) {
								String novasenha = request.getParameter("novasenha");
								String renovasenha = request.getParameter("renovasenha");

								UsuarioBean usuario = new UsuarioBean();
								usuario = UsuarioDao.getUsuarioPorHash(hash);
								if (usuario.getSenha().equalsIgnoreCase(hash) && novasenha.equals(renovasenha)) {
									usuario.setSenha(novasenha);
									UsuarioDao.alterarUsuario(usuario);
									response.sendRedirect("/EstacionamentoWeb/login.jsp");
								}
							} else {
					%>
					<form role='form' class='form-group'
						action="/EstacionamentoWeb/novasenha.jsp" method="post">
						<input type="hidden" name="hash" value="<%out.print(hash);%>">
						<label>Nova Senha:</label> <input class='form-control'
							type="password" name="novasenha" size="20"><br> <label>Digite
							Novamente:</label> <input class='form-control' type="password"
							name="renovasenha" size="20"><br> <input
							class='btn btn-default' type="submit" value="Gravar">
					</form>
					<%
						}
						}
					%>
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