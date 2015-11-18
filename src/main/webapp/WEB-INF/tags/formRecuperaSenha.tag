<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="id" required="true"%>

<form id="${id}" action="/EstacionamentoWeb/recupera" class="form-group"
	method="post">
	<label>Login:</label> <input class='form-control' type="text"
		name="edlogin" size="20"> <label>Email: </label> <input
		class='form-control' type="email" size="30" name="edemail"
		required="required"><br> <input class='btn btn-default'
		type="submit" value="Recuperar">
</form>
