<%@page import="java.util.ArrayList"%>
<%@page import="model.UsuarioDao"%>
<%@page import="model.UsuarioBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Listagem de Usuários</title>
<meta name='viewport' content='width=device-width, initial-scale=1'>
<meta name='author' content='David Martins, Rafael Sérgio' />
<link rel='stylesheet' href='css/bootstrap.min.css'>
<script src='js/jquery-1.11.3.min.js'></script>
<script src='js/bootstrap.min.js'></script>
</head>
<body class='container-fluid'>
	<div class='row'>
		<div class='col-sm-2'></div>
		<div class='col-sm-8'>
			<div class='panel panel-default' style='margin-top: 50px;'>
				<div class='panel-heading'>
					<h1 class='text-center'>Listagem de Usuários</h1>
					<a href='/EstacionamentoWeb/index.html'> Menu</a>
				</div>
				<div class='panel-body'>
					<table class='table table-striped'>
						<tr>
							<th>Nome</th>
							<th>Login</th>
							<th>Email</th>
							<th>Ação</th>
						</tr>
						<%
							String urlremover = "/EstacionamentoWeb/removerusuario.jsp?login=";
							String urlalterar = "/EstacionamentoWeb/alterarusuario.jsp?login=";
							String imgremover = "<img src='remover.png' width='8%'>";
							String imgalterar = "<img src='alterar.png' width='8%'>";
							ArrayList<UsuarioBean> usuarios = new ArrayList<UsuarioBean>();
							usuarios = UsuarioDao.getItens();
							if (!usuarios.isEmpty()) {
								for (UsuarioBean usuario : usuarios) {
									out.println("<tr>");
									out.println("<td>" + usuario.getNome() + "</td>");
									out.println("<td>" + usuario.getLogin() + "</td>");
									out.println("<td>" + usuario.getEmail() + "</td>");
									out.println("<td><a href='" + urlremover + usuario.getLogin() + "'>" + imgremover
											+ "</a> | <a href='" + urlalterar + usuario.getLogin() + "'>" + imgalterar + "</a></td>");
									out.println("</tr>");
								}
							} else {
								out.println("<div class='alert alert-warning'><strong>Ops!</strong> Nenhum cadastro encontrado.</div>");
							}
						%>
					</table>
				</div>
				<div class='panel-footer'>
					<small>&copy <a href='https://github.com/DaveKun'
						target='_blank'>David Martins</a>, <a target='_blank'
						href='https://github.com/rasertux'>Rafael Sérgio</a></small>
				</div>
			</div>
		</div>
	</div>
	<div class='col-sm-2'></div>
</body>
</html>