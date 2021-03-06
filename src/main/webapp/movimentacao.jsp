<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="customtag"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Movimentação</title>
<meta name='viewport' content='width=device-width, initial-scale=1'>
<meta name='author' content='David Martins, Rafael Sérgio' />
<link rel='stylesheet' href='css/bootstrap.min.css'>
<link rel='stylesheet' href='css/bootstrap-datetimepicker.min.css'>
<script src='js/jquery-1.11.3.min.js'></script>
<script type="text/javascript" src="js/moment-with-locales.min.js"></script>
<script src='js/bootstrap.min.js'></script>
<script type="text/javascript" src="js/bootstrap-datetimepicker.min.js"></script>
<script src="js/locales/bootstrap-datetimepicker.pt-BR.js"
	charset="UTF-8"></script>
</head>
<body class='container-fluid'>
	<div class='row'>
		<div class='col-sm-2'></div>
		<div class='col-sm-8'>
			<div class='panel panel-default' style='margin-top: 50px;'>
				<div class='panel-heading'>
					<div align="right">
						<form action="/EstacionamentoWeb/logout" method="post">
							<input type="submit" value="Logout" />
						</form>
					</div>
					<h1 class='text-center'>Movimentação</h1>
					<a href='/EstacionamentoWeb/index.html'> Menu</a>
				</div>
				<div class='panel-body'>
					<div id="usermessage"></div>
					<table class='table table-striped'>
						<tr>
							<th>IdMov</th>
							<th>Veículo</th>
							<th>Entrada</th>
							<th>Saída</th>
							<th>Fatura</th>
							<th>Ação</th>
						</tr>
						<form action="/EstacionamentoWeb/movimentacao" method="post">
							<tr>
								<td>---</td>
								<td><jsp:useBean id="dao" class="model.VeiculoDao" /> <select
									class='form-control' name='placa'>
										<c:forEach var="veiculo" items="${dao.getItens()}">
											<option value="${veiculo.getPlaca()}">${veiculo.getPlaca()}
												${veiculo.getMarca()} ${veiculo.getModelo()}</option>
										</c:forEach>
								</select></td>
								<td><customtag:campoData id="entrada" /><input
									type="submit" value="Incluir" /></td>
						</form>
						<td>---</td>
						<td>---</td>
						<td>---</td>
						</tr>
						<jsp:useBean id="movdao" class="model.MovimentacaoDao" />
						<c:forEach var="mov" items="${movdao.getItens()}">
							<tr id="mov${mov.getIdmov()}">
								<td>${mov.getIdmov()}</td>
								<td>${mov.getPlaca().getPlaca()}
									${mov.getPlaca().getMarca()} ${mov.getPlaca().getModelo()}</td>
								<td id="alterarentrada${mov.getIdmov()}"><fmt:formatDate
										value="${mov.getEntrada().time}" pattern="dd/MM/yyyy HH:mm:ss" /></td>
								<c:choose>
									<c:when test="${not empty mov.getSaida()}">
										<td id="alterarsaida${mov.getIdmov()}"><fmt:formatDate
												value="${mov.getSaida().time}" pattern="dd/MM/yyyy HH:mm:ss" /></td>
									</c:when>
									<c:otherwise>
										<td id="atualizasaida${mov.getIdmov()}"><customtag:campoData
												id="saida${mov.getIdmov()}" /> <a href="#"
											onclick="atualizaSaida(${mov.getIdmov()})">Gravar</a> <script
												type="text/javascript">
												function atualizaSaida(idmov) {
													var saida = $("#saida" + idmov).val();
												$.ajax({
												    type: "post", 
												    url: "/EstacionamentoWeb/movimentacao",
												    data: {'idmov' : idmov, 'saida' : saida},
												    success: function (resposta) {
												    	$("#usermessage").html("");
												    	$("#atualizasaida" + idmov).html(saida + ":00");
														$("#fatura"+idmov).html("R$" + resposta);
												    },
												    error: function (error) {
												    	$("#usermessage").html("<div class='alert alert-danger'><strong>Ops!</strong> Informe uma Data/Hora Saída posterior a Data/Hora Entrada!</div>");
												    }
												})
												};
											</script></td>
									</c:otherwise>
								</c:choose>
								<td id="fatura${mov.getIdmov()}">R$ ${mov.getFatura()}</td>
								<td><a href="#" onclick="remover(${mov.getIdmov()})"><img
										src="img/remover.png" width='15%'></a> | <a href="#"
									onclick="showalterar(${mov.getIdmov()})"><img
										src="img/alterar.png" width='15%'></a></td>
								<script type="text/javascript">
									function remover(idmov) {
									$.get("/EstacionamentoWeb/remover?remover=mov", {'idmov' : idmov}, function() {
									$("#mov" + idmov).remove();
										});
									}
								</script>
							</tr>
						</c:forEach>
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