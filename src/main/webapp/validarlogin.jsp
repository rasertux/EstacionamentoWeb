<%@page import="model.UsuarioDao"%>
<%@page import="model.UsuarioBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
</head>
<body>
	<%
		String login, senha;
		login = request.getParameter("edlogin");
		senha = request.getParameter("edsenha");
		if ((login == null) || (login.isEmpty()) || (senha == null) || (senha.isEmpty())) {
			response.sendRedirect("/EstacionamentoWeb/login.html");
		} else {
			UsuarioBean logado = UsuarioDao.getUsuarioLogin(login, senha);
			if (logado == null) {
				out.println("<h1>Usuário Inválido</h1>");
				out.println("<a href='/EstacionamentoWeb/login.html'>Voltar para login</a>");
			} else {
				response.sendRedirect("/EstacionamentoWeb/index.html");
			}
		}
	%>
</body>
</html>