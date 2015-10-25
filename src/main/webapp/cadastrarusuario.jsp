<%@page import="model.UsuarioDao"%>
<%@page import="model.UsuarioBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Cadastro de Usuários</title>
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
					<h1 class='text-center'>Cadastro de Usuários</h1>
					<a href='/EstacionamentoWeb/index.html'> Menu</a>
				</div>
				<div class='panel-body'>
					<form role='form' class='form-group' action="cadastrarusuario.jsp"
						method="post">
						<label>Nome: </label> <input class='form-control' type="text"
							size="30" name="ednome"><br> <label>Login: </label>
						<input class='form-control' type="text" size="20" name="edlogin"><br>
						<label>Senha: </label> <input class='form-control' type="password"
							size="20" name="edsenha"><br> <label>Email:
						</label> <input class='form-control' type="email" size="30" name="edemail"><br>
						<input class='btn btn-default' type="submit" value="Cadastrar">
						<input class='btn btn-default' type="reset" value="Limpar">
						<hr>
					</form>
				</div>
				<div class='panel-footer'>
					<small>&copy <a href='https://github.com/DaveKun'
						target='_blank'>David Martins</a>, <a target='_blank'
						href='https://github.com/rasertux'>Rafael Sérgio</a></small>
				</div>
			</div>
		</div>
	</div>
	<div class='col-sm-4'></div>
</body>
</html>
<%
	if (request.getMethod().equalsIgnoreCase("POST")) {
		UsuarioBean usuario = new UsuarioBean();
		usuario.setNome(request.getParameter("ednome"));
		usuario.setLogin(request.getParameter("edlogin"));
		usuario.setSenha(request.getParameter("edsenha"));
		usuario.setEmail(request.getParameter("edemail"));

		if (UsuarioDao.inserirUsuario(usuario)) {
			response.sendRedirect("/EstacionamentoWeb/listarusuarios.jsp");
		} else {
			out.println("<span>Erro ao cadastrar</span>");
		}
	}
%>